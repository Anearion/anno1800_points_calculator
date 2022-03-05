package com.anesoft.anno1800influencecalculator.uicomponents.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class GameRecapAdapter() : RecyclerView.Adapter<GameRecapAdapter.ViewHolder>() {

    private var dataSet = mutableListOf<Score>()
    private var clickEnable = false
    private lateinit var listener : OnAdapterItemClick<Score>

    fun updateDataset(list: List<Score>) {
        dataSet.clear()
        dataSet.addAll(list)
        notifyDataSetChanged()
    }

    fun updateDataset(p: Score) {
        dataSet.add(p)
        notifyDataSetChanged()
    }

    fun enableOnClickListener(l: OnAdapterItemClick<Score>){
        clickEnable = true
        listener = l
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val winner: TextView = view.findViewById(R.id.tv_winner)
        val date : TextView = view.findViewById(R.id.tv_date)
        val numberOfPlayer : TextView = view.findViewById(R.id.tv_numberOfPlayer)
        val row: ConstraintLayout = view.findViewById(R.id.container)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_game_recap, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dateTime = dataSet[position].created

        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale( Locale.ITALIAN ).withZone(ZoneId.systemDefault())
        val instant = Instant.ofEpochMilli(dateTime)
        val output = formatter.format(instant)
        viewHolder.date.text = viewHolder.date.context.getString(R.string.date, output)
        if(clickEnable)
            viewHolder.row.setOnClickListener { listener.onClick(dataSet[position]) }
    }

    override fun getItemCount() = dataSet.size

}