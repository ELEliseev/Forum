package com.example.ingeenerforum;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 107;
    private static final String NOTIFICATION_CHANNEL_ID ="112";
    private WebView mWebView;



    private class MyWebViewClient extends WebViewClient
    {






        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //уведомления
        // Create Notification


         mWebView = findViewById(R.id.MainWeb);
        mWebView.getSettings().setBuiltInZoomControls(true); //масштабирование
        // включаем поддержку JavaScript
        mWebView.getSettings().setDisplayZoomControls(false);//откл кнопки с лупами

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        // указываем страницу загрузки

        mWebView.loadUrl( "http://liftovik.listbb.ru/index.php");
        mWebView.setWebViewClient(new MyWebViewClient());
    }
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }

    }
    public void onClickStart(View v) {
        startService(new Intent(this, MyService.class));
    }

    public void onClickStop(View v) {
        stopService(new Intent(this, MyService.class));
    }
    public void onClick(View view) {
        Log.d("ffff","go");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Resources res = this.getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

        // Configure the notification channel.
        notificationChannel.setDescription("Channel description");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(notificationChannel);
        // до версии Android 8.0 API 26
        Notification.Builder builder = new Notification.Builder(this , NOTIFICATION_CHANNEL_ID);
            builder.setContentIntent(contentIntent)
                    // обязательные настройки
                    .setSmallIcon(R.mipmap.ic_launcher)
                    //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                    .setContentTitle("Напоминание")
                    //.setContentText(res.getString(R.string.notifytext))
                    .setContentText("Пора покормить кота") // Текст уведомления
                    // необязательные настройки
                    // .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.hungrycat)) // большая
                    // картинка
                    //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                    .setTicker("Последнее китайское предупреждение!")
                    .setWhen(System.currentTimeMillis())
            .setAutoCancel(true); // автоматически закрыть уведомление после нажатия

            Notification notification =builder.build();

            // Альтернативный вариант
            // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFY_ID, notification);}
else{
            Notification.Builder builder = new Notification.Builder(this );
            builder.setContentIntent(contentIntent)
                    // обязательные настройки
                    .setSmallIcon(R.drawable.ic_stat_name)
                    //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                    .setContentTitle("Напоминание")
                    //.setContentText(res.getString(R.string.notifytext))
                    .setContentText("Пора покормить кота") // Текст уведомления
                    // необязательные настройки
                    // .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.hungrycat)) // большая
                    // картинка
                    //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                    .setTicker("Последнее китайское предупреждение!")
                    .setWhen(System.currentTimeMillis());
            //.setAutoCancel(true); // автоматически закрыть уведомление после нажатия

            Notification notification =builder.build();

            // Альтернативный вариант
            // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFY_ID, notification);
        }


    }
}
