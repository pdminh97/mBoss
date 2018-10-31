package mboss.tsm.mboss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mboss.tsm.Repository.BossRepository;

public class BossListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_list);
        BossRepository bossRepository = new BossRepository(this);
        bossRepository.showBossList();
    }
}
