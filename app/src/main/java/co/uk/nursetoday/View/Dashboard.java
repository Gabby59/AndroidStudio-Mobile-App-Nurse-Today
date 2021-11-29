package co.uk.nursetoday.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.uk.nursetoday.Agency_Posted_Jobs;
import co.uk.nursetoday.Forum_New_Topic;
import co.uk.nursetoday.R;

public class Dashboard extends AppCompatActivity {


    Button jobBoard, forum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        jobBoard = findViewById(R.id.btn_jobsBoard_dashboard);
        forum = findViewById(R.id.btn_forum_dashboard);



        //navigate the user to the Job_Board
        jobBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, Agency_Posted_Jobs.class);
                i.putExtra("ITEM", "Job_Ad");
                i.putExtra("ITEM2", 1);
                startActivity(i);
            }
        });

        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, Forum_New_Topic.class);
                i.putExtra("ITEM", "Job_Ad");
                i.putExtra("ITEM2", 1);
                startActivity(i);
            }
        });


    }
}