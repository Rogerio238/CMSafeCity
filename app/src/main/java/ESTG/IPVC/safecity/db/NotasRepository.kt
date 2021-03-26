package ESTG.IPVC.safecity.db


import ESTG.IPVC.safecity.dao.NotaDao
import ESTG.IPVC.safecity.entities.NotasPessoais
import androidx.lifecycle.LiveData

class NotasRepository(private val notaDao: NotaDao) {


    val allNotas: LiveData<List<NotasPessoais>> = notaDao.getAlphabetizedNotas()

    suspend fun insert(nota: NotasPessoais) {
        notaDao.insert(nota)
    }
    suspend fun deleteNotaById(nota: Int) {
        notaDao.deleteNotaById(nota)
    }
    suspend fun update(titulo: String,corpo:String,id:Int) {
        notaDao.update(titulo,corpo,id)
    }
}
