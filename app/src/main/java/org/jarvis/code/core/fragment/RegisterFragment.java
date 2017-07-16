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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jarvis.code.R;
import org.jarvis.code.api.WeddingApi;
import org.jarvis.code.core.component.DatePickerFragment;
import org.jarvis.code.core.component.ImageCross;
import org.jarvis.code.core.model.request.Customer;
import org.jarvis.code.core.model.response.Product;
import org.jarvis.code.core.model.response.ResponseEntity;
import org.jarvis.code.util.Constant;
import org.jarvis.code.util.FileUtil;
import org.jarvis.code.util.ImageAnimate;
import org.jarvis.code.util.RestApiFactory;
import org.jarvis.code.util.StringUtil;
import org.jarvis.code.util.ValidateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

public class RegisterFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText txtGroomName;
    private EditText txtDadGroomName;
    private EditText txtMomGroomName;
    private EditText txtBrideName;
    private EditText txtDadBrideName;
    private EditText txtMomBrideName;
    private EditText txtDate;
    private ImageButton datePicker, imgBack, imgChoose;
    private EditText txtVillage, txtCommune, txtDistrict;
    private EditText txtHome, txtPhone;
    private EditText txtEmail, txtFb, txtOther;
    private Button btnSubmit;
    private LinearLayout gallery;
    private WeddingApi weddingApi;
    private DatePickerFragment dialogFragment;
    private List<String> validControl;
    private int adImages[] = {R.drawable.coca_col_ad,
            R.drawable.hi_tea_ad, R.drawable.samsung_ad, R.drawable.v_printing_ad};
    private ImageView adImage;

    private Uri uri;
    private File file;

    public RegisterFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weddingApi = RestApiFactory.build(WeddingApi.class);
        dialogFragment = new DatePickerFragment();
        validControl = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        txtGroomName = (EditText) view.findViewById(R.id.txtGroomName);
        txtDadGroomName = (EditText) view.findViewById(R.id.txtDadGroomName);
        txtMomGroomName = (EditText) view.findViewById(R.id.txtMomGroomName);
        txtBrideName = (EditText) view.findViewById(R.id.txtBrideName);
        txtDadBrideName = (EditText) view.findViewById(R.id.txtDadBrideName);
        txtMomBrideName = (EditText) view.findViewById(R.id.txtMomBrideName);
        txtDate = (EditText) view.findViewById(R.id.txtDate);
        datePicker = (ImageButton) view.findViewById(R.id.imgDate);
        datePicker.setOnClickListener(this);
        imgBack = (ImageButton) view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        txtVillage = (EditText) view.findViewById(R.id.txtVillage);
        txtCommune = (EditText) view.findViewById(R.id.txtCommune);
        txtDistrict = (EditText) view.findViewById(R.id.txtDistrict);
        txtHome = (EditText) view.findViewById(R.id.txtHome);
        txtPhone = (EditText) view.findViewById(R.id.txtPhone);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtFb = (EditText) view.findViewById(R.id.txtFacebook);
        txtOther = (EditText) view.findViewById(R.id.txtOther);
        txtOther.setOnFocusChangeListener(this);
        imgChoose = (ImageButton) view.findViewById(R.id.btnChoose);
        imgChoose.setOnClickListener(this);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        gallery = (LinearLayout) view.findViewById(R.id.imgGallery);
        adImage = (ImageView) view.findViewById(R.id.registerImgAd);
        requiredField(view);
        ImageAnimate.animate(adImage,adImages,0,true);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgDate:
                AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                dialogFragment.show(appCompatActivity.getSupportFragmentManager(), "datePicker");
                dialogFragment.setCancelable(false);
                break;
            case R.id.btnChoose:
                browseImage();
                break;
            case R.id.imgBack:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.btnSubmit:
                try {
                    validate();
                    submitCustomer(getEntity());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(Constant.TAG, e.getMessage());
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(e.getMessage().toString().trim());
                    StringBuilder sb = new StringBuilder();
                    for (String msg : validControl)
                        sb.append(msg).append("\r\n");
                    builder.setMessage(sb.toString().trim())
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    validControl.clear();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                    StringBuilder sb = new StringBuilder();
                    for (String msg : validControl)
                        sb.append(msg).append("\r\n");
                    new SweetAlertDialog(getContext())
                            .setTitleText(e.getMessage().toString().trim())
                            .setContentText(sb.toString())
                            .show();
                    validControl.clear();
                }
                break;
            default:
                break;
        }

    }

    private Customer getEntity() {
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
        customer.setOther(txtOther.getText().toString().trim());
        customer.setProductId(product.getId());
        return customer;
    }

    private void requiredField(View view) {
        Context context = getContext();
        ValidateUtil.setRequired(StringUtil.getString(context, R.string.string_groom_name),
                (TextView) view.findViewById(R.id.lblGroomName));
        ValidateUtil.setRequired(StringUtil.getString(context, R.string.string_dad_groom_name),
                (TextView) view.findViewById(R.id.lblDadGroomName));
        ValidateUtil.setRequired(StringUtil.getString(context, R.string.string_mom_groom_name),
                (TextView) view.findViewById(R.id.lblMomGroomName));
        ValidateUtil.setRequired(StringUtil.getString(context, R.string.string_bride_name),
                (TextView) view.findViewById(R.id.lblBrideName));
        ValidateUtil.setRequired(StringUtil.getString(context, R.string.string_dad_bride_name),
                (TextView) view.findViewById(R.id.lblDadBrideName));
        ValidateUtil.setRequired(StringUtil.getString(context, R.string.string_mom_bride_name),
                (TextView) view.findViewById(R.id.lblMomBrideName));
        ValidateUtil.setRequired(StringUtil.getString(context, R.string.string_wedding_date),
                (TextView) view.findViewById(R.id.lblWeddingDate));
        ValidateUtil.setRequired(StringUtil.getString(context, R.string.string_wedding_address),
                (TextView) view.findViewById(R.id.lblAddress));
        ValidateUtil.setRequired(StringUtil.getString(context, R.string.string_phone),
                (TextView) view.findViewById(R.id.lblPhone));

    }

    private void validate() throws Exception {
        if (!ValidateUtil.isValid(txtGroomName))
            validControl.add(txtGroomName.getHint().toString().trim());
        if (!ValidateUtil.isValid(txtDadGroomName))
            validControl.add(txtDadGroomName.getHint().toString().trim());
        if (!ValidateUtil.isValid(txtMomGroomName))
            validControl.add(txtMomGroomName.getHint().toString().trim());
        if (!ValidateUtil.isValid(txtBrideName))
            validControl.add(txtBrideName.getHint().toString().trim());
        if (!ValidateUtil.isValid(txtDadBrideName))
            validControl.add(txtDadBrideName.getHint().toString().trim());
        if (!ValidateUtil.isValid(txtMomBrideName))
            validControl.add(txtMomBrideName.getHint().toString().trim());
        if (ValidateUtil.isEmpty(txtDate))
            validControl.add(StringUtil.getString(getContext(), R.string.string_wedding_date));
        if (ValidateUtil.isEmpty(txtVillage))
            validControl.add(txtVillage.getHint().toString().trim());
        if (ValidateUtil.isEmpty(txtCommune))
            validControl.add(txtCommune.getHint().toString().trim());
        if (ValidateUtil.isEmpty(txtDistrict))
            validControl.add(txtDistrict.getHint().toString().trim());
        if (ValidateUtil.isEmpty(txtPhone))
            validControl.add(txtPhone.getHint().toString().trim());
        if (!validControl.isEmpty())
            throw new Exception("There are invalid fields.");
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

    private void submitCustomer(Customer customer) {
        try {
            final SweetAlertDialog progressDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
            progressDialog.setTitleText("Submitting ...");
            progressDialog.setCancelable(false);
            if (customer != null) {
                validControl.clear();
                String json = new Gson().toJson(customer);
                Log.i(Constant.TAG, json);
                RequestBody requestJson = RequestBody.create(MediaType.parse("text/plain"), json);
                MultipartBody.Part[] requestFiles = new MultipartBody.Part[gallery.getChildCount()];
                for (int i = 0; i < gallery.getChildCount(); i++) {
                    ImageCross imageCross = (ImageCross) gallery.getChildAt(i);
                    File file = imageCross.getFile();
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                    requestFiles[i] = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                }
                weddingApi.submitCustomer(requestJson, requestFiles).enqueue(new Callback<ResponseEntity<String>>() {
                    @Override
                    public void onResponse(Call<ResponseEntity<String>> call, Response<ResponseEntity<String>> response) {
                        if (response.code() == 200) {
                            progressDialog.dismiss();
                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Submit success!")
                                    // .findViewById(R.id.confirm_button).setVisibility(View.GONE)
                                    .show();
                            ResponseEntity<String> responseEntity = response.body();
                            Log.i(Constant.TAG, responseEntity.getMessage());
                            Toast.makeText(getContext(), responseEntity.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEntity<String>> call, Throwable t) {
                        progressDialog.dismiss();
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                        Log.e(Constant.TAG, t.getMessage());
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Constant.TAG, e.getMessage());
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            try {
                Bitmap bitmap = null;
                if (data == null) {
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
}
