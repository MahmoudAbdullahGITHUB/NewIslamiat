package com.perfect.islamyat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PrayerIqamaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrayerIqamaFragment : Fragment() {
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
        val root : View = inflater.inflate(R.layout.fragment_prayer_iqama, container, false)
        val fajr_iqama : Spinner = root.findViewById(R.id.fajr_iqama)
        val dhuhr_iqama : Spinner = root.findViewById(R.id.dhuhr_iqama)
        val asr_iqama : Spinner = root.findViewById(R.id.asr_iqama)
        val maghreb_iqama : Spinner = root.findViewById(R.id.maghreb_iqama)
        val isha_iqama : Spinner = root.findViewById(R.id.isha_iqama)
        val iqama_alarm_switch : Switch = root.findViewById(R.id.iqama_alarm_switch)
        iqama_alarm_switch.isChecked = (activity as MainActivity).isPrayerTimesIqamaOn()!!
        iqama_alarm_switch.setOnClickListener {
            (activity as MainActivity).setPrayerTimesIqamaOn(iqama_alarm_switch.isChecked)
        }
        fajr_iqama.setSelection((activity as MainActivity).prayerTimesIqamaFajr()!!)
        dhuhr_iqama.setSelection((activity as MainActivity).prayerTimesIqamaDhuhr()!!)
        asr_iqama.setSelection((activity as MainActivity).prayerTimesIqamaAsr()!!)
        maghreb_iqama.setSelection((activity as MainActivity).prayerTimesIqamaMaghreb()!!)
        isha_iqama.setSelection((activity as MainActivity).prayerTimesIqamaIsha()!!)
        (activity as MainActivity).inHomeScreen = true
        fajr_iqama.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                (activity as MainActivity).setPrayerTimesIqamaFajr(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        dhuhr_iqama.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                (activity as MainActivity).setPrayerTimesIqamaDhuhr(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        asr_iqama.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                (activity as MainActivity).setPrayerTimesIqamaAsr(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        maghreb_iqama.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                (activity as MainActivity).setPrayerTimesIqamaMaghreb(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        isha_iqama.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                (activity as MainActivity).setPrayerTimesIqamaIsha(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PrayerIqamaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrayerIqamaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}