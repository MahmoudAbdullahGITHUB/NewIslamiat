package com.perfect.islamyat.adapters;

import android.content.Context;

import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.perfect.islamyat.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;

//    private List<EventObjects> allEvents;
//    , List<EventObjects> allEvents
    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate) {
        super(context, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
//        this.allEvents = allEvents;
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }
        if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        //Add day to calendar
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));
        cellNumber.setTag("asdsdsa");
        //Add events to the calendar
        TextView hijriDate = (TextView) view.findViewById(R.id.hijri_date);
        TextView hijriDateDtls = (TextView) view.findViewById(R.id.hijri_date_dtls);
        TextView current_date_text = (TextView) view.findViewById(R.id.current_date_text);
        Calendar eventCalendar = Calendar.getInstance();
//        GregorianCalendar gCal = new GregorianCalendar(currentDate.getTimeZone());
        GregorianCalendar uCal = new UmmalquraCalendar();
        uCal.setTime(dateCal.getTime());
//        hijriDate.setText(uCal.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ar")));

        hijriDate.setText(uCal.get(Calendar.DAY_OF_MONTH)+"");
        hijriDate.setVisibility(View.VISIBLE);
        hijriDateDtls.setText(uCal.get(Calendar.DAY_OF_MONTH)+" "+uCal.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ar")));
        hijriDateDtls.setVisibility(View.GONE);
        String strGregorian  = dateCal.get(Calendar.DAY_OF_MONTH) + " " + getArabicMonth(dateCal.getDisplayName(Calendar.MONTH,Calendar.LONG,
                Locale.ENGLISH)) + " " + dateCal.get(Calendar.YEAR) + " م";
        String strHijri  = uCal.get(Calendar.DAY_OF_MONTH) + " " + uCal.getDisplayName(Calendar.MONTH,Calendar.LONG,
                new Locale("ar")) + " " + uCal.get(Calendar.YEAR) + " هـ" + "<br />";
        current_date_text.setText(Html.fromHtml(strHijri + strGregorian));
        if (dayValue == currentDate.get(Calendar.DAY_OF_MONTH) && displayMonth == currentMonth && displayYear == currentYear) {
//            view.setBackgroundColor(Color.parseColor("#000000"));
            hijriDateDtls.setVisibility(View.VISIBLE);
            hijriDate.setVisibility(View.GONE);

        }

        if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            cellNumber.setVisibility(View.VISIBLE);
//            hijriDate.setVisibility(View.VISIBLE);
        }else{
            view.setBackgroundColor(Color.parseColor("#cccccc"));
//            cellNumber.setVisibility(View.INVISIBLE);
//            hijriDate.setVisibility(View.INVISIBLE);
        }

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hijriDateDtls.setVisibility(View.VISIBLE);
//                hijriDate.setVisibility(View.GONE);
//            }
//        });

//        for(int i = 0; i < allEvents.size(); i++){
////            eventCalendar.setTime(allEvents.get(i).getDate());
//            if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
//                    && displayYear == eventCalendar.get(Calendar.YEAR)){
//                eventIndicator.setBackgroundColor(Color.parseColor("#FF4081"));
//            }
//        }
        return view;
    }
    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }

    private String getArabicMonth(String monthName) {
        String name;
        switch (monthName){
            case "January":
                name = "يناير";
                break;
            case "February":
                name = "فبراير";
                break;
            case "March":
                name = "مارس";
                break;
            case "April":
                name = "ابريل";
                break;
            case "May":
                name = "مايو";
                break;
            case "June":
                name = "يونيو";
                break;
            case "July":
                name = "يوليو";
                break;
            case "August":
                name = "أغسطس";
                break;
            case "September":
                name = "سبتمبر";
                break;
            case "October":
                name = "أكتوبر";
                break;
            case "November":
                name = "نوفمبر";
                break;
            case "December":
                name = "ديسمبر";
                break;
            default:
                name = "يناير";
                break;
        }
        return name;

    }
}
