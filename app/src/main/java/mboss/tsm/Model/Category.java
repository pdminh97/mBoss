package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category  implements Serializable {
    private  int categoryID;
    private  int image;
    private  String name;

    public Category() {
    }

    public Category(int categoryID, int image, String name) {
        this.categoryID = categoryID;
        this.image = image;
        this.name = name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
//    public Category(int image, String name) {
//        this.image = image;
//        this.name = name;
//    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
