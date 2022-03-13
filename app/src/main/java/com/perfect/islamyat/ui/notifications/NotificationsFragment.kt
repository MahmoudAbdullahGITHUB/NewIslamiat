package com.perfect.islamyat.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import com.perfect.islamyat.adapters.ZekrTitleAdapter
import com.perfect.islamyat.models.ZakrTitleModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var azkarTitlesModel: ArrayList<ZakrTitleModel>? = null
    private var azkar_list : RecyclerView? = null
    private var azkar_titles_adapter : ZekrTitleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        azkar_list  = root.findViewById(R.id.azkar_list)
        (activity as MainActivity).nav_view.visibility = View.VISIBLE
        (activity as MainActivity).nav_view.getMenu().getItem(1).setChecked(true)
//        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val azkarTitles = (activity as MainActivity).dbData?.azkarTitles
        val monColors = ArrayList<String>()
        monColors.add("white")
        monColors.add("yellow")
        monColors.add("fuchsia")
        monColors.add("red")
        monColors.add("silver")
        monColors.add("gray")
        monColors.add("olive")
        monColors.add("purple")
        monColors.add("maroon")
        monColors.add("aqua")
        monColors.add("lime")
        monColors.add("teal")
        monColors.add("green")
        monColors.add("blue")


        azkarTitlesModel = ArrayList<ZakrTitleModel>()
        azkarTitles?.moveToFirst()
        var indexLoop:Int = 0
        do {
            print("mat = "+azkarTitles!!.getInt(0)+" m2 "+azkarTitles.getString(1)+"    mmmm")
            azkarTitlesModel?.add(ZakrTitleModel(azkarTitles!!.getInt(0),
                azkarTitles.getString(1), monColors[indexLoop]
            ))
            ++indexLoop
            println("monIndex "+indexLoop)
        } while (azkarTitles!!.moveToNext())
        azkarTitlesModel?.count()
        print("alcount "+azkarTitlesModel?.count()+" \n")
        azkar_list?.setLayoutManager(LinearLayoutManager(activity));
        azkar_titles_adapter = ZekrTitleAdapter(activity as MainActivity,this, azkar_list,azkarTitlesModel)
        azkar_list?.adapter = azkar_titles_adapter
        azkar_list?.refreshDrawableState()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).nav_view.visibility = View.VISIBLE
        (activity as MainActivity).nav_view.getMenu().getItem(1).setChecked(true)
    }
}