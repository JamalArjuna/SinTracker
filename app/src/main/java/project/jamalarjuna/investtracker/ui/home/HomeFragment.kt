package project.jamalarjuna.investtracker.ui.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import project.jamalarjuna.investtracker.R
import project.jamalarjuna.investtracker.data.model.AppDatabase
import project.jamalarjuna.investtracker.data.model.Asset
import project.jamalarjuna.investtracker.data.repository.AssetRepository
import project.jamalarjuna.investtracker.viewmodel.AssetViewModel
import project.jamalarjuna.investtracker.viewmodel.AssetViewModelFactory
import java.text.NumberFormat
import java.util.Locale

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: AssetViewModel
    private lateinit var adapter: AssetAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Dependency Injection
        // init repository
        val dao = AppDatabase.getDatabase(requireContext()).assetDao()
        val repository = AssetRepository(dao)
        // init viewmodel
        val factory = AssetViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AssetViewModel::class.java]
        // init adapter
        adapter = AssetAdapter()

        val rv = view.findViewById<RecyclerView>(R.id.rvAssets)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        val tvInvest = view.findViewById<TextView>(R.id.tvTotalInvestment)
        val tvValue = view.findViewById<TextView>(R.id.tvTotalValue)
        val tvProfit = view.findViewById<TextView>(R.id.tvProfitLoss)

        // tes
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())
            val dao = db.assetDao()

            // TEST INSERT
            viewModel.addAsset(
                asset = Asset(
                    stockname = "PT. Bank Central Asia TBK.",
                    name = "AADI",
                    quantity = 2.0,
                    buyingPrice = 10000.0,
                    currentPrice = 2500.0
                )
            )
        }
        // observe data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // LIST
                launch {
                    viewModel.assets.collect { list ->
                        adapter.submitData(list)
                    }
                }

                // SUMMARY
                launch {
                    viewModel.portoSummary.collect { summary ->
                        val formatter = NumberFormat.getNumberInstance(Locale("in", "ID")).apply {
                            minimumFractionDigits = 0
                            maximumFractionDigits = 2
                        }

                        tvInvest.text = "Invest: ${formatter.format(summary.totalInvestment)}"
                        tvValue.text = "Value: ${formatter.format(summary.totalAsset)}"
                        tvProfit.text = "P/L: ${formatter.format(summary.totalProfitLoss)}"

                        // warna
                        if (summary.totalProfitLoss >= 0) {
                            tvProfit.setTextColor(Color.GREEN)
                        } else {
                            tvProfit.setTextColor(Color.RED)
                        }
                    }
                }
            }
        }
    }
}