package usmanali.kinarabakery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class customerprofileactivity extends AppCompatActivity {
   @BindView(R.id.user_profile_name)
    TextView customername;
    @BindView(R.id.user_profile_username)
    TextView username;
    @BindView(R.id.user_profile_photo)
    ImageView profilepic;
    @BindView(R.id.email) TextView emailtv;
    @BindView(R.id.location) TextView Addresstv;
    @BindView(R.id.phonenumber) TextView phonetv;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collasptoolbar;
    @BindView(R.id.edit_profile_btn)Button edit_profile;
    String nameofcustomer, Email,Username,Address,Phone;
    Boolean Islogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerprofileactivity);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       collasptoolbar.setTitle(" ");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(customerprofileactivity.this);

        Islogin = prefs.getBoolean("Islogin", false);
        nameofcustomer = prefs.getString("Name", "Guest");
       Email = prefs.getString("Email", "Guest");
      Username=  prefs.getString("Username","Guest");
      Address=  prefs.getString("Address","Guest");
      Phone= prefs.getString("Phone","Guest");
       customername.setText(nameofcustomer);
        username.setText("@"+Username);
        emailtv.setText(Email);
        phonetv.setText(Phone);
       Addresstv.setText(Address);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(customerprofileactivity.this, null);
                startActivity(new Intent(customerprofileactivity.this, Edit_Profile_Activity.class), optionsCompat.toBundle());
            }
        });
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
        int color1 = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRect(nameofcustomer.substring(0, 1), color1);
        profilepic.setImageDrawable(drawable);
    }
}
