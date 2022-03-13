package com.perfect.islamyat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import com.perfect.islamyat.models.ZakrTitleModel
import com.perfect.islamyat.ui.ZekrDetailFragment
import com.perfect.islamyat.ui.ZekrDetailFragment2
import com.perfect.islamyat.ui.notifications.NotificationsFragment
import java.util.*
import kotlin.math.abs

class ZekrTitleAdapter(
    private val context: Context,
    private val parentView: NotificationsFragment,
    private val generalInfoRV: RecyclerView?,
    private val ZekrTitlesModels: ArrayList<ZakrTitleModel>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    companion object {
//        private var itemListener: RecyclerViewClickListener? = null
//    }

    inner class MyItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var itemTitle: TextView
        var itemCard: CardView

        init {
            itemTitle = view.findViewById<TextView>(R.id.zekrtitle_title)
            itemCard = view.findViewById<CardView>(R.id.cardcolor)
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
//            parentView.recyclerViewListClicked(view, adapterPosition)

            print("oneClick" + adapterPosition + "\n")

            if (adapterPosition == 0) {
                (parentView.activity as MainActivity).replaceFragmentsWithDetail2(
                    ZekrDetailFragment2::class.java,
                    ZekrTitlesModels!![adapterPosition].id.toString(),
                    ZekrTitlesModels!![adapterPosition].title,
                    ZekrTitlesModels!![adapterPosition].color
                )
            } else
                (parentView.activity as MainActivity).replaceFragmentsWithDetail2(
                    ZekrDetailFragment::class.java,
                    ZekrTitlesModels!![adapterPosition].id.toString(),
                    ZekrTitlesModels!![adapterPosition].title,
                    ZekrTitlesModels!![adapterPosition].color
                )


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.zekrtitle_item, parent, false)
        return MyItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ZekrTitlesModels!!.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = ZekrTitlesModels!![position]
        val holder = holder as ZekrTitleAdapter.MyItemViewHolder
        holder.itemTitle.text = item.title
        var myColor: String = item.color
        holder.itemTitle.setTextColor(-abs(myColor.hashCode()))
        holder.itemCard.setCardBackgroundColor(-abs(myColor.hashCode()))
    }
}
