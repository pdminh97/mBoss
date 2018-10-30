package mboss.tsm.mboss;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

import mboss.tsm.Model.BossActivity;
import mboss.tsm.Repository.BossActivityRepository;

public class AddDateActivity extends AppCompatActivity {
    private EditText datePicker;
    private EditText note;
    private ImageButton imbCheck;
    private BossActivity newBossActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);
        initialView();
    }

    private void initialView(){
        datePicker=findViewById(R.id.edtDate);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
        note=findViewById(R.id.edtNote);
        imbCheck=findViewById(R.id.imbCheck);
    }

    public void clickToAdd(View view) {
        newBossActivity= new BossActivity();
        newBossActivity.setDate(datePicker.getText().toString());
        newBossActivity.setNote(note.getText().toString());
        BossActivityRepository bossActivityRepository=new BossActivityRepository(AddDateActivity.this);
        bossActivityRepository.insertDate(newBossActivity);
        Intent intent = new Intent(AddDateActivity.this,DateListActivity.class);
        startActivity(intent);
    }

    private  void  ChonNgay(){
        final Calendar calendar= Calendar.getInstance();
        final int day= calendar.get(Calendar.DATE);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(year, month, day);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyy");
                datePicker.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year, month, day);
        datePickerDialog.show();
    }
}
