package co.uk.nursetoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

import co.uk.nursetoday.Adapters.JobAdapter;
import co.uk.nursetoday.Application.Session;
import co.uk.nursetoday.Model.GenericList;
import co.uk.nursetoday.Model.Job_Ad;

public class Agency_Posted_Jobs extends AppCompatActivity implements JobAdapter.JobHolder.recyclerInterface {

    private RecyclerView rv;
    private TextView title;
    private Button myApplications;

    private RecyclerView.LayoutManager manager;
    private JobAdapter myJobAdapter;

    private Bundle Extras;
    private Intent i;
    private String jobId;
    private Integer item2;
    private DatabaseReference dbref;

    private ArrayList<GenericList> list = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<Boolean> available = new ArrayList<>();

    public static ConstraintLayout constraintLayout;
    public static View view;

    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_posted__jobs);


        //DESIGN COMPONENTS
        title = findViewById(R.id.tv_title_posted_jobs);
       // sell = findViewById(R.id.btn_sellBook_itemList);
        //btnmsgs = findViewById(R.id.btn_messages_itemList);
        //myBooks = findViewById(R.id.btn_myBooks_itemList);
        //booksforSale = findViewById(R.id.btn_bookSale_itemList);
        //topic = findViewById(R.id.btn_topic_itemList);

        myApplications = findViewById(R.id.btn_myApplications_posted_jobs);

        //RECYCLER VIEW COMPONENTS
        rv = findViewById(R.id.rv_jobslist);
        manager = new LinearLayoutManager(Agency_Posted_Jobs.this);
        rv.setLayoutManager(manager);

        //Database Reference gets node name from last activity
        Extras = getIntent().getExtras();
        jobId = Extras.getString("ITEM");
        item2 = Extras.getInt("ITEM2");
        dbref = FirebaseDatabase.getInstance().getReference().child(jobId);
        dbref.addListenerForSingleValueEvent(listener);


        //TOOL BAR FOR MESSAGE DELETE UNDO
//        setSupportActionBar(toolbar);
        view = findViewById(R.id.coordinatorLayout);


       /* sell.setOnClickListener(v -> {
            Intent i = new Intent(Books_for_sale_List.this, Post_Book.class);
            startActivity(i);
        });
        btnmsgs.setOnClickListener(v -> {
            Intent i = new Intent(Books_for_sale_List.this, Books_for_sale_List.class);
            i.putExtra("ITEM", "Messages");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });

        */
        myApplications.setOnClickListener(v -> {
            Intent i = new Intent(Agency_Posted_Jobs.this, Agency_Posted_Jobs.class);
            i.putExtra("ITEM", "Job_Vacancies");
            i.putExtra("ITEM2", 2);
            startActivity(i);
        });
      /*  booksforSale.setOnClickListener(v -> {
            Intent i = new Intent(Books_for_sale_List.this, Books_for_sale_List.class);
            i.putExtra("ITEM", "Books_for_Sale");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });*/

    }


    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (item2 == 2) {
                for (DataSnapshot dss: snapshot.getChildren()) {
                    //shows all jobs posted by the agency
                    if ((dss.getValue(Job_Ad.class).getUsername()).equals(Session.LiveSession.user.getUsername())) {
                        list.add(dss.getValue(Job_Ad.class));
                        available.add(dss.getValue(Job_Ad.class).getAvaiable());
                        keys.add(dss.getKey());
                    }
                }
                myJobAdapter = new JobAdapter( list, Agency_Posted_Jobs.this);
                rv.setAdapter(myJobAdapter);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    @Override
    public void onItemClick(int i) {

    }
}