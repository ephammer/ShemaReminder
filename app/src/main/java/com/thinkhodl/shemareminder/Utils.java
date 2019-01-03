package com.thinkhodl.shemareminder;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import net.sourceforge.zmanim.ComplexZmanimCalendar;
import net.sourceforge.zmanim.util.GeoLocation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

public class Utils {


    public static int NOTIFICATION_REQUEST_CODE = 45235;

    @SuppressWarnings("static-access")
    public static void generateNotification(Context context) {


        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        createNotificationChannel(context);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "my_channel_01")
                .setSmallIcon(R.drawable.ic_book_stars_01)
                .setContentTitle("Kriath Shema Reminder")
                .setContentText("Did you already pray night Shema?")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);


        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(11221, mBuilder.build());


    }

    private static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Night Shema alerts";
            String description = "Get night Shema reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel_01", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void checkLocation(Context context) {
        // Get saved Location
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        //        SharedPreferences sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        double savedLongitude = Double.parseDouble(sharedPref.getString(context.getString(R.string.saved_longitude), "31.7767"));
        double savedLatitude = Double.parseDouble(sharedPref.getString(context.getString(R.string.saved_latitude), "35.2345"));
        Location savedLocation = new Location("Saved Location");
        savedLocation.setLatitude(savedLatitude);
        savedLocation.setLongitude(savedLongitude);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        float distance = lastKnownLocation.distanceTo(savedLocation);
        int approxDistance = (int) distance;
        if (approxDistance > 10000) {
            setNotificationTimer(context, lastKnownLocation);
        }

    }

    public static Date getNight(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        String locationName = "";
        double elevation = 0; //optional elevation
        TimeZone timeZone = TimeZone.getDefault();
        GeoLocation geoLocation = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
        ComplexZmanimCalendar czc = new ComplexZmanimCalendar(geoLocation);

        return czc.getTzais();
    }

    public static String formatTime(Date date)
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        return sdfDate.format(date);
    }

    public static void setNotificationTimer(Context context, Location location) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.saved_longitude), String.valueOf(location.getLongitude()));
        editor.putString(context.getString(R.string.saved_latitude), String.valueOf(location.getLatitude()));
        editor.apply();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getNight(location));

        // debug
        /*long ONE_MINUTE_IN_MILLIS=1000;//millisecs
        long t= calendar.getTimeInMillis();
        Date afterAddingTenMins=new Date(t + (5 * ONE_MINUTE_IN_MILLIS));
        calendar.setTime(afterAddingTenMins);*/

        Intent myIntent = new Intent(context, SendNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }
}