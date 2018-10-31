package mboss.tsm.Utility;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import mboss.tsm.DAO.BossActivityDAO;
import mboss.tsm.DAO.BossCategoryDAO;
import mboss.tsm.DAO.BossDAO;
import mboss.tsm.DAO.UserDAO;
import mboss.tsm.Model.Boss;
import mboss.tsm.Model.BossActivity;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.Model.User;

@Database(entities = {Boss.class, User.class,  BossCategory.class, BossActivity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract UserDAO userDAO();
    public abstract BossDAO bossDAO();
    public abstract BossCategoryDAO bossCategoryDAO();
    public abstract BossActivityDAO bossActivityDAO();

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "mBoss")
                    .fallbackToDestructiveMigration().build();
        }
        return appDatabase;
    }
}
