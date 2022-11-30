package ua.gura.com.example.androidoracle.activities.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.activities.model.Holiday;

public class HolidaysAdapter
        extends RecyclerView.Adapter<HolidaysAdapter.HolidayViewHolder>
implements View.OnClickListener{

    private List<Holiday> holidays = Collections.emptyList();
    private HolidayListener listener;

    public HolidaysAdapter(HolidayListener listener) {
        this.listener = listener;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolidayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_holiday, parent, false);
        return new HolidayViewHolder(root,this);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayViewHolder holder, int position) {
        Holiday holiday = holidays.get(position);
        holder.nameHolidayTextView.setText(holiday.getName());
        holder.dataTextView.setText(holiday.getDate());
        holder.itemView.setTag(holiday);
    }

    @Override
    public int getItemCount() {
        return holidays.size();
    }

    @Override
    public void onClick(View v) {
        Holiday holiday = (Holiday)v.getTag();
        listener.onHolidayChosen(holiday);
    }

    static class HolidayViewHolder extends RecyclerView.ViewHolder {
        private TextView dataTextView;
        private TextView nameHolidayTextView;

        public HolidayViewHolder(View itemView, View.OnClickListener listener) {
            super(itemView);
            dataTextView = itemView.findViewById(R.id.dataTextView);
            nameHolidayTextView = itemView.findViewById(R.id.nameHolidayTextView);
            itemView.setOnClickListener(listener);
        }
    }

    public interface HolidayListener{
        void onHolidayChosen(Holiday holiday);
    }
}
