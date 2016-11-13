package com.example.core;

import java.util.ArrayList;
import java.util.List;

import entities.Grad;

/**
 * Created by Filip on 9.11.2016..
 */

public interface DataLoadedListener {
    void onDataLoaded(List<Grad> gradovi);
}
