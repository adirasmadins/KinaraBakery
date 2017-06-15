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
    String Username,phone,customername,Address,Email,DateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcartactivity);
        ButterKnife.bind(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(shoppingcartactivity.this);
        Islogin = prefs.getBoolean("Islogin", false);
        Username=prefs.getString("Username",null);
        phone=prefs.getString("Phone",null);
        customername=prefs.getString("Name",null);
        Address=prefs.getString("Address",null);
        Email=prefs.getString("Email",null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shopping Cart");
        Calendar calender=Calendar.getInstance();
      DateTime= String.valueOf(calender.getTime());
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
            PopupMenu pum= new PopupMenu(shoppingcartactivity.this,checkoutbtn);
            MenuInflater mi=getMenuInflater();
            mi.inflate(R.menu.check_out_menu,pum.getMenu());
            pum.show();
            pum.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId()==R.id.cash_on_delivery) {
                        //   SmsManager smssender= SmsManager.getDefault();
                        Cursor res = mydb.get_products_in_cart(Username);
                        if (res.getCount() == 0) {
                        }
                        StringBuffer sb = new StringBuffer();
                        while (res.moveToNext()) {
                            sb.append(res.getString(1) + " ");
                            sb.append("x"+ res.getInt(4) + " "+"\n");
                        }
                        // smssender.sendTextMessage(
                        //
                        // "phone","Kinara Bakery","Dear "+customername+"\n"+"You have ordered "+"\n"+sb.toString()+"\n"+"You will recieve Order Soon",null,null);*/
                        new placingorders().place_orders(customername, Username, Address, phone, Email, sb.toString(),String.valueOf(mydb.getTotalOfAmount(Username)), shoppingcartactivity.this,DateTime);
                        new sendmail(shoppingcartactivity.this,Email,"Your Order has been Recieved","Dear "+customername+"\n"+"\n"+"You have ordered "+"\n"+"\n"+sb.toString()+"\n"+"\n"+"Your Total bill is Rs "+String.valueOf(mydb.getTotalOfAmount(Username))+"\n"+"You will recieve Order Soon").execute();
                            mydb.delete_all(Username);
                            startActivity(new Intent(shoppingcartactivity.this, MainActivity.class));
                            finish();
                    }
                    return false;
                }
            });
        }
    }

}

