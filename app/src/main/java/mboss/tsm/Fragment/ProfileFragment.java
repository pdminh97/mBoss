package mboss.tsm.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import mboss.tsm.Model.User;
import mboss.tsm.Profile.LoginActivity;
import mboss.tsm.Repository.UserReponsitory;
import mboss.tsm.mboss.R;
import mboss.tsm.mboss.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private UserReponsitory userReponsitory;
    private User mUser;
    private TextView phone, username, email;
    private Button change_information;
    private ImageView avatar;
    private TextView register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        UserReponsitory userReponsitory = new UserReponsitory(getActivity());
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        phone = (TextView) view.findViewById(R.id.txtPhone);
        username = (TextView) view.findViewById(R.id.txtUsernameTitle);
        email = (TextView) view.findViewById(R.id.txtMail);
        change_information = (Button) view.findViewById(R.id.chane_infor_user);
        avatar = (ImageView) view.findViewById(R.id.imgAvatar);

        register = view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(
                                LoginType.PHONE,
                                AccountKitActivity.ResponseType.TOKEN);
                intent.putExtra(
                        AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                        configurationBuilder.build());
                startActivityForResult(intent, 1);
            }
        });

        userReponsitory.getmUserInfo(new UserReponsitory.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                Log.e("User: ", user.username + "");
                Log.e("Phone: ", user.phoneNumber + "");
                if(user != null) {
                    phone.setText(user.getPhoneNumber());
                    username.setText(user.getUsername());
                    if (user.getEmail() != null) {
                        email.setText(user.getEmail());
                    }
                    avatar.setImageURI(Uri.parse(user.getPicture()));
                }
            }
            @Override
            public void onDataFail() {

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null) {
                //toastMessage = loginResult.getError().getErrorType().getMessage();
//                showErrorActivity(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                //toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(Account account) {
                            Intent intent = new Intent(getActivity(), RegisterActivity.class);
                            intent.putExtra("phone", account.getPhoneNumber() + "");
                            startActivity(intent);
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                        }
                    });
                    //toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    //toastMessage = String.format("Success:%s...",loginResult.getAuthorizationCode().substring(0, 10));
                }
            }
        }
    }
}
