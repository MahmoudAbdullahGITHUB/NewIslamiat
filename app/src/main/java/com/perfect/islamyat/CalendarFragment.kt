package com.perfect.islamyat

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.perfect.islamyat.ui.CalendarCustomView
import com.perfect.islamyat.utils.monthToArabic
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

//import com.imanoweb.calendarview.CustomCalendarView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_calendar, container, false)
        val cv : CalendarCustomView = root.findViewById(R.id.calendarView)
        val current_date_tv : TextView = root.findViewById(R.id.current_date_tv)
        val date_to_go : TextView = root.findViewById(R.id.date_to_go)
        val date_to_go_btn : Button = root.findViewById(R.id.date_to_go_btn)
        date_to_go.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = activity?.let { it1 ->
                DatePickerDialog(
                    it1,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        date_to_go.setText("" + dayOfMonth + " / " + (monthOfYear+1) + " / " + year)
                    },
                    year,
                    month,
                    day
                )
            }
            dpd?.show()
        }
//        cv.display_current_date.text = "asdas"
        val cal : Calendar = Calendar.getInstance()
        cv.setDate(cal.time)
//        cv.setOnClickListener {
//            current_date_tv.text = cv.currentDateGregorian
//        }
        val uCal: Calendar = UmmalquraCalendar()
        uCal.time = cal.time
        val strGregorian : String = cal.get(Calendar.DAY_OF_MONTH).toString() + " " + cal.getDisplayName(Calendar.MONTH,Calendar.LONG,
            Locale.ENGLISH).monthToArabic()  + " " + cal.get(Calendar.YEAR) + " م"
        val strHijri : String = uCal.get(Calendar.DAY_OF_MONTH).toString() + " " + uCal.getDisplayName(Calendar.MONTH,Calendar.LONG,
            Locale("ar")) + " " + uCal.get(Calendar.YEAR) + " هـ" + "<br />"
        current_date_tv.text = Html.fromHtml(strHijri + strGregorian)
        val current_month : Int = cal.get(Calendar.MONTH)+1
        date_to_go.text = cal.get(Calendar.DAY_OF_MONTH).toString() + " / " + current_month  + " / " + cal.get(Calendar.YEAR)
        date_to_go_btn.setOnClickListener {
            val dtStart = date_to_go.text.toString()
            val format = SimpleDateFormat("dd / MM / yyyy")
            try {
                val date: Date = format.parse(dtStart)
                cv.setDate(date)
                val cal = Calendar.getInstance()
                cal.time = date
                val uCal: Calendar = UmmalquraCalendar()
                uCal.time = date
                val strGregorian : String = cal.get(Calendar.DAY_OF_MONTH).toString() + " " + cal.getDisplayName(Calendar.MONTH,Calendar.LONG,
                    Locale.ENGLISH).monthToArabic()  + " " + cal.get(Calendar.YEAR) + " م"
                val strHijri : String = uCal.get(Calendar.DAY_OF_MONTH).toString() + " " + uCal.getDisplayName(Calendar.MONTH,Calendar.LONG,
                    Locale("ar")) + " " + uCal.get(Calendar.YEAR) + " هـ" + "<br />"
                current_date_tv.text = Html.fromHtml(strHijri + strGregorian)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return root
    }

    public fun updateDate(text: CharSequence) {
        current_date_tv.text = text
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}