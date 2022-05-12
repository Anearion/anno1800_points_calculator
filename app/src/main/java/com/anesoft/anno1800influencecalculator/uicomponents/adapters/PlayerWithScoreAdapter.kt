package com.anesoft.anno1800influencecalculator.uicomponents.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.states.savegame.SaveGameViewModel

class PlayerWithScoreAdapter() : RecyclerView.Adapter<PlayerWithScoreAdapter.ViewHolder>() {

    private var dataSet = mutableListOf<SaveGameViewModel.PlayerWithScore>()
    private var clickEnable = false
    private lateinit var listener: OnAdapterItemClick<SaveGameViewModel.PlayerWithScore>

    fun updateDataset(list: List<SaveGameViewModel.PlayerWithScore>) {
        dataSet.clear()
        dataSet.addAll(list)
        notifyDataSetChanged()
    }

    fun updateDataset(p: SaveGameViewModel.PlayerWithScore) {
        dataSet.add(p)
        notifyDataSetChanged()
    }

    fun enableOnClickListener(l: OnAdapterItemClick<SaveGameViewModel.PlayerWithScore>) {
        clickEnable = true
        listener = l
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.name)
        val row: ConstraintLayout = view.findViewById(R.id.container)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].player.name
        if (dataSet[position].score == 0)
            viewHolder.row.setBackgroundColor(Color.WHITE)
        else
            viewHolder.row.setBackgroundColor(Color.GREEN)
        if (clickEnable)
            viewHolder.row.setOnClickListener { listener.onClick(dataSet[position]) }
    }

    override fun getItemCount() = dataSet.size

}