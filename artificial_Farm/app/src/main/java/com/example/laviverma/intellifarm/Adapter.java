package com.example.laviverma.intellifarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lavi Verma on 03-Jul-18.
 */

public class Adapter extends ArrayAdapter<PolyHouses>
{

    public Adapter(Context context, ArrayList<PolyHouses> phouse) {
        super(context,0, phouse);

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        PolyHouses local_ph = getItem(position);
        TextView polyText = (TextView) listItemView.findViewById(R.id.poly_text);
        polyText.setText(local_ph.getName());

        return listItemView;
    }
}
