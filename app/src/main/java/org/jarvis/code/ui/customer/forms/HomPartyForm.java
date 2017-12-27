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
 * Created by KimChheng on 12/27/2017.
 */

public class HomPartyForm extends RegisterFragment {

    @BindView(R.id.txtOwner)
    EditText txtOwner;
    @BindView(R.id.txtPlace)
    EditText txtPlace;
    @BindView(R.id.txtTime)
    EditText txtTime;

    @Inject
    RegisterPresenter<RegisterView> presenter;

    public HomPartyForm() {
        super();
    }

    public static HomPartyForm newInstance(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        HomPartyForm fragment = new HomPartyForm();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_party, container, false);
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
    public RequestBody createCustomerJson() {
        Entities entities = new Entities();
        entities.setType(product.getType());
        entities.putValue("OWNER", txtOwner.getText().toString().trim());
        entities.putValue("PLACE", txtPlace.getText().toString().trim());
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
        validator.requiredTextField(txtOwner);
        validator.requiredTextField(txtPlace);
        validator.isEmptyTextField(txtTime);
        validator.isEmptyTextField(txtPhone);
        if (!validator.isValid())
            throw new Exception(getString(R.string.invalid_information));
    }

    @Override
    public void setRequiredField() {
        Map<Integer, TextView> controls = new HashMap<>();
        controls.put(R.string.name_of_house_party, factory.build(TextView.class, R.id.lblOwner));
        controls.put(R.string.place, factory.build(TextView.class, R.id.lblPlace));
        controls.put(R.string.time, factory.build(TextView.class, R.id.lblTime));
        controls.put(R.string.phone, factory.build(TextView.class, R.id.lblPhone));
        validator.setRequired(controls);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
