package com.donnicholaus.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.denzcoskun.imageslider.constants.ScaleTypes;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Green";
    private static final String CHANNEL_NAME = "Green";
    private static final String  CHANNEL_DESCRIPTION = "Clean City";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isConnected(this)){
            Toast.makeText(this, "Connect na internet we mbwa", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //Image slider (slides images)

        ImageSlider slider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://homepages.cae.wisc.edu/~ece533/images/cat.png", "I", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://homepages.cae.wisc.edu/~ece533/images/lena.png", "Love", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://homepages.cae.wisc.edu/~ece533/images/girl.png", "You", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://homepages.cae.wisc.edu/~ece533/images/barbara.png", "So", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://homepages.cae.wisc.edu/~ece533/images/pool.png", "Much", ScaleTypes.CENTER_CROP));

        slider.setImageList(slideModels, ScaleTypes.FIT);


        findViewById(R.id.txtView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNotification();
            }
        });

    }

    private void displayNotification(){


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Its working ...")
                .setContentText("1st Notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(1, mBuilder.build());

        // Create pending intent, mention the Activity which needs to be
        //triggered when user clicks on notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Notification.class), PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(contentIntent);

        //Get an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


        //Biulds the notification and issues it
        notificationManager.notify(1, mBuilder.build());
    }

//    new InternetCheck(internet -> { /* do something with boolean response */ });


    private boolean isConnected(MainActivity mainActivity){

        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifiConn != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected())){
            return  true;
        }else
            return false;
    }

    //cjui//
    // TCP/HTTP/DNS (depending on the port, 53=DNS, 80=HTTP, etc.)
//    public boolean isOnline() {
//        try {
//            int timeoutMs = 1500;
//            Socket sock = new Socket();
//            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
//
//            sock.connect(sockaddr, timeoutMs);
//            sock.close();
//
//            return true;
//        } catch (IOException e) { return false; }
//    }
}