package usmanali.kinarabakery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_products_activity extends AppCompatActivity implements View.OnClickListener {

    Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 100;
    private Uri filepath;
    @BindView(R.id.productcatorgeryselector)Spinner catorgery;
    @BindView(R.id.productimg) ImageView productimg;
    @BindView(R.id.productnametxt) EditText name;
    @BindView(R.id.pricetxt) EditText price;
    @BindView(R.id.productquantitytxt) EditText quantity;
    @BindView(R.id.productweighttxt) EditText productweight;
    @BindView(R.id.upload_btn) Button insertbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_activity);
        ButterKnife.bind(this);
        insertbtn.setOnClickListener(this);
        productimg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_btn:
                add_data();
                break;
            case R.id.productimg:
                choose_img();
                break;
        }
    }

    public void choose_img() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null) {
            filepath=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                productimg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void add_data(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        String productname=name.getText().toString();
        String productprice=price.getText().toString();
        String Quantity=quantity.getText().toString();
        String Catorgery=catorgery.getSelectedItem().toString();
        String product_weight=productweight.getText().toString();
        kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
        Call<String> call=service.insertproducts(productname,productprice,Catorgery,Quantity,encodedImage,product_weight);
        final ProgressDialog pd=new ProgressDialog(add_products_activity.this);
        pd.setMessage("Please Wait");
        pd.setCancelable(false);
        pd.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String status=response.body();
                pd.dismiss();
                Toast.makeText(add_products_activity.this,status,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}
