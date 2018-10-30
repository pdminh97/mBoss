package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import mboss.tsm.Model.User;

@Dao
public interface UserDAO {
@Insert
  void InsertUser(User... user);

@Query("Select * from User")
   public User getmUserInfor();
}
