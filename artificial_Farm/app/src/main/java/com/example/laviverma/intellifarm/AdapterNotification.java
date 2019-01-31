package com.example.laviverma.intellifarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lavi Verma on 16-Jul-18.
 */

public class AdapterNotification extends ArrayAdapter<Notification>
{

    public AdapterNotification(Context context, ArrayList<Notification> n) {
        super(context,0, n);

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item2, parent, false);
        }
        Notification n = getItem(position);
       TextView noti = (TextView) listItemView.findViewById(R.id.main_notification);
       TextView time =(TextView)listItemView.findViewById(R.id.timestamp);
       time.setText(n.getTimestamp());
       noti.setText(n.getNotification());

    //    polyText.setText(local_ph.getName());

        return listItemView;
    }
}