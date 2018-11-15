package mboss.tsm.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import mboss.tsm.DAO.VersionDAO;
import mboss.tsm.Model.Category;
import mboss.tsm.Model.Version;
import mboss.tsm.Service.IService;
import mboss.tsm.Utility.APIUtil;
import mboss.tsm.Utility.AppDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VersionRepository {
    private Context context;
    private VersionDAO versionDAO;
    private IService service;

    public VersionRepository(Context context) {
        this.context = context;
        service = APIUtil.getIService();
        AppDatabase database = AppDatabase.getInstance(context);
        this.versionDAO = database.versionDAO();
    }

    public void checkCategoryVersion() {
        getVersion(1, new DataCallBack() {
            @Override
            public void CallBackSuccess(final Version version) {
                service.checkCategoryVersion(version.getNumber()).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call,Response<Integer> response) {
                        if(response.isSuccessful()) {
                            final int versionNum = response.body();
                            if(versionNum > 0) {
                                Log.e("ER", "Vo Version Num" + versionNum);
                                service.getCategories().enqueue(new Callback<List<Category>>() {
                                    @Override
                                    public void onResponse(Call<List<Category>> call,final Response<List<Category>> response) {
                                        Log.e("ER", "Vo Respnose");
                                        if(response.isSuccessful()) {
                                            Log.e("ER", "Vo Respnose Success");
                                            if(response.body() != null) {
                                                deleteCategoryData(new DataCallBack() {
                                                    @Override
                                                    public void CallBackSuccess(Version version) {
                                                        List<Category> categories = response.body();
                                                        Log.e("ER", categories.size() + "");
                                                        CategoryRepository categoryRepository = new CategoryRepository(context);
                                                        for (int i = 0; i < categories.size(); i++) {
                                                            categoryRepository.insertCategory(categories.get(i));
                                                        }
                                                        version.setNumber(versionNum);
                                                        update(version);
                                                    }

                                                    @Override
                                                    public void CallBackFail(String message) {

                                                    }
                                                });
                                            }
                                        } else {
                                            Log.e("ER", "Vo Fail");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Category>> call, Throwable t) {
                                        Log.e("ER", "Vo Respnose Failure");
                                        Log.e("ER", t.getMessage());
                                    }
                                });


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e("ER", "Vo Resppnse Fail");
                    }
                });
            }

            @Override
            public void CallBackFail(String message) {

            }
        });
    }

    public void update(Version version) {
        UpdateAsync updateAsync = new UpdateAsync(versionDAO, version);
        updateAsync.execute();
    }

    private void getVersion(int id, DataCallBack dataCallBack) {
        GetVersionAsync getVersionAsync = new GetVersionAsync(versionDAO, id, dataCallBack);
        getVersionAsync.execute();
    }

    private void deleteCategoryData(DataCallBack dataCallBack) {
        DeleteCategoryDataAsync deleteCategoryDataAsync = new DeleteCategoryDataAsync(dataCallBack, versionDAO);
        deleteCategoryDataAsync.execute();
    }

    private class DeleteCategoryDataAsync extends AsyncTask<Void, Void, Void> {
        private DataCallBack dataCallBack;
        private VersionDAO versionDAO;

        public DeleteCategoryDataAsync(DataCallBack dataCallBack, VersionDAO versionDAO) {
            this.dataCallBack = dataCallBack;
            this.versionDAO = versionDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("ER", "Vo Delete");
            versionDAO.deleteCategoryData();
            Log.e("ER", "Ra Delete");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataCallBack.CallBackSuccess(null);
        }
    }

    private class UpdateAsync extends AsyncTask<Void, Void, Void> {
        private VersionDAO versionDAO;
        private Version version;

        public UpdateAsync(VersionDAO versionDAO, Version version) {
            this.versionDAO = versionDAO;
            this.version = version;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            versionDAO.updateVersionNumber(version);
            return null;
        }
    }

    private class GetVersionAsync extends AsyncTask<Void, Void, Version> {
        private VersionDAO versionDAO;
        private int version;
        private DataCallBack dataCallBack;

        public GetVersionAsync(VersionDAO versionDAO, int version, DataCallBack dataCallBack) {
            this.versionDAO = versionDAO;
            this.version = version;
            this.dataCallBack = dataCallBack;
        }

        @Override
        protected Version doInBackground(Void... voids) {
            return versionDAO.getVersion(version);
        }

        @Override
        protected void onPostExecute(Version version) {
            super.onPostExecute(version);
            dataCallBack.CallBackSuccess(version);
        }
    }

    public interface DataCallBack{
        void CallBackSuccess(Version version);
        void CallBackFail (String message);
    }
}
