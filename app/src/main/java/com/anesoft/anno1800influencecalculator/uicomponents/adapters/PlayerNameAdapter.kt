package com.anesoft.anno1800influencecalculator.uicomponents.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player

class PlayerNameAdapter() :
    RecyclerView.Adapter<PlayerNameAdapter.ViewHolder>() {

    private var dataSet = mutableListOf<Player>()
    private var clickEnable = false
    private lateinit var listener : OnAdapterItemClick

    fun updateDataset(list: List<Player>) {
        dataSet.clear()
        dataSet.addAll(list)
        notifyDataSetChanged()
    }

    fun updateDataset(p: Player) {
        dataSet.add(p)
        notifyDataSetChanged()
    }

    fun enableOnClickListener(l: OnAdapterItemClick){
        clickEnable = true
        listener = l
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.name)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].name
        if(clickEnable)
            viewHolder.textView.setOnClickListener { listener.onClick(dataSet[position]) }
    }

    override fun getItemCount() = dataSet.size

}