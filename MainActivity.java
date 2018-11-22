package com.example.ingeenerforum;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    // Идентификатор уведомления
    private ProgressBar progressBar;
    private static final int NOTIFY_ID = 107;
    private static final String NOTIFICATION_CHANNEL_ID ="112";
    private WebView mWebView;
    Notification.Builder builder;
    NotificationManager notificationManager;
    PendingIntent contentIntent;

    private class MyWebViewClient extends WebViewClient
    {
        @SuppressWarnings("deprecation") @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N) @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
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
        mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                MainActivity.this.setProgress(progress * 1000);

                progressBar.incrementProgressBy(progress);

                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        mWebView.getSettings().setAllowFileAccess(true);
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
   // public void builderStart() {
   //     builder.setContentIntent(contentIntent)
   //             // обязательные настройки
   //             .setSmallIcon(R.mipmap.ic_launcher)
   //             //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
   //             .setContentTitle("Напоминание")
   //             //.setContentText(res.getString(R.string.notifytext))
   //             .setContentText("Пора покормить кота") // Текст уведомления
   //             // необязательные настройки
   //             // .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.hungrycat)) // большая
   //             // картинка
   //             //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
   //             .setTicker("Последнее китайское предупреждение!")
   //             .setWhen(System.currentTimeMillis())
   //             .setAutoCancel(true); // автоматически закрыть уведомление после нажатия
//
   //     Notification notification =builder.build();
//
   //     // Альтернативный вариант
   //     // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
   //     notificationManager.notify(NOTIFY_ID, notification);
   // }
 //  public void onClickStart(View v) {
 //      startService(new Intent(this, MyService.class));
 //  }

 //  public void onClickStop(View v) {
 //      stopService(new Intent(this, MyService.class));
 //  }
 //  public void onClick(View view) {
 //      Log.d("ffff","go");
 //      Intent notificationIntent = new Intent(this, MainActivity.class);
 //      contentIntent = PendingIntent.getActivity(this,
 //              0, notificationIntent,
 //              PendingIntent.FLAG_CANCEL_CURRENT);
 //      notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
 //      Resources res = this.getResources();
 //      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
 //      NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

 //      // Configure the notification channel.
 //      notificationChannel.setDescription("Channel description");
 //      notificationChannel.enableLights(true);
 //      notificationChannel.setLightColor(Color.RED);
 //      notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
 //      notificationChannel.enableVibration(true);
 //      notificationManager.createNotificationChannel(notificationChannel);
 //      // до версии Android 8.0 API 26
 //       builder = new Notification.Builder(this , NOTIFICATION_CHANNEL_ID);
 //          builderStart();
 //         }
// else{
   //          builder = new Notification.Builder(this );
   //         builderStart();


       // }


  //  }
}
