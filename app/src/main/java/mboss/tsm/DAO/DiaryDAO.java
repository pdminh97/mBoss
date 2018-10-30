package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import mboss.tsm.Model.Diary;

@Dao
public interface DiaryDAO {
    @Insert
    public long insertDiary(Diary diary);

    @Query("SELECT * FROM Diary")
    public List<Diary> getDiaries();
}
