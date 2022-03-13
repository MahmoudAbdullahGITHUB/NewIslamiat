package com.perfect.islamyat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.perfect.islamyat.ui.FajrSoundsFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FajrAlarmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FajrAlarmFragment : Fragment() {
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
        val root : View =  inflater.inflate(R.layout.fragment_fajr_alarm, container, false)
        val selected_interval : Spinner = root.findViewById(R.id.selected_interval)
        val list = arrayOf("5 دقائق","10 دقائق","15 دقائق","20 دقائق","25 دقائق","30 دقائق","35 دقائق","40 دقائق","45 دقائق","50 دقائق")
        val remindersound : TextView = root.findViewById(R.id.remindersound)
        val remindertext : TextView  = root.findViewById(R.id.remindertext)
        val fajr_alarm_switch: Switch = root.findViewById(R.id.fajr_alarm_switch)
        val fajr_before_after_alarm_switch: Switch = root.findViewById(R.id.fajr_before_after_alarm_switch)
        val save_fajr_alarm : TextView = root.findViewById(R.id.save_fajr_alarm)
        fajr_alarm_switch.isChecked = (activity as MainActivity).prayerTimesSpecialAlarmFajrOn()!!
        fajr_before_after_alarm_switch.isChecked = (activity as MainActivity).prayerTimesSpecialAlarmBefore()!!

        fajr_before_after_alarm_switch.setOnClickListener {
            if (fajr_before_after_alarm_switch.isChecked) remindertext.text = "التذكير قبل الأذان"
            else remindertext.text = "التذكير بعد الأذان"
        }
        fajr_before_after_alarm_switch.callOnClick()
        save_fajr_alarm.setOnClickListener {
            Log.d("TAGselectedInterval", "onCreateView: "+selected_interval.selectedItemPosition)
            Log.d("TAGBeforeOrAfter", "onCreateView: "+fajr_before_after_alarm_switch.isChecked)
            (activity as MainActivity).saveFajrSpecialAlarmSettings(fajr_alarm_switch.isChecked,
                fajr_before_after_alarm_switch.isChecked,selected_interval.selectedItemPosition)
//            (activity as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit();
            (activity as MainActivity).onBackPressed()
        }
//        val arrayAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this, , list,R.layout.spinner_item)
//        arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
//        selected_interval.adapter = ArrayAdapter<String>(this, R.layout.spinner_item,list)
        selected_interval.adapter = activity?.let { ArrayAdapter<String>(it,R.layout.spinner_item,list) }
        remindersound.setOnClickListener {
            (activity as MainActivity).replaceFragments(FajrSoundsFragment::class.java)
        }
        selected_interval.setSelection((activity as MainActivity).prayerTimesSpecialAlarmOffset()!!)
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FajrAlarmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FajrAlarmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}