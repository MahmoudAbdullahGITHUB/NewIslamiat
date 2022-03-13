package com.perfect.islamyat.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import kotlinx.android.synthetic.main.fragment_esteghfar_sounds.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EsteghfarSoundsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EsteghfarSoundsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var mediaPlayer: MediaPlayer? = null
//    private var mp : MediaPlayer? = null

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
        print("TAGGu0")
        // Inflate the layout for this fragment
//        mp = MediaPlayer()
        val root: View = inflater.inflate(R.layout.fragment_esteghfar_sounds, container, false)
        mediaPlayer = MediaPlayer()
        val est_alarm_1_switch: Switch = root.findViewById(R.id.est_alarm_1_switch)
        val est_alarm_2_switch: Switch = root.findViewById(R.id.est_alarm_2_switch)
        val est_alarm_3_switch: Switch = root.findViewById(R.id.est_alarm_3_switch)
        val est_alarm_4_switch: Switch = root.findViewById(R.id.est_alarm_4_switch)
        val est_alarm_5_switch: Switch = root.findViewById(R.id.est_alarm_5_switch)
        val est_alarm_6_switch: Switch = root.findViewById(R.id.est_alarm_6_switch)
        val est_alarm_7_switch: Switch = root.findViewById(R.id.est_alarm_7_switch)
        val est_alarm_8_switch: Switch = root.findViewById(R.id.est_alarm_8_switch)
        val est_alarm_9_switch: Switch = root.findViewById(R.id.est_alarm_9_switch)

        var esteghfarAlarmSounds = (activity as MainActivity).esteghfarAlarmSounds()
        Log.d("TAGesteghfarAlarmSound", "onCreateView: " + esteghfarAlarmSounds)
        est_alarm_1_switch.isChecked = (esteghfarAlarmSounds!![0].equals('1'))
        Log.d("bool 1", "onCreateView: " + esteghfarAlarmSounds!![0].equals('1'))
        est_alarm_2_switch.isChecked = (esteghfarAlarmSounds[1].equals('1'))
        Log.d("bool 2", "onCreateView: " + esteghfarAlarmSounds!![1].equals('1'))
        est_alarm_3_switch.isChecked = (esteghfarAlarmSounds[2].equals('1'))
        Log.d("bool 3", "onCreateView: " + esteghfarAlarmSounds!![2].equals('1'))
        est_alarm_4_switch.isChecked = (esteghfarAlarmSounds[3].equals('1'))
        Log.d("bool 4", "onCreateView: " + esteghfarAlarmSounds!![3].equals('1'))
        est_alarm_5_switch.isChecked = (esteghfarAlarmSounds[4].equals('1'))
        Log.d("bool 5", "onCreateView: " + est_alarm_5_switch.isChecked)
        est_alarm_6_switch.isChecked = (esteghfarAlarmSounds[5].equals('1'))
        Log.d("bool 6", "onCreateView: " + est_alarm_5_switch.isChecked)
        est_alarm_7_switch.isChecked = (esteghfarAlarmSounds[6].equals('1'))
        Log.d("bool 7", "onCreateView: " + est_alarm_6_switch.isChecked)
        est_alarm_8_switch.isChecked = (esteghfarAlarmSounds[7].equals('1'))
        Log.d("bool 8", "onCreateView: " + est_alarm_7_switch.isChecked)
        est_alarm_9_switch.isChecked = (esteghfarAlarmSounds[8].equals('1'))
        Log.d("bool 9", "onCreateView: " + est_alarm_8_switch.isChecked)

        Log.d("TAGesteghfarAlarmSound2", "onCreateView: " + esteghfarAlarmSounds)


        /***/
//        (activity as MainActivity).saveEsteghfarAlarmSounds("000010000")

        /**
         *
         */
//        saveSequence()

        esteghfarAlarmSounds = (activity as MainActivity).esteghfarAlarmSounds()
        Log.d("TAGesteghfarAlarmSound3", "onCreateView: " + esteghfarAlarmSounds)

