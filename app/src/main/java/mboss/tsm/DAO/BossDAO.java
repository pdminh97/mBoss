package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mboss.tsm.Model.Boss;

@Dao
public interface BossDAO {
    @Insert
    public void insertBoss(Boss boss);

    @Query("SELECT * FROM Boss")
    public List<Boss> getBosses();

    @Update
    public int  updateBoss(Boss ... bosses);

    @Delete
    public void deleteBoss(Boss boss);

}
