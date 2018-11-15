package mboss.tsm.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import mboss.tsm.DAO.AccountDAO;
import mboss.tsm.Model.Account;
import mboss.tsm.Model.Diary;
import mboss.tsm.Model.Token;
import mboss.tsm.Service.IService;
import mboss.tsm.Utility.APIUtil;
import mboss.tsm.Utility.AppDatabase;
import mboss.tsm.Utility.Constant;
import mboss.tsm.mboss.RegisterActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepository {
    private AccountDAO accountDAO;
    private IService service;
    private Context context;

    public AccountRepository(Context context) {
        this.context = context;
        AppDatabase database = AppDatabase.getInstance(context);
        service = APIUtil.getIService();
        this.accountDAO = database.accountDAO();
    }

    public void register(final Account account) {
        service.register(account).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    if(response.body() > 0) {
                        account.setAccountID(response.body());
                        account.setCurrentLogin(true);
                        insert(account);
                        ((RegisterActivity) context).finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    public void login(final String username, final String password) {
        service.login(username, password, "password").enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()) {
                    Constant.token = response.body();
                    Log.e("Er", Constant.token.getAccess_token());
                    findAccount(username, password, new GetAccountCallBack() {
                        @Override
                        public void CallBackSuccess(Account account) {
                            account.setCurrentLogin(true);
                            updateCurrentAccount(account);
                        }

                        @Override
                        public void CallBackFail(String message) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }

    public void insert(Account account) {
        InsertSync insertSync = new InsertSync(accountDAO, account);
        insertSync.execute();
    }

    public void getCurrentAccount(GetAccountCallBack getAccountCallBack) {
        GetCurrentAccountSync getCurrentAccountSync = new GetCurrentAccountSync(accountDAO, getAccountCallBack);
        getCurrentAccountSync.execute();
    }

    public void updateCurrentAccount(Account account) {
        UpdateCurrentAccountSync updateCurrentAccountSync = new UpdateCurrentAccountSync(accountDAO, account);
        updateCurrentAccountSync.execute();
    }

    public void findAccount(String username, String password, GetAccountCallBack getAccountCallBack) {
        FindAccountSync findAccountSync = new FindAccountSync(accountDAO, username, password, getAccountCallBack);
        findAccountSync.execute();
    }

    private class InsertSync extends AsyncTask<Void, Void, Void> {
        private AccountDAO accountDAO;
        private Account account;

        public InsertSync(AccountDAO accountDAO, Account account) {
            this.accountDAO = accountDAO;
            this.account = account;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accountDAO.insert(account);
            return null;
        }
    }

    private class GetCurrentAccountSync extends AsyncTask<Void, Void, Account> {
        private AccountDAO accountDAO;
        private GetAccountCallBack getAccountCallBack;


        public GetCurrentAccountSync(AccountDAO accountDAO, GetAccountCallBack getAccountCallBack) {
            this.accountDAO = accountDAO;
            this.getAccountCallBack = getAccountCallBack;
        }

        @Override
        protected Account doInBackground(Void... voids) {
            return accountDAO.getCurrentAccount();
        }

        @Override
        protected void onPostExecute(Account account) {
            super.onPostExecute(account);
            getAccountCallBack.CallBackSuccess(account);
        }
    }

    private class UpdateCurrentAccountSync extends AsyncTask<Void, Void, Void> {
        private AccountDAO accountDAO;
        private Account account;

        public UpdateCurrentAccountSync(AccountDAO accountDAO, Account account) {
            this.accountDAO = accountDAO;
            this.account = account;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accountDAO.update(account);
            return null;
        }
    }

    private class FindAccountSync extends AsyncTask<Void, Void, Account> {
        private AccountDAO accountDAO;
        private String username;
        private String password;
        private GetAccountCallBack getAccountCallBack;

        public FindAccountSync(AccountDAO accountDAO, String username, String password, GetAccountCallBack getAccountCallBack) {
            this.accountDAO = accountDAO;
            this.username = username;
            this.password = password;
            this.getAccountCallBack = getAccountCallBack;
        }

        @Override
        protected Account doInBackground(Void... voids) {
            accountDAO.findAccount(username, password);
            return null;
        }

        @Override
        protected void onPostExecute(Account account) {
            super.onPostExecute(account);
            getAccountCallBack.CallBackSuccess(account);
        }
    }

    public interface GetAccountCallBack {
        void CallBackSuccess(Account account);

        void CallBackFail(String message);
    }
}
