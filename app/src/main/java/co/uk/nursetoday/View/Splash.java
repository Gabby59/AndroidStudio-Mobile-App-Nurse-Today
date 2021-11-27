package co.uk.nursetoday.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import co.uk.nursetoday.R;

public class Splash extends AppCompatActivity {

    private TextView fname, sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        fname = findViewById(R.id.tv_fname_splash);
      //  sname = findViewById(R.id.tv_sname_splash2);
        Bundle b = getIntent().getExtras();

        fname.setText(b.getString("name")); //have to insert real user data in here
        //sname.setText(b.getString("surname")); //have to insert real user data in here

        /****** Create Thread that will sleep for 5 seconds****/
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(5000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                    //Close activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}
