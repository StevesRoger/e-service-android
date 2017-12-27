package org.jarvis.code.ui.customer;

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
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jarvis.code.R;
import org.jarvis.code.model.Product;
import org.jarvis.code.ui.base.AbstractFragment;
import org.jarvis.code.ui.widget.ImageCross;
import org.jarvis.code.util.ComponentFactory;
import org.jarvis.code.util.FileUtil;
import org.jarvis.code.util.Validator;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by KimChheng on 6/6/2017.
 */

public abstract class RegisterFragment extends AbstractFragment implements RegisterView {

    protected SweetAlertDialog progressDialog;
    protected ComponentFactory factory;
    protected Uri uri;
    protected File file;
    protected Product product;
    protected FragmentManager fragmentManager;
    protected Validator validator;

    protected ImageButton imgMap;
    protected ImageButton imgChoose;
    protected Button btnSubmit;
    protected LinearLayout layoutImage;
    protected Spinner colorSpinner;
    protected TextView lblProductCode;
    protected TextView lblProductAmount;
    protected EditText txtProductQty;
    protected EditText txtPhone, txtEmail, txtFacebook, txtOther;

    public RegisterFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = getArguments().getParcelable("product");
        validator = new Validator(getBaseActivity());
        fragmentManager = getBaseActivity().getSupportFragmentManager();
    }

    public void onCreateHeaderView(View view) {
        colorSpinner = (Spinner) view.findViewById(R.id.colorSpinner);
        lblProductCode = (TextView) view.findViewById(R.id.lblProductCode);
        lblProductAmount = (TextView) view.findViewById(R.id.lblProductAmount);
        txtProductQty = (EditText) view.findViewById(R.id.txtProductQty);
    }

    public void onCreateFooterView(View view) {
        txtPhone = (EditText) view.findViewById(R.id.txtPhone);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtFacebook = (EditText) view.findViewById(R.id.txtFacebook);
        txtOther = (EditText) view.findViewById(R.id.txtOther);
        imgMap = (ImageButton) view.findViewById(R.id.imgMap);
        imgChoose = (ImageButton) view.findViewById(R.id.imgChoose);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        layoutImage = (LinearLayout) view.findViewById(R.id.img_gallery);
        imgMap.setOnClickListener(this);
        imgChoose.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgMap:
                showMessage("Sorry for this feature will release on next version.", Toast.LENGTH_SHORT);
                break;
            case R.id.imgChoose:
                browseImage();
                break;
            /*case R.id.imgBack:
                backToMainActivity();
                break;*/
            case R.id.btn_submit:
                submitCustomer();
                break;
            default:
                break;
        }

    }

    public abstract void submitCustomer();

    @Override
    public MultipartBody.Part[] requestFiles() {
        MultipartBody.Part[] requestFiles = new MultipartBody.Part[layoutImage.getChildCount()];
        for (int i = 0; i < layoutImage.getChildCount(); i++) {
            ImageCross imageCross = (ImageCross) layoutImage.getChildAt(i);
            File file = imageCross.getFile();
            RequestBody requestBody = RequestBody.create(MediaType.parse(imageCross.getType()), file);
            requestFiles[i] = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
        }
        return requestFiles;
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
                layoutImage.addView(imageCross);
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

    /*@OnTextChanged(R.id.txtProductQty)
    void calculateAmount(CharSequence text, int start, int count, int after) {
        try {
            long amount = 0;
            int qty = 0;
            if (text != null && !text.toString().isEmpty()) {
                //qty = Integer.parseInt(text.toString().trim());
                //amount = qty * 7;
            }
            lblProductAmount.setText(getString(R.string.price) + "$00");
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Invalid input!", Toast.LENGTH_SHORT);
            txtProductQty.setText("");
        }
    }*/
}
