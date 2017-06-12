package usmanali.kinarabakery;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static usmanali.kinarabakery.R.id.tableView;

public class Track_orders extends AppCompatActivity {
    String[] tableheaders={"Orderid","Items","Price","Orderdatetime"};
    String[][] tabledata;
    TableView<String[]> tb;
    String Username;
    kinarabakeryservice service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Track_orders.this);
        Username = prefs.getString("Username", "Guest");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tb = (TableView<String[]>) findViewById(tableView);
        tb.setColumnCount(4);
        tb.setHeaderBackgroundColor(Color.TRANSPARENT);
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(Track_orders.this,tableheaders));
        track(Username);
    }
public void track(String Username){
    service=apiclient.getClient().create(kinarabakeryservice.class);
    Call<ArrayList<orders>> call =service.track_orders(Username);
    call.enqueue(new Callback<ArrayList<orders>>() {
        @Override
        public void onResponse(Call<ArrayList<orders>> call, Response<ArrayList<orders>> response) {
            ArrayList<orders> ordersdetail=response.body();
            tabledata= new String[ordersdetail.size()][4];
            orders orderinfo;
            for(int i=0; i<ordersdetail.size();i++){
                orderinfo=ordersdetail.get(i);
                tabledata[i][0]= String.valueOf(orderinfo.getOrderid());
                tabledata[i][1]= orderinfo.getItems();
                tabledata[i][2] = orderinfo.getPrice();
                tabledata[i][3]= orderinfo.getOrderdatetime();
                tb.setDataAdapter(new SimpleTableDataAdapter(Track_orders.this,tabledata));
            }
        }

        @Override
        public void onFailure(Call<ArrayList<orders>> call, Throwable t) {
            Log.e("Ordertracking",t.toString());
        }
    });
}
}
