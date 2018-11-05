package mboss.tsm.BroadcashReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Build;
import android.util.Log;

import mboss.tsm.Service.RingtoneService1;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       String state = intent.getExtras().getString("extra");
       String content = intent.getExtras().getString("content");
       String category = intent.getExtras().getString("category");
       String bossname = intent.getExtras().getString("bossname");

       Intent serviceIntent = new Intent(context, RingtoneService1.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, RingtoneService1.class));
            serviceIntent.putExtra("extra", state);
            serviceIntent.putExtra("content", content);
            serviceIntent.putExtra("category", category);
            serviceIntent.putExtra("bossname", bossname);
            context.startForegroundService(serviceIntent);
        } else {
            serviceIntent.putExtra("extra", state);
            serviceIntent.putExtra("content", content);
            serviceIntent.putExtra("category", category);
            serviceIntent.putExtra("bossname", bossname);
//            context.startService(new Intent(context, RingtoneService1.class));
            context.startService(serviceIntent);
        }
//       context.startService(serviceIntent);
    }
}
