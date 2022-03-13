package com.perfect.islamyat.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import kotlinx.android.synthetic.main.fragment_prayer_calc_method.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PrayerCalcMethodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrayerCalcMethodFragment : Fragment() {
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
        val root : View = inflater.inflate(R.layout.fragment_prayer_calc_method, container, false)
        val prayer_calc_1_switch : Switch = root.findViewById(R.id.prayer_calc_1_switch)
        val prayer_calc_2_switch : Switch = root.findViewById(R.id.prayer_calc_2_switch)
        val prayer_calc_3_switch : Switch = root.findViewById(R.id.prayer_calc_3_switch)
        val prayer_calc_4_switch : Switch = root.findViewById(R.id.prayer_calc_4_switch)
        val prayer_calc_5_switch : Switch = root.findViewById(R.id.prayer_calc_5_switch)
        prayer_calc_1_switch.isChecked = ((activity as MainActivity).prayerTimesCalcMethod()==4)
        prayer_calc_2_switch.isChecked = ((activity as MainActivity).prayerTimesCalcMethod()==5)
        prayer_calc_3_switch.isChecked = ((activity as MainActivity).prayerTimesCalcMethod()==3)
        prayer_calc_4_switch.isChecked = ((activity as MainActivity).prayerTimesCalcMethod()==1)
        prayer_calc_5_switch.isChecked = ((activity as MainActivity).prayerTimesCalcMethod()==2)
        (activity as MainActivity).inHomeScreen = true
        prayer_calc_1_switch.setOnClickListener {
            Log.d("TAGCalc", "onCreateView: 4")
            if (prayer_calc_1_switch.isChecked) setPrayerCalcMethod(4)
            prayer_calc_1_switch.isChecked = true
        }
        prayer_calc_2_switch.setOnClickListener {
            Log.d("TAGCalc", "onCreateView: 5")

            if (prayer_calc_2_switch.isChecked) setPrayerCalcMethod(5)
            prayer_calc_2_switch.isChecked = true
        }
        prayer_calc_3_switch.setOnClickListener {
            Log.d("TAGCalc", "onCreateView: 3")

            if (prayer_calc_3_switch.isChecked) setPrayerCalcMethod(3)
            prayer_calc_3_switch.isChecked = true
        }
        prayer_calc_4_switch.setOnClickListener {
            Log.d("TAGCalc", "onCreateView: 1")
            if (prayer_calc_4_switch.isChecked) setPrayerCalcMethod(1)
            prayer_calc_4_switch.isChecked = true
        }
        prayer_calc_5_switch.setOnClickListener {
            Log.d("TAGCalc", "onCreateView: 2")

            if (prayer_calc_5_switch.isChecked) setPrayerCalcMethod(2)
            prayer_calc_5_switch.isChecked = true
        }
        return root
    }

    fun setPrayerCalcMethod(methodID : Int) {
        (activity as MainActivity).setPrayerTimesCalcMethod(methodID)
        prayer_calc_1_switch.isChecked = false
        prayer_calc_2_switch.isChecked = false
        prayer_calc_3_switch.isChecked = false
        prayer_calc_4_switch.isChecked = false
        prayer_calc_5_switch.isChecked = false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PrayerCalcMethodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrayerCalcMethodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}