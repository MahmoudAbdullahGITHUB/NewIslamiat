package com.perfect.islamyat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import com.perfect.islamyat.adapters.sunanAdapter
import com.perfect.islamyat.models.infoModel
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SunanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SunanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var sunanModels: ArrayList<infoModel>? = null
    private var sunan_list : RecyclerView? = null
    private var sunan_adapter : sunanAdapter? = null

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
        val root = inflater.inflate(R.layout.fragment_sunan, container, false)
        sunan_list = root.findViewById(R.id.sunan_list)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val info = (activity as MainActivity).dbData?.sunan
        sunanModels = ArrayList<infoModel>()
        info?.moveToFirst()
        do {
            sunanModels?.add(infoModel(info!!.getInt(0),info.getString(1),info.getString(2)))
        } while (info!!.moveToNext())
        sunanModels?.count()
        sunan_list?.setLayoutManager(LinearLayoutManager(activity));
        sunan_adapter = sunanAdapter(activity as MainActivity,this, sunan_list,sunanModels)
        sunan_list?.adapter = sunan_adapter
        sunan_list?.refreshDrawableState()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SunanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SunanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}