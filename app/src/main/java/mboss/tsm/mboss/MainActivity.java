package mboss.tsm.mboss;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import mboss.tsm.Fragment.DiaryFragment;
import mboss.tsm.Fragment.MyBossesFragment;
import mboss.tsm.Fragment.ProfileFragment;
import mboss.tsm.Fragment.ServiceFragment;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_pet_list:
                    ft.replace(R.id.fragment_place, new MyBossesFragment()).commit();
                    return true;
                case R.id.navigation_service:
                    ft.replace(R.id.fragment_place, new ServiceFragment()).commit();
                    return true;
                case R.id.navigation_diary:
                    ft.replace(R.id.fragment_place, new DiaryFragment()).commit();
                    return true;
                case R.id.navigation_profile:

                    ft.replace(R.id.fragment_place, new ProfileFragment()).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new MyBossesFragment()).commit();
    }

    public void clickToViewProfile(View view) {

    }


}
