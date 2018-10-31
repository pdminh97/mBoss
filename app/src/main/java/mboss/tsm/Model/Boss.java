package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Boss")
public class Boss {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "BossID")
    @SerializedName("BossID")
    @Expose
    public Integer bossID;
    @SerializedName("BossName")
    @Expose
    @ColumnInfo(name = "BossName")
    public String bossName;
    @SerializedName("BossAge")
    @Expose
    @ColumnInfo(name = "BossAge")
    public Integer bossAge;
    @SerializedName("BossWeight")
    @Expose
    @ColumnInfo(name = "BossWeight")
    public Integer bossWeight;
    @SerializedName("AccountID")
    @Expose
    @ColumnInfo(name = "AccountID")
    public Integer accountID;

    public Integer getBossID() {
        return bossID;
    }

    public void setBossID(Integer bossID) {
        this.bossID = bossID;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public Integer getBossAge() {
        return bossAge;
    }

    public void setBossAge(Integer bossAge) {
        this.bossAge = bossAge;
    }

    public Integer getBossWeight() {
        return bossWeight;
    }

    public void setBossWeight(Integer bossWeight) {
        this.bossWeight = bossWeight;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }
}
