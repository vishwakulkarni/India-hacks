package bullstock.com.bullstock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.TypefaceProvider;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TypefaceProvider.registerDefaultIconSets();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SharedPreferences credentialsSharedPref = getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE);
                Intent mainIntent;

                if (credentialsSharedPref.contains("username")) {
                    mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    mainIntent = new Intent(getApplicationContext(),Login_activity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
