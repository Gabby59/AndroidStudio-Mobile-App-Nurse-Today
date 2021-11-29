package co.uk.nursetoday.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Topic implements Parcelable, GenericList {

    String username, subject, details, date;
    Integer threads;

    public Topic(String username, String subject, String details, String date, Integer threads) {
        this.username = username;
        this.subject = subject;
        this.details = details;
        this.date = date;
        this.threads = threads;
    }

    public Topic() {
    }

    protected Topic(Parcel in){
        username=in.readString();
        subject=in.readString();
        details=in.readString();
        date=in.readString();
        threads=in.readInt();
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel parcel) {
            return new Topic(parcel);
        }

        @Override
        public Topic[] newArray(int i) {
            return new Topic[i];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

  //  @Override
    public int describeContents() {
        return 0;
    }

    //@Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(username);
        dest.writeString(subject);
        dest.writeString(details);
        dest.writeString(date);
        dest.writeInt(threads);

    }


}
