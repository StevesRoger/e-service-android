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

public class CeremonyForm extends RegisterFragment {

    @BindView(R.id.txtNameBody)
    EditText txtNameBody;
    @BindView(R.id.txtNameStarter)
    EditText txtNameStater;
    @BindView(R.id.txtCeremonyStep)
    EditText txtStep;
    @BindView(R.id.txtCeremonyPlace)
    EditText txtPlace;

    @Inject
    RegisterPresenter<RegisterView> presenter;

    public CeremonyForm() {
        super();
    }

    public static CeremonyForm newInstance(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        CeremonyForm fragment = new CeremonyForm();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ceremony, container, false);
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
        colorSpinner.setAdapter(new ColorAdapter(getContext(), product.getColors()));
        setRequiredField();
    }

    @Override
    public RequestBody createCustomerJson() {
        Entities entities = new Entities();
        entities.setType(product.getType());
        entities.putValue("DEAD_NAME", txtNameBody.getText().toString().trim());
        entities.putValue("STARTER_NAME", txtNameStater.getText().toString().trim());
        entities.putValue("PLACE", txtPlace.getText().toString().trim());
        entities.putValue("PHASE", txtStep.getText().toString().trim());
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
        validator.requiredTextField(txtNameBody);
        validator.requiredTextField(txtNameStater);
        validator.isEmptyTextField(txtPlace);
        validator.isEmptyTextField(txtStep);
        validator.isEmptyTextField(txtPhone);
        if (!validator.isValid())
            throw new Exception(getString(R.string.invalid_information));
    }

    @Override
    public void setRequiredField() {
        Map<Integer, TextView> controls = new HashMap<>();
        controls.put(R.string.name_of_body, factory.build(TextView.class, R.id.lblNameBody));
        controls.put(R.string.name_of_starter, factory.build(TextView.class, R.id.lblNameStarter));
        controls.put(R.string.place, factory.build(TextView.class, R.id.lblCeremonyPlace));
        controls.put(R.string.ceremony_step, factory.build(TextView.class, R.id.lblCeremonyStep));
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
