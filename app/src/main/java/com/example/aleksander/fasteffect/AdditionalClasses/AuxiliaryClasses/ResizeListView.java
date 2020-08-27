package com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Klasa służaca do dynamicznej zmiany lisView
 */
public class ResizeListView {

    public static final String TAG = "com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses";

    /**
     * Zmienia rozmiary opkna w sposob "dynamiczny"
     * @param listView ktory ma zostac zmieniony
     */
    public void resize(ListView listView) {

        Log.i(TAG, "resize - resize dla listView");

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {

            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
