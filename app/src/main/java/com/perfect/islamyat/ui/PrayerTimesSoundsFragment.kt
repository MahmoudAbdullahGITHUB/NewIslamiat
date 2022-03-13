package com.perfect.islamyat.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import kotlinx.android.synthetic.main.fragment_prayer_times_sounds.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PrayerTimesSoundsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrayerTimesSoundsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var mediaPlayer: MediaPlayer? = null
    lateinit var prayer_alarm_1_switch: Switch
    lateinit var prayer_alarm_2_switch: Switch
    lateinit var prayer_alarm_3_switch: Switch
    lateinit var prayer_alarm_4_switch: Switch
    lateinit var prayer_alarm_5_switch: Switch
    lateinit var prayer_alarm_6_switch: Switch
    lateinit var prayer_alarm_7_switch: Switch
    lateinit var prayer_alarm_8_switch: Switch
    lateinit var prayer_alarm_9_switch: Switch
    lateinit var prayer_alarm_10_switch: Switch
    lateinit var prayer_alarm_11_switch: Switch
    lateinit var prayer_alarm_12_switch: Switch
    lateinit var prayer_alarm_13_switch: Switch
    lateinit var prayer_alarm_14_switch: Switch
    lateinit var prayer_alarm_15_switch: Switch
    lateinit var prayer_alarm_16_switch: Switch

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
        val root: View = inflater.inflate(R.layout.fragment_prayer_times_sounds, container, false)
        mediaPlayer = MediaPlayer()

        //references for switch
        this.prayer_alarm_1_switch = root.findViewById(R.id.prayer_alarm_1_switch)
        this.prayer_alarm_2_switch = root.findViewById(R.id.prayer_alarm_2_switch)
        this.prayer_alarm_3_switch = root.findViewById(R.id.prayer_alarm_3_switch)
        this.prayer_alarm_4_switch = root.findViewById(R.id.prayer_alarm_4_switch)
        this.prayer_alarm_5_switch = root.findViewById(R.id.prayer_alarm_5_switch)
        this.prayer_alarm_6_switch = root.findViewById(R.id.prayer_alarm_6_switch)
        this.prayer_alarm_7_switch = root.findViewById(R.id.prayer_alarm_7_switch)
        this.prayer_alarm_8_switch = root.findViewById(R.id.prayer_alarm_8_switch)
        this.prayer_alarm_9_switch = root.findViewById(R.id.prayer_alarm_9_switch)
        this.prayer_alarm_10_switch = root.findViewById(R.id.prayer_alarm_10_switch)
        this.prayer_alarm_11_switch = root.findViewById(R.id.prayer_alarm_11_switch)
        this.prayer_alarm_12_switch = root.findViewById(R.id.prayer_alarm_12_switch)
        this.prayer_alarm_13_switch = root.findViewById(R.id.prayer_alarm_13_switch)
        this.prayer_alarm_14_switch = root.findViewById(R.id.prayer_alarm_14_switch)
        this.prayer_alarm_15_switch = root.findViewById(R.id.prayer_alarm_15_switch)
        this.prayer_alarm_16_switch = root.findViewById(R.id.prayer_alarm_16_switch)
        /**
         * usage ??
         */
        (activity as MainActivity).inHomeScreen = true

        //references for imageView
        val prayer_play_1: ImageView = root.findViewById(R.id.prayer_play_1)
        val prayer_play_2: ImageView = root.findViewById(R.id.prayer_play_2)
        val prayer_play_3: ImageView = root.findViewById(R.id.prayer_play_3)
        val prayer_play_4: ImageView = root.findViewById(R.id.prayer_play_4)
        val prayer_play_5: ImageView = root.findViewById(R.id.prayer_play_5)
        val prayer_play_6: ImageView = root.findViewById(R.id.prayer_play_6)
        val prayer_play_7: ImageView = root.findViewById(R.id.prayer_play_7)
        val prayer_play_8: ImageView = root.findViewById(R.id.prayer_play_8)
        val prayer_play_9: ImageView = root.findViewById(R.id.prayer_play_9)
        val prayer_play_10: ImageView = root.findViewById(R.id.prayer_play_10)
        val prayer_play_12: ImageView = root.findViewById(R.id.prayer_play_12)
        val prayer_play_13: ImageView = root.findViewById(R.id.prayer_play_13)
        val prayer_play_14: ImageView = root.findViewById(R.id.prayer_play_14)
        val prayer_play_15: ImageView = root.findViewById(R.id.prayer_play_15)
        val prayer_play_11: ImageView = root.findViewById(R.id.prayer_play_11)
        val prayer_play_16: ImageView = root.findViewById(R.id.prayer_play_16)

        prayer_alarm_1_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 1)
        prayer_alarm_2_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 2)
        prayer_alarm_3_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 3)
        prayer_alarm_4_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 4)
        prayer_alarm_5_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 5)
        prayer_alarm_6_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 6)
        prayer_alarm_7_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 7)
        prayer_alarm_8_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 8)
        prayer_alarm_9_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 9)
        prayer_alarm_10_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 10)
        prayer_alarm_11_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 11)
        prayer_alarm_12_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 12)
        prayer_alarm_13_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 13)
        prayer_alarm_14_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 14)
        prayer_alarm_15_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 15)
        prayer_alarm_16_switch.isChecked = ((activity as MainActivity).prayerTimesSound() == 16)

        prayer_alarm_1_switch.setOnClickListener {
            if (prayer_alarm_1_switch.isChecked) setPrayerSound(1)
            prayer_alarm_1_switch.isChecked = true
        }

        prayer_alarm_2_switch.setOnClickListener {
            if (prayer_alarm_2_switch.isChecked) setPrayerSound(2)
            prayer_alarm_2_switch.isChecked = true
        }

        prayer_alarm_3_switch.setOnClickListener {
            if (prayer_alarm_3_switch.isChecked) setPrayerSound(3)
            prayer_alarm_3_switch.isChecked = true
        }

        prayer_alarm_4_switch.setOnClickListener {
            if (prayer_alarm_4_switch.isChecked) setPrayerSound(4)
            prayer_alarm_4_switch.isChecked = true
        }

        prayer_alarm_5_switch.setOnClickListener {
            if (prayer_alarm_5_switch.isChecked) setPrayerSound(5)
            prayer_alarm_5_switch.isChecked = true
        }

        prayer_alarm_6_switch.setOnClickListener {
            if (prayer_alarm_6_switch.isChecked) setPrayerSound(6)
            prayer_alarm_6_switch.isChecked = true
        }

        prayer_alarm_7_switch.setOnClickListener {
            if (prayer_alarm_7_switch.isChecked) setPrayerSound(7)
            prayer_alarm_7_switch.isChecked = true
        }

        prayer_alarm_8_switch.setOnClickListener {
            if (prayer_alarm_8_switch.isChecked) setPrayerSound(8)
            prayer_alarm_8_switch.isChecked = true
        }

        prayer_alarm_9_switch.setOnClickListener {
            if (prayer_alarm_9_switch.isChecked) setPrayerSound(9)
            prayer_alarm_9_switch.isChecked = true
        }

        prayer_alarm_10_switch.setOnClickListener {
            if (prayer_alarm_10_switch.isChecked) setPrayerSound(10)
            prayer_alarm_10_switch.isChecked = true
        }

        prayer_alarm_11_switch.setOnClickListener {
            if (prayer_alarm_11_switch.isChecked) setPrayerSound(11)
            prayer_alarm_11_switch.isChecked = true
        }

        prayer_alarm_12_switch.setOnClickListener {
            if (prayer_alarm_12_switch.isChecked) setPrayerSound(12)
            prayer_alarm_12_switch.isChecked = true
        }

        prayer_alarm_13_switch.setOnClickListener {
            if (prayer_alarm_13_switch.isChecked) setPrayerSound(13)
            prayer_alarm_13_switch.isChecked = true
        }

        prayer_alarm_14_switch.setOnClickListener {
            if (prayer_alarm_14_switch.isChecked) setPrayerSound(14)
            prayer_alarm_14_switch.isChecked = true
        }

        prayer_alarm_15_switch.setOnClickListener {
            Log.d("TAGllllla", "onCreateView: ")
            if (prayer_alarm_15_switch.isChecked) setPrayerSound(15)
            prayer_alarm_15_switch.isChecked = true
        }

        prayer_alarm_16_switch.setOnClickListener {
            Log.d("TAGllllla", "onCreateView: ")
            if (prayer_alarm_16_switch.isChecked) setPrayerSound(16)
            prayer_alarm_16_switch.isChecked = true
        }

        prayer_play_1.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_maky_first, it as ImageView)
        }

        prayer_play_2.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_maky_sec, it as ImageView)
        }

        prayer_play_3.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_madny_first, it as ImageView)
        }

        prayer_play_4.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_qatar, it as ImageView)
        }

        prayer_play_5.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_1, it as ImageView)
        }

        prayer_play_6.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_2, it as ImageView)
        }

        prayer_play_7.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_3, it as ImageView)
        }

        prayer_play_8.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_4, it as ImageView)
        }

        prayer_play_9.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_5, it as ImageView)
        }

        prayer_play_10.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_6, it as ImageView)
        }

        prayer_play_11.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_7, it as ImageView)
        }

        prayer_play_12.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_8, it as ImageView)
        }

        prayer_play_13.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_9, it as ImageView)
        }

        prayer_play_14.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_10, it as ImageView)
        }

        prayer_play_15.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_sound_0, it as ImageView)
        }

        prayer_play_16.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.azan_madny_sec, it as ImageView)
        }

        return root
    }

    fun setPrayerSound(soundID: Int) {
        Log.d("setPrayerSound", "$soundID")
        (activity as MainActivity).savePrayerTimesSound(soundID)
        prayer_alarm_1_switch.isChecked = false
        prayer_alarm_2_switch.isChecked = false
        prayer_alarm_3_switch.isChecked = false
        prayer_alarm_4_switch.isChecked = false
        prayer_alarm_5_switch.isChecked = false
        prayer_alarm_6_switch.isChecked = false
        prayer_alarm_7_switch.isChecked = false
        prayer_alarm_8_switch.isChecked = false
        prayer_alarm_9_switch.isChecked = false
        prayer_alarm_10_switch.isChecked = false
        prayer_alarm_11_switch.isChecked = false
        prayer_alarm_12_switch.isChecked = false
        prayer_alarm_13_switch.isChecked = false
        prayer_alarm_14_switch.isChecked = false
        prayer_alarm_15_switch.isChecked = false
        prayer_alarm_16_switch.isChecked = false
    }

    fun adjustDrawables() {
        prayer_play_1.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_2.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_3.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_4.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_5.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_6.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_7.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_8.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_9.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_10.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_11.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_12.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_13.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_14.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_15.setImageDrawable(activity!!.getDrawable(R.drawable.play))
        prayer_play_16.setImageDrawable(activity!!.getDrawable(R.drawable.play))
    }

    fun playSound(context: Context?, resId: Int, img: ImageView) {
        if (mediaPlayer?.isPlaying()!!) {
            mediaPlayer?.stop();
            mediaPlayer?.reset();
        }
        mediaPlayer = MediaPlayer.create(context, resId)
        this.mediaPlayer?.setOnCompletionListener {
            try {
                img.setImageDrawable(activity!!.getDrawable(R.drawable.play))
            } catch (e: Exception) {

            }
        }
        this.mediaPlayer?.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer?.isPlaying()!!) {
            mediaPlayer?.stop();
            mediaPlayer?.reset();
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PrayerTimesSoundsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrayerTimesSoundsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}