package com.example.adsmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appnext.ads.interstitial.Interstitial;
import com.appnext.banners.BannerAdRequest;
import com.appnext.banners.BannerListener;
import com.appnext.banners.BannerSize;
import com.appnext.banners.BannerView;
import com.appnext.core.AppnextAdCreativeType;
import com.appnext.core.AppnextError;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.nativeads.MediaView;
import com.appnext.nativeads.NativeAd;
import com.appnext.nativeads.NativeAdRequest;
import com.appnext.nativeads.NativeAdView;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdsManager;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class MyAdsManager {

    private static boolean is_initialize = false;
    private static int color;
    private static int loader_color = Color.parseColor("#D81B60");

    private static String id;
    private static String device_id = "f757feca-3bc1-49d3-ad3c-70cdf15293bc";

    public static int getLoader_color() {
        return loader_color;
    }

    public static void setLoader_color(int color) {
        loader_color = color;
    }

    public static void setDevice_id(String id) {
        device_id = id;
    }

    public static void CreateInterstitial(final Activity ac, final Intent intent, String fb_id, final String admob_id, final String appnext_id) {
        if (!is_initialize) {
            AudienceNetworkAds.initialize(ac);
            is_initialize = true;
            Log.e("LLLL :", "Initialize");
        }

        final CustomDialogClass customDialog = new CustomDialogClass(ac, loader_color);
        customDialog.show();

        final InterstitialAd FB_Interstitial = new InterstitialAd(ac, fb_id);
        FB_Interstitial.loadAd();
        FB_Interstitial.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                ac.startActivity(intent);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("LLLL_FB_Interstitial", "FB Interstitial failed to load : " + adError.getErrorMessage());
                final com.google.android.gms.ads.InterstitialAd AdMob_Interstitial = new com.google.android.gms.ads.InterstitialAd(ac);
                AdMob_Interstitial.setAdUnitId(admob_id);
                AdRequest admob_ad_request = new AdRequest.Builder().build();
                AdMob_Interstitial.loadAd(admob_ad_request);
                AdMob_Interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        AdMob_Interstitial.show();
                        if (customDialog.isShowing()) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        ac.startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        Log.e("LLLL_AdMob_Interstitial", "AdMob Interstitial failed to load");
                        Interstitial AppNext_Interstitial = new Interstitial(ac, appnext_id);
                        AppNext_Interstitial.loadAd();
                        AppNext_Interstitial.setOnAdLoadedCallback(new OnAdLoaded() {
                            @Override
                            public void adLoaded(String s, AppnextAdCreativeType appnextAdCreativeType) {
                                if (customDialog.isShowing()) {
                                    customDialog.dismiss();
                                }
                            }

                        });
                        AppNext_Interstitial.setOnAdClosedCallback(new OnAdClosed() {
                            @Override
                            public void onAdClosed() {
                                ac.startActivity(intent);
                            }
                        });
                        AppNext_Interstitial.setOnAdErrorCallback(new OnAdError() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void adError(String s) {
                                Log.e("LLLL_AppNext_Interstitial", "AppNext Interstitial failed to load : " + s);
                                if (customDialog.isShowing()) {
                                    customDialog.dismiss();
                                }
                                ac.startActivity(intent);
                            }
                        });


                    }
                });
            }

            @Override
            public void onAdLoaded(Ad ad) {
                FB_Interstitial.show();
                if (customDialog.isShowing()) {
                    customDialog.dismiss();
                }
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

    }

    public static void Load_Native_Banner(final Activity ac, final RelativeLayout relativeLayout, String fb_id, final String admob_id, final String appnext_id) {
        if (!is_initialize) {
            AudienceNetworkAds.initialize(ac);
            is_initialize = true;
            Log.e("LLLL :", "Initialize");
        }
        AdSettings.addTestDevice(device_id);       //Div
        final NativeBannerAd FB_nativeBannerAd = new NativeBannerAd(ac, fb_id);
        FB_nativeBannerAd.loadAd();
        FB_nativeBannerAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("LLLL_FB_Banner", "FB Banner failed to load : " + adError.getErrorMessage());
                AdView admob_banner_ad = new AdView(ac);
                admob_banner_ad.setAdSize(AdSize.SMART_BANNER);
                admob_banner_ad.setAdUnitId(admob_id);
                relativeLayout.addView(admob_banner_ad);
                AdRequest admob_ad_request = new AdRequest.Builder().build();
                admob_banner_ad.loadAd(admob_ad_request);
                admob_banner_ad.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        relativeLayout.setVisibility(View.VISIBLE);
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        Log.e("LLLL_AdMob_Banner", "AdMob Banner failed to load");
                        final BannerView AppNext_Banner = new BannerView(ac);
                        AppNext_Banner.setPlacementId(appnext_id);
                        AppNext_Banner.setBannerSize(BannerSize.LARGE_BANNER);
                        AppNext_Banner.loadAd(new BannerAdRequest());
                        AppNext_Banner.setBannerListener(new BannerListener() {

                            @Override
                            public void onAdLoaded(String s, AppnextAdCreativeType appnextAdCreativeType) {
                                super.onAdLoaded(s, appnextAdCreativeType);
                                Log.e("LLLL_AppNext_Banner", "AppNext Banner loaded : " + s);
                                relativeLayout.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(AppnextError appnextError) {
                                super.onError(appnextError);
                                Log.e("LLLL_AppNext_Banner", "AppNext Banner failed to load : " + appnextError.getErrorMessage());
                                relativeLayout.setVisibility(View.GONE);
                            }
                        });


                    }
                });
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (FB_nativeBannerAd != ad) {
                    return;
                }
                inflate_FB_Ad_banner(ac, relativeLayout, FB_nativeBannerAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        });
    }

    private static void inflate_FB_Ad_banner(Activity ac, RelativeLayout relativeLayout, NativeBannerAd fb_nativeBannerAd) {
        fb_nativeBannerAd.unregisterView();

        LinearLayout linear_adView = (LinearLayout) LayoutInflater.from(ac).inflate(R.layout.fb_native_banner, relativeLayout, false);
        relativeLayout.addView(linear_adView);
        relativeLayout.setVisibility(View.VISIBLE);

        RelativeLayout ad_choices_container = linear_adView.findViewById(R.id.ad_choices_container);
        ad_choices_container.removeAllViews();
        ad_choices_container.addView(new AdChoicesView(ac, fb_nativeBannerAd, true), 0);

        TextView native_ad_title = linear_adView.findViewById(R.id.native_ad_title);
        TextView native_ad_social_context = linear_adView.findViewById(R.id.native_ad_social_context);
        TextView native_ad_sponsored_label = linear_adView.findViewById(R.id.native_ad_sponsored_label);
        AdIconView adIconView = linear_adView.findViewById(R.id.native_icon_view);
        Button native_ad_call_to_action = linear_adView.findViewById(R.id.native_ad_call_to_action);
        native_ad_call_to_action.setText(fb_nativeBannerAd.getAdCallToAction());

        native_ad_call_to_action.setVisibility(fb_nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);

        native_ad_title.setText(fb_nativeBannerAd.getAdvertiserName());
        native_ad_social_context.setText(fb_nativeBannerAd.getAdSocialContext());

        native_ad_sponsored_label.setText(fb_nativeBannerAd.getSponsoredTranslation());
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(native_ad_title);
        clickableViews.add(native_ad_call_to_action);

        fb_nativeBannerAd.registerViewForInteraction(linear_adView, adIconView, clickableViews);
    }

    public static void Load_Native_Ad(final Activity ac, final RelativeLayout relativeLayout, String fb_id, final String admob_id, final String appnext_id) {
        if (!is_initialize) {
            AudienceNetworkAds.initialize(ac);
            is_initialize = true;
            Log.e("LLLL :", "Initialize");
        }
        AdSettings.addTestDevice(device_id);       //Div
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(ac, fb_id, com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
        relativeLayout.addView(adView);
        adView.loadAd();
        adView.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(final Ad ad, AdError adError) {
                Log.e("LLLL_FB_Native :", "FB Native failed to load : " + adError.getErrorMessage());
                AdView admob_native_ad = new AdView(ac);
                admob_native_ad.setAdSize(AdSize.MEDIUM_RECTANGLE);
                admob_native_ad.setAdUnitId(admob_id);
                relativeLayout.addView(admob_native_ad);
                AdRequest admob_ad_request = new AdRequest.Builder().build();
                admob_native_ad.loadAd(admob_ad_request);
                admob_native_ad.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        relativeLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        Log.e("LLLL_AdMob_Native :", "AdMob Native failed to load");

                        final NativeAd AppNext_native_ad = new NativeAd(ac, appnext_id);
//                        AppNext_native_ad.setPrivacyPolicyColor(PrivacyIcon.PP_ICON_COLOR_LIGHT);
                        AppNext_native_ad.loadAd(new NativeAdRequest());
                        AppNext_native_ad.setAdListener(new com.appnext.nativeads.NativeAdListener() {

                            @Override
                            public void onAdLoaded(NativeAd nativeAd, AppnextAdCreativeType appnextAdCreativeType) {
                                super.onAdLoaded(nativeAd, appnextAdCreativeType);
                                Log.e("LLLL_AppNext_Native :", "AppNext Native loaded  ");
                                if (AppNext_native_ad != nativeAd) {
                                    return;
                                }
                                inflate_AppNext_Native(ac, relativeLayout, nativeAd);
                            }

                            @Override
                            public void onError(NativeAd nativeAd, AppnextError appnextError) {
                                super.onError(nativeAd, appnextError);
                                Log.e("LLLL_AppNext_Native :", "AppNext Native failed to load : " + appnextError.getErrorMessage());
                                relativeLayout.setVisibility(View.GONE);
                            }
                        });
                    }
                });
            }

            @Override
            public void onAdLoaded(Ad ad) {
                relativeLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        });
    }

    private static void inflate_AppNext_Native(Activity ac, RelativeLayout relativeLayout, NativeAd nativeAd) {
        NativeAdView adView = (NativeAdView) LayoutInflater.from(ac).inflate(R.layout.appnext_native, relativeLayout, false);
        relativeLayout.addView(adView);
        relativeLayout.setVisibility(View.VISIBLE);

        NativeAdView nativeAdView = adView.findViewById(R.id.na_view);
        ImageView imageView = adView.findViewById(R.id.na_icon);
        TextView textView = adView.findViewById(R.id.na_title);
        MediaView mediaView = adView.findViewById(R.id.na_media);
        Button button = adView.findViewById(R.id.install);
        TextView rating = adView.findViewById(R.id.rating);
        TextView description = adView.findViewById(R.id.description);
        ArrayList<View> viewArrayList = new ArrayList<>();
        viewArrayList.add(button);
        viewArrayList.add(mediaView);

        //The ad Icon
        nativeAd.downloadAndDisplayImage(imageView, nativeAd.getIconURL());

        //The ad title
        textView.setText(nativeAd.getAdTitle());

        //Setting up the Appnext MediaView
        nativeAd.setMediaView(mediaView);

        //The ad rating
        rating.setText(nativeAd.getStoreRating());

        //The ad description
        description.setText(nativeAd.getAdDescription());

        //Registering the clickable areas - see the array object in `setViews()` function
        nativeAd.registerClickableViews(nativeAdView);

        //Setting up the entire native ad view
        nativeAd.setNativeAdView(nativeAdView);


    }

    public static void setupAdsManager(Activity ac, final List<com.facebook.ads.NativeAd> mAds, final MyAdapterManager adp, String fb_id, int i) {
        final NativeAdsManager mAdsManager = new NativeAdsManager(ac, fb_id, i);
        mAdsManager.loadAds(com.facebook.ads.NativeAd.MediaCacheFlag.ALL);
        mAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                if (mAdsManager.getUniqueNativeAdCount() != 0) {
                    mAds.clear();
                    for (int i = 0; i < mAdsManager.getUniqueNativeAdCount(); i++) {
                        mAds.add(mAdsManager.nextNativeAd());
                    }
                    if (adp != null)
                        adp.notifyDataSetChanged();
                }
            }

            @Override
            public void onAdError(AdError adError) {
                Log.e("LLLL_FB_Error", "Facebook Native Ad Error : '" + adError.getErrorMessage() + "'");
            }
        });
    }
}
