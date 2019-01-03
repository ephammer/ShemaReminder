package com.thinkhodl.shemareminder.backend;


import com.thinkhodl.shemareminder.R;

import java.util.ArrayList;

public class PrayerLists {

    private static ArrayList<Prayer> mShemaPrayer;

    public static ArrayList<Prayer> getmShemaPrayer() {

        mShemaPrayer = new ArrayList<>();

        mShemaPrayer.add(new Prayer(R.string.bed_shema_shema_intro,
                Prayer.TYPE_SUBTITLE));
        mShemaPrayer.add(new Prayer(R.string.bed_shema_shema,
                Prayer.TYPE_TITLE));
        mShemaPrayer.add(new Prayer(R.string.bed_shema_shema_baruch_shem_kevod,
                Prayer.TYPE_SUBTITLE));
        mShemaPrayer.add(new Prayer(R.string.bed_shema_shema_firstparagraph));
        mShemaPrayer.add(new Prayer(R.string.bed_shema_shema_secondparagraph));
        mShemaPrayer.add(new Prayer(R.string.bed_shema_shema_thirdparagraph));

        return mShemaPrayer;
    }


}
