package com.perfect.islamyat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import com.perfect.islamyat.models.infoModel
import com.perfect.islamyat.ui.DetailFragment
import com.perfect.islamyat.ui.SunanFragment
import java.util.*

class sunanAdapter (private val context: Context, private val parentView: SunanFragment, private val generalInfoRV: RecyclerView?, private val generalInfoModels: ArrayList<infoModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    companion object {
//        private var itemListener: RecyclerViewClickListener? = null
//    }

    inner class MyItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var itemTitle: TextView
        init {
            itemTitle = view.findViewById<TextView>(R.id.general_info_title)
            view.setOnClickListener(this)
        }
        override fun onClick(view: View) {
//            parentView.recyclerViewListClicked(view, adapterPosition)
            (parentView.activity as MainActivity).replaceFragmentsWithDetail(DetailFragment::class.java,generalInfoModels!![adapterPosition].title,generalInfoModels!![adapterPosition].description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.generalinfo_item, parent, false)
        return MyItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return generalInfoModels!!.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = generalInfoModels!![position]
        val holder = holder as sunanAdapter.MyItemViewHolder
        holder.itemTitle.text = item.title
    }
}
