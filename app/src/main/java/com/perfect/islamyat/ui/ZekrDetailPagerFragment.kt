package com.perfect.islamyat.ui

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.fragment_zekr_detail_pager.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ZekrDetailPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ZekrDetailPagerFragment : Fragment() {
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

//    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root : View = inflater.inflate(R.layout.fragment_zekr_detail_pager, container, false)
        val zekr_detail_header : TextView = root.findViewById(R.id.zekr_detail_header)
        val zekr_detail_body : TextView = root.findViewById(R.id.zekr_detail_body)
        val zekr_detail_footer : TextView = root.findViewById(R.id.zekr_detail_footer)
        val circularProgressBar = root.findViewById<CircularProgressBar>(R.id.circularProgressBar)
        val txt_counter : TextView = root.findViewById(R.id.txt_counter)
        val zekr_detail_counter : TextView = root.findViewById(R.id.zekr_detail_counter)
        val view_counter : View = root.findViewById(R.id.view_counter)
        val progress_rl : RelativeLayout = root.findViewById(R.id.progress_rl)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = requireArguments().getInt(ARG_POSITION)
        txt_counter.text = "0"
        (activity as MainActivity).azkar?.moveToFirst()
        (activity as MainActivity).azkar?.move(position)
        print("twow "+(activity as MainActivity).azkar?.getColumnIndex("Header"))
        zekr_detail_header.text = (activity as MainActivity).azkar?.getString(3)
        zekr_detail_body.text = (activity as MainActivity).azkar?.getString(4)
        zekr_detail_footer.text = (activity as MainActivity).azkar?.getString(5)
        if ((activity as MainActivity).azkar?.getInt(6)!!.toFloat()>1) {
            progress_rl.visibility = View.VISIBLE
            view_counter.visibility = View.VISIBLE
            zekr_detail_counter.visibility = View.VISIBLE
            when ((activity as MainActivity).azkar?.getInt(6)!!){
                3 -> zekr_detail_counter.text = "ثلاث مرات"
                4 -> zekr_detail_counter.text = "أربعة مرات"
                100 -> zekr_detail_counter.text = "مئة مرة"
            }
        }
        circularProgressBar.apply {
            // Set Progress
            progress = 0f
            // or with animation
            setProgressWithAnimation(0f, 1000) // =1s

            // Set Progress Max
            progressMax = (activity as MainActivity).azkar?.getInt(6)!!.toFloat()

            // Set ProgressBar Color
            progressBarColor = Color.BLUE
            // or with gradient
//            progressBarColorStart = Color.WHITE
//            progressBarColorEnd = Color.BLUE
            progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            // Set background ProgressBar Color
            backgroundProgressBarColor = Color.GRAY

            // or with gradient
//            backgroundProgressBarColorStart = Color.WHITE
//            backgroundProgressBarColorEnd = Color.RED
            backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

//            // Set Width
            progressBarWidth = 7f // in DP
//            backgroundProgressBarWidth = 3f // in DP

            // Other
            roundBorder = true
            startAngle = 0f
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }

        view.setOnClickListener {
            if (circularProgressBar.progressMax>circularProgressBar.progress) {
                circularProgressBar.setProgressWithAnimation(circularProgressBar.progress + 1, 100)
                txt_counter.text = (txt_counter.text.toString().toInt() + 1).toString()
                vibratePhone()
            }
        }
    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
//            text_sebha.transitionAlpha=0.2f
//            text_sebha.transitionAlpha=1f
        } else {
            vibrator.vibrate(100)
//            text_sebha.transitionAlpha=0.2f
//            text_sebha.transitionAlpha=1f
        }
    }

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"
        fun getInstance(position: Int) = ZekrDetailPagerFragment().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }
    }
}