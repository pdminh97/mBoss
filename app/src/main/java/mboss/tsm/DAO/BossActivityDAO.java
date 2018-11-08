package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mboss.tsm.Model.BossActivity;

@Dao
public interface BossActivityDAO {
    @Insert
    public  void insertDate(BossActivity bossActivity);

    @Query("SELECT * FROM BossActivity where BossID =:BossID and CategoryID =:CategoryID and Status = 0 order by bossActivityID desc")
    public List<BossActivity> getDatePicker(int BossID, int CategoryID);

    @Query("SELECT * FROM BossActivity where BossID =:BossID and CategoryID =:CategoryID and Status = 1 order by bossActivityID desc")
    public List<BossActivity> getDatePickerHistory(int BossID, int CategoryID);

    @Delete
    public  void deleteDate(BossActivity bossActivity);

    @Update
    public void finishBossActivity(BossActivity... bossActivity);
}
