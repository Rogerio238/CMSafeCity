package ESTG.IPVC.safecity.api

import retrofit2.Call
import retrofit2.http.*

interface  EndPoints {
    @GET("/users")
    fun getUsers():Call<List<User>>
    @GET("/users/{id}")
    fun getuserById(@Path("id")id:Int):Call<User>
    @FormUrlEncoded
    @POST("/posts")
    fun posTest(@Field("title")first:String?): Call<OutputPost>
}