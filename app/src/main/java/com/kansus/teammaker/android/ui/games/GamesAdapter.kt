package com.kansus.teammaker.android.ui.games


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kansus.teammaker.R
import com.kansus.teammaker.android.Navigator
import com.kansus.teammaker.android.extension.inflate
import com.kansus.teammaker.domain.model.Game
import kotlinx.android.synthetic.main.fragment_games_item.view.*
import kotlin.properties.Delegates

/**
 *
 */
class GamesAdapter(
    games: List<Game>,
    internal var selectionListener: (Game, Navigator.Extras) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    internal var mValues: List<Game> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    init {
        mValues = games
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.fragment_games_item))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(mValues[position], selectionListener)

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieView: Game, clickListener: (Game, Navigator.Extras) -> Unit) {
            itemView.item_number.text = movieView.id.toString()
            itemView.content.text = movieView.name
            itemView.setOnClickListener { clickListener(movieView, Navigator.Extras(itemView.item_number)) }
        }
    }
}
