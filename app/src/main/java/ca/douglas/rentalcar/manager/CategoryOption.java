package ca.douglas.rentalcar.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.douglas.rentalcar.R;

public class CategoryOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_option);


        Button btnAddNewCat = (Button)findViewById(R.id.btnAddNewCat);
        Button btnEditCat = (Button)findViewById(R.id.btnEditCat);

        btnAddNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(CategoryOption.this, AddCategory.class);
                startActivity(i);
            }
        });

        btnEditCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(CategoryOption.this, EditCategory.class);
                startActivity(i);
            }
        });

    }
}
