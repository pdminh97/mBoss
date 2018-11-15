package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import mboss.tsm.Model.Version;

@Dao
public interface VersionDAO {
    @Update
    void updateVersionNumber(Version version);

    @Query("SELECT * FROM Version WHERE ID =:id")
    Version getVersion(int id);

    @Insert
    void insert(Version...versions);

    @Query("DELETE FROM Category")
    void deleteCategoryData();

    @Query("SELECT COUNT(ID) FROM Version")
    int getCount();
}
