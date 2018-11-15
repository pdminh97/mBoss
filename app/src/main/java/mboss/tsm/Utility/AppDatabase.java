package mboss.tsm.Utility;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import mboss.tsm.DAO.AccountDAO;
import mboss.tsm.DAO.BossActivityDAO;
import mboss.tsm.DAO.BossCategoryDAO;
import mboss.tsm.DAO.BossDAO;
import mboss.tsm.DAO.CategoryDAO;
import mboss.tsm.DAO.DiaryDAO;
import mboss.tsm.DAO.UserDAO;
import mboss.tsm.DAO.VersionDAO;
import mboss.tsm.Model.Account;
import mboss.tsm.Model.Boss;
import mboss.tsm.Model.BossActivity;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.Model.Category;
import mboss.tsm.Model.Diary;
import mboss.tsm.Model.User;
import mboss.tsm.Model.Version;
import mboss.tsm.mboss.R;

@Database(entities = {Version.class, Account.class, Boss.class, User.class, BossCategory.class, BossActivity.class, Diary.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract UserDAO userDAO();

    public abstract BossDAO bossDAO();

    public abstract DiaryDAO diaryDAO();

    public abstract BossCategoryDAO bossCategoryDAO();

    public abstract BossActivityDAO bossActivityDAO();

    public abstract CategoryDAO categoryDAO();

    public abstract AccountDAO accountDAO();

    public abstract VersionDAO versionDAO();

    public static AppDatabase getInstance(final Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "mBoss")
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    //getInstance(context).versionDAO().insert(Version.initVersionData());
                                    getInstance(context).categoryDAO().insertAllCategory(Category.initCategoryData());
                                }
                            });
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }
}
