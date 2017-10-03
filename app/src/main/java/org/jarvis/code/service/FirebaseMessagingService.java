package org.jarvis.code.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.RemoteMessage;

import org.jarvis.code.R;
import org.jarvis.code.activity.MainActivity;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;
import org.json.JSONArray;

import java.util.Map;

/**
 * Created by KimChheng on 9/3/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Map<String, String> data = remoteMessage.getData();
            Loggy.i(FirebaseMessagingService.class, "onMessageReceived");
            Loggy.i(FirebaseMessagingService.class, data.toString());
            //showNotification(data.get("title"), data.get("message"));
            JSONArray jsonArray = new JSONArray(data.get("data"));
            Intent intent = new Intent(Constants.FCM_BROADCAST_ACTION);
            intent.putExtra("data", jsonArray.toString());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            Loggy.i(FirebaseMessagingService.class, "send broadcast");
        } catch (Exception e) {
            e.printStackTrace();
            Loggy.i(FirebaseMessagingService.class, e.getMessage());
        }
    }

    private void showNotification(String title, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }
}
