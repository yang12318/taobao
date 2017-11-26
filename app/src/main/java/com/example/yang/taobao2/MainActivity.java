package com.example.yang.taobao2;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;

public class MainActivity extends AppCompatActivity implements  OnClickListener{


    private MySqliteHelper helper;

    Button login;
    Button register;

    String  name;
    String  mypwd;
    private EditText user;
    private EditText pwd;
    int userflag ;//定义一个标示判断 用户名是否存在
    int loginflag ;//登录时判断用户密码是否输入正确
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        user = (EditText)findViewById(R.id.username);
        pwd=(EditText)findViewById(R.id.password);

    }


    public void  insert()
    {


        helper = new MySqliteHelper(getApplicationContext());
        SQLiteDatabase db=helper.getWritableDatabase();    //建立打开可读可写的数据库实例


        //查询一下，是否用户名重复
        String sql1 = "select * from users";
        Cursor cursor = db.rawQuery(sql1, null);
        while (cursor.moveToNext()) {
            //第一列为id
            name =  cursor.getString(1); //获取第2列的值,第一列的索引从0开始
            mypwd = cursor.getString(2);//获取第3列的值

            if((user.getText().toString().isEmpty())||(pwd.getText().toString().isEmpty())){

                Toast.makeText(this, "不能为空，请重新输入", Toast.LENGTH_SHORT).show();
                break;
            }


            userflag = 1;  //不存在此用户


            if((user.getText().toString().equals(name)))
            {
                Toast.makeText(this, "已存在此用户，请重新注册", Toast.LENGTH_SHORT).show();


                userflag =0;
                break;
            }

        }

        if(userflag==1)
        {
            String sql2 = "insert into users(name,pwd) values ('"+user.getText().toString().trim()+"','"+pwd.getText().toString().trim()+"')";
            db.execSQL(sql2);
            Toast.makeText(this, "注册成功,请点击登录！", Toast.LENGTH_SHORT).show();
        }





    }


    public void select()
    {

        helper = new MySqliteHelper(getApplicationContext());
        SQLiteDatabase db=helper.getWritableDatabase();

        String sql = "select * from users";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            //第一列为id
            name =  cursor.getString(1); //获取第2列的值,第一列的索引从0开始
            mypwd = cursor.getString(2);//获取第3列的值



            if((user.getText().toString().equals(name))&&(pwd.getText().toString().equals(mypwd)))
            {
                Toast.makeText(this, "用户验证成功", Toast.LENGTH_SHORT).show();
                loginflag=1;

                //intent bundle传值
                Intent ShopMain = new Intent();
                ShopMain .setClass(this,ShopMain.class);
                Bundle bundle = new Bundle(); //该类用作携带数据
                bundle.putString("user", user.getText().toString());
                ShopMain.putExtras(bundle);   //向MainActivity传值
                this.startActivity(ShopMain);
                finish();//退出*/

            }


        }

        if((user.getText().toString().isEmpty())||(pwd.getText().toString().isEmpty())){

            Toast.makeText(this, "不能为空，请重新输入", Toast.LENGTH_SHORT).show();
        }


        if(loginflag!=1)
        {
            Toast.makeText(this, "账号或者密码错误,请重新输入", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
        //Toast.makeText(this, "已经关闭数据库", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.login:
                select();

                break;
            case R.id.register:
                insert();
                break;
        }

    }


}
