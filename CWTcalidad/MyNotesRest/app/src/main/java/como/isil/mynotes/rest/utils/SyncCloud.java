package como.isil.mynotes.rest.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import como.isil.mynotes.rest.LoginActivity;
import como.isil.mynotes.rest.PruebaActivity;


/**
 * Created by Fabricio on 19/11/2016.
 */
public  class SyncCloud extends AsyncTask<String, String, String>{




    private String resp;

    OnSyncCload mlistener;
    Context context;


    public SyncCloud(OnSyncCload mlistener, Context context) {
        this.mlistener = mlistener;
        this.context = context;

    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress("Verificando Conexion..."); // Calls onProgressUpdate()


        try {


            Boolean con = isURLReachable(context);
            resp = String.valueOf(con) ;
        }  catch (Exception e) {
            e.printStackTrace();
            resp = e.getMessage();
        }



        return resp;
    }


    @Override
    protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation


        mlistener.onPostExecute(result);
    }


    @Override
    protected void onPreExecute() {

        mlistener.onPreExecute();
    }


    @Override
    protected void onProgressUpdate(String... text) {


        mlistener.onProgressUpdate(text[0]);

    }






    static public boolean isURLReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {



                URL url = new URL("http://api.backendless.com");   // Change to "http://google.com" for www  test.
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(10 * 1000);          // 10 s.
                urlc.setReadTimeout(10 * 1000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                    Log.v("sync", "Success url !");
                    return true;
                } else {
                    return false;
                }
            }
            catch (MalformedURLException e1) {
                return false;
            } catch (IOException e) {
                return false;
            } catch (NetworkOnMainThreadException e) {
                Log.v("sync", ""+e.toString());
                return false;
            }
        }
        return false;
    }



}
