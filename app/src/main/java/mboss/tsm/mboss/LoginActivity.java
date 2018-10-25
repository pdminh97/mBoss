package mboss.tsm.mboss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mboss.tsm.Repository.AccountRepository;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickToLogin(View view) {
        EditText edtUsername = findViewById(R.id.txtUsername);
        EditText edtPassword = findViewById(R.id.txtPassword);
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        AccountRepository accountRepository = new AccountRepository(this);
        accountRepository.Login(username, password);
    }
}
