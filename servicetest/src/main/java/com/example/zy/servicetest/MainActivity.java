package com.example.zy.servicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener{
    private Button startButton;
    private Button stopButton;
    private Button bindButton;
    private Button unbindButton;

    private MyService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder)service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //将service改造成一个前台服务
        
        startButton=(Button)findViewById(R.id.Start_services);
        stopButton =(Button)findViewById(R.id.Stop_services);
        bindButton =(Button)findViewById(R.id.Bind_service);
        unbindButton=(Button)findViewById(R.id.UnBind_Service);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        bindButton.setOnClickListener(this);
        unbindButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Start_services:{
                Intent startIntent=new Intent(this,MyService.class);
                startService(startIntent);
                break;
            }

            case R.id.Stop_services:{
                Intent stopIntent=new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            }
            //绑定Service
            case R.id.Bind_service:{
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);

                break;
            }
            //解除绑定
            case R.id.UnBind_Service:{
                unbindService(connection);
            }
            default:break;
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
}
