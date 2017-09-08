package usmanali.kinarabakery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class signupactivity extends AppCompatActivity {
    @BindView(R.id.textInputLayoutname) TextInputLayout name;
    @BindView(R.id.textInputLayoutusername) TextInputLayout Username;
    @BindView(R.id.textInputLayoutpassword) TextInputLayout Password;
    @BindView(R.id.textInputLayoutphone) TextInputLayout Phone;
    @BindView(R.id.textInputLayoutaddress) TextInputLayout Address;
    @BindView(R.id.textInputLayoutemail) TextInputLayout Email;
    @BindView(R.id.textInputLayoutconfirmpassword) TextInputLayout confirmpassword;
    @BindView(R.id.etname) EditText etname;
    @BindView(R.id.etusername) EditText etusername;
    @BindView(R.id.etpassword) EditText etpassword;
    @BindView(R.id.etphone)  EditText etphone;
    @BindView(R.id.etaddress) EditText etaddress;
    @BindView(R.id.etemail) EditText etemail;
    @BindView(R.id.etconfirmpassword) EditText etconfirmpassword;
    @BindView(R.id.loginbtn) Button Login;
    @BindView(R.id.siguup) Button signup;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ActivityOptionsCompat optionsCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        ButterKnife.bind(this);
        optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(signupactivity.this,null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

 @OnClick(R.id.siguup)  public void signupbtnclick() {
     if (etname.getText().toString().isEmpty())
         name.setError("Please Enter Your Name");
        else if (etusername.getText().toString().isEmpty())
             Username.setError("Please Enter Your Username");
        else if (etpassword.getText().toString().isEmpty())
             Password.setError("Please Enter Your Password");
        else if (etphone.getText().toString().isEmpty())
             Phone.setError("Please Enter Your Phone Number");
        else if (etaddress.getText().toString().isEmpty())
             Address.setError("Please Enter Your Address");
       else  if (etemail.getText().toString().isEmpty())
             Email.setError("Please Enter Your Email");
         else if (etconfirmpassword.getText().toString().isEmpty())
             confirmpassword.setError("Please Confirm your Password");
        else if (!etpassword.getText().toString().equals(etconfirmpassword.getText().toString()))
             confirmpassword.setError("Passwords Do not Match");
         else {
             signup(etname.getText().toString(), etusername.getText().toString(), etconfirmpassword.getText().toString(), etphone.getText().toString(), etaddress.getText().toString(), etemail.getText().toString(),signupactivity.this);
         }
     }

    public  void signup(String name, String username, String password, String phone, String address, String Email, final Context context){
        kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
        Call<String> call=service.signup(name,username,password,phone,address,Email);
        final ProgressDialog pd=new ProgressDialog(signupactivity.this);
        pd.setMessage("Please Wait");
        pd.setCancelable(false);
        pd.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String status=response.body();
                pd.dismiss();
                Toast.makeText(signupactivity.this,status,Toast.LENGTH_LONG).show();
                if(status.equals("Registration Sucess")){
                    ActivityOptionsCompat optionsCompat =ActivityOptionsCompat.makeSceneTransitionAnimation(signupactivity.this,null);
                    startActivity(new Intent(signupactivity.this,LoginActivity.class),optionsCompat.toBundle());
                    finish();
                }

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    @OnClick(R.id.loginbtn) public void goto_login_activity(){
        startActivity(new Intent(signupactivity.this,LoginActivity.class),optionsCompat.toBundle());
    }
}
