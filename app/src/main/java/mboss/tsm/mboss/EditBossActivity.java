package mboss.tsm.mboss;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import mboss.tsm.Model.Boss;

public class EditBossActivity extends AppCompatActivity {
    private Boss mBoss;
    private Spinner edtSpinnerSpecies;
    private Spinner edtSpinnerGender;
    private ImageView edtAvata;
    private EditText edtName;
    private EditText edtWeight;
    private EditText edtColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_boss);
        initialView();
        initialData();
    }


    private void initialView(){
        Intent intent= getIntent();
        mBoss = (Boss) intent.getSerializableExtra(BossListActivity.BOSSES);
        edtAvata=findViewById(R.id.editImage_avata);
        edtSpinnerSpecies=findViewById(R.id.edtSpecies_spinner);
        edtName=findViewById(R.id.edtName);
        edtSpinnerGender=findViewById(R.id.edtGender_spinner);
        edtColor=findViewById(R.id.edtColor);
        edtWeight=findViewById(R.id.edtWeight);
    }

    private  void initialData() {
        edtAvata.setImageURI(Uri.parse(mBoss.getPictures()));
        //edtSpinnerSpecies.getSelectedItem(mBoss.setSpecies();
        edtName.setText(mBoss.getBossName());
        edtColor.setText(mBoss.getColor());
        edtWeight.setText(mBoss.getBossWeight());

    }
}
