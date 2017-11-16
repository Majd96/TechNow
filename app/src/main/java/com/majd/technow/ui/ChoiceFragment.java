package com.majd.technow.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.majd.technow.R;

/**
 * Created by majd on 11/10/17.
 */

public class ChoiceFragment extends Fragment {
    public static boolean isTwoPane;
    View rootView;

    public ChoiceFragment() {
        //required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_choice, container, false);
        isTwoPane = rootView.findViewById(R.id.linear_layout_twoPane) != null;

        AdView adView = rootView.findViewById(R.id.adView1);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Load ads into Banner Ads
        adView.loadAd(request);
        return rootView;
    }
}
