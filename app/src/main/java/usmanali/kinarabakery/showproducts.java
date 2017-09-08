package usmanali.kinarabakery;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SAJIDCOMPUTERS on 3/7/2017.
 */

  class showproducts {

    ArrayList<products> productsArrayList;
    Retrofit retrofit;
    Context context;
    public showproducts(Context context){
    productsArrayList=new ArrayList<products>();
        this.context=context;
    }
    public void show_all_products(final RecyclerView allproductslist, final Context con){
        kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
        Call<ArrayList<products>> call=service.getallproducts();
        call.enqueue(new Callback<ArrayList<products>>() {
            @Override
            public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
                ArrayList<products> allproducts=response.body();
                allproductslist.setAdapter(new adapter(allproducts,con));
            }

            @Override
            public void onFailure(Call<ArrayList<products>> call, Throwable t) {

            }
        });
    }
    public void get_product_by_catorgery(final RecyclerView productsbycatorgeryrecyclerview, final Context con, String Catorgery, final SearchView sv){
        kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
        Call<ArrayList<products>> call=service.getproductsbycatorgery(Catorgery);
        final ProgressDialog pd=new ProgressDialog(con);
        pd.setMessage("Please Wait");
        pd.setCancelable(false);
        pd.show();
        call.enqueue(new Callback<ArrayList<products>>() {
            @Override
            public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
                ArrayList<products> productsbycatorgerylist= response.body();
                pd.dismiss();
                final showmoreproductsadapter sma=new showmoreproductsadapter(productsbycatorgerylist,con);
                productsbycatorgeryrecyclerview.setAdapter(sma);
                sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        sma.getFilter().filter(newText);
                        return true;
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<products>> call, Throwable t) {

            }
        });
    }
    public  void fetch_all_products(final SearchView searchView, final RecyclerView allproductslist, final Context context){
        //for more products page
        kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
        Call<ArrayList<products>> call=service.getallproducts();
        final ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Please Wait");
        pd.setCancelable(false);
        pd.show();
        call.enqueue(new Callback<ArrayList<products>>() {
            @Override
            public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
                ArrayList<products> allproducts=response.body();
                pd.dismiss();
                final showmoreproductsadapter sma=new showmoreproductsadapter(allproducts,context);
                allproductslist.setAdapter(sma);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        sma.getFilter().filter(newText);
                        return true;
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<products>> call, Throwable t) {
                Log.e("Retrofit","Unable to fetch data");
            }
        });
    }
    public ArrayList<products> show_products_in_cart(dbhelper mydb,String Username){
        ArrayList<products> productsincart=new ArrayList<>();
        Cursor res = mydb.get_products_in_cart(Username);
        if (res.getCount() == 0) {
            Toast.makeText(context,"No Products in Cart",Toast.LENGTH_LONG) .show();
        }
        while (res.moveToNext()) {
            products p=new products(res.getString(1),res.getInt(2),res.getInt(0),res.getString(3),res.getInt(4),res.getInt(6));
            productsincart.add(p);
        }
        return productsincart;
    }
    public void show_product_by_catorgery(final RecyclerView productsbycatorgeryrecyclerview, final Context con, String Catorgery){
        kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
        Call<ArrayList<products>> call=service.getproductsbycatorgery(Catorgery);
        call.enqueue(new Callback<ArrayList<products>>() {
            @Override
            public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
                ArrayList<products> productsbycatorgerylist= response.body();
                if(productsbycatorgerylist.size()>=0) {
                    productsbycatorgeryrecyclerview.setAdapter(new adapter(productsbycatorgerylist, con));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<products>> call, Throwable t) {

            }
        });
    }
    public void increment_quantity(String quantity, String productid, final Context context){
        kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
        Call<String> call=service.increment_quantity(quantity,productid);
        final ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Please Wait");
        pd.setCancelable(false);
        pd.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                pd.dismiss();
                Toast.makeText(context, response.body(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void decrement_quantity(String quantity, String productid, final Context context){
        kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
        Call<String> call=service.decrement_quantity(quantity,productid);
        final ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Please Wait");
        pd.setCancelable(false);
        pd.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                pd.dismiss();
                Toast.makeText(context, response.body(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
