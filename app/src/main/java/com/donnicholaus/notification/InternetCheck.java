package com.donnicholaus.notification;

import android.net.ConnectivityManager;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class InternetCheck extends AsyncTask<Void,Void,Boolean> {

    private Consumer mConsumer;
    public  interface Consumer { void accept(Boolean internet); }

    public  InternetCheck(Consumer consumer) { mConsumer = consumer; execute(); }

    @Override protected Boolean doInBackground(Void... voids) { try {
        Socket sock = new Socket();
        sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
        sock.close();
        return true;
    } catch (IOException e) { return false; } }

    @Override protected void onPostExecute(Boolean internet) { mConsumer.accept(internet); }

    ///////////////////////////////////////////////////////////////////////////////////
    // Usage

 //   new InternetCheck(internet -> { /* do something with boolean response */ });
 // TCP/HTTP/DNS (depending on the port, 53=DNS, 80=HTTP, etc.)
     public boolean isOnline() {
         try {
             int timeoutMs = 1500;
             Socket sock = new Socket();
             SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

             sock.connect(sockaddr, timeoutMs);
             sock.close();

             return true;
         } catch (IOException e) { return false; }
     }


}
