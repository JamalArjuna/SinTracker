package project.jamalarjuna.investtracker.data.model
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDAO {
    @Insert
    suspend fun insert(asset: Asset)

    @Query("SELECT * FROM asset")
    fun getAllAsset(): Flow<List<Asset>>

}