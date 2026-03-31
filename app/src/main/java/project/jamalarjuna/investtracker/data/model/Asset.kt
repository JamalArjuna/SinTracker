package project.jamalarjuna.investtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "asset")
data class Asset(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val avgPrice: Double,
    val quantity: Double,
    val buyingPrice: Double
)
