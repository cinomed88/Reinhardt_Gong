package ca.bcit.reinhardt_gong;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private ArrayList<Reading> arrayList;
    private Context context;

    public ReportAdapter(ArrayList<Reading> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item, parent, false);
        ReportViewHolder holder = new ReportViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
//        holder.tv_month.setText(arrayList.get(position).getMonth());
//        holder.tv_DSN.setText(arrayList.get(position).getDSN());
//        holder.tv_systolic.setText(String.valueOf(arrayList.get(position).getSystolic()));
//        holder.tv_diastolic.setText(String.valueOf(arrayList.get(position).getDiastolic()));
//        holder.tv_avgCondition.setText(arrayList.get(position).getAvgCondition());
        String dateString = arrayList.get(position).getReadingDate();
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date  = dateFormat.parse(dateString, new ParsePosition(0));
        DateFormat monthFormat = new SimpleDateFormat("MMM yyyy");
        String currentMonth = monthFormat.format(date);

        holder.tv_month.setText(currentMonth);
        holder.tv_DSN.setText(arrayList.get(position).getFamily_member());
        holder.tv_systolic.setText(Float.toString(arrayList.get(position).getSystolicReading()));
        holder.tv_diastolic.setText(Float.toString(arrayList.get(position).getDiastolicReading()));
        holder.tv_avgCondition.setText(arrayList.get(position).getCondition());
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView tv_month;
        TextView tv_DSN;
        TextView tv_systolic;
        TextView tv_diastolic;
        TextView tv_avgCondition;


        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_month = itemView.findViewById(R.id.tv_month);
            this.tv_DSN = itemView.findViewById(R.id.tv_DSN);
            this.tv_systolic = itemView.findViewById(R.id.tv_systolic);
            this.tv_diastolic = itemView.findViewById(R.id.tv_diastolic);
            this.tv_avgCondition = itemView.findViewById(R.id.tv_avgCondition);

        }
    }
}