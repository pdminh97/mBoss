package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Account implements Serializable {
    @SerializedName("AccountID")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AccountID")
    private int accountID;
    @SerializedName("Username")
    @Expose
    @ColumnInfo(name = "Username")
    private String username;
    @SerializedName("Password")
    @Expose
    @ColumnInfo(name = "Password")
    private String password;
    @SerializedName("Fullname")
    @Expose
    @ColumnInfo(name = "Fullname")
    private String fullname;
    @ColumnInfo(name = "CurrentLogin")
    private boolean currentLogin;

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(boolean currentLogin) {
        this.currentLogin = currentLogin;
    }
}
