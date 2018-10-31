package mboss.tsm.mboss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
<<<<<<< HEAD
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;
=======
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
>>>>>>> 97d415886366daafc65fceb0afe1ec7e3769494f
=======
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
>>>>>>> parent of 97d4158... Update homefragment and alarm

import mboss.tsm.RecyclerViewAdapter.HomeRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {
<<<<<<< HEAD
    private BottomNavigationView mBottomNav;
=======

<<<<<<< HEAD

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


>>>>>>> 97d415886366daafc65fceb0afe1ec7e3769494f
=======
>>>>>>> parent of 97d4158... Update homefragment and alarm
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
<<<<<<< HEAD
        RecyclerView recyclerView = findViewById(R.id.rvHome);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getItemId() + "", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
=======
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, new MyBossesFragment()).commit();
>>>>>>> 97d415886366daafc65fceb0afe1ec7e3769494f
=======
        RecyclerView recyclerView = findViewById(R.id.rvHome);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
>>>>>>> parent of 97d4158... Update homefragment and alarm
    }
}
