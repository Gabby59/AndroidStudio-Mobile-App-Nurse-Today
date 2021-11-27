package co.uk.nursetoday.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.uk.nursetoday.Model.Job_Ad;
import co.uk.nursetoday.Model.Users;
import co.uk.nursetoday.R;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder>{

    ArrayList <Job_Ad> list;
    JobHolder.recyclerInterface listener;

    public JobAdapter(ArrayList list, JobHolder.recyclerInterface _listener) {
        this.list = list;
        listener = _listener;
    }


    @NonNull
    @Override
    public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       //the jobcard item is inflated into the holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobcard, parent,false);
        JobHolder holder = new JobHolder(v, listener);
        //return the holder
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobHolder holder, int i) {
        //where we actually put the data to the card views with values from our list
        holder.jobTitle.setText("Job title: "+list.get(i).getTitle());
        holder.agencyAuthor.setText("Agency:"+list.get(i).getAgency());
        holder.nurseCat.setText("Nurse:"+list.get(i).getNurseCat());
        holder.locationCat.setText(("Location:"+list.get(i).getLocationCat()));
        holder.description.setText("Job Description:"+list.get(i).getDescription());
        Picasso.get().load(list.get(i).getImageUrl()).fit().into(holder.agencyPhoto);
    }

    @Override //gets size of list of items to tell the adapter how many cards are needed
    public int getItemCount() {
        return list.size();
    }

    public static class JobHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //creating the Java counterparts of the jobcard
        TextView jobTitle, agencyAuthor, nurseCat, locationCat, description ;
        ImageView agencyPhoto;
        Button details;
        recyclerInterface listener;

        public JobHolder(@NonNull View itemView, recyclerInterface _listener) {
            super(itemView);
            //link our java to their xml counterparts
            jobTitle = itemView.findViewById(R.id.tv_jobTitle_jobcard);
            agencyAuthor = itemView.findViewById(R.id.tv_author_jobcard);
            nurseCat = itemView.findViewById(R.id.tv_nurseCat_jobcard);
            locationCat = itemView.findViewById(R.id.tv_locationCat_jobcard);
            description = itemView.findViewById(R.id.tv_description_jobcard);
            agencyPhoto = itemView.findViewById(R.id.agencyImage_jobcard);
            details = itemView.findViewById(R.id.btn_details_jobcard);
            listener = _listener;
            itemView.setOnClickListener(this);

        }

        @Override //gets the adapter position where the user taps
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }

        public interface recyclerInterface{
            /*interface to be implemented by activity class to tell adapter what to do on item click
            and that same class will be  passed to the adapter constructor as a listener interface for the item touch*/
            void onItemClick(int i);
        }
    }

}
