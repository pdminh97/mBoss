package mboss.tsm.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;

import java.util.List;

import mboss.tsm.DAO.CategoryDAO;
import mboss.tsm.Model.Category;
import mboss.tsm.Utility.AppDatabase;

public class CategoryRepository {
    private CategoryDAO categoryDAO;

    public CategoryRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        categoryDAO = database.categoryDAO();
    }

    public void getCategories(DataCallBack dataCallBack) {
        GetCategoriesAsync getCategoriesAsync = new GetCategoriesAsync(categoryDAO, dataCallBack);
        getCategoriesAsync.execute();
    }

    public void insertCategory(Category category) {
        InsertCategoryAsync insertCategoryAsync = new InsertCategoryAsync(categoryDAO, category);
        insertCategoryAsync.execute();
    }

    private class GetCategoriesAsync extends AsyncTask<Void, Void, List<Category>> {
        private CategoryDAO categoryDAO;
        private DataCallBack getDataCallBack;

        public GetCategoriesAsync(CategoryDAO categoryDAO, DataCallBack getDataCallBack) {
            this.categoryDAO = categoryDAO;
            this.getDataCallBack = getDataCallBack;
        }

        @Override
        protected List<Category> doInBackground(Void... voids) {
            return categoryDAO.getCategories();
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);
            getDataCallBack.CallBackSuccess(categories);
        }
    }

    private class InsertCategoryAsync extends AsyncTask<Void, Void, Void> {
        private CategoryDAO categoryDAO;
        private Category category;

        public InsertCategoryAsync(CategoryDAO categoryDAO, Category category) {
            this.categoryDAO = categoryDAO;
            this.category = category;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                categoryDAO.insertAllCategory(category);
            } catch (Exception e) {

            }
            return null;
        }
    }

    public interface DataCallBack{
        void CallBackSuccess(List<Category> categories);
        void CallBackFail (String message);
    }
}
