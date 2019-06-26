package ca.douglas.rentalcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;

public class MainActivity extends AppCompatActivity {

    private  MyDBConnection myCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //THIS IS A TEST

        //this is from william



        myCustomers= new MyDBConnection();
        Button loginBtn= (Button)findViewById(R.id.btnLogin);
        //existed customer , click button to login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verify the customer via password and email
                setDefaultData();


            }
        });

        Button registerBtn=(Button)findViewById(R.id.btnRegister);
        //as new customer, click the button to register
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create new customer

            }
        });

    }



    private void setDefaultData() {

        CollectionReference car_customer=myCustomers.getCollectionReference("myTest");

        String []id={
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10"
        };
        String []customerName={
                "Ralph Weatherhogg",
                "Consuelo Albone",
                "Hamel Ronald",
                "Elvis Fudge",
                "Amalee Peffer",
                "Cheslie Bedwell",
                "Esmeralda Mabbot",
                "Sonja Bew",
                "Job Frissell",
                "Mahmud Mackie"
        };

        String [] email={
                "brushbury0@eventbrite.com",
                "khorsey1@dion.ne.jp",
                "bblackshaw2@aol.com",
                "tcaccavari3@ihg.com",
                "mduddridge4@senate.gov",
                "ksnyder5@squidoo.com",
                "emactrustam6@si.edu",
                "rfogg7@livejournal.com",
                "dstorrock8@ebay.co.uk",
                "spuddefoot9@icio.us"

        };

        String [] gender={
                "Male",
                "Female",
                "Female",
                "Female",
                "Female",
                "Male",
                "Female",
                "Male",
                "Male",
                "Male"

        };


        String [] phone={
                "392-122-2738",
                "786-903-8068",
                "433-426-3397",
                "100-988-9758",
                "629-298-9042",
                "621-790-7407",
                "823-343-1119",
                "431-926-1785",
                "654-775-8450",
                "178-480-9752"
        };



        // Map<String,Customer> data;
        for (int i=0 ; i < email.length;i++) {
            // data = new HashMap<>();
            Customer n= new Customer();
            n.customerName=customerName[i];
            n.eamil=email[i];
            n.gender=gender[i];
            n.phone=phone[i];


            //data.put("Car_Customer", n);

            car_customer.document(id[i]).set(n);



        }

    }

}
