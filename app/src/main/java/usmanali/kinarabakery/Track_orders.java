package usmanali.kinarabakery;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Track_orders extends AppCompatActivity {
 @BindView(R.id.orderslist) RecyclerView orderlist;
    @BindView(R.id.toolbar) Toolbar toolbar;
    String Username;
    kinarabakeryservice service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders);
        ButterKnife.bind(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Track_orders.this);
        Username = prefs.getString("Username", "Guest");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderlist.setLayoutManager(new GridLayoutManager(this,2));
        track(Username);
    }
public void track(String Username){
    service=apiclient.getClient().create(kinarabakeryservice.class);
    Call<ArrayList<orders>> call =service.track_orders(Username);
    final ProgressDialog pd=new ProgressDialog(Track_orders.this);
    pd.setMessage("Please Wait");
    pd.show();
    call.enqueue(new Callback<ArrayList<orders>>() {
        @Override
        public void onResponse(Call<ArrayList<orders>> call, Response<ArrayList<orders>> response) {
            ArrayList<orders> ordersdetail=response.body();
            pd.dismiss();
            if(ordersdetail.size()>0) {
                orderlist.setAdapter(new show_orders_adapter(ordersdetail, Track_orders.this));
            }else{
                Toast.makeText(Track_orders.this,"No Order history found",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ArrayList<orders>> call, Throwable t) {
            Log.e("Ordertracking",t.toString());
        }
    });
}
}
