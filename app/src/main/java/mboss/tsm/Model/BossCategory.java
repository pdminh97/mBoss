package mboss.tsm.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.List;

import mboss.tsm.Utility.ConvertType;

@Entity(tableName = "BossCategory")
public class BossCategory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public int  id;
    @ColumnInfo(name = "BossID")
    public int  bossID;
    @ColumnInfo(name = "BossListCategory")
    @TypeConverters(ConvertType.class)
    public List<Category> mCategorytList;


    public int getBossID() {
        return bossID;
    }

    public void setBossID(int bossID) {
        this.bossID = bossID;
    }

    public List<Category> getmCategorytList() {
        return mCategorytList;
    }

    public void setmCategorytList(List<Category> mCategorytList) {
        this.mCategorytList = mCategorytList;
    }
}
