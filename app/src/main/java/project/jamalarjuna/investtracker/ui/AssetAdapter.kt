package project.jamalarjuna.investtracker.ui

import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.jamalarjuna.investtracker.data.model.Asset
import project.jamalarjuna.investtracker.R

class AssetAdapter : RecyclerView.Adapter<AssetAdapter.AssetViewHolder>() {

    inner class AssetViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
        val tvProfit: TextView = view.findViewById(R.id.tvProfit)
        val tvInvested: TextView = view.findViewById(R.id.tvInvested)
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
        holder.tvName.text = asset.name
        holder.tvInvested.text = "Invested: Rp. ${String.format("%.2f", asset.buyingPrice * asset.quantity)}"
        holder.tvQuantity.text = "Qty: ${asset.quantity}"
        val profit = (asset.currentPrice - asset.buyingPrice) * asset.quantity
        holder.tvProfit.text = "Profit: ${String.format("%.2f", profit)}"
    }

    override fun getItemCount(): Int {
        return data.size
    }
}