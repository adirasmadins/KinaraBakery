package usmanali.kinarabakery;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class showmoreproducts extends AppCompatActivity  {

   @BindView(R.id.moreproductslist)RecyclerView allproductslist;
    @BindView(R.id.toolbar) Toolbar toolbar;
    showproducts sp;
    products p;
   android.support.v7.widget.SearchView productsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showmoreproducts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        ButterKnife.bind(this);
        allproductslist.setLayoutManager(new GridLayoutManager(showmoreproducts.this,2));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       allproductslist.setLayoutManager(new GridLayoutManager(this,2));
        sp=new showproducts(showmoreproducts.this);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Catorgery"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menuitem,menu);
        MenuItem searchitem=menu.findItem(R.id.action_search);
         productsearch=(SearchView) MenuItemCompat.getActionView(searchitem);
        if(getIntent().getStringExtra("Catorgery").equals("All Products")){
            sp.fetch_all_products(productsearch,allproductslist,showmoreproducts.this);
        }else {
            sp.get_product_by_catorgery(allproductslist, showmoreproducts.this, getIntent().getStringExtra("Catorgery"), productsearch);
        }
        return true;
    }

}

