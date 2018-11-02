package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import mboss.tsm.Model.BossCategory;

@Dao
public interface BossCategoryDAO {
        @Insert
        public void insertBossCategory(BossCategory... bossCategories);

        @Query("SELECT * FROM BossCategory WHERE BossID =:id")
        List<BossCategory> getBossCategory(int id);

        @Delete
        public void deleteBossCategory(BossCategory ... bossCategory);
        @Update
        public void  updateBossCategory(BossCategory ... bossCategories);
}
