package com.nfujii.permissions;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.Manifest;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 1;

    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readExternalStorage();
            }
        });

        mTextView = (TextView)findViewById(R.id.text_view);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE: {
                // 要求がキャンセルされた場合は配列は空
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 権限が与えられたので権限が必要な処理を行う
                    Log.d(TAG, "ユーザーにより権限が与えられた");
                    readExternalStorage();
                } else {
                    // 拒否されたので、権限が必要な必要なを無効化する
                    Log.d(TAG, "ユーザーにより権限の付与を拒否された");
                }
            }
        }
    }

    private void readExternalStorage() {
        // Android 6.0でかつtarget SDK 22以下の場合で、
        // 権限がOFFになっている場合、下記のメソッドでは常にGRANTEDが返ってきてしまうらしい
        // その場合->PermissionChecker.checkSelfPermission()を使えばOKらしい
        // http://sys1yagi.hatenablog.com/entry/2015/11/07/185539
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // 説明を表示すべきかどうか
            // ユーザーが以前に「今後確認しない」をチェックして拒否している場合trueになる
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // 説明を表示すべき場合はダイアログなどで表示し、設定画面から権限を与えてもらう必要あり
                Log.d(TAG, "ユーザーに権限の必要性を表示する必要がある");

            } else {
                // 説明の表示は必要ないので、権限を要求する
                Log.d(TAG, "権限がないので要求する");
                requestPermission();
            }
        } else {
            // 権限が必要な処理を行う
            Log.d(TAG, "権限があるので処理を行う");
            File[] files = Environment.getExternalStorageDirectory().listFiles();
            StringBuffer sb = new StringBuffer();
            for (File file : files) {
                sb.append(file.getName()).append('\n');
            }
            mTextView.setText(sb.toString());
        }
    }

    private void requestPermission() {
        // システムがダイアログを表示し、ユーザーに権限を与えるか尋ねる
        // このダイアログはカスタマイズ不可なので、その他の情報をユーザーに与えたい場合は
        // このメソッドを呼ぶ前に表示する
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE);
    }
}
