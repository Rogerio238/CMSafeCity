package ESTG.IPVC.safecity.api

import retrofit2.Call
import retrofit2.http.*

interface  EndPoints {

    @GET("/api/ocorrencias")
    fun getocorrencias():Call<List<User>>

    @GET("/api/ocorrencias/obras")
    fun getobras():Call<List<User>>




    @GET("/users/{id}")
    fun getuserById(@Path("id")id:Int):Call<User>


    @FormUrlEncoded
    @POST("/api/app/login")
    fun login(  @Field("user") first: String?,
                @Field("PASSWORD") pass: String?): Call<OutputPost>


}