package com.perfect.islamyat.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.perfect.islamyat.*
import com.perfect.islamyat.ui.GeneralInfoFragment
import com.perfect.islamyat.ui.SunanFragment
import kotlinx.android.synthetic.main.activity_main.*


class MoreFragment : Fragment() {

    companion object {
        fun newInstance() = MoreFragment()
    }

    private lateinit var viewModel: MoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.more_fragment, container, false)
        val general_info_rl : RelativeLayout= root.findViewById(R.id.general_info_rl)
        val sunan_rl : RelativeLayout= root.findViewById(R.id.sunan_rl)
        val fajr_alarm_rl : RelativeLayout= root.findViewById(R.id.fajr_alarm_rl)
        val calendar_rl : RelativeLayout= root.findViewById(R.id.calendar_rl)
        val esteghfar_rl: RelativeLayout= root.findViewById(R.id.esteghfar_rl)
        val qibla_rl : RelativeLayout = root.findViewById(R.id.qibla_rl)
        (activity as MainActivity).nav_view.visibility = View.VISIBLE
        (activity as MainActivity).nav_view.getMenu().getItem(4).setChecked(true)
        general_info_rl.setOnClickListener {
            (activity as MainActivity).replaceFragments(GeneralInfoFragment::class.java)
        }
        sunan_rl.setOnClickListener {
            (activity as MainActivity).replaceFragments(SunanFragment::class.java)
        }
        calendar_rl.setOnClickListener {
            (activity as MainActivity).replaceFragments(CalendarFragment::class.java)
        }
        fajr_alarm_rl.setOnClickListener {
            (activity as MainActivity).replaceFragments(FajrAlarmFragment::class.java)
        }
        esteghfar_rl.setOnClickListener {
            (activity as MainActivity).replaceFragments(EsteghfarAlarmFragment::class.java)
        }
        qibla_rl.setOnClickListener {
//            (activity as MainActivity).levelNo = 1
//            (activity as MainActivity).nav_view.visibility = View.GONE
            val intent = Intent(activity, CompassActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoreViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).nav_view.visibility = View.VISIBLE
        (activity as MainActivity).nav_view.getMenu().getItem(4).setChecked(true)
    }
}