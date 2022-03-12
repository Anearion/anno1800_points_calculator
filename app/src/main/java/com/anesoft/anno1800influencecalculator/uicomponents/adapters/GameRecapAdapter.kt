package com.anesoft.anno1800influencecalculator.uicomponents.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.model.Game
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class GameRecapAdapter() : RecyclerView.Adapter<GameRecapAdapter.ViewHolder>() {

    private var dataSet = mutableListOf<Game>()
    private var clickEnable = false
    private lateinit var listener : OnAdapterItemClick<Game>

    fun updateDataset(list: List<Game>) {
        dataSet.clear()
        dataSet.addAll(list)
        notifyDataSetChanged()
    }

    fun updateDataset(p: Game) {
        dataSet.add(p)
        notifyDataSetChanged()
    }

    fun enableOnClickListener(l: OnAdapterItemClick<Game>){
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

        val context = viewHolder.date.context
        val game = dataSet[position]

        val dateTime = game.scoreList[0].created

        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale( Locale.ITALIAN ).withZone(ZoneId.systemDefault())
        val instant = Instant.ofEpochMilli(dateTime)
        val output = formatter.format(instant)
        viewHolder.date.text = context.getString(R.string.date, output)

        viewHolder.numberOfPlayer.text = context.getString(R.string.number_of_players, game.numberOfPlayers)
        viewHolder.winner.text = context.getString(R.string.winner, game.winner)

        if(clickEnable)
            viewHolder.row.setOnClickListener { listener.onClick(game) }
    }

    override fun getItemCount() = dataSet.size

}