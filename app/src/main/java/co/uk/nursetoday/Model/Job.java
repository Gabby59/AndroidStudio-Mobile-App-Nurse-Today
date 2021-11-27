package co.uk.nursetoday.Model;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Job implements Parcelable {

    private String title, agency, description, nurseCat, locationCat, imageUrl;// type

    public Job(String title, String agency, String description, String nurseCat, String locationCat, String imageUrl){ // String type) {
        this.title = title;
        this.agency = agency;
        this.description = description;
        this.nurseCat = nurseCat;
        this.locationCat = locationCat;
        this.imageUrl = imageUrl;
      //  this.type = type;
    }

    public Job(){
    }

    protected Job (Parcel in){
        title = in.readString();
        agency = in.readString();
        description = in.readString();
        nurseCat = in.readString();
        locationCat = in.readString();
        imageUrl = in.readString();
      //  type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(agency);
        dest.writeString(description);
        dest.writeString(nurseCat);
        dest.writeString(locationCat);
        dest.writeString(imageUrl);
       // dest.writeString(type);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNurseCat() {
        return nurseCat;
    }

    public void setNurseCat(String nurseCat) {
        this.nurseCat = nurseCat;
    }

    public String getLocationCat() {
        return locationCat;
    }

    public void setLocationCat(String locationCat) {
        this.locationCat = locationCat;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

  /*  public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    } */
}
