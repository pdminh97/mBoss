package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import mboss.tsm.Model.Boss;

@Dao
public interface BossDAO {
    @Insert
    public long insertBoss(Boss boss);
}
