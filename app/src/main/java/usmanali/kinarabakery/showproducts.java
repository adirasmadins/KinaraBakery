package usmanali.kinarabakery;

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
    kinarabakeryservice service;
    Context context;

    public showproducts(Context context){
    productsArrayList=new ArrayList<products>();
         service=apiclient.getClient().create(kinarabakeryservice.class);
        this.context=context;
    }
    public void show_list_of_rusks(final RecyclerView productslist, final Context c){
        Call<ArrayList<products>> call=service.get_rusks();
        call.enqueue(new Callback<ArrayList<products>>() {
            @Override
            public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
               ArrayList<products> productsArrayList=response.body();
                productslist.setAdapter( new adapter(productsArrayList,c));
            }

            @Override
            public void onFailure(Call<ArrayList<products>> call, Throwable t) {
                Log.e("Retrofit",t.toString());
            }
        });
    }
    public void show_list_of_deserts(final RecyclerView cakeslist, final Context con){
        Call<ArrayList<products>> call=service.get_cakes();
        call.enqueue(new Callback<ArrayList<products>>() {
            @Override
            public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
                ArrayList<products>listofcakes=response.body();
                cakeslist.setAdapter(new adapter(listofcakes,con));
            }

            @Override
            public void onFailure(Call<ArrayList<products>> call, Throwable t) {

            }
        });
    }
    public void show_all_products(final RecyclerView allproductslist, final Context con){
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
        Call<ArrayList<products>> call=service.getproductsbycatorgery(Catorgery);
        call.enqueue(new Callback<ArrayList<products>>() {
            @Override
            public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
                ArrayList<products> productsbycatorgerylist= response.body();
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
    public   void fetch_all_products(final SearchView searchView, final RecyclerView allproductslist, final Context context){//for more products page
        Call<ArrayList<products>> call=service.getallproducts();
        call.enqueue(new Callback<ArrayList<products>>() {
            @Override
            public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
                ArrayList<products> allproducts=response.body();
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
            products p=new products(res.getString(1),res.getInt(2),res.getInt(0),res.getString(3),res.getInt(4));
            productsincart.add(p);
        }
        return productsincart;
    }
public void show_list_of_methai(final RecyclerView methailist, final Context context) {
    Call<ArrayList<products>> call = service.get_methai();
    call.enqueue(new Callback<ArrayList<products>>() {
        @Override
        public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
            ArrayList<products> methai = response.body();
            methailist.setAdapter(new adapter(methai, context));
        }

        @Override
        public void onFailure(Call<ArrayList<products>> call, Throwable t) {
            Log.e("get_methai", t.toString());
        }
    });
}
public void show_list_of_breadandbuns(final RecyclerView breadandbunslist, final Context context){
    Call<ArrayList<products>> call=service.get_breadsandbuns();
    call.enqueue(new Callback<ArrayList<products>>() {
        @Override
        public void onResponse(Call<ArrayList<products>> call, Response<ArrayList<products>> response) {
            ArrayList<products> breadsandbunslist=response.body();
            breadandbunslist.setAdapter(new adapter(breadsandbunslist,context));
        }

        @Override
        public void onFailure(Call<ArrayList<products>> call, Throwable t) {
            Log.e("breads",t.toString());
        }
    });
}

}
