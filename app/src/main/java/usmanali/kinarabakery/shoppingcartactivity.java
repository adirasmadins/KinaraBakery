package usmanali.kinarabakery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class shoppingcartactivity extends AppCompatActivity {

@BindView(R.id.cartlist) RecyclerView cartlist;
    @BindView(R.id.btnCheckout) Button checkoutbtn;
    @BindView(R.id.toolbar)Toolbar toolbar;
    cartitemsadapter cartadapter;
    dbhelper mydb;
    Boolean Islogin;
    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcartactivity);
        ButterKnife.bind(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(shoppingcartactivity.this);
        Islogin = prefs.getBoolean("Islogin", false);
        Username = prefs.getString("Username", "Guest");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shopping Cart");
        mydb=new dbhelper(shoppingcartactivity.this);
        cartlist.setLayoutManager(new LinearLayoutManager(shoppingcartactivity.this));
        cartadapter=new cartitemsadapter(new showproducts(shoppingcartactivity.this).show_products_in_cart(mydb,Username),shoppingcartactivity.this);
        cartlist.setAdapter(cartadapter);

    }
    @OnClick(R.id.btnCheckout) public void go_to_orders_activity() {
        if (cartadapter.getItemCount()<1) {
            Toast.makeText(shoppingcartactivity.this, "You dont have any items in cart", Toast.LENGTH_LONG).show();
        } else if(!Islogin)
                Toast.makeText(shoppingcartactivity.this,"You are not logged in",Toast.LENGTH_LONG).show();
         else {
            startActivity(new Intent(shoppingcartactivity.this,Billing_Activity.class));
        }
    }
}

