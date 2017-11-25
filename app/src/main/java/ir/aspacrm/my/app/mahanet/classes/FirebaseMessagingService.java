package ir.aspacrm.my.app.mahanet.classes;


import android.app.PendingIntent;
import android.content.Intent;

import com.google.firebase.messaging.RemoteMessage;

import ir.aspacrm.my.app.mahanet.ActivityShowNotify;

import static ir.aspacrm.my.app.mahanet.G.context;

/**
 * Created by Firoozian on 1/14/2017.
 */


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent(this, ActivityShowNotify.class);
        PendingIntent intent2 = PendingIntent.getBroadcast(context, 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

}