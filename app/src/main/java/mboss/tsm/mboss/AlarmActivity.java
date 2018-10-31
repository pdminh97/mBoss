package mboss.tsm.mboss;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import mboss.tsm.BroadcashReceiver.AlarmReceiver;

public class AlarmActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private TimePicker alarmTimePicker;
    private DatePicker alarmDatePicker;

    private TextView alarmTextView;
    private AlarmReceiver alarm;
    private Button start_alarm;
    private Button stop_alarm;

    private AlarmActivity inst;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        this.context = this;
        initialView();
        initialData();
    }

    private void initialView() {
        alarmTextView = findViewById(R.id.timePicker);
        alarmDatePicker = findViewById(R.id.datePicker);
        alarmTimePicker = findViewById(R.id.alarmTimePicker);


    }

    private void initialData() {
        final Calendar calendar = Calendar.getInstance();
        start_alarm = (Button) findViewById(R.id.start_alarm);
        stop_alarm = (Button) findViewById(R.id.stop_alarm);
        final Intent intent = new Intent(this.context, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.SECOND, 3);
                int year = alarmDatePicker.getYear();
                int month = alarmDatePicker.getMonth();
                int day = alarmDatePicker.getDayOfMonth();
                int hour = alarmTimePicker.getCurrentHour();
                int minute = alarmTimePicker.getCurrentMinute();

                setAlarmText("You set a " + "Year: " + year + "Month: " + month + "Day: " + day + " Hour: " + hour + ":" + minute);

                calendar.set(Calendar.YEAR, alarmDatePicker.getYear());
                calendar.set(Calendar.MONTH, alarmDatePicker.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, alarmDatePicker.getDayOfMonth());
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
                intent.putExtra("extra", "yes");
                pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                setAlarmText("Alarm set to " + hour + ":" + minute);
            }
        });
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("extra", "no");
                sendBroadcast(intent);

                alarmManager.cancel(pendingIntent);
                setAlarmText("Alarm canceled");
            }
        });


    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("MyActivity", "on Destroy");
    }
}
