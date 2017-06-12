package usmanali.kinarabakery;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by SAJIDCOMPUTERS on 4/13/2017.
 */

public interface kinarabakeryservice {
    @GET("get_all_products.php")
    Call<ArrayList<products>> getallproducts();
    @GET("get_rusk.php")
    Call<ArrayList<products>> get_rusks();
    @GET("get_deserts.php")
    Call<ArrayList<products>> get_cakes();
    @FormUrlEncoded
    @POST("get_products_by_catorgery.php")
    Call<ArrayList<products>> getproductsbycatorgery(@Field("catorgery")String catorgery);
    @FormUrlEncoded
    @POST("place_orders.php")
    Call<String> place_orders(@Field("Name")String name,@Field("Username")String username,@Field("Phone")String phone,@Field("Address")String address,@Field("Email")String email,@Field("itemname")String itemname,@Field("Price")String price,@Field("DateTime")String DateTime);
    @FormUrlEncoded
    @POST("Signup.php")
    Call<String> signup(@Field("Name")String Name,@Field("Username")String Username,@Field("Password")String Password,@Field("Phone")String Phone,@Field("Address")String Address,@Field("Email")String Email);
    @FormUrlEncoded
    @POST("customerlogin.php")
    Call<ArrayList<user>> login(@Field("Username")String Username,@Field("Password")String Password);
    @FormUrlEncoded
    @POST("insert_products_to_db.php")
    Call<String> insertproducts(@Field("productname")String productname,@Field("price") String price,@Field("catorgery")String catorgery,@Field("quantity")String quantity,@Field("image")String image);
    @GET("get_methai.php")
    Call<ArrayList<products>> get_methai();
    @FormUrlEncoded
    @POST("track_orders.php")
    Call<ArrayList<orders>> track_orders(@Field("Username")String Username);
    @GET("getbreadandbuns.php")
    Call<ArrayList<products>> get_breadsandbuns();
}
