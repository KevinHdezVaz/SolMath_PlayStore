package com.vazk.calculator;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.security.Principal;

public class Notificaciones extends FirebaseMessagingService {
    private static final String DESCUENTO = "descuento";
    public Notificaciones() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if  (remoteMessage.getData().size()>0 && remoteMessage.getNotification()!=null){
            sendNotificacion(remoteMessage);
        }

    }

    private void sendNotificacion(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, PrincipalMenu.class);


        String descStr = remoteMessage.getData().get(DESCUENTO);
        float desc = Float.valueOf(descStr != null? descStr : "0");

        intent.putExtra(DESCUENTO, desc);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSound  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);





        Notification.Builder notificacionBuilder = new Notification.Builder(this)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            notificacionBuilder.setColor(desc > .4 ?
                    ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) :
                    ContextCompat.getColor(getApplicationContext(), R.color.colorGRadiente));

            notificacionBuilder.setPriority(Notification.PRIORITY_MAX);

        }

            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            String chanel = "channel.id.notification.normal";
            String chanelName = "Oferta normal";
            NotificationChannel channel = new NotificationChannel(chanel,chanelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100,200,200,50});

            if  (notificationManager!= null){
                notificationManager.createNotificationChannel(channel);
            }
            notificacionBuilder.setChannelId(chanel);
        }
        if  (notificationManager!= null){
            notificationManager.notify("",0,notificacionBuilder.build());
        }

    }

}
