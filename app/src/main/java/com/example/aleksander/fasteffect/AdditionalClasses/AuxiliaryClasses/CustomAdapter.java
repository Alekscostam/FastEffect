package com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aleksander.fasteffect.R;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    public static final String TAG ="com.example.aleksander.fasteffect.AdditionalClasses.AuxiliaryClasses";

    /**
     * Konstruktor moze przyjac dowolny, wybrany przez nas ksztalt(parametry)
     */
    public CustomAdapter(@NonNull Context context, List<String> productList) {
        super(context, R.layout.custom_row_adapter, productList);
    }

    /**
     * Własny widok dla adaptera
     * Dane sa dostepne dzieki inicjalizacji konstruktora, ktory znajduje sie powyzej
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Log.i(TAG,"getView - ładowanie produktów do adapterów");

        View customView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row_adapter, parent, false);

        String singleProduct = getItem(position);

        TextView productName =  customView.findViewById(R.id.productName);
        TextView productCalories =  customView.findViewById(R.id.productCalories);
        TextView restOfValuesName = customView.findViewById(R.id.restOfValuesName);

        assert singleProduct != null;
        String[] splitProducts = singleProduct.split(" {2}");

        productName.setText(splitProducts[0]);
        productCalories.setText(splitProducts[1]);
        restOfValuesName.setText((splitProducts[2] + " " + splitProducts[3] + " " + splitProducts[4] + " " + splitProducts[5]));

        return customView;
    }
}
