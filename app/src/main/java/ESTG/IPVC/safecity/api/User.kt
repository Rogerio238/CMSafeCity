package ESTG.IPVC.safecity.api

data class User(
    val id:Int,
    val User_id: String,
    val name:String,
    val email:String,
    val address: Address
)
data class iduser(
    val id: String?
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
)

data class Geo(
    val lat: String,
    val lng: String
)