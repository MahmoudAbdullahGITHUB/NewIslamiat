package com.perfect.islamyat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PrayerSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrayerSettingsFragment : Fragment() {
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
        val root : View = inflater.inflate(R.layout.fragment_prayer_settings, container, false)
        val time_12 : RadioButton = root.findViewById(R.id.time_12)
        val time_24 : RadioButton = root.findViewById(R.id.time_24)
        val prayer_sound_btn : Button = root.findViewById(R.id.prayer_sound_btn)
        val prayer_iqama_btn : Button = root.findViewById(R.id.prayer_iqama_btn)
        val prayer_calc_btn : Button = root.findViewById(R.id.prayer_calc_btn)
        time_24.isChecked = (activity as MainActivity).prayerTimes24()!!
        time_12.isChecked = !time_24.isChecked
        time_12.setOnClickListener {
            (activity as MainActivity).savePrayerTimes24(!time_12.isChecked)
        }
        time_24.setOnClickListener {
            (activity as MainActivity).savePrayerTimes24(time_24.isChecked)
        }
        prayer_sound_btn.setOnClickListener {
            (activity as MainActivity).replaceFragments(PrayerTimesSoundsFragment::class.java)
        }
        prayer_iqama_btn.setOnClickListener {
            (activity as MainActivity).replaceFragments(PrayerIqamaFragment::class.java)
        }
        prayer_calc_btn.setOnClickListener {
            (activity as MainActivity).replaceFragments(PrayerCalcMethodFragment::class.java)
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
         * @return A new instance of fragment PrayerSettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrayerSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}