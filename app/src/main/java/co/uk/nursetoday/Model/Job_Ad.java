package co.uk.nursetoday.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Job_Ad extends Job implements GenericList , Parcelable {

    private String username;
    private Boolean isAvaiable;

    public Job_Ad(String title, String agency, String description, String nurseCat, String locationCat, String imageUrl, Boolean isAvaiable) {
        super(title, agency, description, nurseCat, locationCat, imageUrl); //type);
        //this.username = username;
        this.isAvaiable = isAvaiable;
    }

    public Job_Ad(String username, Boolean isAvaiable) {
        this.username= username;
        this.isAvaiable = isAvaiable;
    }

    public Job_Ad() {
    }

    protected Job_Ad(Parcel in) {
        super(in);
        username = in.readString();
        byte tmpIsAvaiable = in.readByte();
        isAvaiable = tmpIsAvaiable == 0 ? null : tmpIsAvaiable == 1;
    }

    public static final Creator<Job_Ad> CREATOR = new Creator<Job_Ad>() {
        @Override
        public Job_Ad createFromParcel(Parcel in) {
            return new Job_Ad(in);
        }

        @Override
        public Job_Ad[] newArray(int size) {
            return new Job_Ad[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String stuId) {
        this.username = username;
    }

    public Boolean getAvaiable() {
        return isAvaiable;
    }

    public void setAvaiable(Boolean avaiable) {
        isAvaiable = avaiable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(username);
        dest.writeByte((byte) (isAvaiable ? 1 : 0));
    }

//name, description, category, url for image ,
    //id for the reviews node (where all comments of the agency will be saved)
}


