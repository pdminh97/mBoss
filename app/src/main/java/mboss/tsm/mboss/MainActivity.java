package mboss.tsm.mboss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import mboss.tsm.Fragment.BossDetailFragment;
import mboss.tsm.Fragment.DiaryFragment;
import mboss.tsm.Fragment.MyBossesFragment;
import mboss.tsm.Fragment.ProfileFragment;
import mboss.tsm.Fragment.ServiceFragment;
import mboss.tsm.Model.Boss;

public class MainActivity extends AppCompatActivity {

    public static final String BOSSES = "Bosses";

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
        Boss mBoss;
        Intent intent = getIntent();
        mBoss = (Boss) intent.getSerializableExtra(MyBossesFragment.BOSSES);
//        Log.e("Hrll", mBoss.getBossName());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (mBoss != null) {
           changeListBossToBossDetail(mBoss, ft);
        } else {
            ft.replace(R.id.fragment_place, new MyBossesFragment()).commit();
        }
    }

    private void changeListBossToBossDetail(Boss mBoss, FragmentTransaction ft){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BOSSES, mBoss);
        BossDetailFragment bossDetailFragment = new BossDetailFragment();
        bossDetailFragment.setArguments(bundle);
        ft.replace(R.id.fragment_place, new BossDetailFragment()).commit();
    }

    public void clickToViewProfile(View view) {

    }


}
