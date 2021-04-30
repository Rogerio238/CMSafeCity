package ESTG.IPVC.safecity

import ESTG.IPVC.safecity.adapters.UserAdapter
import ESTG.IPVC.safecity.api.EndPoints
import ESTG.IPVC.safecity.api.OutputPost
import ESTG.IPVC.safecity.api.ServiceBuilder
import ESTG.IPVC.safecity.api.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
class   MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val request= ServiceBuilder.buildService(EndPoints::class.java)
            val call=request.getUsers()
            call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call:Call<List<User>>,response: Response<List<User>>){
                if(response.isSuccessful){
                    recyclerview.apply{
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = UserAdapter(response.body()!!)

                    }
                }
            }
            override fun onFailure(call:Call<List<User>>,t:Throwable){
                Toast.makeText(this@MainActivity,"${t.message}",Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getSingle(view: View) {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getuserById(2)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val c: User = response.body()!!
                    Toast.makeText(this@MainActivity, c.address.zipcode, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }
 fun post(view: View){
     val request = ServiceBuilder.buildService(EndPoints::class.java)
     val call = request.posTest("teste")
     call.enqueue(object :Callback<OutputPost>{
         override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
             if(response.isSuccessful){
                 val c:OutputPost=response.body()!!
                 Toast.makeText(this@MainActivity,c.id.toString()+"-"+ c.title,Toast.LENGTH_SHORT).show()
             }
         }

         override fun onFailure(call: Call<OutputPost>, t: Throwable) {
             Toast.makeText(this@MainActivity,"${t.message}",Toast.LENGTH_SHORT).show()
         }
     })


 }
}