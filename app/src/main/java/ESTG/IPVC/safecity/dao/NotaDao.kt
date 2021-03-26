package ESTG.IPVC.safecity.dao

import ESTG.IPVC.safecity.entities.NotasPessoais
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas_table ORDER BY titulo ASC")
    fun getAlphabetizedNotas(): LiveData<List<NotasPessoais>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nota: NotasPessoais)

    @Query("DELETE FROM notas_table")
    suspend fun deleteAll()

    @Update
suspend fun updateNota(nota: NotasPessoais)

@Query("DELETE FROM notas_table WHERE id == :id")
suspend fun deleteNotaById(id: Int)

@Query("UPDATE notas_table SET titulo=:titulo , corpo=:corpo WHERE id == :id")
suspend fun update(titulo: String,corpo: String,id: Int)
}