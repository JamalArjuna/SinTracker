package project.jamalarjuna.investtracker.data.repository

import project.jamalarjuna.investtracker.data.model.Asset
import project.jamalarjuna.investtracker.data.model.AssetDAO

class AssetRepository(private val dao: AssetDAO) {
    fun getAllAssets() = dao.getAllAsset()

    suspend fun insert(asset: Asset){
        dao.insert(asset)
    }
}