package usmanali.kinarabakery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Profile_Activity extends AppCompatActivity {
    @BindView(R.id.edit_profile_btn)Button edit_profile;
    String nameofcustomer, email,username,address,phone,password;
    Boolean Islogin;
    int Id;
    @BindView(R.id.nametextinputlayout)
    TextInputLayout name;
    @BindView(R.id.usernametextinputlayout) TextInputLayout Username;
    @BindView(R.id.useridtextinputlayout) TextInputLayout userid;
    @BindView(R.id.phonetextinputlayout) TextInputLayout Phone;
    @BindView(R.id.addresstextinputlayout) TextInputLayout Address;
    @BindView(R.id.emailtextinputlayout) TextInputLayout Email;
    @BindView(R.id.newpasswordtextinputlayout) TextInputLayout newpassword;
    @BindView(R.id.nametxt) TextInputEditText etname;
    @BindView(R.id.usernametxt) TextInputEditText etusername;
    @BindView(R.id.useridtxt) TextInputEditText etuserid;
    @BindView(R.id.phonetxt)  TextInputEditText etphone;
    @BindView(R.id.addresstxt) TextInputEditText etaddress;
    @BindView(R.id.emailtxt) TextInputEditText etemail;
    @BindView(R.id.newpasswordtxt) TextInputEditText etnewpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile_);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Edit_Profile_Activity.this);
        Islogin = prefs.getBoolean("Islogin", false);
        nameofcustomer = prefs.getString("Name", "Guest");
        email = prefs.getString("Email", "Guest");
        username=  prefs.getString("Username","Guest");
        address=  prefs.getString("Address","Guest");
        phone= prefs.getString("Phone","Guest");
        password=prefs.getString("Password","Guest");
        Id=prefs.getInt("Id",0);
        Log.d("Id",String.valueOf(Id));
        //setting the values
        etname.setText(nameofcustomer);
        etemail.setText(email);
        etphone.setText(phone);
        etusername.setText(username);
        etaddress.setText(address);
        etnewpassword.setText(password);
        etuserid.setText(String.valueOf(Id));
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (etname.getText().toString().isEmpty())
                    name.setError("Please Enter Your Name");
                else if (etusername.getText().toString().isEmpty())
                    Username.setError("Please Enter Your Username");
                else if (etphone.getText().toString().isEmpty())
                    Phone.setError("Please Enter Your Phone Number");
                else if (etaddress.getText().toString().isEmpty())
                    Address.setError("Please Enter Your Address");
                else  if (etemail.getText().toString().isEmpty())
                    Email.setError("Please Enter Your Email");
                else if (etnewpassword.getText().toString().isEmpty()) {
                    newpassword.setError("Please Enter your new Password");
                }else{
                    edit_profile(etname.getText().toString(),etusername.getText().toString(),etnewpassword.getText().toString(),etemail.getText().toString(),etaddress.getText().toString(),etphone.getText().toString(),String.valueOf(Id));
                }
            }
        });
    }
public void edit_profile(String name,String username,String password,String email,String address,String phone,String id){
    kinarabakeryservice service=apiclient.getClient().create(kinarabakeryservice.class);
    Call<String> call=service.edit_profile(name,username,password,email,address,phone,id);
    final ProgressDialog pd=new ProgressDialog(Edit_Profile_Activity.this);
    pd.setMessage("Please Wait");
    pd.setCancelable(false);
    pd.show();
    call.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String status=response.body();
            pd.dismiss();
            Toast.makeText(Edit_Profile_Activity.this,status,Toast.LENGTH_LONG).show();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Edit_Profile_Activity.this);
            prefs.edit().remove("Islogin").commit();
            prefs.edit().remove("Name").commit();
            prefs.edit().remove("Username").commit();
            prefs.edit().remove("Email").commit();
            prefs.edit().remove("Address").commit();
            prefs.edit().remove("Phone").commit();
            prefs.edit().remove("Password").commit();
            prefs.edit().remove("Id").commit();
            ActivityOptionsCompat optionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation(Edit_Profile_Activity.this,null);
            startActivity(new Intent(Edit_Profile_Activity.this,LoginActivity.class),optionsCompat.toBundle());
            finish();
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Toast.makeText(Edit_Profile_Activity.this,t.toString(),Toast.LENGTH_LONG).show();
        }
    });
}
}

