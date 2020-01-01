package com.example.intercrew;

/**
 * Created by Miguel Garzón on 2017-05-09.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.intercrew.R.layout.day_hours_list;

public class DayHoursList extends ArrayAdapter<DayHours> {
    private Activity context;
    private List<DayHours> hours;


    private final String[] days = {"Monday", "Tuesday", "Wednesday" , "Thursday" , "Friday" , "Saturday" , "Sunday"};

    public DayHoursList(Activity context, List<DayHours> hours) {
        super(context, R.layout.day_hours_list, hours);
        this.context = context;
        this.hours = hours;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(day_hours_list, null, true);

        TextView textViewDay = (TextView) listViewItem.findViewById(R.id.textViewDayID);
        TextView textViewTime= (TextView) listViewItem.findViewById(R.id.textViewTimeID);

        DayHours dayHour = hours.get(position);

        String day = days[position];
        String time = dayHour.getStartHours() + ":" + dayHour.getStartMinutes() + " to " + dayHour.getEndHours() + ":" + dayHour.getEndMinutes();
        textViewDay.setText(day);
        textViewTime.setText(time);
        return listViewItem;
    }
}