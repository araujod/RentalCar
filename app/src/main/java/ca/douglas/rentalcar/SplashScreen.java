package ca.douglas.rentalcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent i;
        /*if(helper.getEmailLoggedUser() != null)
            i = new Intent(this, Home.class);
        else
            i = new Intent(this, Welcome.class);
*/
        /*try {
            Thread.sleep(5000);
        }
        catch(Exception e){

        }*/
        i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
