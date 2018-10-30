package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import mboss.tsm.Model.BossActivity;

@Dao
public interface BossActivityDAO {
    @Insert
    public  void insertDate(BossActivity bossActivity);

    @Query("SELECT * FROM BossActivity")
    public List<BossActivity> getDatePicker();

    @Delete
    public  void deleteDate(BossActivity bossActivity);
}
