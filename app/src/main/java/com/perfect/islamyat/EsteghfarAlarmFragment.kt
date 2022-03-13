package com.perfect.islamyat

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.perfect.islamyat.ui.EsteghfarSoundsFragment
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FajrAlarmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EsteghfarAlarmFragment : Fragment() {
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
        val root : View =  inflater.inflate(R.layout.fragment_esteghfar_alarm, container, false)
        val esteghfar_btn : Button = root.findViewById(R.id.esteghfar_btn)
        val esteghfaralarmrl : RelativeLayout = root.findViewById(R.id.esteghfaralarmrl)
        val esteghfaralarmrl2 : RelativeLayout = root.findViewById(R.id.esteghfaralarmrl2)
        val esteghfar_alarm_switch : Switch = root.findViewById(R.id.esteghfar_alarm_switch)
        val starttime_picker : TextView = root.findViewById(R.id.starttime_picker)
        val endtime_picker : TextView = root.findViewById(R.id.endtime_picker)
        val save_esteghfar_alarm : TextView = root.findViewById(R.id.save_esteghfar_alarm)
        val esteghfar_repetition : Spinner = root.findViewById(R.id.esteghfar_repetition)

//        starttime_picker.text = (activity as MainActivity).esteghfarAlarmStartTime()
//        endtime_picker.text = (activity as MainActivity).esteghfarAlarmEndTime()

        starttime_picker.setOnClickListener {
            val mcurrentTime: Calendar = Calendar.getInstance()
            val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute: Int = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(context, OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                var selectedTime = ""
                if (selectedHour.toString().length==1) selectedTime = "0" + selectedHour else selectedTime = selectedHour.toString()
                print("selectedTime = "+selectedTime)
                if (selectedMinute.toString().length==1) selectedTime = selectedTime + ":0" + selectedMinute else selectedTime = selectedTime + ":" + selectedMinute
                print("selectedTime2 = "+selectedTime+"\n")
                starttime_picker.setText(
                        selectedTime
                    )
                }, hour, minute, true
            ) //Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        endtime_picker.setOnClickListener {
            val mcurrentTime: Calendar = Calendar.getInstance()
            val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute: Int = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(context, OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                var selectedTime = ""
                if (selectedHour.toString().length==1) selectedTime = "0" + selectedHour else selectedTime = selectedHour.toString()
                if (selectedMinute.toString().length==1) selectedTime = selectedTime + ":0" + selectedMinute else selectedTime = selectedTime + ":" + selectedMinute
                endtime_picker.setText(
                    selectedTime
                )
            }, hour, minute, true
            ) //Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }


        esteghfar_alarm_switch.isChecked = (activity as MainActivity).esteghfarAlarmOn()!!

        if (esteghfar_alarm_switch.isChecked) {
            esteghfaralarmrl.visibility = View.VISIBLE
            esteghfaralarmrl2.visibility = View.VISIBLE
        }
        starttime_picker.text = (activity as MainActivity).esteghfarAlarmStartTime()
        endtime_picker.text = (activity as MainActivity).esteghfarAlarmEndTime()
        esteghfar_repetition.setSelection((activity as MainActivity).esteghfarAlarmRepetitionPeriod()!!)

        esteghfar_alarm_switch.setOnClickListener {
            if (esteghfar_alarm_switch.isChecked) {
                esteghfaralarmrl.visibility = View.VISIBLE
                esteghfaralarmrl2.visibility = View.VISIBLE
            } else {
                esteghfaralarmrl.visibility = View.GONE
                esteghfaralarmrl2.visibility = View.GONE
            }
        }

        save_esteghfar_alarm.setOnClickListener {
//             Toast.makeText(context,"Entered Toast",Toast.LENGTH_LONG).show()

            Log.d("pickerStartTime ",starttime_picker.text.toString())
            Log.d("pickerStartTime2 ",endtime_picker.text.toString())
            Log.d("pickerStartTime3 ",esteghfar_repetition.selectedItemPosition.toString())

            (activity as MainActivity).saveEsteghfarAlarm(esteghfar_alarm_switch.isChecked,starttime_picker.text.toString(),
                endtime_picker.text.toString(),esteghfar_repetition.selectedItemPosition)
            Log.d("TAGG", "entered saveButton")
            Toast.makeText(context,"Entered Toast",Toast.LENGTH_LONG).show()

            (activity as MainActivity).onBackPressed()


        }



        esteghfar_btn.setOnClickListener {
            (activity as MainActivity).replaceFragments3(EsteghfarSoundsFragment::class.java)
        }

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