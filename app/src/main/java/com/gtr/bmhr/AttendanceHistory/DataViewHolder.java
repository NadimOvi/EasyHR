package com.gtr.bmhr.AttendanceHistory;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gtr.bmhr.R;

public class DataViewHolder extends RecyclerView.ViewHolder {

    TextView empId,dtPunchDate,attnTime,location;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
       /* empId=itemView.findViewById(R.id.empId);*/
        dtPunchDate=itemView.findViewById(R.id.dtPunchDate);
        attnTime=itemView.findViewById(R.id.attnTime);
        location=itemView.findViewById(R.id.location);

    }
}
