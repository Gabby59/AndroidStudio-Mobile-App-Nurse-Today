package co.uk.nursetoday.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.uk.nursetoday.R;

public class User_Type_Reg extends AppCompatActivity {

    private Button btn_nursesignup, btn_agencysignup;

    private Intent i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_reg);

        btn_nursesignup = findViewById(R.id.btn_nursesignup);
        btn_agencysignup = findViewById(R.id.btn_agencysignup);

     btn_nursesignup.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             i = new Intent(User_Type_Reg.this, Register_Nurse.class);
             startActivity(i);
         }
     });

        btn_agencysignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(User_Type_Reg.this, Register_Agency.class);
                //here we would pass on the message ot the activity to either be INV or VIS
                startActivity(i);
            }
        });

    }
}

