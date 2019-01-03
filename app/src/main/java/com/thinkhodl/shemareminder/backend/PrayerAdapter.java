package com.thinkhodl.shemareminder.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.thinkhodl.shemareminder.R;

import java.util.ArrayList;

public class PrayerAdapter extends RecyclerView.Adapter<PrayerAdapter.ViewHolder> {

    private static Context mContext;
    private ArrayList<Prayer> mArrayPrayer;
    private LayoutInflater mInflater;

    public PrayerAdapter(ArrayList<Prayer> prayers, Context context) {
        mArrayPrayer = prayers;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PrayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prayer_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        int contentRes = mArrayPrayer.get(position).getContentResource();
        int type = mArrayPrayer.get(position).getType();

        switch (type) {
            case Prayer.TYPE_TITLE:
                holder.mTitleTextView.setText(contentRes);
                holder.mTitleTextView.setVisibility(View.VISIBLE);
                holder.mContentTextView.setVisibility(View.GONE);
                holder.mSubtitleTextView.setVisibility(View.GONE);
                holder.mCaptionTextView.setVisibility(View.GONE);
                break;

            case Prayer.TYPE_MAIN_PRAYER:
                holder.mTitleTextView.setVisibility(View.GONE);
                holder.mSubtitleTextView.setVisibility(View.GONE);
                holder.mContentTextView.setVisibility(View.VISIBLE);
                holder.mCaptionTextView.setVisibility(View.GONE);
                holder.mContentTextView.setText(contentRes);
                break;
            case Prayer.TYPE_SUBTITLE:
                holder.mContentTextView.setVisibility(View.GONE);
                holder.mTitleTextView.setVisibility(View.GONE);
                holder.mSubtitleTextView.setVisibility(View.VISIBLE);
                holder.mCaptionTextView.setVisibility(View.GONE);
                holder.mSubtitleTextView.setText(contentRes);
                break;
            case Prayer.TYPE_CAPTION:
                holder.mContentTextView.setVisibility(View.GONE);
                holder.mTitleTextView.setVisibility(View.GONE);
                holder.mSubtitleTextView.setVisibility(View.GONE);
                holder.mCaptionTextView.setVisibility(View.VISIBLE);
                holder.mCaptionTextView.setText(contentRes);
                break;
            default:
                Log.e("Adapter", "ERROR");
                break;
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayPrayer.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item
        TextView mTitleTextView;
        TextView mSubtitleTextView;
        TextView mContentTextView;
        TextView mCaptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.prayerItem_title);
            mSubtitleTextView = itemView.findViewById(R.id.prayerItem_subtitle);
            mContentTextView = itemView.findViewById(R.id.prayerItem_main);
            mCaptionTextView =itemView.findViewById(R.id.prayerItem_caption);

            /*
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                    mContext.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE);
            int fontSizeMain = sharedPreferences.getInt(mContext.getString(R.string.saved_font_size),20);
            mContentTextView.setTextSize(fontSizeMain);
            mTitleTextView.setTextSize((float) (fontSizeMain*1.25));
            mSubtitleTextView.setTextSize((float) (fontSizeMain*0.8));
            mCaptionTextView.setTextSize((float) (fontSizeMain*0.8));
            */

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mTitleTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                mSubtitleTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                mContentTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                mCaptionTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
            }
        }
    }

}
