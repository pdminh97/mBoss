package mboss.tsm.Fragment;


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

import mboss.tsm.Model.User;
import mboss.tsm.Repository.UserReponsitory;
import mboss.tsm.mboss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private UserReponsitory userReponsitory;
    private User mUser;
    private TextView phone, username, email;
    private Button change_information;
    private ImageView avatar;

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


}
