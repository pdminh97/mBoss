package mboss.tsm.mboss;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

import mboss.tsm.BroadcashReceiver.AlarmReceiver;
import mboss.tsm.Fragment.BossDetailFragment;
import mboss.tsm.Fragment.DateListFragment;
import mboss.tsm.Model.BossActivity;
import mboss.tsm.Repository.BossActivityRepository;

public class AddDateActivity extends AppCompatActivity {
    private EditText datePicker;
    private EditText timePicker;
    private EditText note;
    private BossActivity newBossActivity;
    //s
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TextView status_notification;
    private AlarmReceiver alarm;
    private Switch bell;

    Context context;
    private AddDateActivity inst;
    int year, month, day, hour, minute;
    private boolean check = false;
    private static final String CHECK = "check";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);
        this.context = this;
        initialView();
        initialData();
    }

    private void initialView(){
        final Calendar calendar= Calendar.getInstance();
        datePicker=findViewById(R.id.edtDate);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyy");
        datePicker.setText(simpleDateFormat.format(calendar.getTime()));
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        timePicker=findViewById(R.id.edtTime);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime();
            }
        });
        note=findViewById(R.id.edtNote);
        bell =findViewById(R.id.bell);
        status_notification = findViewById(R.id.status_notification);
    }

    public void clickToAdd(View view) {
        newBossActivity= new BossActivity();
        newBossActivity.setDate(datePicker.getText().toString());
        newBossActivity.setNote(note.getText().toString());
        newBossActivity.setTime(timePicker.getText().toString());
        BossActivityRepository bossActivityRepository=new BossActivityRepository(AddDateActivity.this);
        bossActivityRepository.insertDate(newBossActivity);

        Intent intent = new Intent(AddDateActivity.this,DateListActivity.class);
        intent.putExtra(CHECK, check);
        startActivity(intent);
    }

    private void chooseDate(){
        final Calendar calendar= Calendar.getInstance();
        day= calendar.get(Calendar.DATE);
        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);
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

    private void chooseTime(){
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddDateActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(hourOfDay, minute);
                        SimpleDateFormat simpleTimeformat = new SimpleDateFormat("hh:mm");
                        timePicker.setText(simpleTimeformat.format(calendar.getTime()));
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void initialData() {
        this.context = this;
        final Calendar calendar = Calendar.getInstance();
        final Intent intent = new Intent(this.context, AlarmReceiver.class);
        final Intent getIntent = getIntent();
        final String category = getIntent.getExtras().getString("CATEGORY");
        final String bossName = getIntent.getExtras().getString("BOSSNAME");
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        @TargetApi(Build.VERSION_CODES.M)
        bell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calendar.add(Calendar.SECOND, 3);
                if(isChecked){
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    intent.putExtra("extra", "yes");
                    intent.putExtra("content", note+"");
                    intent.putExtra("category", category+"");
                    intent.putExtra("bossname", bossName+"");
                    check = true;
                    pendingIntent = PendingIntent.getBroadcast(AddDateActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                    setAlarmText("Notification set to " +day+"-"+month+"-"+year+"   "+ hour + ":" + minute);
                    setNotificationText("Thông báo bật");

                }
                else{
                    check = false;
                    intent.putExtra("extra", "no");
                    sendBroadcast(intent);
                    alarmManager.cancel(pendingIntent);
                    setNotificationText("Thông báo tắt");
                }
            }
        });
    }


    public void setNotificationText(String text_notify) {
        status_notification.setText(text_notify);
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("Notification", "on Destroy");
    }

}
