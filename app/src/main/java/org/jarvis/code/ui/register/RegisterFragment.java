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
import org.jarvis.code.model.Customer;
import org.jarvis.code.model.Product;
import org.jarvis.code.ui.base.AbstractFragment;
import org.jarvis.code.ui.widget.ImageCross;
import org.jarvis.code.ui.widget.JDatePicker;
import org.jarvis.code.ui.widget.JTimePicker;
import org.jarvis.code.util.Animator;
import org.jarvis.code.util.ComponentFactory;
import org.jarvis.code.util.Constants;
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
import butterknife.OnTextChanged;
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
    @BindView(R.id.txtDateKh)
    EditText txtDateKh;
    @BindView(R.id.txtTimeEat)
    EditText txtTimeEat;
    @BindView(R.id.txtProductQty)
    EditText txtProductQty;

    @BindViews({R.id.imgBack, R.id.imgMap, R.id.imgChoose})
    List<ImageButton> imageButtons;

    @BindViews({R.id.txtAddress, R.id.txtPlaceEat, R.id.txtPhone, R.id.txtEmail, R.id.txtFacebook, R.id.txtOther})
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
    TextView txtProductCode;
    @BindView(R.id.lblProductAmount)
    TextView txtProductAmount;

    @Inject
    RegisterPresenter<RegisterView> presenter;
    @Inject
    FragmentManager fragmentManager;
    @Inject
    Validator validator;

    private SweetAlertDialog progressDialog;
    private ComponentFactory factory;
    private Uri uri;
    private File file;
    private Product product;

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
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this, view));
        presenter.onAttach(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Animator(imageAd, Constants.advertisement, getContext()).animateAD(0, true);
        factory = new ComponentFactory(getContext(), view);
        editTexts.get(editTexts.size() - 1).setOnFocusChangeListener(this);
        new JTimePicker(txtTimeEat, getContext());
        new JDatePicker(txtDate, getContext());
        txtProductCode.setText(getResources().getString(R.string.code) + product.getCode());
        colorSpinner.setAdapter(new ColorAdapter(getContext(), product.getColors()));
        setRequiredField();
    }

    @OnClick({R.id.txtDate, R.id.imgBack, R.id.imgMap, R.id.imgChoose, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgMap:
                showMessage("Sorry for this feature will release on next version.", Toast.LENGTH_SHORT);
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
        customer.setAddress(editTexts.get(0).getText().toString().trim());
        customer.setPlaceEat(editTexts.get(1).getText().toString().trim());
        customer.setPhone(editTexts.get(2).getText().toString().trim());
        customer.setEmail(editTexts.get(3).getText().toString().trim());
        customer.setFb(editTexts.get(4).getText().toString().trim());
        customer.setOther(editTexts.get(5).getText().toString().trim());
        customer.setProductId(product.getId());
        customer.setColor(colorSpinner.getSelectedItem().toString());
        customer.setQty(new Integer(txtProductQty.getText().toString().trim()));
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
    public void validate() throws Exception {
        validator.requiredTextField(txtGroomName);
        validator.requiredTextField(txtDadGroomName);
        validator.requiredTextField(txtMomGroomName);
        validator.requiredTextField(txtBrideName);
        validator.requiredTextField(txtDadBrideName);
        validator.requiredTextField(txtMomBrideName);
        validator.isEmptyTextField(txtDate, getString(R.string.wedding_date));
        validator.isEmptyTextField(editTexts.get(0));
        validator.isEmptyTextField(editTexts.get(1));
        validator.isEmptyTextField(editTexts.get(2));
        validator.isEmptyTextField(editTexts.get(3));
        validator.isEmptyTextField(txtProductQty, getString(R.string.quantity));
        validator.isEmptyTextField(txtDateKh, getString(R.string.wedding_date_khmer));
        validator.isEmptyTextField(txtTimeEat);
        validator.isEmptyTextField(editTexts.get(1));
        //validator.isEmptyTextField(txtEmail);
        //validator.isEmptyTextField(txtFb);
        if (!validator.isValid())
            throw new Exception(getString(R.string.invalid_information));
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
    public void backToMainActivity() {
        fragmentManager.popBackStack();
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.setTitleText(getString(R.string.submitting));
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


    @OnTextChanged(R.id.txtProductQty)
    void calculateAmount(CharSequence text, int start, int count, int after) {
        try {
            long amount = 0;
            int qty = 0;
            if (text != null && !text.toString().isEmpty()) {
                //qty = Integer.parseInt(text.toString().trim());
                //amount = qty * 7;
            }
            txtProductAmount.setText(getString(R.string.price) + "$00");
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Invalid input!", Toast.LENGTH_SHORT);
            txtProductQty.setText("");
        }
    }
}
