package ca.douglas.rentalcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;

import java.security.NoSuchAlgorithmException;

import ca.douglas.rentalcar.DB.MyDBConnection;
import ca.douglas.rentalcar.entity.User;
import ca.douglas.rentalcar.manager.MainManager;
import ca.douglas.rentalcar.sales.MainSales;
import ca.douglas.rentalcar.user.MainUser;

public class MainActivity extends AppCompatActivity {

    private MyDBConnection myCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCustomers = new MyDBConnection();

        //to create managers and salesmen
        //setDefaultData();

        Button loginBtn= (Button)findViewById(R.id.btnSignIn);
        //existed customer , click button to login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verify the customer via password and email
                Intent i;
                i = new Intent(MainActivity.this, LogIn.class);
                startActivity(i);

            }
        });

        Button registerBtn=(Button)findViewById(R.id.btnSignUp);
        //as new customer, click the button to register
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create new customer
                Intent i;
                i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });

    }

    private void setDefaultData() {

        // e-mail should be name + "@gmail.com";
        // Address should be 3 last phone digits + customer name "street Vancouver BC"
        // License should be 5 first character of name + last four digits o phone + 0.
        // Password should be igual name hash code
        String[] customerName = {"Alexandre", "Andre", "David", "William", "Eduardo", "Priscila", "Carla"};
        String[] customerPhone = {"604-440-4567","778-837-3317","778-751-5678","778-751-9854","778-837-3456","778-837-7831"};
        String UID;

        User myUser = new User();
        MainUser myMainUser = new MainUser();

        //create 3 new managers 0 , 1 and 2
        for (int ind = 0; ind < 3; ind++){
            UID = "";
            for (int i=0 ; i < (int)(Math.random()*5) + 5; i++)
                UID += (char)((Math.random()*26) + 'A');

            myUser.setId(UID);
            myUser.setCustomerName(customerName[ind]);
            myUser.setEmail(customerName[ind] + "@gmail.com");
            myUser.setPhone(customerPhone[ind]);
            myUser.setAddress(customerPhone[ind].substring(9) + " " + customerName[ind] + " street, Vancouver BC");
            myUser.setDriverLicense(customerName[ind].substring(0,5)+customerPhone[ind].substring(8)+"0");
            try{
                myUser.setPwd(myMainUser.getMd5(customerName[ind]));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            myUser.setType("manager");
            //add usee to database
            myMainUser.AddData(myUser);
        }

        //create 3 new salesmen 3 , 4 and 5
        for (int ind = 3; ind < 6; ind++){
            UID = "";
            for (int i=0 ; i < (int)(Math.random()*5) + 5; i++)
                UID += (char)((Math.random()*26) + 'A');

            myUser.setId(UID);
            myUser.setCustomerName(customerName[ind]);
            myUser.setEmail(customerName[ind] + "@gmail.com");
            myUser.setPhone(customerPhone[ind]);
            myUser.setAddress(customerPhone[ind].substring(9) + " " + customerName[ind] + " street, Vancouver BC");
            myUser.setDriverLicense(customerName[ind].substring(0,5)+customerPhone[ind].substring(8)+"0");
            try{
                myUser.setPwd(myMainUser.getMd5(customerName[ind]));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            myUser.setType("manager");
            //add usee to database
            myMainUser.AddData(myUser);
        }
    }
}
