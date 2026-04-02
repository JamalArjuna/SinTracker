package project.jamalarjuna.investtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import project.jamalarjuna.investtracker.data.model.Asset
import project.jamalarjuna.investtracker.data.model.AssetSummary
import project.jamalarjuna.investtracker.data.repository.AssetRepository

class AssetViewModel(private val repository: AssetRepository): ViewModel() {
    val assets = repository.getAllAssets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )



    fun addAsset(asset: Asset){
        viewModelScope.launch {
            repository.insert(asset)
        }
    }

    val portoSummary = assets
        .map {
            List ->

            val totalInvestment = List.sumOf {
                it.buyingPrice * it.quantity
            }

            val totalAsset = List.sumOf {
                it.currentPrice * it.quantity
            }

            val totalPnL = List.sumOf {
                (it.currentPrice - it.buyingPrice) * it.quantity
            }

            AssetSummary(
                totalInvestment = totalInvestment,
                totalAsset = totalAsset,
                totalProfitLoss = totalPnL
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AssetSummary(
                totalInvestment = 0.0,
                totalAsset = 0.0,
                totalProfitLoss = 0.0
            )
        )
}