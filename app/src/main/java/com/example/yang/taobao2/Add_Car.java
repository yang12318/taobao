package com.example.yang.taobao2;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;
public class Add_Car extends AppCompatActivity {
    Button confirm;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car);
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("android.intent.action.Good");
                startActivity(intent);
            }
        });
        confirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Add_Car.this, "成功添加！", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent("android.intent.action.Good");
                startActivity(intent);
            }
        });
    }


}