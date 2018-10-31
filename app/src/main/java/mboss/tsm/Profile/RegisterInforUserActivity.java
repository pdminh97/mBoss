package mboss.tsm.Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;
import java.util.UUID;

import mboss.tsm.Model.User;
import mboss.tsm.Repository.UserReponsitory;
import mboss.tsm.mboss.MainActivity;
import mboss.tsm.mboss.R;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class RegisterInforUserActivity extends AppCompatActivity {
    private ImageView imageView;
    private Uri resultUri;
    private EditText username;
    private Button saveUserInfor;
    private Button logout;
    private User user;
    private Account account;
    private TextView phoneNumber;
    public static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_infor_user);
        InitialView();
        InitialData();


    }

    public void accessImageLibrary() {
        EasyImage.openGallery(RegisterInforUserActivity.this, 0);
    }

    private void InitialData() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessImageLibrary();
            }
        });

        saveUserInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User();
                user.setUsername(username.getText().toString());
//                user.setEmail();
                if (resultUri != null) {
                    user.setPicture(resultUri.toString());
                }
                user.setPhoneNumber(phoneNumber.getText().toString());
//                user.setPhoneNumber(phoneNumber1.toString());
                UserReponsitory userReponsitory = new UserReponsitory(RegisterInforUserActivity.this);
                userReponsitory.InsertUser(user, new UserReponsitory.OnDataCallBackUser() {
                    @Override
                    public void onDataSuccess(User user) {
                        Toast.makeText(RegisterInforUserActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterInforUserActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onDataFail() {
                        Toast.makeText(RegisterInforUserActivity.this, "Add user " + username + " failed", Toast.LENGTH_SHORT).show();
                    }
                });
                intentToUserProfile();
            }
        });


    }

    private void intentToUserProfile() {
        Intent intent = new Intent(RegisterInforUserActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void InitialView() {
        imageView = findViewById(R.id.image_avata);
        phoneNumber = (TextView) findViewById(R.id.txtPhone);
        username = findViewById(R.id.username);
        saveUserInfor = findViewById(R.id.save_info_user);
//        Intent intent = getIntent();
//        String phone = intent.getStringExtra("phone");


    }

    @Override
    protected void onResume() {
        super.onResume();
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                TextView phoneNumber1;
                phoneNumber1 = (TextView) findViewById(R.id.txtPhone);
                // Get Account Kit ID
                String accountKitId = account.getId();
                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (phoneNumber != null) {
                    String phoneNumberString = phoneNumber.toString();
                    phoneNumber1.setText(String.format(phoneNumberString));
                }
//
                // Get email
                String email = account.getEmail();
            }

            @Override
            public void onError(final AccountKitError error) {
                Toast.makeText(RegisterInforUserActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();

                Log.e(TAG, "hello" + error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            resultUri = UCrop.getOutput(data);
            imageView.setImageDrawable(null);
            imageView.setImageURI(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        EasyImage.handleActivityResult(requestCode, resultCode, data, RegisterInforUserActivity.this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                UCrop uCrop = UCrop.of(Uri.fromFile(imageFiles.get(0)), Uri.fromFile(new File(getCacheDir(), String.valueOf(UUID.randomUUID() + ".png"))));
                uCrop.withAspectRatio(1, 1);
                uCrop.withMaxResultSize(1000, 1000);
                uCrop = advancedOptions(uCrop);
                uCrop.start(RegisterInforUserActivity.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
            }
        });
    }

    private UCrop advancedOptions(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.withMaxResultSize(1000, 1000);
        return uCrop.withOptions(options);
    }


}
