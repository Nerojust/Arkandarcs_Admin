package com.nerojust.arkandarcsadmin.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static final String CUSTOMER_SESSION = "ArkandArcspref";
    private static MyApplication myApplication;
    private static int logoutTime;
    private String LOG_TAG = "ApplicationObserver";
    private Retrofit retrofit;
    private boolean logoutCounter;


    public static SharedPreferences getSharedPreferences() {
        return myApplication.getSharedPreferences(CUSTOMER_SESSION, Context.MODE_PRIVATE);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();




        OkHttpClient okHttpClientNetwork = new OkHttpClient().newBuilder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new AddHeaderInterceptor());
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientNetwork)
                .client(httpClient.build())
                //.client(client.newBuilder().build())
                .baseUrl(Constants.BASE_URL_ARKANDARCS)
                .build();

    }

    //using retrofit
    public Retrofit getRetrofit() {
        return retrofit;
    }

    //inactivity logout settings
    public static class LogOutTimerUtil {
        // delay in milliseconds i.e. 5 min = 300000 ms
        private static Timer longTimer;

        public static synchronized void startLogoutTimer(final Context context, final LogOutListener logOutListener) {
            stopLogoutTimer();

            //logoutTime = sessionManagerAgent.getSessionIdleTimeForAgent() * 60000;
            //10secs
            //logoutTime = 10000;
            //1min 15sec
            //logoutTime = 90000;
            //3 minutes
            logoutTime = 180000;

            //10 minutes
            //logoutTime = 1000000;

            if (longTimer == null) {
                longTimer = new Timer();
                longTimer.schedule(new TimerTask() {
                    public void run() {
                        cancel();
                        longTimer = null;
                        try {
                            boolean foreGround = new ForegroundCheckTask().execute(context).get();
                            if (foreGround) {
                                try {
                                    logOutListener.doLogout();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    logOutListener.doLogout();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }, logoutTime);
            }
        }

        private static synchronized void stopLogoutTimer() {
            if (longTimer != null) {
                longTimer.cancel();
                longTimer = null;
            }
        }

        public interface LogOutListener {
            void doLogout();
        }

        static class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Context... params) {
                final Context context = params[0].getApplicationContext();
                return isAppOnForeground(context);
            }

            private boolean isAppOnForeground(Context context) {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> appProcesses = Objects.requireNonNull(activityManager).getRunningAppProcesses();
                if (appProcesses == null) {
                    return false;
                }
                final String packageName = context.getPackageName();
                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                    if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                        return true;
                    }
                }
                return false;
            }
        }
    }

    //create an intercepter to add multiple headers to chain
    public class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Authorization", "Bearer " + AppUtils.getSessionManagerInstance().getToken());

            return chain.proceed(builder.build());
        }
    }


}
