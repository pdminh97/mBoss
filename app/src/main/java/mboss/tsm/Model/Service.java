package mboss.tsm.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Service {
    @SerializedName("ServiceID")
    @Expose
    private int serviceID;
    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("ClinicID")
    @Expose
    private int clinicID;
    @SerializedName("ClinicName")
    @Expose
    private String clinicName;
    @SerializedName("AvgPrice")
    @Expose
    private int avgPrice;
    @SerializedName("Rating")
    @Expose
    private double rating;
    @SerializedName("ServiceDetails")
    @Expose
    private List<ServiceDetail> serviceDetails;

    public List<ServiceDetail> getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(List<ServiceDetail> serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getClinicID() {
        return clinicID;
    }

    public void setClinicID(int clinicID) {
        this.clinicID = clinicID;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public int getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(int avgPrice) {
        this.avgPrice = avgPrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
