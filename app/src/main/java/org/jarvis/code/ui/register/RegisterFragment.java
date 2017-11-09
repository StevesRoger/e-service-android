package org.jarvis.code.ui.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jarvis.code.R;
import org.jarvis.code.adapter.ColorAdapter;
import org.jarvis.code.dagger.component.ActivityComponent;
import org.jarvis.code.model.read.Product;
import org.jarvis.code.model.write.Customer;
import org.jarvis.code.ui.base.AbstractFragment;
import org.jarvis.code.ui.custom.ImageCross;
import org.jarvis.code.ui.custom.JDatePicker;
import org.jarvis.code.util.ComponentFactory;
import org.jarvis.code.util.FileUtil;
import org.jarvis.code.util.Loggy;
import org.jarvis.code.util.Validator;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by KimChheng on 6/6/2017.
 */

public class RegisterFragment extends AbstractFragment implements RegisterView {

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

    @BindViews({R.id.imgDate, R.id.imgBack, R.id.imgMap, R.id.imgChoose})
    List<ImageButton> imageButtons;

    @BindViews({R.id.txtVillage, R.id.txtCommune, R.id.txtDistrict, R.id.txtHome, R.id.txtPhone, R.id.txtEmail, R.id.txtFacebook, R.id.txtOther})
    List<EditText> editTexts;

    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.img_gallery)
    LinearLayout gallery;
    @BindView(R.id.register_img_ad)
    ImageView imageAd;
    @BindView(R.id.colorSpinner)
    Spinner colorSpinner;
    @BindView(R.id.lblProductCode)
    TextView productCode;
    @BindView(R.id.txtProductQty)
    EditText productQty;
    @BindView(R.id.lblProductAmount)
    TextView productAmount;

    @Inject
    RegisterPresenter<RegisterView> presenter;
    @Inject
    JDatePicker jDatePicker;
    @Inject
    FragmentManager fragmentManager;
    @Inject
    Validator validator;
    @Inject
    SweetAlertDialog progressDialog;

    private ComponentFactory factory;
    private Uri uri;
    private File file;
    private Product product;

    private boolean isLoaded = false;
    private boolean isVisibleToUser;

    public RegisterFragment() {
        super();
    }

    public static RegisterFragment newInstance(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setArguments(bundle);
        return registerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = getArguments().getParcelable("product");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isVisibleToUser && (!isLoaded)) {
            //AnimateAD.animate(imageAd, MainActivity.advertisements, 0, true, getContext());
            isLoaded = true;
        }
        factory = new ComponentFactory(getContext(), view);
        editTexts.get(editTexts.size() - 1).setOnFocusChangeListener(this);
        productCode.setText(getResources().getString(R.string.string_code) + product.getCode());
        colorSpinner.setAdapter(new ColorAdapter(getContext(), product.getColors()));
        Loggy.i(RegisterFragment.class, colorSpinner.getSelectedItem().toString());
        /*txtDate.setOnClickListener(this);
        imageButtons.get(0).setOnClickListener(this);
        imageButtons.get(1).setOnClickListener(this);
        imageButtons.get(2).setOnClickListener(this);
        imageButtons.get(3).setOnClickListener(this);
        btnSubmit.setOnClickListener(this);*/
        requiredField();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isAdded()) {
            //AnimateAD.animate(imageAd, MainActivity.advertisements, 0, true, getContext());
            isLoaded = true;
        }
    }

    @OnClick({R.id.txtDate, R.id.imgDate, R.id.imgBack, R.id.imgMap, R.id.imgChoose, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgDate:
            case R.id.txtDate:
                showDialogDatePicker();
                break;
            case R.id.imgMap:
                toastMessage("Sorry for this feature will release on next version.");
                break;
            case R.id.imgChoose:
                browseImage();
                break;
            case R.id.imgBack:
                backToMainActivity();
                break;
            case R.id.btn_submit:
                try {
                    presenter.submitCustomer();
                } catch (Exception e) {
                    e.printStackTrace();
                    Loggy.e(RegisterFragment.class, e.getMessage());
                    showSweetAlert(SweetAlertDialog.ERROR_TYPE, e.getMessage().toString().trim(), validator.getMessage());
                    validator.clear();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public RequestBody requestJson() {
        Customer customer = new Customer();
        customer.setGroomName(txtGroomName.getText().toString().trim());
        customer.setGroomDadName(txtDadGroomName.getText().toString().trim());
        customer.setGroomMomName(txtMomGroomName.getText().toString().trim());
        customer.setBrideName(txtBrideName.getText().toString().trim());
        customer.setBrideDadName(txtDadBrideName.getText().toString().trim());
        customer.setBrideMomName(txtMomBrideName.getText().toString().trim());
        customer.setDate(txtDate.getText().toString().trim());
        StringBuilder address = new StringBuilder();
        address.append(editTexts.get(0).getText().toString().trim()).append(" ");
        address.append(editTexts.get(1).getText().toString().trim()).append(" ");
        address.append(editTexts.get(2).getText().toString().trim());
        customer.setAddress(address.toString());
        customer.setHome(editTexts.get(3).getText().toString().trim());
        customer.setPhone(editTexts.get(4).getText().toString().trim());
        customer.setEmail(editTexts.get(5).getText().toString().trim());
        customer.setFb(editTexts.get(6).getText().toString().trim());
        customer.setOther(editTexts.get(7).getText().toString().trim());
        customer.setProductId(product.getId());
        customer.setColor(colorSpinner.getSelectedItem().toString());
        String json = new Gson().toJson(customer);
        Loggy.i(RegisterPresenterImpl.class, json);
        return RequestBody.create(MediaType.parse("text/plain"), json);
    }

    @Override
    public MultipartBody.Part[] requestFiles() {
        MultipartBody.Part[] requestFiles = new MultipartBody.Part[gallery.getChildCount()];
        for (int i = 0; i < gallery.getChildCount(); i++) {
            ImageCross imageCross = (ImageCross) gallery.getChildAt(i);
            File file = imageCross.getFile();
            RequestBody requestBody = RequestBody.create(MediaType.parse(imageCross.getType()), file);
            requestFiles[i] = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
        }
        return requestFiles;
    }

    @Override
    public void requiredField() {
        Map<Integer, TextView> controls = new HashMap<>();
        controls.put(R.string.string_groom_name, factory.build(TextView.class, R.id.lblGroomName));
        controls.put(R.string.string_dad_groom_name, factory.build(TextView.class, R.id.lblDadGroomName));
        controls.put(R.string.string_mom_groom_name, factory.build(TextView.class, R.id.lblMomGroomName));
        controls.put(R.string.string_bride_name, factory.build(TextView.class, R.id.lblBrideName));
        controls.put(R.string.string_dad_bride_name, factory.build(TextView.class, R.id.lblDadBrideName));
        controls.put(R.string.string_mom_bride_name, factory.build(TextView.class, R.id.lblMomBrideName));
        controls.put(R.string.string_wedding_date, factory.build(TextView.class, R.id.lblWeddingDate));
        controls.put(R.string.string_wedding_address, factory.build(TextView.class, R.id.lblAddress));
        controls.put(R.string.string_phone, factory.build(TextView.class, R.id.lblPhone));
        //controls.put(R.string.string_email, factory.build(TextView.class, R.id.lblEmail));
        //controls.put(R.string.string_facebook, factory.build(TextView.class, R.id.lblFacebook));
        validator.setRequired(controls);
    }

    @Override
    public void validate() throws Exception {
        validator.requiredTextField(txtGroomName);
        validator.requiredTextField(txtDadGroomName);
        validator.requiredTextField(txtMomGroomName);
        validator.requiredTextField(txtBrideName);
        validator.requiredTextField(txtDadBrideName);
        validator.requiredTextField(txtMomBrideName);
        validator.isEmptyTextField(txtDate, getString(R.string.string_wedding_date));
        validator.isEmptyTextField(editTexts.get(0));
        validator.isEmptyTextField(editTexts.get(1));
        validator.isEmptyTextField(editTexts.get(2));
        validator.isEmptyTextField(editTexts.get(3));
        //validator.isEmptyTextField(txtEmail);
        //validator.isEmptyTextField(txtFb);
        if (!validator.isValid())
            throw new Exception(getString(R.string.string_problem));
    }

    @Override
    public void browseImage() {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");

        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = FileUtil.createImageFile();
        if (file != null) {
            uri = Uri.fromFile(file);
            if (uri != null)
                takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        Intent chooserIntent = Intent.createChooser(gallery, "Choose an option");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhoto});
        startActivityForResult(chooserIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            try {
                Bitmap bitmap;
                if (data == null || data.getData() == null) {
                    if (file != null && uri != null)
                        FileUtil.reduceImageSize(getContext(), uri, file);
                } else {
                    uri = data.getData();
                    file = new File(FileUtil.getRealPathFromURI(getContext(), uri));
                    if (FileUtil.isMB(file)) {
                        file = FileUtil.createImageFile();
                        FileUtil.reduceImageSize(getContext(), uri, file);
                    }
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uri), null, options);
                ImageCross imageCross = new ImageCross(getContext());
                imageCross.setPhoto(bitmap);
                imageCross.setFile(file);
                imageCross.setType(FileUtil.getMimeType(file));
                gallery.addView(imageCross);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("====>> RegisterFragment", e.getMessage());
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus)
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void showDialogDatePicker() {
        jDatePicker.show(fragmentManager, "datePicker");
        jDatePicker.setCancelable(false);
    }

    @Override
    public void backToMainActivity() {
        fragmentManager.popBackStack();
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.setTitleText(getString(R.string.string_submitting));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismissWithAnimation();
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

}
