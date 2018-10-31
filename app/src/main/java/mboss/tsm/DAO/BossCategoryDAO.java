package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import mboss.tsm.Model.BossCategory;

@Dao
public interface BossCategoryDAO {
        @Insert
        public void insertBossCategory(BossCategory bossCategory);

        @Query("SELECT * FROM BossCategory")
        public List<BossCategory> getBossCategory();
}
