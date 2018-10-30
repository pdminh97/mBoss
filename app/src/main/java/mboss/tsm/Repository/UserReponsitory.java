package mboss.tsm.Repository;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import mboss.tsm.DAO.UserDAO;
import mboss.tsm.Model.User;
import mboss.tsm.Utility.AppDatabase;

public class UserReponsitory {
    private Context context;
    private UserDAO userDAO;
    private Activity mActivity;

    public UserReponsitory(Activity mActivity) {
        AppDatabase database = AppDatabase.getInstance(mActivity.getApplicationContext());
        this.userDAO = database.userDAO();
        this.mActivity = mActivity;
    }
    public interface OnDataCallBackUser {
        void onDataSuccess(User user);
        void onDataFail();
    }

    public void InsertUser(User user, OnDataCallBackUser listener) {
        InsertUserInforAsync insertUserInforAsync = new InsertUserInforAsync(userDAO, listener);
        insertUserInforAsync.execute(user);
    }

    public void getmUserInfo(OnDataCallBackUser listener) {
        GetInforUserAsync getInfoUserAsync = new GetInforUserAsync(userDAO, listener);
        getInfoUserAsync.execute();
    }

    private class InsertUserInforAsync extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;
        private OnDataCallBackUser mListener;

        public InsertUserInforAsync(UserDAO userDAO, OnDataCallBackUser listener) {
            this.userDAO = userDAO;
            mListener = listener;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.InsertUser(users);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mListener.onDataSuccess(null);

        }
    }



    private class GetInforUserAsync extends AsyncTask<User, Void, Void> {
        private UserDAO mUserDao;
        private OnDataCallBackUser mListener;
        private User mUser;

        public GetInforUserAsync(UserDAO userDao, OnDataCallBackUser callBackUser){
            this.mUserDao = userDao;
            mListener = callBackUser;
        }


        @Override
        protected Void doInBackground(User... users) {
            mUser = mUserDao.getmUserInfor();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mUser!=null){
                mListener.onDataSuccess(mUser);
            }
            else {
                mListener.onDataFail();

            }
        }

    }


}
