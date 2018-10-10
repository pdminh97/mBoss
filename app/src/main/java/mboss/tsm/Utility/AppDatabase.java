package mboss.tsm.Utility;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import mboss.tsm.DAO.DiaryDAO;
import mboss.tsm.Model.Boss;
import mboss.tsm.Model.Diary;

@Database(entities = {Diary.class, Boss.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DiaryDAO diaryDAO();

    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context) {
        if(appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "mBoss").build();
        }
        return appDatabase;
    }
}
