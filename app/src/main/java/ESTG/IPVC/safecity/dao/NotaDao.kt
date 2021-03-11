package ESTG.IPVC.safecity.dao

import ESTG.IPVC.safecity.entities.NotasPessoais
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas_table ORDER BY titulo ASC")
    fun getAlphabetizedNotas(): LiveData<List<NotasPessoais>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nota: NotasPessoais)

    @Query("DELETE FROM notas_table")
    suspend fun deleteAll()
}