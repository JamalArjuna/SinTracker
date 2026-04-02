package project.jamalarjuna.investtracker.ui

import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.jamalarjuna.investtracker.data.model.Asset
import project.jamalarjuna.investtracker.R
import java.text.NumberFormat
import java.util.Locale

class AssetAdapter : RecyclerView.Adapter<AssetAdapter.AssetViewHolder>() {

    private val formatter = NumberFormat.getNumberInstance(Locale("in", "ID")).apply {
        minimumFractionDigits = 0
        maximumFractionDigits = 2
    }



    inner class AssetViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvLongName: TextView = view.findViewById(R.id.tvLongname)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
        val tvProfit: TextView = view.findViewById(R.id.tvProfit)
        val tvInvested: TextView = view.findViewById(R.id.tvInvested)
        val tvCurrentPrice: TextView = view.findViewById(R.id.tvCurrentPrice)
    }

    fun submitData(newData: List<Asset>) {
        data = newData
        notifyDataSetChanged()
    }

    private var data = listOf<Asset>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AssetAdapter.AssetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asset, parent, false)
        return AssetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssetAdapter.AssetViewHolder, position: Int) {
        val asset = data[position]
        // data formatter IDR
        val invested = asset.buyingPrice * asset.quantity
        val profit = (asset.currentPrice - asset.buyingPrice) * asset.quantity

        holder.tvLongName.text = asset.stockname
        holder.tvName.text = asset.name
        holder.tvInvested.text = "Rp. ${formatter.format(invested)}"
        holder.tvQuantity.text = "${asset.quantity}"
        holder.tvProfit.text = formatter.format(profit)
        holder.tvCurrentPrice.text = "Rp. ${formatter.format(asset.currentPrice)}"

        // Change background based on profit/loss
        if (profit < 0) {
            holder.tvProfit.setBackgroundResource(R.drawable.bg_loss_indicator)
        } else {
            holder.tvProfit.setBackgroundResource(R.drawable.bg_profit_indicator)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}