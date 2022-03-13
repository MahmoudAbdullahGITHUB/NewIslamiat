package com.perfect.islamyat.ui.dashboard

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import kotlinx.android.synthetic.main.activity_main.*

class SebhaFragment : Fragment() {

    private lateinit var dashboardViewModel: SebhaViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(SebhaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val sebha1: TextView = root.findViewById(R.id.sebha1)
        val sebha2: TextView = root.findViewById(R.id.sebha2)
        val sebha3: TextView = root.findViewById(R.id.sebha3)
        val sebhaCnt: TextView = root.findViewById(R.id.text_sebha)
        val dailyCnt: TextView = root.findViewById(R.id.dailyCnt)
        val resetBtn: ImageView = root.findViewById(R.id.resetBtn)
        val resetBtn2: LinearLayout = root.findViewById(R.id.resetBtn2)
        val mainSebhaRL : RelativeLayout = root.findViewById(R.id.mainSebhaRL)
        val sebhaVals = ArrayList<String>()
        var startIndex = 0
        sebhaVals.add("استغفر الله")
        sebhaVals.add("سبحان الله")
        sebhaVals.add("الحمد لله")
        sebhaVals.add("الله اكبر")
        sebhaVals.add("لا إله إلا الله")
        (activity as MainActivity).cntVal = 0
        sebhaCnt.text = (activity as MainActivity).cntVal.toString()
        dailyCnt.text = (activity as MainActivity).getDailyCnt().toString()
        sebha1.text =sebhaVals[0]
        sebha2.text =sebhaVals[1]
        sebha3.text =sebhaVals[2]
        (activity as MainActivity).nav_view.visibility = View.VISIBLE
        (activity as MainActivity).nav_view.getMenu().getItem(3).setChecked(true)

        sebhaCnt.setOnClickListener {
            (activity as MainActivity).cntVal++
            (activity as MainActivity).totCntVal++
            (activity as MainActivity).saveDailyCnt(false)
            sebhaCnt.text = (activity as MainActivity).cntVal.toString()
            dailyCnt.text = (activity as MainActivity).getDailyCnt().toString()
            vibratePhone()
        }

        mainSebhaRL.setOnClickListener {
            (activity as MainActivity).cntVal++
            (activity as MainActivity).totCntVal++
            (activity as MainActivity).saveDailyCnt(false)
            sebhaCnt.text = (activity as MainActivity).cntVal.toString()
            dailyCnt.text = (activity as MainActivity).getDailyCnt().toString()
            vibratePhone()
        }
        sebha1.setOnClickListener {
            sebhaCnt.text = "0"
            (activity as MainActivity).cntVal = 0
            if (startIndex==0) {
                sebha1.text =sebhaVals[4]
                sebha2.text =sebhaVals[0]
                sebha3.text =sebhaVals[1]
                startIndex = 4
            } else if (startIndex==4) {
                sebha1.text =sebhaVals[3]
                sebha2.text =sebhaVals[4]
                sebha3.text =sebhaVals[0]
                startIndex =3
            } else if (startIndex==3) {
                sebha1.text =sebhaVals[2]
                sebha2.text =sebhaVals[3]
                sebha3.text =sebhaVals[4]
                startIndex =2
            } else if (startIndex==2) {
                sebha1.text =sebhaVals[1]
                sebha2.text =sebhaVals[2]
                sebha3.text =sebhaVals[3]
                startIndex =1
            } else if (startIndex==1) {
                sebha1.text =sebhaVals[0]
                sebha2.text =sebhaVals[1]
                sebha3.text =sebhaVals[2]
                startIndex =0
            }
        }
        sebha3.setOnClickListener {
            sebhaCnt.text = "0"
            (activity as MainActivity).cntVal = 0
            if (startIndex==0) {
                sebha1.text =sebhaVals[1]
                sebha2.text =sebhaVals[2]
                sebha3.text =sebhaVals[3]
                startIndex = 1
            } else if (startIndex==1) {
                sebha1.text =sebhaVals[2]
                sebha2.text =sebhaVals[3]
                sebha3.text =sebhaVals[4]
                startIndex =2
            } else if (startIndex==2) {
                sebha1.text =sebhaVals[3]
                sebha2.text =sebhaVals[4]
                sebha3.text =sebhaVals[0]
                startIndex =3
            } else if (startIndex==3) {
                sebha1.text =sebhaVals[4]
                sebha2.text =sebhaVals[0]
                sebha3.text =sebhaVals[1]
                startIndex =2
            } else if (startIndex==4) {
                sebha1.text =sebhaVals[0]
                sebha2.text =sebhaVals[1]
                sebha3.text =sebhaVals[2]
                startIndex =0
            }
        }
        resetBtn.setOnClickListener {
            (activity as MainActivity).saveDailyCnt(true)
            dailyCnt.text = (activity as MainActivity).getDailyCnt().toString()
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).nav_view.visibility = View.VISIBLE
        (activity as MainActivity).nav_view.getMenu().getItem(3).setChecked(true)
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
}