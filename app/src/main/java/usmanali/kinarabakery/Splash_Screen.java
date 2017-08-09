package usmanali.kinarabakery;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i=new Intent(Splash_Screen.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 10000);
    }
    }
