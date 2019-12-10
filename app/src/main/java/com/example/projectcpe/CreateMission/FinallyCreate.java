package com.example.projectcpe.CreateMission;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.Adapter.StepAdapter;
import com.example.projectcpe.Main2Activity;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.example.projectcpe.ViewModel.Step;

import java.util.ArrayList;
import java.util.List;


public class FinallyCreate extends AppCompatActivity implements StepAdapter.OnCustomrPictureClick {

    RecyclerView recyclerViewStep;
    StepAdapter adapter;
    List<Step> steplist;
    String getName, getDetail;
    int getNumOfStep, getAge;
    Button btSubmit;
    LinearLayout frameEdittext;

    public ImageView mediumImage;
    EditText addEdittext;


    private  final int CAMERA_RESULT_CODE = 100;
    private  final int RESULT_LOAD_IMAGE = 101;
    public static int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_create);

        Initia();

        Bundle bundle = getIntent().getExtras();
       getNumOfStep = bundle.getInt("NumOfStep");
       getName = bundle.getString("name");
       getDetail = bundle.getString("name");
       getAge = bundle.getInt("age");

        NumStepListener(this.getNumOfStep);

        btSubmit.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               Intent intent = new Intent(FinallyCreate.this, Main2Activity.class);
               startActivity(intent);
               return true;
           }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < StepAdapter.stepList.size(); i++) {
                    Toast.makeText(getApplicationContext(),  StepAdapter.stepList.get(i).getAnswer(), Toast.LENGTH_SHORT).show();

                }
            }
        });

//        loadData();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == 1) {
//            if(resultCode == Activity.RESULT_OK){
//
//
//                data.getIntExtra("NumOfStep",this.mediumNum);
//
//
//                NumStepListener(this.mediumNum);
//            }
//            if (resultCode == Activity.RESULT_CANCELED) {
//                //Write your code if there's no result
//            }
//        }
//    }

    private void NumStepListener(int mediumNum) {

        for (int i = 1 ; i <= mediumNum ;i++) {
            steplist.add(
                    new Step());

        }

        adapter = new StepAdapter(steplist, this);
        recyclerViewStep.setAdapter(adapter);
    }

    private void Initia() {
        steplist = new ArrayList<Step>();
        btSubmit = findViewById(R.id.btnsubmit);
        recyclerViewStep = findViewById(R.id.recyclerViewStepCreate);
        recyclerViewStep.setHasFixedSize(true);
        recyclerViewStep.setLayoutManager(new LinearLayoutManager(this));
         frameEdittext = findViewById(R.id.frameEdittext);

//        for (int i = 0 ; i<)
//
//        adapter = new StepAdapter(, this);

        recyclerViewStep.setAdapter(adapter);

    }



    @Override
    public void oncustompictureclick(final int pos, final ImageView imageView) {

        Toast.makeText(getApplicationContext(), String.valueOf(pos), Toast.LENGTH_SHORT ).show();

        position = pos;

        this.mediumImage = imageView;


        final Dialog dialog;
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.picture_dialog);
        dialog.setCancelable(true);

        dialog.show();

        Button btCamera = dialog.findViewById(R.id.ChooseCamera);
        btCamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "this is Choose Camera", Toast.LENGTH_SHORT).show();
                if(ContextCompat.checkSelfPermission(FinallyCreate.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(it.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(it, CAMERA_RESULT_CODE);
                        dialog.cancel();
                    }
                }
                else{
                    if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                        Toast.makeText(FinallyCreate.this, "ไม่สามารถใช้งานกล้องได้", Toast.LENGTH_LONG).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_RESULT_CODE);
                }
            }
        });


        Button btGallary = dialog.findViewById(R.id.ChooseGallary);

        btGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getImageIntent.setType("image/*");
                startActivityForResult(getImageIntent , RESULT_LOAD_IMAGE );
                Toast.makeText(getApplicationContext(), "this is Choose Gallary", Toast.LENGTH_SHORT).show();
            }
        });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_RESULT_CODE || requestCode == RESULT_LOAD_IMAGE){
                Bundle bd = data.getExtras();
                Bitmap bmp = (Bitmap) bd.get("data");
                if (bmp!=null) {
                    setPic(bmp);
                }

            }
        }


    }

    private void setPic(Bitmap bitmap) {

        this.mediumImage.setImageBitmap(bitmap);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_RESULT_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(it.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(it, CAMERA_RESULT_CODE);
                }
            }
            else{
                Toast.makeText(FinallyCreate.this, "ไม่สามารถใช้งานกล้องได้", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }





    }



