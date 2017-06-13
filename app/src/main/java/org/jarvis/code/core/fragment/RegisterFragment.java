package org.jarvis.code.core.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.jarvis.code.R;
import org.jarvis.code.core.component.ImageCross;

/**
 * Created by KimChheng on 6/6/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText txtGroomName;
    private EditText txtDadGroomName;
    private EditText txtMomGroomName;
    private EditText txtBrideName;
    private EditText txtDadBrideName;
    private EditText txtMomBrideName;
    private ImageButton datePicker;
    private EditText txtVillage;
    private EditText txtCommune;
    private EditText txtDistrict;
    private EditText txtPhone1, txtPhone2;
    private EditText txtEmail, txtFb, txtOther;
    private Button btnChoose;
    private LinearLayout gallery;
    private int[] mImgIds;

    public RegisterFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImgIds = new int[]{R.drawable.no_image_available, R.drawable.no_image_available,
                R.drawable.no_image_available, R.drawable.no_image_available,
                R.drawable.no_image_available, R.drawable.no_image_available, R.drawable.no_image_available};
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
        datePicker = (ImageButton) view.findViewById(R.id.imgDate);
        datePicker.setOnClickListener(this);
        txtVillage = (EditText) view.findViewById(R.id.txtVillage);
        txtCommune = (EditText) view.findViewById(R.id.txtCommune);
        txtDistrict = (EditText) view.findViewById(R.id.txtDistrict);
        txtPhone1 = (EditText) view.findViewById(R.id.txtPhone1);
        txtPhone2 = (EditText) view.findViewById(R.id.txtPhone2);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtFb = (EditText) view.findViewById(R.id.txtFacebook);
        txtOther = (EditText) view.findViewById(R.id.txtOther);
        btnChoose = (Button) view.findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(this);
        gallery = (LinearLayout) view.findViewById(R.id.imgGallery);

        /*for (int i = 0; i < mImgIds.length; i++) {
            ImageCross imageCross = new ImageCross(getContext());
            imageCross.setPhoto(mImgIds[i]);
            gallery.addView(imageCross);
        }*/
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgDate:
                pickDate();
                break;
            case R.id.btnChoose:
                chooseImage();
                break;
            default:
                break;
        }

    }

    private void pickDate() {
        DialogFragment newFragment = new DatePickerFragment();
        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
        newFragment.show(appCompatActivity.getSupportFragmentManager(), "datePicker");
    }

    private void chooseImage() {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooserIntent = Intent.createChooser(gallery, getResources().getString(R.string.choose_image));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhoto});
        startActivityForResult(chooserIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            try {
                if (data == null) return;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(data.getData()), null, options);
                ImageCross imageCross = new ImageCross(getContext());
                imageCross.setPhoto(bitmap);
                gallery.addView(imageCross);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("====>> RegisterFragment", e.getMessage());
            }
        }
    }
}
