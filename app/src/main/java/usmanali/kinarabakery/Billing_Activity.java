package usmanali.kinarabakery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Billing_Activity extends AppCompatActivity {
    @BindView(R.id.placeorderbtn) Button placeorderbtn;
    @BindView(R.id.checkout_items_list) RecyclerView checkout_items_list;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.order_item_count) TextView itemcount;
    @BindView(R.id.order_full_amounts) TextView totalamount;
    @BindView(R.id.order_total_amount) TextView subtotal;
    String Username,phone,customername,Address,Email,DateTime;
    dbhelper mydb=new dbhelper(Billing_Activity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Billing_Activity.this);
        Username = prefs.getString("Username", "Guest");
        phone=prefs.getString("Phone",null);
        customername=prefs.getString("Name",null);
        Address=prefs.getString("Address",null);
        Email=prefs.getString("Email",null);
        getSupportActionBar().setTitle("Your Bill");
        purchased_item_adapter adapter=new purchased_item_adapter(new showproducts(Billing_Activity.this).show_products_in_cart(new dbhelper(Billing_Activity.this),Username));
        java.util.Calendar calender= java.util.Calendar.getInstance();
        DateTime= String.valueOf(calender.getTime());
        checkout_items_list.setLayoutManager(new LinearLayoutManager(Billing_Activity.this));
        checkout_items_list.setAdapter(adapter);
        itemcount.setText(String.valueOf(adapter.getItemCount()));
        totalamount.setText(String.valueOf(mydb.getTotalOfAmount(Username)));
        subtotal.setText(String.valueOf(mydb.getTotalOfAmount(Username)));
        placeorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              place_order_and_send_email();
            }
        });

    }

public void place_order_and_send_email(){
    Cursor res = mydb.get_products_in_cart(Username);
    if (res.getCount() == 0) {
        Toast.makeText(Billing_Activity.this, "You dont have any items in cart", Toast.LENGTH_LONG).show();
    }else {
        StringBuffer sb = new StringBuffer();
        while (res.moveToNext()) {
            sb.append(res.getString(1) + " ");
            sb.append("x" + res.getInt(4) + " " + "\n");
        }
        new placingorders().place_orders(customername, Username, Address, phone, Email, sb.toString(), String.valueOf(mydb.getTotalOfAmount(Username)), Billing_Activity.this, DateTime);
        new sendmail(Billing_Activity.this, Email, "Your Order has been Recieved", "Dear " + customername + "\n" + "\n" + "You have ordered " + "\n" + "\n" + sb.toString() + "\n" + "\n" + "Your Total bill is Rs " + String.valueOf(mydb.getTotalOfAmount(Username)) + "\n" + "You will recieve Order Soon").execute();
        mydb.delete_all(Username);
        startActivity(new Intent(Billing_Activity.this, MainActivity.class));
        finish();
    }
}
}
