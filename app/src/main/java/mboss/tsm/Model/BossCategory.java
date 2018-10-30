package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

import mboss.tsm.Utility.ConvertCategoryType;

@Entity(tableName = "BossCategory")
public class BossCategory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "BossID")
    @Expose
    public int  bossID;
    @Expose
    @ColumnInfo(name = "BossListCategory")
    @TypeConverters(ConvertCategoryType.class)
    public Category mCategorytList;


    public int getBossID() {
        return bossID;
    }

    public void setBossID(int bossID) {
        this.bossID = bossID;
    }

    public Category getmCategorytList() {
        return mCategorytList;
    }

    public void setmCategorytList(Category mCategorytList) {
        this.mCategorytList = mCategorytList;
    }
}

