package com.example.zy.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private DownloadBinder mBinder=new DownloadBinder();
    public MyService() {
    }

    class DownloadBinder extends Binder {
        //模拟下载过程
        public void startDownload(){
            Log.e("MyService","startDownload progress");
        }

        public int getProgress(){
            Log.e("MyService","getProgress");
            return 0;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyServices","Services Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyServices", "Services Start");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MyService","Services Stop");
    }
}
