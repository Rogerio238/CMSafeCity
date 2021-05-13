package ESTG.IPVC.safecity.api



data class User(
    val id:Int,
    val titulo:String,
    val descricao:String,
    val latitude:String,
    val longitude:String,
    val userid:Int,
    val user:String,
    val tipo_id:Int,
    val tipo_ocorrencia:String,
    val password:String
)

data class iduser(
        val id: String?
)



