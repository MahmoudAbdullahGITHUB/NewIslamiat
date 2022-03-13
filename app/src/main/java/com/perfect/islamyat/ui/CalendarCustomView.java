package com.perfect.islamyat.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//import com.inducesmile.androidcustomcalendar.database.DatabaseQuery;
import com.perfect.islamyat.CalendarFragment;
import com.perfect.islamyat.MainActivity;
import com.perfect.islamyat.R;
import com.perfect.islamyat.adapters.GridAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

    public class CalendarCustomView extends LinearLayout{
        private static final String TAG = CalendarCustomView.class.getSimpleName();
        private ImageView previousButton, nextButton;
        private TextView currentDate;
        private GridView calendarGridView;
        private Button addEventButton;
        private static final int MAX_CALENDAR_COLUMN = 42;
        private int month, year;
        private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        private Context context;
        private GridAdapter mAdapter;
        public String currentDateGregorian;
        public String currentDateHijri;
        public CalendarCustomView(Context context) {
            super(context);
        }
        public CalendarCustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            initializeUILayout();
//            setUpCalendarAdapter();
            Calendar X = Calendar.getInstance();
            X.add(Calendar.DAY_OF_MONTH,2);
            Date x = X.getTime();
            setDate(x);
            setPreviousButtonClickEvent();
            setNextButtonClickEvent();
            setGridCellClickEvents();
            Log.d(TAG, "I need to call this method");
        }
        public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
        private void initializeUILayout(){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.calendar_layout, this);
            previousButton = (ImageView)view.findViewById(R.id.previous_month);
            nextButton = (ImageView)view.findViewById(R.id.next_month);
            currentDate = (TextView)view.findViewById(R.id.display_current_date);
            addEventButton = (Button)view.findViewById(R.id.add_calendar_event);
            calendarGridView = (GridView)view.findViewById(R.id.calendar_grid);
        }
        private void setPreviousButtonClickEvent(){
            previousButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal.add(Calendar.MONTH, -1);
                    setUpCalendarAdapter();
                }
            });
        }
        private void setNextButtonClickEvent(){
            nextButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal.add(Calendar.MONTH, 1);
                    setUpCalendarAdapter();
                }
            });
        }
        private void setGridCellClickEvents(){
            calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(context, "Clicked " + position, Toast.LENGTH_LONG).show();
//                    hijriDateDtls.setVisibility(View.VISIBLE);
//                    hijriDate.setVisibility(View.GONE);
                    final int size = calendarGridView.getChildCount();
                    for(int i = 0; i < size; i++) {
                        ViewGroup gridChild = (ViewGroup) calendarGridView.getChildAt(i);
                        int childSize = gridChild.getChildCount();
                        if (i!=position) {
                            ((LinearLayout) gridChild.getChildAt(1)).getChildAt(0).setVisibility(View.VISIBLE);
                            ((LinearLayout) gridChild.getChildAt(1)).getChildAt(1).setVisibility(View.GONE);
                        } else {
                            ((LinearLayout) gridChild.getChildAt(1)).getChildAt(1).setVisibility(View.VISIBLE);
                            ((LinearLayout) gridChild.getChildAt(1)).getChildAt(0).setVisibility(View.GONE);
                            ((CalendarFragment)((MainActivity)context).getSupportFragmentManager().findFragmentByTag("com.perfect.islamyat.CalendarFragment")).updateDate(((TextView)((LinearLayout) gridChild.getChildAt(1)).getChildAt(2)).getText());

                        }
//                        for (int k = 0; k < childSize; k++) {
//                            if (gridChild.getChildAt(k) instanceof TextView) {
//                                gridChild.getChildAt(k).setVisibility(View.GONE);
//                            }

//                        }
                    }
                }
            });
        }
        private void setUpCalendarAdapter(){
            List<Date> dayValueInCells = new ArrayList<Date>();
//            mQuery = new DatabaseQuery(context);
//            List<EventObjects> mEvents = mQuery.getAllFutureEvents();
            Calendar mCal = (Calendar)cal.clone();
            mCal.set(Calendar.DAY_OF_MONTH, 1);
            int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
            mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
            while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
                dayValueInCells.add(mCal.getTime());
                mCal.add(Calendar.DAY_OF_MONTH, 1);
            }
            Log.d(TAG, "Number of date " + dayValueInCells.size());
            String sDate = formatter.format(cal.getTime());
            currentDate.setText(sDate);
            mAdapter = new GridAdapter(context, dayValueInCells, cal);
            calendarGridView.setAdapter(mAdapter);
        }

        public void setDate(Date newDate) {
            cal.setTime(newDate);
            setUpCalendarAdapter();
        }
    }
