package mboss.tsm.mboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void clickToSend(View view) {
        EditText edtPhone = findViewById(R.id.txtPhone);
        String phone = edtPhone.getText().toString();
        if(phone.length() < 9) {
            Toast.makeText(this, "Nhap Dung So Di Tg Lozz", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, VerifyCodeActivity.class);
            intent.putExtra("phone", phone);
            startActivity(intent);
        }
    }
}
