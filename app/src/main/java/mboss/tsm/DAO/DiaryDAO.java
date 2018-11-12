package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mboss.tsm.Model.BossActivity;
import mboss.tsm.Model.Diary;

@Dao
public interface DiaryDAO {
    @Insert
    public void insertDiary(Diary diary);

    @Query("SELECT * FROM Diary where Status = 1 and DiaryTime <= date('now') order by date(DiaryTime) desc")
    public List<Diary> getDiaries();

    @Update
    public void update(Diary diary);

    @Query("SELECT * FROM Diary where BossID =:BossID and CategoryID =:CategoryID and Status = 0 order by DiaryID desc")
    public List<Diary> getDatePickedForBossActivity(int BossID, int CategoryID);

    @Query("SELECT * FROM Diary where BossID =:BossID and CategoryID =:CategoryID and Status = 1 order by DiaryID desc")
    public List<Diary> getBossActivityCompleted(int BossID, int CategoryID);

    @Update
    public void completeBossActivity(Diary... diaries);
}
