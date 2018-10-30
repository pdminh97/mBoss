package mboss.tsm.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import mboss.tsm.Model.Token;
import mboss.tsm.Service.IService;
import mboss.tsm.Utility.APIUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepository {
    private IService service;
    private Context context;

    public AccountRepository(Context context) {
        this.service = APIUtil.getIService();
        this.context = context;
    }

    public void Login(String username, String password) {
        service.login(username, password, "password").enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()) {
                    Token token = response.body();
                    if(token != null) {
                        Toast.makeText(context, token.getAccess_token(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
