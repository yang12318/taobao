package com.example.yang.taobao2;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoodMain extends AppCompatActivity {

    private DrawerLayout mydrawerLayout;
    private Good[] goods={new Good("商品",R.drawable.picture)};
    private List<Good> goodList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mydrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navView=findViewById(R.id.navview);
        ActionBar actionBar=getSupportActionBar();
        initGoods();

        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);
        GoodAdapter adapter = new GoodAdapter(goodList);
        recyclerView.setAdapter(adapter);

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        navView.setCheckedItem(R.id.username);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mydrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initGoods() {
        goodList.clear();
        for (int i=0;i<10;i++){
            Random random=new Random();
            int intdex=random.nextInt(goods.length);
            goodList.add(goods[intdex]);
        }
    }}