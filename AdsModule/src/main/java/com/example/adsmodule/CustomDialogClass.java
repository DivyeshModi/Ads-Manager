package com.example.adsmodule;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import com.github.ybq.android.spinkit.SpinKitView;

public class CustomDialogClass extends Dialog {

    private int loader_color;

    CustomDialogClass(Activity a, int loader_color) {
        super(a);
        this.loader_color = loader_color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        SpinKitView mSpinkit = findViewById(R.id.mSpinkit);
        mSpinkit.setColor(loader_color);
        setCancelable(false);
    }
}
