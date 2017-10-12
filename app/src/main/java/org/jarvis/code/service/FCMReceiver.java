package org.jarvis.code.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by ki.kao on 9/28/2017.
 */

public class FCMReceiver extends BroadcastReceiver {

    public FCMReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
           // IReceiver receiver=intent.getSerializableExtra()
           // if(receiver!=null)
            if (intent.getAction().equals(Constants.FCM_BROADCAST_ACTION_NEW)) {
                String data = intent.getStringExtra("data");
               // if (receiver != null)
                   // receiver.onReceive(context, new JSONArray(data));
                Loggy.i(FCMReceiver.class, data);
            } else if (intent.getAction().equals(Constants.FCM_BROADCAST_ACTION_UPDATE)) {

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IReceiver {
        void onNewItem(Context context, JSONArray jsonArray);

        void onUpdateItem(Context context, JSONArray jsonArray);

        void onDeleteItem(Context context, JSONArray jsonArray);
    }
}
