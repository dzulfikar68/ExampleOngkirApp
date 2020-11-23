package io.github.dzulfikar68.exampleongkirapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
        private val name: String,
        private val items: List<CostsItem>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName?.text = name
        holder.tvService?.text = item.service
        holder.tvDescription?.text = item.description
        val cost = item.cost?.let { if (it.isNullOrEmpty()) "Rp. ${it[0].value.toString()}" else "-" }
        holder.tvCost?.text = cost
        val duration = item.cost?.let { if (it.isNullOrEmpty()) "${it[0].etd} Hari" else "-" }
        holder.tvDuration?.text = duration
    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var tvName: TextView? = null
        var tvService: TextView? = null
        var tvDescription: TextView? = null
        var tvCost: TextView? = null
        var tvDuration: TextView? = null

        init {
            tvName = row.findViewById(R.id.tvName) as TextView
            tvService = row.findViewById(R.id.tvService) as TextView
            tvDescription = row.findViewById(R.id.tvDescription) as TextView
            tvCost = row.findViewById(R.id.tvCost) as TextView
            tvDuration = row.findViewById(R.id.tvDuration) as TextView
        }
    }
}