package com.example.ayfer.onlinedovizbilgileri;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Emiliya Ismayil on 17-Dec-17.
 */

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("messaildirim başlığıge")); // Mesaj içeriği alınıp bildirim gösteren metod çağırılıyor
    }

    private void showNotification(String message) {

        Intent i = new Intent(this, MainActivity.class); // Bildirime basıldığında hangi aktiviteye gidilecekse
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setAutoCancel(true) // Kullanıcı bildirime girdiğinde otomatik olarak silinsin. False derseniz bildirim kalıcı olur.
                .setContentTitle("Döviz Bilgileri") // B
                .setContentText(message) // Bildirim mesajı
                //.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark) // Bildirim simgesi
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }
}