package mboss.tsm.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceDetail {
    @SerializedName("ServiceDetailName")
    @Expose
    private String name;

    @SerializedName("Price")
    @Expose
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
