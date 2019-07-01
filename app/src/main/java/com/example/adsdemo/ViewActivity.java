package com.example.adsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adsmodule.AdViewHolder;
import com.example.adsmodule.MyAdsManager;
import com.example.adsmodule.MyAdapterManager;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private Activity ac;
    private RecyclerView mRecyclerview;
    private ArrayList<Model_User> User_List = new ArrayList<>();

    private List<NativeAd> mAds = new ArrayList<>();
    private MyAdapterManager myAdapterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ac = ViewActivity.this;
        User_List.clear();
        for (int i = 0; i < 11; i++) {
            User_List.add(new Model_User("Div","84122212"));
            User_List.add(new Model_User("Vivek","1210201"));
            User_List.add(new Model_User("Bhargav","212010"));
            User_List.add(new Model_User("Ashish","477301"));
            User_List.add(new Model_User("Vinit","121"));
        }

        mRecyclerview = findViewById(R.id.mRecyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(ac));

        int pos = Math.round(User_List.size() / 10);
        Load_Recycler_view(pos);
        MyAdsManager.setupAdsManager(ac,
                mAds,
                myAdapterManager,
                getResources().getString(R.string.fb_native_id),
                Math.round(User_List.size() / pos));
    }

    private void Load_Recycler_view(int ad_pos) {
        myAdapterManager = new MyAdapterManager(ac, User_List, R.layout.graphics_txt, mAds, ad_pos)
                .setRowItemView(new MyAdapterManager.AdapterView() {
                    @Override
                    public Object setAdapterView(ViewGroup parent, int viewType, int layoutId) {
                        if (viewType == MyAdapterManager.TYPE_AD) {
                            return new AdViewHolder(ac, LayoutInflater.from(ac).inflate(R.layout.graphics_native_banner, parent, false));
                        }
                        return new MyViewHolder(LayoutInflater.from(ac).inflate(layoutId, parent, false));
                    }

                    @Override
                    public void onBindView(Object holder, final int position, Object data, List<Object> dataList) {
                        if (getItemView(position) == MyAdapterManager.TYPE_AD) {
                            int adjustedPosition = position / MyAdapterManager.mAdPosition;
                            Log.e("Ads_Pos :", position + " / " + MyAdapterManager.mAdPosition + " = " + adjustedPosition);
                            if (adjustedPosition >= 0 && adjustedPosition < mAds.size()) {
                                ((AdViewHolder) holder).setAdData(mAds.get(adjustedPosition));
                            }
                        } else {
                            final int adjustedPosition = position - Math.min((position / MyAdapterManager.mAdPosition), MyAdapterManager.mAdPosition - 1);
                            Log.e("Art_Pos :", position + " - " + Math.min((position / MyAdapterManager.mAdPosition), MyAdapterManager.mAdPosition - 1) + " = " + adjustedPosition);
                            if (adjustedPosition >= 0 && adjustedPosition < dataList.size()) {
                                ((MyViewHolder) holder).setData(dataList.get(adjustedPosition));
                            }
                        }
                    }

                    int getItemView(int position) {
                        if (mAds.size() > 0 && position > 0 && position % MyAdapterManager.mAdPosition == 0 && position / MyAdapterManager.mAdPosition < mAds.size()) {
                            return MyAdapterManager.TYPE_AD;
                        }
                        return MyAdapterManager.TYPE_DATA;
                    }
                });
        mRecyclerview.setAdapter(myAdapterManager);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt, sub_txt;

        MyViewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
            sub_txt = itemView.findViewById(R.id.sub_txt);
        }

        void setData(Object o) {
            Model_User m=((Model_User) o);
            txt.setText(m.getName());
            sub_txt.setText(m.getPhone_No());
        }
    }
}
