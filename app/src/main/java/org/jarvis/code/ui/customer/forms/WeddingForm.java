package org.jarvis.code.ui.customer.forms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jarvis.code.R;
import org.jarvis.code.adapter.ColorAdapter;
import org.jarvis.code.model.Customer;
import org.jarvis.code.model.Product;
import org.jarvis.code.ui.customer.RegisterFragment;
import org.jarvis.code.ui.customer.RegisterPresenter;
import org.jarvis.code.ui.customer.RegisterPresenterImpl;
import org.jarvis.code.ui.customer.RegisterView;
import org.jarvis.code.ui.widget.JDatePicker;
import org.jarvis.code.ui.widget.JTimePicker;
import org.jarvis.code.util.ComponentFactory;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by KimChheng on 12/27/2017.
 */

public class WeddingForm extends RegisterFragment {

    @BindView(R.id.txtGroomName)
    EditText txtGroomName;
    @BindView(R.id.txtDadGroomName)
    EditText txtDadGroomName;
    @BindView(R.id.txtMomGroomName)
    EditText txtMomGroomName;
    @BindView(R.id.txtBrideName)
    EditText txtBrideName;
    @BindView(R.id.txtDadBrideName)
    EditText txtDadBrideName;
    @BindView(R.id.txtMomBrideName)
    EditText txtMomBrideName;
    @BindView(R.id.txtDate)
    EditText txtDate;
    @BindView(R.id.txtDateKh)
    EditText txtDateKh;
    @BindView(R.id.txtTimeEat)
    EditText txtTimeEat;
    @BindView(R.id.txtAddress)
    EditText txtAddress;
    @BindView(R.id.txtPlaceEat)
    EditText txtPlaceEat;

    @Inject
    RegisterPresenter<RegisterView> presenter;

    public WeddingForm() {
        super();
    }

    public static WeddingForm newInstance(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        WeddingForm fragment = new WeddingForm();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wedding, container, false);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this, view));
        presenter.onAttach(this);
        onCreateHeaderView(view);
        onCreateFooterView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        factory = new ComponentFactory(getContext(), view);
        txtOther.setOnFocusChangeListener(this);
        new JTimePicker(txtTimeEat, getContext());
        new JDatePicker(txtDate, getContext());
        lblProductCode.setText(getResources().getString(R.string.code) + product.getCode());
        colorSpinner.setAdapter(new ColorAdapter(getContext(), product.getColors()));
        setRequiredField();
    }

    @Override
    public void submitCustomer() {
        try {
            presenter.submitCustomer(Constants.CUSTOMER);
        } catch (Exception e) {
            e.printStackTrace();
            Loggy.e(RegisterFragment.class, e.getMessage());
            showSweetAlert(SweetAlertDialog.ERROR_TYPE, e.getMessage().toString().trim(), validator.getMessage());
            validator.clear();
        }
    }

    @Override
    public RequestBody createCustomerJson() {
        Customer customer = new Customer();
        customer.setGroomName(txtGroomName.getText().toString().trim());
        customer.setGroomDadName(txtDadGroomName.getText().toString().trim());
        customer.setGroomMomName(txtMomGroomName.getText().toString().trim());
        customer.setBrideName(txtBrideName.getText().toString().trim());
        customer.setBrideDadName(txtDadBrideName.getText().toString().trim());
        customer.setBrideMomName(txtMomBrideName.getText().toString().trim());
        customer.setDate(txtDate.getText().toString().trim());
        customer.setKhdate(txtDateKh.getText().toString().trim());
        customer.setTime(txtTimeEat.getText().toString().trim());
        customer.setAddress(txtAddress.getText().toString().trim());
        customer.setPlaceEat(txtPlaceEat.getText().toString().trim());
        customer.setPhone(txtPhone.getText().toString().trim());
        customer.setEmail(txtEmail.getText().toString().trim());
        customer.setFb(txtFacebook.getText().toString().trim());
        customer.setOther(txtOther.getText().toString().trim());
        customer.setProductId(product.getId());
        customer.setColor(colorSpinner.getSelectedItem().toString());
        customer.setQty(new Integer(txtProductQty.getText().toString().trim()));
        String json = new Gson().toJson(customer);
        Loggy.i(RegisterPresenterImpl.class, json);
        return RequestBody.create(MediaType.parse("text/plain"), json);
    }

    @Override
    public void validate() throws Exception {
        validator.requiredTextField(txtGroomName);
        validator.requiredTextField(txtDadGroomName);
        validator.requiredTextField(txtMomGroomName);
        validator.requiredTextField(txtBrideName);
        validator.requiredTextField(txtDadBrideName);
        validator.requiredTextField(txtMomBrideName);
        validator.isEmptyTextField(txtDate, getString(R.string.wedding_date));
        validator.isEmptyTextField(txtAddress);
        validator.isEmptyTextField(txtPlaceEat);
        validator.isEmptyTextField(txtPhone);
        validator.isEmptyTextField(txtProductQty, getString(R.string.quantity));
        validator.isEmptyTextField(txtDateKh, getString(R.string.wedding_date_khmer));
        validator.isEmptyTextField(txtTimeEat);
        //validator.isEmptyTextField(txtEmail);
        //validator.isEmptyTextField(txtFb);
        if (!validator.isValid())
            throw new Exception(getString(R.string.invalid_information));
    }

    @Override
    public void setRequiredField() {
        Map<Integer, TextView> controls = new HashMap<>();
        controls.put(R.string.groom_name, factory.build(TextView.class, R.id.lblGroomName));
        controls.put(R.string.dad_groom_name, factory.build(TextView.class, R.id.lblDadGroomName));
        controls.put(R.string.mom_groom_name, factory.build(TextView.class, R.id.lblMomGroomName));
        controls.put(R.string.bride_name, factory.build(TextView.class, R.id.lblBrideName));
        controls.put(R.string.dad_bride_name, factory.build(TextView.class, R.id.lblDadBrideName));
        controls.put(R.string.mom_bride_name, factory.build(TextView.class, R.id.lblMomBrideName));
        controls.put(R.string.wedding_date, factory.build(TextView.class, R.id.lblWeddingDate));
        controls.put(R.string.wedding_address, factory.build(TextView.class, R.id.lblAddress));
        controls.put(R.string.phone, factory.build(TextView.class, R.id.lblPhone));
        controls.put(R.string.quantity, factory.build(TextView.class, R.id.lblProductQty));
        controls.put(R.string.wedding_date_khmer, factory.build(TextView.class, R.id.lblWeddingDateKh));
        controls.put(R.string.time_eat, factory.build(TextView.class, R.id.lblTimeEat));
        controls.put(R.string.place_eat, factory.build(TextView.class, R.id.lblPlaceEat));
        //controls.put(R.string.string_email, factory.build(TextView.class, R.id.lblEmail));
        //controls.put(R.string.string_facebook, factory.build(TextView.class, R.id.lblFacebook));
        validator.setRequired(controls);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

}
