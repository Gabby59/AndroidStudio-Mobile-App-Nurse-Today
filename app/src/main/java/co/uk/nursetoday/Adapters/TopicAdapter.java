package co.uk.nursetoday.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.uk.nursetoday.Model.Forum_Thread;
import co.uk.nursetoday.R;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.Holder> {

    ArrayList<Forum_Thread> thread;

    public TopicAdapter(ArrayList<Forum_Thread> thread) {
        this.thread = thread;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forumcard, parent,false);
       Holder holder = new Holder(v);
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.rep.setVisibility(View.INVISIBLE);
        holder.reps.setVisibility(View.INVISIBLE);

        holder.author.setText("by: " + thread.get(position).getAuthor());
        holder.title.setText("Message: " + thread.get(position).getMessage());
        holder.date.setText("On " + thread.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return thread.size();
    }


    public static class Holder extends RecyclerView.ViewHolder {

        TextView author, date,title, rep, reps;

        public Holder(@NonNull View itemView) {
            super(itemView);
            //link java counterparts with the xml elements (Forumcard)
            author = itemView.findViewById(R.id.tv_author_forum);
            date = itemView.findViewById(R.id.tv_date_forum);
            title = itemView.findViewById(R.id.topicTitle_forum);
            rep = itemView.findViewById(R.id.tv_replies_forum);
            reps = itemView.findViewById(R.id.tv_num_replies_forum);
        }
    }

}
