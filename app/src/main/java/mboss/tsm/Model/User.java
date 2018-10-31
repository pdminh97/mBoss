package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "User")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "UserID")
    @SerializedName("UserID")
    @Expose
    public Integer userId;
    @ColumnInfo(name = "Username")
    @SerializedName("Username")
    @Expose
    public String username;

    @ColumnInfo(name = "PhoneNumber")
    @SerializedName("PhoneNumber")
    @Expose
    public String phoneNumber;

    @ColumnInfo(name = "Email")
    @SerializedName("Email")
    @Expose
    public String email;

    @ColumnInfo(name = "Picture")
    @SerializedName("Picture")
    @Expose
    public String picture;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }
}
