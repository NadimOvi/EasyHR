package com.gtr.bmhr.AttendanceHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gtr.bmhr.Model.AttendanceDataShow;
import com.gtr.bmhr.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private List<AttendanceDataShow> list;
    private Context context;


    public DataAdapter(List<AttendanceDataShow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.attendancehistory_layout,viewGroup,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int position) {
        final AttendanceDataShow attendanceDataShow = list.get(position);

        if (attendanceDataShow==null){
            dataViewHolder.dtPunchDate.setText("");
            dataViewHolder.attnTime.setText("");
            dataViewHolder.location.setText("");
        }
        /*if (list.size()==0) {
            dataViewHolder.dtPunchDate.setText("");
            dataViewHolder.attnTime.setText("");
            dataViewHolder.location.setText("");
        }*/
        /*if (dataViewHolder.dtPunchDate==null&&dataViewHolder.attnTime==null&&dataViewHolder.location==null){
            dataViewHolder.dtPunchDate.setText("");
            dataViewHolder.attnTime.setText("");
            dataViewHolder.location.setText("");
        }*/else{
            String inputDateStr = attendanceDataShow.getDtPunchDate();
            String inputTimeStr = attendanceDataShow.getDtPunchTime();
            String inputLocationStr = attendanceDataShow.getLocationName();

            if (inputDateStr==null && inputTimeStr ==null && inputLocationStr==null){
                dataViewHolder.dtPunchDate.setText("");
                dataViewHolder.attnTime.setText("");
                dataViewHolder.location.setText("");
            }else{
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


                Date date = null;
                try {
                    date = inputFormat.parse(inputDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputDateStr = outputFormat.format(date);
                dataViewHolder.dtPunchDate.setText(outputDateStr);


                DateFormat outputTime = new SimpleDateFormat("HH:mm");

                Date time = null;
                try {
                    time = inputFormat.parse(inputTimeStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputTimeStr = outputTime.format(time);
                dataViewHolder.attnTime.setText(outputTimeStr);

                dataViewHolder.location.setText(inputLocationStr);

       /* if (dataViewHolder.dtPunchDate==null&&dataViewHolder.attnTime==null&&dataViewHolder.location==null){
            dataViewHolder.dtPunchDate.setText("");
            dataViewHolder.attnTime.setText("");
            dataViewHolder.location.setText("");
        }*/
            }
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

