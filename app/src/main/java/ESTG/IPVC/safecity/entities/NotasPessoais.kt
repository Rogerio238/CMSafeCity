package ESTG.IPVC.safecity.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas_table")
data class NotasPessoais(@PrimaryKey(autoGenerate = true)val id:Int? = null,
                         @ColumnInfo(name="Titulo")val titulo:String,
                         @ColumnInfo(name="Corpo")val corpo:String
)


