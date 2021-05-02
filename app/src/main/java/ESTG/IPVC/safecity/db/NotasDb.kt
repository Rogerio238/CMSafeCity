package ESTG.IPVC.safecity.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import ESTG.IPVC.safecity.dao.NotaDao
import ESTG.IPVC.safecity.entities.NotasPessoais
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch





@Database(entities = arrayOf(NotasPessoais::class), version = 1, exportSchema = false)
public abstract class NotasDb : RoomDatabase() {

    abstract fun NotaDao(): NotaDao
private class NotasDatabaseCallBack(
    private val scope:CoroutineScope
):RoomDatabase.Callback(){
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        INSTANCE?.let{database->
            scope.launch {
                var notaDao=database.NotaDao()
               // notaDao.deleteAll()


            }
        }
    }
}

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NotasDb? = null

        fun getDatabase(context: Context,scope: CoroutineScope): NotasDb {
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                   NotasDb::class.java,
                    "notas_database"

                )
                    .fallbackToDestructiveMigration()
                    .addCallback(NotasDatabaseCallBack(scope))
                    .build()
                INSTANCE=instance
                return instance
            }
        }
    }
}