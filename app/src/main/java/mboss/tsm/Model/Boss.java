package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "Boss")
public class Boss implements Serializable {
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
    public Float bossWeight;
    @SerializedName("AccountID")
    @Expose
    @ColumnInfo(name = "AccountID")
    public Integer accountID;
    @SerializedName("Pictures")
    @Expose
    @ColumnInfo(name = "Pictures")
    public String pictures;
    @SerializedName("Gender")
    @Expose
    @ColumnInfo(name = "Gender")
    public String gender;
    @SerializedName("Species")
    @Expose
    @ColumnInfo(name = "Species")
    public String species;
    @SerializedName("Color")
    @Expose
    @ColumnInfo(name = "Color")
    public String color;

    public Boss() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

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

    public Float getBossWeight() {
        return bossWeight;
    }

    public void setBossWeight(Float bossWeight) {
        this.bossWeight = bossWeight;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }
}
