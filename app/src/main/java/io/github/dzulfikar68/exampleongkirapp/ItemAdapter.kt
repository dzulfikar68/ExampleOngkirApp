package io.github.dzulfikar68.exampleongkirapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*

class ItemAdapter(
        private val name: String?,
        private val items: List<CostsItem>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName?.text = name ?: "-"
        holder.tvService?.text = item.service.let { if (!it.isNullOrEmpty()) "[${it}]" else "-" }
        holder.tvDescription?.text = item.description ?: "-"
        holder.tvCost?.text = item.cost.let { if (!it.isNullOrEmpty()) it[0].value?.toCurrency() else "-" }
        holder.tvDuration?.text = item.cost.let { if (!it.isNullOrEmpty()) findAndPrintHari(it[0].etd) else "-" }
    }

    private fun findAndPrintHari(text: String?): String {
        val containHari = text?.toLowerCase(Locale.ROOT)?.contains("hari")
        return if (containHari == true) {
            text
        } else {
            "${text} Hari"
        }
    }

    private fun Int.toCurrency(): String {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(this)
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