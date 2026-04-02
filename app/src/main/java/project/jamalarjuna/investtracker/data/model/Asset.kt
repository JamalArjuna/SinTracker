package project.jamalarjuna.investtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "asset")
data class Asset(
    @PrimaryKey
    val name: String, // kode emiten
    val stockname: String, // nama emiten
    val currentPrice: Double, // harga sekarang suatu emiten
    val quantity: Double, // total quantity/Lot yang dimiliki
    val buyingPrice: Double // harga beli suatu emiten
)
