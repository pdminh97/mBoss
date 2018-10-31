package mboss.tsm.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Clinic implements Serializable {
    @SerializedName("ClinicID")
    @Expose
    private int clinicID;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("ClinicName")
    @Expose
    private String name;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longtitude")
    @Expose
    private String longtitude;
    @SerializedName("Rating")
    @Expose
    private float rating;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getClinicID() {
        return clinicID;
    }

    public void setClinicID(int clinicID) {
        this.clinicID = clinicID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }
}
