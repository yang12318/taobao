package com.example.yang.taobao2;


/*import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_Shop extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopadd);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button confirm = findViewById(R.id.confirm);
        Button cancel = findViewById(R.id.cancel);
        toolbar.setTitle("淘宝");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent("android.intent.action.MAIN2");
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("android.intent.action.MAIN2");
                startActivity(intent);
            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
    }
}*/
import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//public class Add_Shop extends AppCompatActivity implements View.OnClickListener {
    /*private MySqliteHelper helper;

    Button confirm;
    Button cancel;
    String  name;
    String  img;
    private EditText shopname;
    private EditText image;
    int nameflag ;//定义一个标示判断 店铺名是否存在*/
public class Add_Shop extends AppCompatActivity {
    public static final int CHOOSE_PHOTO = 2;
    Button confirm;
    Button cancel;
    private MySqliteHelper helper;
    private ImageView picture;
    int nameflag;//定义一个标示判断 店铺名是否存在
    private Uri imageUri;
    private EditText shopname;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopadd);
        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        EditText shopname = (EditText) findViewById(R.id.shopname);
        picture = (ImageView) findViewById(R.id.picture);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("android.intent.action.MAIN2");
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });


        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Add_Shop.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Add_Shop.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    public void insert() {


        helper = new MySqliteHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();    //建立打开可读可写的数据库实例


        //查询一下，是否店铺名重复
        String shop1 = "select * from name";
        Cursor cursor = db.rawQuery(shop1, null);
        while (cursor.moveToNext()) {
            name = cursor.getString(1); //获取第2列的值,第一列的索引从0开始

            if (shopname.getText().toString().isEmpty()) {

                Toast.makeText(this, "不能为空，请重新输入", Toast.LENGTH_SHORT).show();
                break;
            }


            nameflag = 1;  //不存在此店铺


            if ((shopname.getText().toString().equals(name))) {
                Toast.makeText(this, "已存在此店铺，请重新填写", Toast.LENGTH_SHORT).show();


                nameflag = 0;
                break;
            }

        }

        if (nameflag == 1) {
            String shop2 = "insert into shops(name) values ('" + shopname.getText().toString() + "')";
            db.execSQL(shop2);
            Toast.makeText(this, "增添成功！", Toast.LENGTH_SHORT).show();
        }

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }




}


    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopadd);

        findViewById(R.id.confirm).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        shopname = (EditText)findViewById(R.id.shopname);
        image=(EditText)findViewById(R.id.shopimage);

    }


    public void  insert()
    {


        helper = new MySqliteHelper(getApplicationContext());
        SQLiteDatabase db=helper.getWritableDatabase();    //建立打开可读可写的数据库实例



        //查询一下，是否店铺名重复
        String shop1 = "select * from name";
        Cursor cursor = db.rawQuery(shop1, null);
        while (cursor.moveToNext()) {
            name =  cursor.getString(1); //获取第2列的值,第一列的索引从0开始
            img = cursor.getString(2);//获取第3列的值

            if((shopname.getText().toString().isEmpty())||(image.getText().toString().isEmpty())){

                Toast.makeText(this, "不能为空，请重新输入", Toast.LENGTH_SHORT).show();
                break;
            }


            nameflag = 1;  //不存在此店铺


            if((shopname.getText().toString().equals(name)))
            {
                Toast.makeText(this, "已存在此店铺，请重新填写", Toast.LENGTH_SHORT).show();


                nameflag =0;
                break;
            }

        }

        if(nameflag==1)
        {
            String shop2 = "insert into shops(name,img) values ('"+shopname.getText().toString()+"','"+image.getText().toString()+"')";
            db.execSQL(shop2);
            Toast.makeText(this, "增添成功！", Toast.LENGTH_SHORT).show();
        }





    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.confirm:
                insert();
                break;
            case R.id.cancel:
                Intent intent =new Intent("android.intent.action.MAIN2");
                startActivity(intent);
                break;
        }
    }

}*/
