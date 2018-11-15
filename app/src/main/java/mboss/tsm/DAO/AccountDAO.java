package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import mboss.tsm.Model.Account;

@Dao
public interface AccountDAO {
    @Insert
    void insert(Account account);

    @Query("SELECT * FROM Account WHERE CurrentLogin == 1")
    Account getCurrentAccount();

    @Update
    void update(Account account);

    @Query("SELECT * FROM Account WHERE Username =:username AND Password =:password")
    Account findAccount(String username, String password);
}
