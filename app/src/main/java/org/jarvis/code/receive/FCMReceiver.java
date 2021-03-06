package org.jarvis.code.receive;

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

    private IReceiver receiver;

    public FCMReceiver() {
    }

    public FCMReceiver(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals(Constants.FCM_BROADCAST_ACTION)) {
                String data = intent.getStringExtra("data");
                if (receiver != null)
                    receiver.onReceive(context, new JSONArray(data));
                Loggy.i(FCMReceiver.class, data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface IReceiver {

        void onReceive(Context context, JSONArray jsonArray);
    }
}
