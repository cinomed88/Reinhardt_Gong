package ca.bcit.reinhardt_gong;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ReadingListAdapter extends ArrayAdapter<Reading> {
    private Activity context;
    private List<Reading> readingList;

    public ReadingListAdapter(Activity context, List<Reading> taskList) {
        super(context, R.layout.list_layout, taskList);
        this.context = context;
        this.readingList = taskList;
    }

    public ReadingListAdapter(Context context, int resource, List<Reading> objects, Activity context1, List<Reading> taskList) {
        super(context, resource, objects);
        this.context = context1;
        this.readingList = taskList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvSerialNumber = listViewItem.findViewById(R.id.textViewSerialNumber);
        TextView tvSystolic = listViewItem.findViewById(R.id.textViewSystolicPressure);
        TextView tvDiastolic = listViewItem.findViewById(R.id.textViewDiastolicPressure);
        TextView tvDate = listViewItem.findViewById(R.id.textViewDate);
        TextView tvTime = listViewItem.findViewById(R.id.textViewTime);
        TextView tvCondition = listViewItem.findViewById(R.id.textViewCondition);


        Reading reading = readingList.get(position);
        String systolic = Float.toString(reading.getSystolicReading());
        String diastolic = Float.toString(reading.getDiastolicReading());


        tvSerialNumber.setText(reading.getFamily_member());
        tvSystolic.setText(systolic);
        tvDiastolic.setText(diastolic);
        tvDate.setText(reading.getReadingDate());
        tvTime.setText(reading.getReadingTime());
        tvCondition.setText(reading.getCondition());

        return listViewItem;
    }

}

