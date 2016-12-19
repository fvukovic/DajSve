package com.example.filip.dajsve.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.dajsve.R;

/**
 * Created by Filip on 19.12.2016..
 */

public class PocetnaFragment extends Fragment{

    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public PocetnaFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pocetna_fragment,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("noveponude").setIndicator("Nove ponude"),
                NovePonudeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("sveponude").setIndicator("Sve ponude"),
                SvePonudeFragment.class, null);



        return rootView;
    }

}
