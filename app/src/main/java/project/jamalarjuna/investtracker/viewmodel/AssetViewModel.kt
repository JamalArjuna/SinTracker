package project.jamalarjuna.investtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import project.jamalarjuna.investtracker.data.model.Asset
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
}