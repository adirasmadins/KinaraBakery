package usmanali.kinarabakery;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static usmanali.kinarabakery.R.drawable.profilepic;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.recycler_view_list)
    RecyclerView productslist;
    @BindView(R.id.recycler_view_list2)
    RecyclerView Cakeslist;
    @BindView(R.id.recycler_view_list3)
    RecyclerView Rusklist;
    @BindView(R.id.recycler_view_list4)
    RecyclerView methailist;
    @BindView(R.id.recycler_view_list5) RecyclerView breadsandbunslist;
    @BindView(R.id.btnMore)
    Button btnmore;
    @BindView(R.id.btnMore2)
    Button btnMore2;
    @BindView(R.id.btnMore3)
    Button btnMore3;
    @BindView(R.id.btnMore4)
    Button btnmore4;
    @BindView(R.id.btnMore5)
    Button btnmore5;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView nv;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerlayout;
    @BindView(R.id.carouselpager)
    ViewPager carouselpager;
    String productname, price;
    TextView customername;
    TextView email;
    dbhelper mydb;
    ImageView letterimg;
    Intent i;
    showproducts sp;
    Boolean Islogin;
    ArrayList<Integer> carouselimages;
    ArrayList<products> productsArrayList;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String nameofcustomer, Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        add_images_to_carousel();
        setSupportActionBar(toolbar);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Islogin = prefs.getBoolean("Islogin", false);
        nameofcustomer = prefs.getString("Name", "Guest");
        Username = prefs.getString("Username", "Guest");
        mydb = new dbhelper(MainActivity.this);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_logout) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    prefs.edit().remove("Islogin").commit();
                    prefs.edit().remove("Name").commit();
                    prefs.edit().remove("Email").commit();
                    prefs.edit().remove("Username").commit();
                    prefs.edit().remove("Phone").commit();
                    prefs.edit().remove("Address").commit();
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
                    startActivity(new Intent(MainActivity.this, MainActivity.class), optionsCompat.toBundle());
                    finish();
                } else if (item.getItemId() == R.id.action_signup) {
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
                    startActivity(new Intent(MainActivity.this, signupactivity.class), optionsCompat.toBundle());
                } else if (item.getItemId() == R.id.Login) {
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class), optionsCompat.toBundle());
                } else if (item.getItemId() == R.id.shoppingcart) {
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
                    startActivity(new Intent(MainActivity.this, shoppingcartactivity.class), optionsCompat.toBundle());
                } else if (item.getItemId() == R.id.profile) {
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
                    startActivity(new Intent(MainActivity.this, customerprofileactivity.class), optionsCompat.toBundle());
                }
                return false;
            }
        });
        View header = nv.inflateHeaderView(R.layout.headerlayout);
        customername = (TextView) header.findViewById(R.id.name);
        email = (TextView) header.findViewById(R.id.Email);
        letterimg = (ImageView) header.findViewById(R.id.letterimg);
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(nameofcustomer.substring(0, 1), color1);
        letterimg.setImageDrawable(drawable);
        customername.setText(nameofcustomer);
        email.setText("@"+Username);
        if (Islogin) {
            nv.inflateMenu(R.menu.logoutmenuitem);
        } else {
            nv.inflateMenu(R.menu.menu_main);
        }
        productsArrayList = new ArrayList<>();
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerlayout.setDrawerListener(actionBarDrawerToggle);

        btnmore.setOnClickListener(this);
        btnMore2.setOnClickListener(this);
        btnMore3.setOnClickListener(this);
        btnmore4.setOnClickListener(this);
        btnmore5.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        productslist.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        Cakeslist.setLayoutManager(layoutManager2);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        Rusklist.setLayoutManager(layoutManager3);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        methailist.setLayoutManager(layoutManager4);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        breadsandbunslist.setLayoutManager(layoutManager5);
        sp = new showproducts(MainActivity.this);

        sp.show_list_of_rusks(Rusklist, MainActivity.this);
        sp.show_list_of_deserts(Cakeslist, MainActivity.this);
        sp.show_all_products(productslist, MainActivity.this);
        sp.show_list_of_methai(methailist,MainActivity.this);
        sp.show_list_of_breadandbuns(breadsandbunslist,MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (Islogin)
            getMenuInflater().inflate(R.menu.logoutmenuitem, menu);
        else if (!Islogin) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_signup) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
            startActivity(new Intent(MainActivity.this, signupactivity.class), optionsCompat.toBundle());
            return true;
        }
        if (id == R.id.Login) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
            startActivity(new Intent(MainActivity.this, LoginActivity.class), optionsCompat.toBundle());
        }
        if (id == R.id.shoppingcart) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
            startActivity(new Intent(MainActivity.this, shoppingcartactivity.class), optionsCompat.toBundle());
        }
        if (id == R.id.action_logout) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            prefs.edit().remove("Islogin").commit();
            prefs.edit().remove("Name").commit();
            prefs.edit().remove("Email").commit();
            prefs.edit().remove("Username").commit();
            prefs.edit().remove("Phone").commit();
            prefs.edit().remove("Address").commit();
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
            startActivity(new Intent(MainActivity.this, MainActivity.class), optionsCompat.toBundle());
            finish();
        }
        if(id==R.id.addproducts){
            startActivity(new Intent(MainActivity.this,add_products_activity.class));
        }if(id==R.id.track_orders){
            startActivity(new Intent(MainActivity.this,Track_orders.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (carouselpager.getCurrentItem() == 0) {
                    carouselpager.setCurrentItem(1);
                } else if (carouselpager.getCurrentItem() == 1) {
                    carouselpager.setCurrentItem(2);
                } else if (carouselpager.getCurrentItem() == 2) {
                    carouselpager.setCurrentItem(3);
                } else if (carouselpager.getCurrentItem() == 3) {
                    carouselpager.setCurrentItem(0);
                }
                new Handler().postDelayed(this, 10000);
            }
        }, 10000);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnMore2) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
            i = new Intent(MainActivity.this, showmoreproducts.class);
            i.putExtra("Catorgery", "Deserts");
            startActivity(i, optionsCompat.toBundle());
        } else if (view.getId() == R.id.btnMore3) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
            i = new Intent(MainActivity.this, showmoreproducts.class);
            i.putExtra("Catorgery", "Rusks");
            startActivity(i, optionsCompat.toBundle());
        } else if (view.getId() == R.id.btnMore) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
            i = new Intent(MainActivity.this, showmoreproducts.class);
            i.putExtra("Catorgery", "All Products");
            startActivity(i, optionsCompat.toBundle());
        }else if(view.getId()==R.id.btnMore4){
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
            i = new Intent(MainActivity.this, showmoreproducts.class);
            i.putExtra("Catorgery", "Methai");
            startActivity(i, optionsCompat.toBundle());
        }else if(view.getId()==R.id.btnMore5) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);
            i = new Intent(MainActivity.this, showmoreproducts.class);
            i.putExtra("Catorgery", "Breads & Buns");
            startActivity(i, optionsCompat.toBundle());
        }
    }

    public void add_images_to_carousel() {
        carouselimages = new ArrayList<>();
        carouselimages.add(R.drawable.pineapplecake);
        carouselimages.add(R.drawable.cakeimg2);
        carouselimages.add(R.drawable.cookie);
        carouselimages.add(R.drawable.rusks);
        carouselpager.setAdapter(new carouseladapter(MainActivity.this, carouselimages));
    }

}
