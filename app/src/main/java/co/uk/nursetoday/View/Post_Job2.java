package co.uk.nursetoday.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.uk.nursetoday.Model.Job_Ad;
import co.uk.nursetoday.Agency_Posted_Jobs;
import co.uk.nursetoday.R;

public class Post_Job2 extends AppCompatActivity {

    Spinner nurseCat, locationCat;
    ArrayAdapter<String> nurseCatAdapter, locationCatAdapter;
    EditText title, agency, description;
    Button post;
    TextView error;
    String url, jobId;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__job2);

        title = findViewById(R.id.et_jobTitle_postJob2);
        agency = findViewById(R.id.et_agency_postJob2);
        description = findViewById(R.id.et_jobDescription_postJob2);
        post = findViewById(R.id.btn_post_postJob2);
        error = findViewById(R.id.tv_error_postJob2);
        nurseCat = findViewById(R.id.spinner_nurseCategory_postJob2);
        locationCat = findViewById(R.id.spinner_locationCategory_postJob2);
        Bundle extra = getIntent().getExtras(); //retrieve "putExtra" from previsous activity
        url = extra.getString("url");
        jobId = extra.getString("id");
        Resources res = getResources();


        //send the values of the array to the spinner
        nurseCatAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, res.getStringArray(R.array.nurseCategory));
        nurseCat.setAdapter(nurseCatAdapter);

        locationCatAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                res.getStringArray(R.array.locationCategory));
        locationCat.setAdapter(locationCatAdapter);


        post.setOnClickListener(v -> {
            if (title.getText().toString().isEmpty() || description.getText().toString().isEmpty() || agency.getText().toString().isEmpty()
                    || nurseCat.getSelectedItemId() == 0|| locationCat.getSelectedItemId() == 0) {
                error.setText("Please fill in all fields!!");
                error.setVisibility(View.VISIBLE);
            } else {
                dbref = FirebaseDatabase.getInstance().getReference("Job_Vacancies");
                Job_Ad jobs = new Job_Ad(title.getText().toString(), agency.getText().toString(),
                        description.getText().toString(), nurseCat.getSelectedItem().toString(),
                        locationCat.getSelectedItem().toString(), url, true);
                        //Session.LiveSession.user.getUsername(), true);
                dbref.child(jobId).setValue(jobs);
                Toast.makeText(Post_Job2.this, "Job Successfully Posted", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Post_Job2.this, Agency_Posted_Jobs.class);
                i.putExtra("ITEM", "Job_Ad");
                i.putExtra("ITEM2", 2);
                startActivity(i);
            }});


    }
}