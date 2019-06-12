package com.example.lyp.day10_handler;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lyp.day10_handler.beans.Bean;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button camera;
    private Button album;
    private ImageView iv;
    private File file;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initData();
        initView();

    }

    private void initView() {
        camera = (Button) findViewById(R.id.camera);
        album = (Button) findViewById(R.id.album);

        camera.setOnClickListener(this);
        album.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:

                tackPhoto();

                break;
            case R.id.album:

                opAlbum();

                break;
        }
    }

    private void tackPhoto() {

        //拍照授权
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){

            opCamera();

        }else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},100);

        }

    }

    private void opCamera() {

        //第二部 创建一个目录用来存放
        file = new File(getExternalCacheDir(), System.currentTimeMillis() + "jpg");

        if (!file.exists()){

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //将目录转换为URI 路径
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            uri = Uri.fromFile(file);
        }else {

            uri =FileProvider.getUriForFile(this,"com.example.lyp.day10_handler", file);

        }

        //打开相机

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(intent,16);

    }

    private void initData() {

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){

        }else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);

        }

    }

    private void opAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");

        startActivityForResult(intent,17);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==16){

               /* try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));

                    mIv.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
                //上传照片

                upLoadFile(file);
            }else if (requestCode==17){

                Uri uri = data.getData();

                //通过URI路径转换为对应的file文件
                File file=getFileFromUri(uri,this);
                upLoadFile(file);

            }



        }
    }


    private File getFileFromUri(Uri uri, Context context) {

        if (uri==null){
            return null;
        }

        switch (uri.getScheme()){

            case "content":
                return getFileFromContentUri(uri,this);
            case "file":
                return new File(uri.getPath());

            default:
                return null;

        }
    }


    private File getFileFromContentUri(Uri uri, Context context) {

        File file=null;
        Cursor cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null, null);
        if (cursor!=null){
            if(cursor.moveToNext()){

                String string = cursor.getString(cursor.getColumnIndex("_data"));

                cursor.close();
                if (!TextUtils.isEmpty(string)){

                    file= new File(string);

                }

            }
        }


        return file;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode==100&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//
//            upLoadFile();
//
//        }
//
//    }

    private void upLoadFile(File file) {

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);

        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "H1809A")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url("http://yun918.cn/study/public/file_upload.php")
                //http://yun918.cn/study/public/file_upload.php
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.i("tag", "onFailure: "+e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                final Bean.DataBean data = new Gson().fromJson(string, Bean.class).getData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data!=null){
                            Glide.with(Main2Activity.this).load(data.getUrl()).into(iv);
                            Toast.makeText(Main2Activity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });


    }
}
