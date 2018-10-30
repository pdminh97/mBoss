package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "BossActivity")
public class BossActivity {
    @PrimaryKey()
    @ColumnInfo(name = "BossID")
    @SerializedName("BossID")
    @Expose
    public Integer bossID;
    @Expose
    @ColumnInfo(name = "ActivityID")
    @SerializedName("ActivityID")
    public Integer activityID;
    @Expose
    @ColumnInfo(name = "Date")
    @SerializedName("Date")
    public String date;
    @Expose
    @ColumnInfo(name = "Cared")
    @SerializedName("Cared")
    public String cared;
    @Expose
    @ColumnInfo(name = "Note")
    @SerializedName("Note")
    public String note;

    public BossActivity() {
    }

    public Integer getBossID() {
        return bossID;
    }

    public void setBossID(Integer bossID) {
        this.bossID = bossID;
    }

    public Integer getActivityID() {
        return activityID;
    }

    public void setActivityID(Integer activityID) {
        this.activityID = activityID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCared() {
        return cared;
    }

    public void setCared(String cared) {
        this.cared = cared;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
