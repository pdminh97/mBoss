package mboss.tsm.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import mboss.tsm.Model.Category;

@Dao
public interface CategoryDAO {
    @Query("SELECT CategoryID, Name, Image FROM Category")
    List<Category> getCategories();

    @Insert
    void insertAllCategory(Category... categories);
}
