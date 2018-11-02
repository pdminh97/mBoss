package mboss.tsm.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Random;

import mboss.tsm.Fragment.DiaryFragment;
import mboss.tsm.mboss.AddDateActivity;
import mboss.tsm.mboss.AlarmActivity;
import mboss.tsm.mboss.R;

public class RingtoneService1 extends Service {

    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String channelId = "default_channel_id";
        String channelDescription = "Default Channel";
        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), DiaryFragment.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        String content = intent.getExtras().getString("content");
        String category = intent.getExtras().getString("category");
        String bossname = intent.getExtras().getString("bossname");
        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle(category + " cho " + bossname+" !")
                .setContentText(content+"")
                .setSmallIcon(R.drawable.icon_alarm)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setShowWhen(true)
                .setChannelId(channelId)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .build();

        String state = intent.getExtras().getString("extra");
        Log.e("what is going on here  ", state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }


        if(!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");

                mMediaPlayer = MediaPlayer.create(this, R.raw.slow_spring_board);

            mMediaPlayer.start();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = mNM.getNotificationChannel(channelId);
                if (notificationChannel == null) {
                    int importance = NotificationManager.IMPORTANCE_HIGH; //Set the importance level
                    notificationChannel = new NotificationChannel(channelId, channelDescription, importance);
                    notificationChannel.setLightColor(Color.GREEN); //Set if it is necesssary
                    notificationChannel.enableVibration(true); //Set if it is necesssary
                    mNM.createNotificationChannel(notificationChannel);
                }
            }
            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        }
        else if (!this.isRunning && startId == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        else if (this.isRunning && startId == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");

            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        Log.e("MyActivity", "In the service");

        return START_NOT_STICKY;

    }


    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }







}
