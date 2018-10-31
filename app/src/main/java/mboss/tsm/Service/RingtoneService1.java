package mboss.tsm.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import mboss.tsm.mboss.AlarmActivity;
import mboss.tsm.mboss.MainActivity;
import mboss.tsm.mboss.R;

public class RingtoneService1 extends Service {
    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startStatus;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), AlarmActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Tắm cho chó nè !")
                .setContentText("Open")
                .setSmallIcon(R.drawable.icon_alarm)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();
        String state = intent.getExtras().getString("extra");
        assert state != null;
        switch (state) {
            case "no":
                startStatus = 0;
                break;
            case "yes":
                startStatus = 1;
                break;
            default:
                startStatus = 0;
                break;
        }


        if (!this.isRunning && startStatus == 1) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.mboss_alarm);
            mMediaPlayer.start();
            mNM.notify(0, mNotify);
            this.isRunning = true;
            this.startStatus = 0;
        }
        else if (!this.isRunning && startStatus == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startStatus = 0;

        }

        else if (this.isRunning && startStatus == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startStatus = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");

            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startStatus = 0;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();
        this.isRunning = false;
    }
}
