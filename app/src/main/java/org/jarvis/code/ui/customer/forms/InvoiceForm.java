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
import org.jarvis.code.model.Entities;
import org.jarvis.code.model.Product;
import org.jarvis.code.ui.customer.RegisterFragment;
import org.jarvis.code.ui.customer.RegisterPresenter;
import org.jarvis.code.ui.customer.RegisterPresenterImpl;
import org.jarvis.code.ui.customer.RegisterView;
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
 * Created by KimChheng on 12/31/2017.
 */

public class InvoiceForm extends RegisterFragment {

    @BindView(R.id.txtCompany)
    EditText txtCompanyName;
    @BindView(R.id.txtCustomer)
    EditText txtCustomerName;
    @BindView(R.id.txtAddress)
    EditText txtAddress;
    @BindView(R.id.txtTime)
    EditText txtTime;

    @Inject
    RegisterPresenter<RegisterView> presenter;

    public InvoiceForm() {
        super();
    }

    public static InvoiceForm newInstance(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        InvoiceForm fragment = new InvoiceForm();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
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
        lblProductCode.setText(getResources().getString(R.string.code) + product.getCode());
        new JTimePicker(txtTime, getContext());
        colorSpinner.setAdapter(new ColorAdapter(getContext(), product.getColors()));
        setRequiredField();
    }

    @Override
    public RequestBody createCustomerJson() {
        Entities entities = new Entities();
        entities.setType(product.getType());
        entities.putValue("COMPANY_NAME", txtCompanyName.getText().toString().trim());
        entities.putValue("CUSTOMER_NAME", txtCustomerName.getText().toString().trim());
        entities.putValue("ADDRESS", txtAddress.getText().toString().trim());
        entities.putValue("TIME", txtTime.getText().toString().trim());
        entities.putValue("PRO_ID", product.getId());
        entities.putValue("QTY", new Integer(txtProductQty.getText().toString().trim()));
        entities.putValue("COLOR", colorSpinner.getSelectedItem().toString());
        entities.putValue("PHONE", txtPhone.getText().toString().trim());
        entities.putValue("EMAIL", txtEmail.getText().toString().trim());
        entities.putValue("FACEBOOK", txtFacebook.getText().toString().trim());
        entities.putValue("OTHER", txtOther.getText().toString().trim());
        String json = new Gson().toJson(entities);
        Loggy.i(RegisterPresenterImpl.class, json);
        return RequestBody.create(MediaType.parse("text/plain"), json);
    }

    @Override
    public void validate() throws Exception {
        validator.requiredTextField(txtCompanyName);
        validator.requiredTextField(txtCustomerName);
        validator.isEmptyTextField(txtAddress);
        validator.isEmptyTextField(txtTime);
        validator.isEmptyTextField(txtPhone);
        if (!validator.isValid())
            throw new Exception(getString(R.string.invalid_information));
    }

    @Override
    public void setRequiredField() {
        Map<Integer, TextView> controls = new HashMap<>();
        controls.put(R.string.name_of_company, factory.build(TextView.class, R.id.lblCompany));
        controls.put(R.string.name_of_customer, factory.build(TextView.class, R.id.lblCustomer));
        controls.put(R.string.wedding_address, factory.build(TextView.class, R.id.lblAddress));
        controls.put(R.string.time, factory.build(TextView.class, R.id.lblTime));
        controls.put(R.string.phone, factory.build(TextView.class, R.id.lblPhone));
        validator.setRequired(controls);
    }

    @Override
    public void submitCustomer() {
        try {
            presenter.submitCustomer(Constants.ENTITIES);
        } catch (Exception e) {
            e.printStackTrace();
            Loggy.e(RegisterFragment.class, e.getMessage());
            showSweetAlert(SweetAlertDialog.ERROR_TYPE, e.getMessage().toString().trim(), validator.getMessage());
            validator.clear();
        }
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
