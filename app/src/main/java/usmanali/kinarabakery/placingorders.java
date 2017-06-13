package usmanali.kinarabakery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SAJIDCOMPUTERS on 3/31/2017.
 */

public class placingorders {
    Retrofit retrofit;
    kinarabakeryservice service;
placingorders(){
   // retrofit=new Retrofit.Builder().baseUrl("http://192.168.1.5/").addConverterFactory(GsonConverterFactory.create()).build();
    //service=retrofit.create(kinarabakeryservice.class);
}
  public  void place_orders(String Name, String Username, String Address, String Phone, String Email, String itemname, String price, final Context context,String DateTime){

      kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
      Call<String>  call=service.place_orders(Name,Username,Phone,Address,Email,itemname,price,DateTime);
      call.enqueue(new Callback<String>() {
          @Override
          public void onResponse(Call<String> call, Response<String> response) {
           String status=response.body();
              Toast.makeText(context,status,Toast.LENGTH_LONG).show();
          }

          @Override
          public void onFailure(Call<String> call, Throwable t) {
              Log.e("orderplacingFailure",t.toString());
          }
      });
  }
}

