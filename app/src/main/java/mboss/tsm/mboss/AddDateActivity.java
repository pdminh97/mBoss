package mboss.tsm.mboss;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import mboss.tsm.BroadcashReceiver.AlarmReceiver;
import mboss.tsm.Model.BossActivity;
import mboss.tsm.Repository.BossActivityRepository;

public class AddDateActivity extends AppCompatActivity {
    private TextView datePicker;
    private TextView timePicker;
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
    public boolean check = false;
    private static final String CHECK = "check";
    private ImageButton back;
    final Calendar calendar = Calendar.getInstance();

    String category;
    String bossName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_date);
        this.context = this;

        final Intent getIntent = getIntent();
        category = getIntent.getExtras().getString("CATEGORY");
        bossName = getIntent.getExtras().getString("BOSSNAME");
        initialView();
        initialData();
    }

    private void initialView() {
        final Calendar calendar = Calendar.getInstance();
        datePicker = findViewById(R.id.edtDate);
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyy");
//        datePicker.setText(simpleDateFormat.format(calendar.getTime()));
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        timePicker = findViewById(R.id.edtTime);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime();
            }
        });
        note = findViewById(R.id.edtNote);
        bell = findViewById(R.id.bell);
        status_notification = findViewById(R.id.status_notification);
        back = findViewById(R.id.btn_back);
    }

    public void clickToAdd(View view) {
        newBossActivity = new BossActivity();
        newBossActivity.setDate(datePicker.getText().toString());
        newBossActivity.setNote(note.getText().toString());
        newBossActivity.setTime(timePicker.getText().toString());
        newBossActivity.setNotificationStatus(check);
        BossActivityRepository bossActivityRepository = new BossActivityRepository(AddDateActivity.this);
        bossActivityRepository.insertDate(newBossActivity);

        Intent intent = new Intent(AddDateActivity.this, DateListActivity.class);
        intent.putExtra("CATEGORY", category);
        intent.putExtra("BOSSNAME", bossName);
        intent.putExtra("CHECK", check);
        intent.putExtra("mBoss",  newBossActivity);
        setResult(RESULT_OK, intent);
        finish();

    }

    private void chooseDate() {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int day1) {
//                calendar.set(year1, month1, day1);
                month = month1;
                year = year1;
                day = day1;
                calendar.set(Calendar.YEAR, year1);
                calendar.set(Calendar.MONTH, month1);
                calendar.set(Calendar.DAY_OF_MONTH, day1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                datePicker.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);


        datePickerDialog.show();
    }

    private void chooseTime() {
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddDateActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
//                        calendar.set(hourOfDay, minute1);
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute1);
                        hour = hourOfDay;
                        minute = minute1;
                        Log.e("Time hour of day: ", hour + " " + minute);
                        SimpleDateFormat simpleTimeformat = new SimpleDateFormat("HH:mm");
                        timePicker.setText(simpleTimeformat.format(calendar.getTime()));
                    }
                }, hour, minute, false);
//        timePickerDialog.show();
        timePickerDialog.show();

    }

    private void initialData() {
        this.context = this;
        final Intent intent = new Intent(this.context, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        @TargetApi(Build.VERSION_CODES.M)
        bell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                calendar.add(Calendar.SECOND, 3);
                if (isChecked) {
                    intent.putExtra("extra", "yes");
                    intent.putExtra("content", note.getText().toString() + "");
                    intent.putExtra("category", category + "");
                    intent.putExtra("bossname", bossName + "");
                    check = isChecked;
                    Log.e("Time", hour + " : " + minute + " date: " + day + "-" + month + "-" + year + "   " + hour + ":" + minute);
                    pendingIntent = PendingIntent.getBroadcast(AddDateActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    setNotificationText("Thông báo bật");

                } else if (!isChecked) {
                    check = isChecked;
                    intent.putExtra("extra", "no");
                    sendBroadcast(intent);
                    alarmManager.cancel(pendingIntent);
                    setNotificationText("Thông báo tắt");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddDateActivity.this, DateListActivity.class);
                intent1.putExtra("CATEGORY", category);
                intent1.putExtra("BOSSNAME", bossName);
                setResult(RESULT_OK, intent1);
                finish();
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
