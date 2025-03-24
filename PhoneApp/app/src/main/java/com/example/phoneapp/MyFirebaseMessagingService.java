//package com.example.phoneapp;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    private static final String TAG = "FCMService";
//
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage message) {
//        Log.d(TAG,"From: "+message.getFrom());
//        if (message.getNotification()!=null){
//            sendNotification(message.getNotification().getTitle(),message.getNotification().getBody());
//        }
//        super.onMessageReceived(message);
//    }
//
//    @Override
//    public void onNewToken(@NonNull String token) {
//        super.onNewToken(token);
//    }
//
//    private void sendNotification(String title, String body) {
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
//
//
//        String channelId = "Default";
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)
//                .setSmallIcon(R.drawable.profile)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            NotificationChannel notificationChannel = new NotificationChannel(channelId,"Default Channel",NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//
//        notificationManager.notify(0,notificationBuilder.build());
//    }
//}
