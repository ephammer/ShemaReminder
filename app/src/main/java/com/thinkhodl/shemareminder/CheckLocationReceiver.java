package com.thinkhodl.shemareminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CheckLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i("App", "called receiver method");
        try{
            Utils.checkLocation(context);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}