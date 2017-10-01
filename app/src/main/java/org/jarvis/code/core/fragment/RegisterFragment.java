package org.jarvis.code.core.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jarvis.code.R;
import org.jarvis.code.network.RequestClient;
import org.jarvis.code.core.control.JDatePicker;
import org.jarvis.code.core.control.ImageCross;
import org.jarvis.code.core.model.read.Product;
import org.jarvis.code.core.model.read.ResponseEntity;
import org.jarvis.code.core.model.write.Customer;
import org.jarvis.code.util.ComponentFactory;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.FileUtil;
import org.jarvis.code.util.Loggy;
import org.jarvis.code.util.RequestFactory;
import org.jarvis.code.util.Validator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KimChheng on 6/6/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, Callback<ResponseEntity<Map<String, Object>>> {

    private EditText txtGroomName;
    private EditText txtDadGroomName;
    private EditText txtMomGroomName;
    private EditText txtBrideName;
    private EditText txtDadBrideName;
    private EditText txtMomBrideName;
    private EditText txtDate;

    private ImageButton datePicker, imgBack, imgMap, imgChoose;

    private EditText txtVillage, txtCommune, txtDistrict;
    private EditText txtHome, txtPhone;
    private EditText txtEmail, txtFb, txtOther;

    private Button btnSubmit;
    private LinearLayout gallery;
    private RequestClient requestService;
    private JDatePicker dialogFragment;
    private ImageView imageAd;
    private SweetAlertDialog progressDialog;

    private Validator validator;
    private ComponentFactory factory;
    private Uri uri;
    private File file;

    public RegisterFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestService = RequestFactory.build(RequestClient.class);
        dialogFragment = new JDatePicker();
        validator = new Validator(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        factory = new ComponentFactory(getContext(), view);

        txtGroomName = (EditText) view.findViewById(R.id.txtGroomName);
        txtDadGroomName = (EditText) view.findViewById(R.id.txtDadGroomName);
        txtMomGroomName = (EditText) view.findViewById(R.id.txtMomGroomName);
        txtBrideName = (EditText) view.findViewById(R.id.txtBrideName);
        txtDadBrideName = (EditText) view.findViewById(R.id.txtDadBrideName);
        txtMomBrideName = (EditText) view.findViewById(R.id.txtMomBrideName);
        txtDate = (EditText) view.findViewById(R.id.txtDate);
        txtDate.setOnClickListener(this);
        datePicker = (ImageButton) view.findViewById(R.id.imgDate);
        datePicker.setOnClickListener(this);
        txtVillage = (EditText) view.findViewById(R.id.txtVillage);
        txtCommune = (EditText) view.findViewById(R.id.txtCommune);
        txtDistrict = (EditText) view.findViewById(R.id.txtDistrict);
        txtHome = (EditText) view.findViewById(R.id.txtHome);
        txtPhone = (EditText) view.findViewById(R.id.txtPhone);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtFb = (EditText) view.findViewById(R.id.txtFacebook);
        txtOther = (EditText) view.findViewById(R.id.txtOther);
        txtOther.setOnFocusChangeListener(this);
        imgBack = (ImageButton) view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        imgMap = (ImageButton) view.findViewById(R.id.imgMap);
        imgMap.setOnClickListener(this);
        imgChoose = (ImageButton) view.findViewById(R.id.imgChoose);
        imgChoose.setOnClickListener(this);
        btnSubmit = (Button) view.findViewById(R.id.imgSubmit);
        btnSubmit.setOnClickListener(this);
        gallery = (LinearLayout) view.findViewById(R.id.imgGallery);
        imageAd = (ImageView) view.findViewById(R.id.registerImgAd);

        requiredField();
        //AnimateAD.animate(imageAd, MainActivity.advertisements, 0, true, getContext());

        return view;
    }

    private void openDialogDate() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
        dialogFragment.show(appCompatActivity.getSupportFragmentManager(), "datePicker");
        dialogFragment.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgDate:
                openDialogDate();
                break;
            case R.id.txtDate:
                openDialogDate();
                break;
            case R.id.imgMap:
                Toast.makeText(getContext(), "Sorry for this feature will release on next version.", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(getActivity(), MapsActivity.class);
                //startActivity(intent);
                break;
            case R.id.imgChoose:
                browseImage();
                break;
            case R.id.imgBack:
                getActivity().getSupportFragmentManager().popBackStack();
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.imgSubmit:
                try {
                    validate();
                    submitCustomer();
                } catch (Exception e) {
                    e.printStackTrace();
                    Loggy.e(RegisterFragment.class, e.getMessage());
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(e.getMessage().toString().trim())
                            .setContentText(validator.getMessage())
                            .show();
                    validator.clear();
                }
                break;
            default:
                break;
        }

    }

    private Customer createCustomer() {
        Bundle bundle = getArguments();
        Product product = (Product) bundle.getSerializable("product");
        Customer customer = new Customer();
        customer.setGroomName(txtGroomName.getText().toString().trim());
        customer.setGroomDadName(txtDadGroomName.getText().toString().trim());
        customer.setGroomMomName(txtMomGroomName.getText().toString().trim());
        customer.setBrideName(txtBrideName.getText().toString().trim());
        customer.setBrideDadName(txtDadBrideName.getText().toString().trim());
        customer.setBrideMomName(txtMomBrideName.getText().toString().trim());
        customer.setDate(txtDate.getText().toString().trim());
        String address = txtVillage.getText().toString() + " " + txtCommune.getText().toString() + " " + txtDistrict.getText().toString();
        customer.setAddress(address);
        customer.setHome(txtHome.getText().toString().trim());
        customer.setPhone(txtPhone.getText().toString().trim());
        customer.setEmail(txtEmail.getText().toString().trim());
        customer.setFb(txtFb.getText().toString().trim());
        customer.setOther(txtOther.getText().toString().trim());
        customer.setProductId(product.getId());
        return customer;
    }

    private void requiredField() {
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
        controls.put(R.string.string_email, factory.build(TextView.class, R.id.lblEmail));
        controls.put(R.string.string_facebook, factory.build(TextView.class, R.id.lblFacebook));
        validator.setRequired(controls);
    }

    private void validate() throws Exception {
        validator.requiredTextField(txtGroomName);
        validator.requiredTextField(txtDadGroomName);
        validator.requiredTextField(txtMomGroomName);
        validator.requiredTextField(txtBrideName);
        validator.requiredTextField(txtDadBrideName);
        validator.requiredTextField(txtMomBrideName);
        validator.isEmptyTextField(txtDate, getString(R.string.string_wedding_date));
        validator.isEmptyTextField(txtVillage);
        validator.isEmptyTextField(txtCommune);
        validator.isEmptyTextField(txtDistrict);
        validator.isEmptyTextField(txtPhone);
        validator.isEmptyTextField(txtEmail);
        validator.isEmptyTextField(txtFb);
        if (!validator.isValid())
            throw new Exception(getString(R.string.string_problem));
    }

    private void browseImage() {
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

    private void submitCustomer() {
        try {
            Customer customer = createCustomer();
            if (customer != null) {
                progressDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                progressDialog.setTitleText(getString(R.string.string_submitting));
                progressDialog.setCancelable(false);
                String json = new Gson().toJson(customer);
                Log.i(Constants.TAG, json);
                RequestBody requestJson = RequestBody.create(MediaType.parse("text/plain"), json);
                MultipartBody.Part[] requestFiles = new MultipartBody.Part[gallery.getChildCount()];
                for (int i = 0; i < gallery.getChildCount(); i++) {
                    ImageCross imageCross = (ImageCross) gallery.getChildAt(i);
                    File file = imageCross.getFile();
                    RequestBody requestBody = RequestBody.create(MediaType.parse(imageCross.getType()), file);
                    requestFiles[i] = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                }
                requestService.submitCustomer(requestJson, requestFiles).enqueue(this);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Constants.TAG, e.getMessage());
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
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
    public void onResponse(Call<ResponseEntity<Map<String, Object>>> call, Response<ResponseEntity<Map<String, Object>>> response) {
        if (response.code() == 200) {
            progressDialog.dismiss();
            ResponseEntity<Map<String, Object>> responseEntity = response.body();
            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(getString(R.string.string_sending_infor))
                    .setContentText(getString(R.string.string_success))
                    // .findViewById(R.id.confirm_button).setVisibility(View.GONE)
                    .show();
            Loggy.i(RegisterFragment.class, responseEntity.getMessage());
            //Toast.makeText(getContext(), responseEntity.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<ResponseEntity<Map<String, Object>>> call, Throwable t) {
        progressDialog.dismiss();
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.string_fail))
                .setContentText(t.getMessage())
                .show();
        Log.e(Constants.TAG, t.getMessage());
        //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
