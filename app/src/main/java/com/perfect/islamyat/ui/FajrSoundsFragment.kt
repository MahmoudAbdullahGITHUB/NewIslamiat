package com.perfect.islamyat.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import kotlinx.android.synthetic.main.fragment_fajr_sounds.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FajrSoundsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FajrSoundsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var mediaPlayer: MediaPlayer? = null
    var fajr_alarm_1_switch : Switch? = null
    var fajr_alarm_2_switch : Switch? = null
    var fajr_alarm_3_switch : Switch? = null
    var fajr_alarm_4_switch : Switch? = null
    var fajr_alarm_5_switch : Switch? = null
    var fajr_alarm_6_switch : Switch? = null
    var fajr_alarm_7_switch : Switch? = null
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
        val root : View = inflater.inflate(R.layout.fragment_fajr_sounds, container, false)
        mediaPlayer = MediaPlayer()
        fajr_alarm_1_switch = root.findViewById(R.id.fajr_alarm_1_switch)
        fajr_alarm_2_switch = root.findViewById(R.id.fajr_alarm_2_switch)
        fajr_alarm_3_switch = root.findViewById(R.id.fajr_alarm_3_switch)
        fajr_alarm_4_switch = root.findViewById(R.id.fajr_alarm_4_switch)
        fajr_alarm_5_switch = root.findViewById(R.id.fajr_alarm_5_switch)
        fajr_alarm_6_switch = root.findViewById(R.id.fajr_alarm_6_switch)
        fajr_alarm_7_switch = root.findViewById(R.id.fajr_alarm_7_switch)
        val fajr_play_1 : ImageView = root.findViewById(R.id.fajr_play_1)
        val fajr_play_2 : ImageView = root.findViewById(R.id.fajr_play_2)
        val fajr_play_3 : ImageView = root.findViewById(R.id.fajr_play_3)
        val fajr_play_4 : ImageView = root.findViewById(R.id.fajr_play_4)
        val fajr_play_5 : ImageView = root.findViewById(R.id.fajr_play_5)
        val fajr_play_6 : ImageView = root.findViewById(R.id.fajr_play_6)
        val fajr_play_7 : ImageView = root.findViewById(R.id.fajr_play_7)

        fajr_play_1.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context,R.raw.fagr_sound_0,it as ImageView)
        }
        fajr_play_2.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context,R.raw.fagr_sound_1,it as ImageView)
        }
        fajr_play_3.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context,R.raw.fagr_sound_2,it as ImageView)
        }
        fajr_play_4.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context,R.raw.fagr_sound_3,it as ImageView)
        }
        fajr_play_5.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context,R.raw.fagr_sound_4,it as ImageView)
        }
        fajr_play_6.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context,R.raw.fagr_sound_5,it as ImageView)
        }
        fajr_play_7.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context,R.raw.fagr_sound_6,it as ImageView)
        }


        fajr_alarm_1_switch?.isChecked = ((activity as MainActivity).prayerTimesSpecialAlarmFajrSound() == 1)
        fajr_alarm_2_switch?.isChecked = ((activity as MainActivity).prayerTimesSpecialAlarmFajrSound() == 2)
        fajr_alarm_3_switch?.isChecked = ((activity as MainActivity).prayerTimesSpecialAlarmFajrSound() == 3)
        fajr_alarm_4_switch?.isChecked = ((activity as MainActivity).prayerTimesSpecialAlarmFajrSound() == 4)
        fajr_alarm_5_switch?.isChecked = ((activity as MainActivity).prayerTimesSpecialAlarmFajrSound() == 5)
        fajr_alarm_6_switch?.isChecked = ((activity as MainActivity).prayerTimesSpecialAlarmFajrSound() == 6)
        fajr_alarm_7_switch?.isChecked = ((activity as MainActivity).prayerTimesSpecialAlarmFajrSound() == 7)

        fajr_alarm_1_switch?.setOnClickListener {
            adjustSwitches(1)
        }
        fajr_alarm_2_switch?.setOnClickListener {
            adjustSwitches(2)
        }
        fajr_alarm_3_switch?.setOnClickListener {
            adjustSwitches(3)
        }
        fajr_alarm_4_switch?.setOnClickListener {
            adjustSwitches(4)
        }
        fajr_alarm_5_switch?.setOnClickListener {
            adjustSwitches(5)
        }
        fajr_alarm_6_switch?.setOnClickListener {
            adjustSwitches(6)
        }
        fajr_alarm_7_switch?.setOnClickListener {
            adjustSwitches(7)
        }
        return root

    }

    fun playSound(context: Context?, resId: Int, img: ImageView) {
        if(mediaPlayer?.isPlaying()!!)
        {
            mediaPlayer?.stop();
            mediaPlayer?.reset();
        }
        mediaPlayer = MediaPlayer.create(context, resId)
        this.mediaPlayer?.setOnCompletionListener {
//            mp -> mp.release()
            img.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        }
        this.mediaPlayer?.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        if(mediaPlayer?.isPlaying()!!)
        {
            mediaPlayer?.stop();
            mediaPlayer?.reset();
        }
    }


    fun adjustSwitches(SwitchID : Int) {
        if (SwitchID != 1)
            fajr_alarm_1_switch?.isChecked = false
        if (SwitchID != 2)
            fajr_alarm_2_switch?.isChecked = false
        if (SwitchID != 3)
            fajr_alarm_3_switch?.isChecked = false
        if (SwitchID != 4)
            fajr_alarm_4_switch?.isChecked = false
        if (SwitchID != 5)
            fajr_alarm_5_switch?.isChecked = false
        if (SwitchID != 6)
            fajr_alarm_6_switch?.isChecked = false
        if (SwitchID != 7)
            fajr_alarm_7_switch?.isChecked = false
        (activity as MainActivity).saveFajrSpecialAlarmSoundSettings(SwitchID)
    }

    fun adjustDrawables() {
//        if (ImageViewID != 1)
            fajr_play_1.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 2)
            fajr_play_2.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 3)
            fajr_play_3.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 4)
            fajr_play_4.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 5)
            fajr_play_5.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 6)
            fajr_play_6.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 7)
            fajr_play_7.setImageDrawable(activity!!.getDrawable(R.drawable.play))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FajrSoundsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FajrSoundsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}