//        saveSequence()


        est_alarm_1_switch.setOnClickListener {
            println("Entttttttt");
            saveSequence()


            esteghfarAlarmSounds = (activity as MainActivity).esteghfarAlarmSounds()


//            Log.d("TAGESAS", "onCreateView: "+esteghfarAlarmSounds)
//            if ( esteghfarAlarmSounds.equals("100000000") || esteghfarAlarmSounds.equals("010000000")
//                || esteghfarAlarmSounds.equals("001000000") || esteghfarAlarmSounds.equals("000100000")
//                || esteghfarAlarmSounds.equals("000010000")
//                || esteghfarAlarmSounds.equals("000001000") || esteghfarAlarmSounds.equals("000000100")
//                || esteghfarAlarmSounds.equals("000000010")
//                || esteghfarAlarmSounds.equals("000000001")
//            ){
//                Log.d("TAGESASEntered", "onCreateView: "+esteghfarAlarmSounds)
//
//                est_alarm_1_switch.isChecked = true
//            }

        }
        est_alarm_2_switch.setOnClickListener {
            saveSequence()
//            esteghfarAlarmSounds = (activity as MainActivity).esteghfarAlarmSounds()
//            Log.d("TAGESAS3", "onCreateView: " + esteghfarAlarmSounds)
//            if (esteghfarAlarmSounds.equals("100000000")) {
//                est_alarm_1_switch.isChecked = true
//            }

        }
        est_alarm_3_switch.setOnClickListener {
            saveSequence()
        }
        est_alarm_4_switch.setOnClickListener {
            saveSequence()
        }
        est_alarm_5_switch.setOnClickListener {
            saveSequence()
        }
        est_alarm_6_switch.setOnClickListener {
            saveSequence()
        }
        est_alarm_7_switch.setOnClickListener {
            saveSequence()
        }
        est_alarm_8_switch.setOnClickListener {
            saveSequence()
        }
        est_alarm_9_switch.setOnClickListener {
            saveSequence()
        }

        Log.d("TAGesteghfarAlarmSound4", "onCreateView: " + esteghfarAlarmSounds)

        val est_play_1: ImageView = root.findViewById(R.id.est_play_1)
        val est_play_2: ImageView = root.findViewById(R.id.est_play_2)
        val est_play_3: ImageView = root.findViewById(R.id.est_play_3)
        val est_play_4: ImageView = root.findViewById(R.id.est_play_4)
        val est_play_5: ImageView = root.findViewById(R.id.est_play_5)
        val est_play_6: ImageView = root.findViewById(R.id.est_play_6)
        val est_play_7: ImageView = root.findViewById(R.id.est_play_7)
        val est_play_8: ImageView = root.findViewById(R.id.est_play_8)
        val est_play_9: ImageView = root.findViewById(R.id.est_play_9)
        est_play_1.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.estghfar_0, it as ImageView)
        }
        est_play_2.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.estghfar_1, it as ImageView)
        }
        est_play_3.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.estghfar_3, it as ImageView)
        }
        est_play_4.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.estghfar_9, it as ImageView)
        }
        est_play_5.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.estghfar_5, it as ImageView)
        }
        est_play_6.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.estghfar_6, it as ImageView)
        }
        est_play_7.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.estghfar_7, it as ImageView)
        }
        est_play_8.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.estghfar_8, it as ImageView)
        }
        est_play_9.setOnClickListener {
            adjustDrawables()
            (it as ImageView).setImageDrawable(activity!!.getDrawable(R.drawable.pause))
            playSound(context, R.raw.estghfar_9, it as ImageView)
        }

        return root

    }

