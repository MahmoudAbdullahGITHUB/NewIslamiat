package com.perfect.islamyat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import com.perfect.islamyat.adapters.pagerAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ZekrDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("ARG_PARAM1")
            param2 = it.getString("ARG_PARAM2")
            param3 = it.getString("ARG_PARAM3")


            print(" param1 "+param1+"\n")
            print(" param2 "+param2+"\n")
            print(" param3 "+param3+"\n")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_zekr_detail, container, false)
        val zekr_detail_pager : ViewPager2 = root.findViewById(R.id.zekr_detail_pager)
        val zekr_detail_title : TextView = root.findViewById(R.id.zekr_detail_title)
//        param2 = param2?.replace("\\n","<br />")
        val azkar = (activity as MainActivity).dbData!!.getAzkarForID(param1?.toInt()!!)
        (activity as MainActivity).azkar = azkar
        azkar.moveToFirst()
        print("azkar.getString(2) "+azkar.getString(5)+"azkar "+azkar.getColumnName(2))
        zekr_detail_title.text = azkar.getString(2)
        val pager_adapter = pagerAdapter(activity as MainActivity,azkar.count)
        zekr_detail_pager.adapter = pager_adapter
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}