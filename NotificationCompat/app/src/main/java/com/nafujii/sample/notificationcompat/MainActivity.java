package com.nafujii.sample.notificationcompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final int ACCENT_COLOR = 0x0042A5F5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.show_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationMinimum();
            }
        });
        findViewById(R.id.show_big_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationBigIcon();
            }
        });
        findViewById(R.id.show_vibration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationVibration();
            }
        });
        findViewById(R.id.show_launch_application).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationApp();
            }
        });
        findViewById(R.id.show_big_text_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationStyle();
            }
        });
        findViewById(R.id.show_big_picture_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationPictureStyle();
            }
        });
        findViewById(R.id.show_inbox_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationInboxStyle();
            }
        });
        findViewById(R.id.show_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationWithButton();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            String command = intent.getStringExtra("message");
            Log.v("intent", "Message: " + command);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * アイコンとタイトルをステータスバーに表示する
     */
    private void sendNotificationMinimum() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("ノーティフィケーションの最小表示");
        builder.setContentText("コンテンツの内容");
        builder.setContentInfo("情報欄");
        builder.setTicker("アプリからの通知概要");
        builder.setColor(ACCENT_COLOR);

        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    /**
     * 大きなアイコンの通知を表示する
     */
    private void sendNotificationBigIcon() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("大きなアイコンで表示");

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(largeIcon);

        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build()); // 第1引数は通知ID
    }

    /**
     * バイツレーション付き
     */
    private void sendNotificationVibration() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("バイブレーション付き");
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);
        // 以下は上と同義
        // builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(3, builder.build());
    }

    /**
     * 通知をクリックでアプリを起動
     */
    private void sendNotificationApp() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("通知からアプリを起動する");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(4, builder.build());
    }

    /**
     * BigTextStyleを利用する
     */
    private void sendNotificationStyle() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("タイトル");  // 画像非表示時に表示。未設定の場合空欄になる。
        builder.setContentText("コンテンツの内容"); // 画像非表示時に表示。未設定の場合空欄になる。
        builder.setContentInfo("情報欄"); // 画像表示時も表示される

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle(builder);
        bigTextStyle.setBigContentTitle("BigTextStyleを利用する");
        bigTextStyle.bigText("コンテンツのテキスト");
        bigTextStyle.setSummaryText("通知内容のサマリ");

        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(5, builder.build());
    }

    /**
     * BigPictureStyleを利用する
     */
    private void sendNotificationPictureStyle() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("タイトル");  // 画像非表示時に表示。未設定の場合空欄になる。
        builder.setContentText("コンテンツの内容"); // 画像非表示時に表示。未設定の場合空欄になる。
        builder.setContentInfo("情報欄"); // 画像表示時も表示される

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle(builder);
        Bitmap largePicture = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bigPictureStyle.bigPicture(largePicture);
        bigPictureStyle.setBigContentTitle("BigPictureStyleを利用する");
        bigPictureStyle.setSummaryText("通知内容のサマリ");

        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(6, builder.build());
    }

    /**
     * InboxStyleを利用する
     */
    private void sendNotificationInboxStyle() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("タイトル");  // 画像非表示時に表示。未設定の場合空欄になる。
        builder.setContentText("コンテンツの内容"); // 画像非表示時に表示。未設定の場合空欄になる。
        builder.setContentInfo("情報欄"); // 画像表示時も表示される

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(builder);
        inboxStyle.setBigContentTitle("InboxStyleを利用する");
        inboxStyle.setSummaryText("通知内容のサマリ");
        inboxStyle.addLine("複数行で表示する(1)");
        inboxStyle.addLine("複数行で表示する(2)");

        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(7, builder.build());
    }

    /**
     * 通知にボタンを追加
     */
    private void sendNotificationWithButton() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("ボタンを追加する");

        // DELボタンを追加
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("message", "DEL");
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(android.R.drawable.ic_input_delete, "DEL", pendingIntent);

        // ADDボタンを追加する
        Intent addIntent = new Intent(getApplicationContext(), MainActivity.class);
        addIntent.putExtra("message", "ADD");
        PendingIntent addPendingIntent = PendingIntent.getActivity(MainActivity.this, 1, addIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(android.R.drawable.ic_input_add, "ADD", addPendingIntent);

        builder.setAutoCancel(true);
        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(8, builder.build());
    }

    /**
     * すべての内容ををロックスクリーンに表示
     */
    private void setNotificationWitPublic() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentTitle("Public notification");
        builder.setContentText("ロックスクリーンに表示される");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC); // デフォルトはVISIBILITY_PRIVATE

        builder.setAutoCancel(true);
        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(9, builder.build());
    }

    /**
     * ロックスクリーンと通常の通知の表示内容を分ける
     */
    private void setNotificationWithPrivate() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentTitle("ロックスクリーン用の通知");
        builder.setContentText("ロックスクリーン用の表示");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationCompat.Builder privateBuilder = new NotificationCompat.Builder(getApplicationContext());
        privateBuilder.setContentTitle("ロックスクリーンには表示されない通知");
        privateBuilder.setContentText("ロックスクリーンには代替の通知が表示される。");
        privateBuilder.setSmallIcon(R.mipmap.ic_launcher);
        privateBuilder.setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
        privateBuilder.setPublicVersion(builder.build());

        builder.setAutoCancel(true);
        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(10, privateBuilder.build());
    }
}
