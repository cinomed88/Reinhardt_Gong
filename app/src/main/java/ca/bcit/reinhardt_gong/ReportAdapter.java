package ca.bcit.reinhardt_gong;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private ArrayList<Report> arrayList;
    private Context context;

    public ReportAdapter(ArrayList<Report> arrayList, Context context) {
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
        holder.tv_month.setText(arrayList.get(position).getMonth());
        holder.tv_DSN.setText(arrayList.get(position).getDSN());
        holder.tv_systolic.setText(String.valueOf(arrayList.get(position).getSystolic()));
        holder.tv_diastolic.setText(String.valueOf(arrayList.get(position).getDiastolic()));
        holder.tv_avgCondition.setText(arrayList.get(position).getAvgCondition());
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