//    fun playSound(context: Context?, resId: Int, img: ImageView) {
//        val sound = MediaPlayer.create(context, resId)
//        sound.setOnCompletionListener {
//                mp -> mp.release()
//                img.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        }
//        sound.start()
//    }

    fun saveSequence() {
        println("TAGGu")
        var esteghfarAlarmSounds = (activity as MainActivity).esteghfarAlarmSounds()

        var sequence = "000000000".toCharArray()

        if (est_alarm_1_switch?.isChecked == true || esteghfarAlarmSounds.equals("100000000") || esteghfarAlarmSounds.equals(
                "010000000"
            )
            || esteghfarAlarmSounds.equals("001000000") || esteghfarAlarmSounds.equals("000100000")
            || esteghfarAlarmSounds.equals("000010000")
            || esteghfarAlarmSounds.equals("000001000") || esteghfarAlarmSounds.equals("000000100")
            || esteghfarAlarmSounds.equals("000000010")
            || esteghfarAlarmSounds.equals("000000001")
        ) {
            Log.d("TAGYess1", "saveSequence: ")
            sequence[0] = '1'
            est_alarm_1_switch.isEnabled = true

        }

        if (est_alarm_2_switch?.isChecked == true) {
            Log.d("TAGYess2", "saveSequence: ")
            sequence[1] = '1'

        }
        if (est_alarm_3_switch?.isChecked == true) sequence[2] = '1'
        if (est_alarm_4_switch?.isChecked == true) sequence[3] = '1'
        if (est_alarm_5_switch?.isChecked == true) sequence[4] = '1'
        if (est_alarm_6_switch?.isChecked == true) sequence[5] = '1'
        if (est_alarm_7_switch?.isChecked == true) sequence[6] = '1'
        if (est_alarm_8_switch?.isChecked == true) sequence[7] = '1'
        if (est_alarm_9_switch?.isChecked == true) sequence[8] = '1'


/*
//        if (est_alarm_1_switch!!.isChecked) sequence[0]='1'
//        if (est_alarm_2_switch!!.isChecked) sequence[1]='1'
//        if (est_alarm_3_switch!!.isChecked) sequence[2]='1'
//        if (est_alarm_4_switch!!.isChecked) sequence[3]='1'
//        if (est_alarm_5_switch!!.isChecked) sequence[4]='1'
//        if (est_alarm_6_switch!!.isChecked) sequence[5]='1'
//        if (est_alarm_7_switch!!.isChecked) sequence[6]='1'
//        if (est_alarm_8_switch!!.isChecked) sequence[7]='1'
//        if (est_alarm_9_switch!!.isChecked) sequence[8]='1'
*/

        var updatedSequence = String(sequence)

        if (updatedSequence.equals("000000000")) {
            est_alarm_1_switch.setOnClickListener(View.OnClickListener {
                est_alarm_1_switch?.isChecked = true
            })
            est_alarm_1_switch?.isChecked == true
            updatedSequence = "100000000"
        }

        Log.d("TAGGu", updatedSequence)


        (activity as MainActivity).saveEsteghfarAlarmSounds(updatedSequence)


        Log.d("TAGESASSave", "onCreateView: " + esteghfarAlarmSounds)
        if (esteghfarAlarmSounds.equals("100000000") || esteghfarAlarmSounds.equals(
                "010000000"
            )
            || esteghfarAlarmSounds.equals("001000000") || esteghfarAlarmSounds.equals("000100000")
            || esteghfarAlarmSounds.equals("000010000")
            || esteghfarAlarmSounds.equals("000001000") || esteghfarAlarmSounds.equals("000000100")
            || esteghfarAlarmSounds.equals("000000010")
            || esteghfarAlarmSounds.equals("000000001")) {
            est_alarm_1_switch.isChecked = true
            est_alarm_1_switch.isEnabled = false
        } else {
            est_alarm_1_switch.isEnabled = true
        }
    }


    fun playSound(context: Context?, resId: Int, img: ImageView) {
        if (mediaPlayer?.isPlaying()!!) {
            mediaPlayer?.stop();
            mediaPlayer?.reset();
        }
        mediaPlayer = MediaPlayer.create(context, resId)
        this.mediaPlayer?.setOnCompletionListener {
//                mp -> mp.release()
            img.setImageDrawable(activity!!.getDrawable(R.drawable.play))
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
         * @return A new instance of fragment EsteghfarSoundsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EsteghfarSoundsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun adjustDrawables() {
//        if (ImageViewID != 1)
        est_play_1.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 2)
        est_play_2.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 3)
        est_play_3.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 4)
        est_play_4.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 5)
        est_play_5.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 6)
        est_play_6.setImageDrawable(activity!!.getDrawable(R.drawable.play))
//        if (ImageViewID != 7)
        est_play_7.setImageDrawable(activity!!.getDrawable(R.drawable.play))

        est_play_8.setImageDrawable(activity!!.getDrawable(R.drawable.play))

        est_play_9.setImageDrawable(activity!!.getDrawable(R.drawable.play))
    }
}