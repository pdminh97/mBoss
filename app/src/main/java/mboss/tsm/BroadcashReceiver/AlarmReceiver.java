package mboss.tsm.BroadcashReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Build;

import mboss.tsm.Service.RingtoneService1;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       String state = intent.getExtras().getString("extra");

       Intent serviceIntent = new Intent(context, RingtoneService1.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, RingtoneService1.class));
            serviceIntent.putExtra("extra", state);
            context.startForegroundService(serviceIntent);
        } else {
//            context.startService(new Intent(context, RingtoneService1.class));
            context.startService(serviceIntent);
        }
//       context.startService(serviceIntent);
    }
}
