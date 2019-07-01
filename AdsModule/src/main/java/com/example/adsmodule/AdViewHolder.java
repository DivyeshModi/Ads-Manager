package com.example.adsmodule;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;
import java.util.List;

public class AdViewHolder extends RecyclerView.ViewHolder {

    //    TextView tvDescription;
    MediaView adMediaView;

    private Activity ac;
    TextView native_ad_title, native_ad_social_context, native_ad_sponsored_label;
    AdIconView native_icon_view;
    RelativeLayout ad_choices_container;
    Button native_ad_call_to_action;

    public AdViewHolder(Activity ac, @NonNull View itemView) {
        super(itemView);
        this.ac = ac;
        native_ad_title = itemView.findViewById(R.id.native_ad_title);
        native_ad_social_context = itemView.findViewById(R.id.native_ad_social_context);
        native_ad_sponsored_label = itemView.findViewById(R.id.native_ad_sponsored_label);
        native_icon_view = itemView.findViewById(R.id.native_icon_view);
        ad_choices_container = itemView.findViewById(R.id.ad_choices_container);
        native_ad_call_to_action = itemView.findViewById(R.id.native_ad_call_to_action);
//        tvDescription = itemView.findViewById(R.id.tvDescription);
        adMediaView = itemView.findViewById(R.id.adMediaView);
    }

    public void setAdData(NativeAd data) {
        // Set the Text.
//        tvDescription.setText(data.getAdLinkDescription());
        native_ad_title.setText(data.getAdvertiserName());
        native_ad_social_context.setText(data.getAdSocialContext());
        native_ad_sponsored_label.setText(data.getSponsoredTranslation());
        native_ad_call_to_action.setText(data.getAdCallToAction());

        ad_choices_container.removeAllViews();
        ad_choices_container.addView(new AdChoicesView(ac, data, true), 0);


        NativeAd.Image adIcon = data.getAdIcon();


        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(native_ad_title);
        clickableViews.add(native_icon_view);
        clickableViews.add(native_ad_call_to_action);
//            clickableViews.add(adMediaView);
        data.registerViewForInteraction(itemView, adMediaView, native_icon_view, clickableViews);

        //configureLayoutToAspectRatio(adMediaView, adContainer);
    }
}