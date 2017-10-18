package org.jarvis.code.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;
import org.json.JSONArray;

/**
 * Created by ki.kao on 9/28/2017.
 */

public class FirebaseBroadcastReceiver extends BroadcastReceiver {

    private BroadcastAction broadcastAction;

    public FirebaseBroadcastReceiver(BroadcastAction broadcastAction) {
        super();
        this.broadcastAction = broadcastAction;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            int action = intent.getIntExtra("action", 0);
            String data = intent.getStringExtra("data");
            JSONArray jsonArray = new JSONArray(data);
            Loggy.i(FirebaseBroadcastReceiver.class, data);
            Loggy.i(FirebaseBroadcastReceiver.class, String.valueOf(action));
            if (broadcastAction != null && action > 0 && jsonArray.length() > 0) {
                if (intent.getAction().equals(Constants.FCM_BROADCAST_PRODUCT)) {
                    actionFilter(action, context, jsonArray);
                } else if (intent.getAction().equals(Constants.FCM_BROADCAST_PROMOTION)) {
                    actionFilter(action, context, jsonArray);
                } else if (intent.getAction().equals(Constants.FCM_BROADCAST_ADVERTISEMENT)) {
                    actionFilter(action, context, jsonArray);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actionFilter(int action, Context context, JSONArray jsonArray) {
        switch (action) {
            case 1:
                broadcastAction.onNewItem(context, jsonArray);
                break;
            case 2:
                broadcastAction.onUpdateItem(context, jsonArray);
                break;
            case 3:
                broadcastAction.onDeleteItem(context, jsonArray);
                break;
            default:
                break;
        }
    }

    public interface BroadcastAction {
        void onNewItem(Context context, JSONArray jsonArray);

        void onUpdateItem(Context context, JSONArray jsonArray);

        void onDeleteItem(Context context, JSONArray jsonArray);
    }
}
