package mboss.tsm.mboss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mboss.tsm.Model.Account;

public class RegisterActivity extends AppCompatActivity {
    private EditText register_password;
    private EditText txtFullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_password);
        register_password = findViewById(R.id.register_password);
        txtFullname = findViewById(R.id.txtFullname);
    }

    public void clickToRegister(View view) {
        String phone = getIntent().getStringExtra("phone");
        String pw = register_password.getText().toString();
        String fullname = txtFullname.getText().toString();
        Account account = new Account();
        account.setFullname(fullname);
        account.setUsername(phone);
        account.setPassword(pw);
    }
}
