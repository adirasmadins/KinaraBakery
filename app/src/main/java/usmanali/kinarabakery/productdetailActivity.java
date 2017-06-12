package usmanali.kinarabakery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class productdetailActivity extends AppCompatActivity {
    String imageurl, productname, catorgery,id;
    StringBuffer productinformation;
     int quantity,price;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.productimage)
    ImageView productimage;
    @BindView(R.id.productinformation)
    TextView productinfo;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout ctl;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<products> productsArrayList;
    products p;
    Intent i;
    dbhelper mydb;
    Boolean Islogin;
   String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ctl.setTitle(" ");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(productdetailActivity.this);
        Islogin = prefs.getBoolean("Islogin", false);
        Username=prefs.getString("Username"," ");
        imageurl = getIntent().getStringExtra("productimage");
        productname = getIntent().getStringExtra("name");
        price = getIntent().getIntExtra("price",0);
        catorgery=getIntent().getStringExtra("catorgery");
        id=String.valueOf(getIntent().getIntExtra("Productid",0));
        quantity=getIntent().getIntExtra("Quantity",1);
        Picasso.with(productdetailActivity.this).load(imageurl).into(productimage);
        productinformation = new StringBuffer();
        productinformation.append("ID                         "+id+"\n");
        productinformation.append("Name                       " + productname + "\n");
        productinformation.append("Price                      " + "Rs " + String.valueOf(price)+"\n");
        productinformation.append("Catorgery                  "+ catorgery+"\n");
        productinfo.setText(productinformation.toString());
        mydb = new dbhelper(productdetailActivity.this);
    }

    @OnClick(R.id.fab)
    public void add_to_cart() {
        if (Islogin){
            final AlertDialog.Builder quantityselectiondialog = new AlertDialog.Builder(productdetailActivity.this);
        quantityselectiondialog.setTitle("Select Quantity");
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.alertdialoglayout, null);
        quantityselectiondialog.setView(v);
        final NumberPicker quantitypicker = (NumberPicker) v.findViewById(R.id.selectquantity);
        quantitypicker.setMaxValue(quantity);
        quantitypicker.setMinValue(1);
        quantityselectiondialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int Price = price * quantitypicker.getValue();
                Boolean isinserted = mydb.insert_product_toshoppingcart(productname, Price, imageurl,quantitypicker.getValue(),Username);
                if (isinserted) {
                    Toast.makeText(productdetailActivity.this, "Product Added to cart", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(productdetailActivity.this, "Product not Added to cart", Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        quantityselectiondialog.show();
    }else{
            Toast.makeText(productdetailActivity.this,"You are not logged in",Toast.LENGTH_LONG).show();
        }
}
}




