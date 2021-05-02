package ESTG.IPVC.safecity.viewModel

import ESTG.IPVC.safecity.Notas
import ESTG.IPVC.safecity.dao.NotaDao
import ESTG.IPVC.safecity.db.NotasRepository
import ESTG.IPVC.safecity.db.NotasDb
import ESTG.IPVC.safecity.entities.NotasPessoais
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotasViewModel(application: Application):AndroidViewModel(application) {
    private val repository: NotasRepository

    val allNotas:LiveData<List<NotasPessoais>>

    init{
        val notaDao = NotasDb.getDatabase(application, viewModelScope).NotaDao()
        repository = NotasRepository(notaDao)
        allNotas=repository.allNotas
    }
    fun insert(nota:NotasPessoais)=viewModelScope.launch(Dispatchers.IO){
        repository.insert(nota)
    }
    fun deleteNotaById(nota:Int)=viewModelScope.launch(Dispatchers.IO){
        repository.deleteNotaById(nota)
    }
    fun update(titulo:String,corpo:String,id:Int)=viewModelScope.launch{
        repository.update(titulo,corpo,id)
    }

}