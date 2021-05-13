package ESTG.IPVC.safecity

import  ESTG.IPVC.safecity.api.EndPoints
import ESTG.IPVC.safecity.api.ServiceBuilder
import ESTG.IPVC.safecity.api.User
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.MenuInflater
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu

import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var mMap: GoogleMap
    private lateinit var ocorrencias: List<User>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        var  continenteLat = 41.7843
       var continenteLong = -8.8148
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                lastLocation = locationResult!!.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc , 15.0f))
                findViewById<TextView>(R.id.textLngLAt).setText("Lat " + loc.latitude + " - Long" + loc.longitude)
                findViewById<TextView>(R.id.textDistancia).setText("Distancia " + calculateDistance(
                    lastLocation.latitude, lastLocation.longitude,
                    continenteLat, continenteLong).toString());


            }
        }
        // call the service and add markers
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getocorrencias()
        var position: LatLng
        createLocationRequest()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    ocorrencias = response.body()!!
                    val extras = intent.extras
                    val id = extras?.getString("id")
                    val tipo_id = extras?.getInt("tipo_id")

                    for (ocorrencia in ocorrencias) {
                        if (ocorrencia.userid.toString() == id) {


                            if (tipo_id == 0) {
                                Toast.makeText(this@MapsActivity, ocorrencia.latitude.toString(), Toast.LENGTH_SHORT).show()
                                if (ocorrencia.id == id?.toInt()) {
                                    position = LatLng(
                                        ocorrencia.latitude.toDouble(),
                                        ocorrencia.longitude.toDouble()
                                    )
                                    mMap.addMarker(MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao))
                                } else {
                                    position = LatLng(
                                        ocorrencia.latitude.toDouble(),
                                        ocorrencia.longitude.toDouble()
                                    )
                                    mMap.addMarker(
                                        MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao).icon(
                                            BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_GREEN
                                            )
                                        )
                                    )
                                }
                            } else {
                                if (ocorrencia.id == tipo_id) {
                                    if (ocorrencia.id == id?.toInt()) {
                                        position = LatLng(
                                            ocorrencia.latitude.toDouble(),
                                            ocorrencia.longitude.toDouble()
                                        )
                                        mMap.addMarker(MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao))
                                    } else {
                                        position = LatLng(
                                            ocorrencia.latitude.toDouble(),
                                            ocorrencia.longitude.toDouble()
                                        )
                                        mMap.addMarker(
                                            MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao).icon(
                                                BitmapDescriptorFactory.defaultMarker(
                                                    BitmapDescriptorFactory.HUE_GREEN
                                                )
                                            )
                                        )
                                    }
                                }
                            }
                        }else {
                            if (tipo_id == 0) {
                                Toast.makeText(this@MapsActivity, ocorrencia.latitude, Toast.LENGTH_SHORT).show()
                                if (ocorrencia.id == id?.toInt()) {
                                    position = LatLng(
                                        ocorrencia.latitude.toDouble(),
                                        ocorrencia.longitude.toDouble()
                                    )
                                    mMap.addMarker(MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao))
                                } else {
                                    position = LatLng(
                                        ocorrencia.latitude.toDouble(),
                                        ocorrencia.longitude.toDouble()
                                    )
                                    mMap.addMarker(
                                        MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao).icon(
                                            BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_BLUE
                                            )
                                        )
                                    )
                                }
                            } else {
                                if (ocorrencia.id == tipo_id) {
                                    if (ocorrencia.id == id?.toInt()) {
                                        position = LatLng(
                                            ocorrencia.latitude.toDouble(),
                                            ocorrencia.longitude.toDouble()
                                        )
                                        mMap.addMarker(MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao))
                                    } else {
                                        position = LatLng(
                                            ocorrencia.latitude.toDouble(),
                                            ocorrencia.longitude.toDouble()
                                        )
                                        mMap.addMarker(
                                            MarkerOptions().position(position).title(ocorrencia.user+ " - " + ocorrencia.titulo + " - " + ocorrencia.descricao).icon(
                                                BitmapDescriptorFactory.defaultMarker(
                                                    BitmapDescriptorFactory.HUE_BLUE
                                                )
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })




    }
    private fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {

        val results = FloatArray(1);
        Location.distanceBetween(lat1,lng1,lat2,lng2,results)

        return results[0];
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.logout -> {
                var token = getSharedPreferences("utilizador", Context.MODE_PRIVATE)
                intent.putExtra("loginutilizador", " ")
                var editor = token.edit()
                editor.putString("loginutilizador", " ")

                editor.commit()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val intent = Intent(this@MapsActivity, login::class.java)
                startActivity(intent)
                finish()
                true
            }
           R.id.filtarTipoId -> {
               mMap.clear()
               val request = ServiceBuilder.buildService(EndPoints::class.java)
               val call = request.getobras()
               var position: LatLng
               createLocationRequest()
               call.enqueue(object : Callback<List<User>> {
                   override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                       if (response.isSuccessful) {
                           ocorrencias = response.body()!!
                           val extras = intent.extras
                           val id = extras?.getString("id")
                           val tipo_id =2

                           for (ocorrencia in ocorrencias) {
                               if (ocorrencia.userid.toString() == id) {


                                   if (tipo_id == 2) {
                                       Toast.makeText(this@MapsActivity, ocorrencia.latitude.toString(), Toast.LENGTH_SHORT).show()
                                       if (ocorrencia.id == id?.toInt()) {
                                           position = LatLng(
                                               ocorrencia.latitude.toDouble(),
                                               ocorrencia.longitude.toDouble()
                                           )
                                           mMap.addMarker(MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao))
                                       } else {
                                           position = LatLng(
                                               ocorrencia.latitude.toDouble(),
                                               ocorrencia.longitude.toDouble()
                                           )
                                           mMap.addMarker(
                                               MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao).icon(
                                                   BitmapDescriptorFactory.defaultMarker(
                                                       BitmapDescriptorFactory.HUE_GREEN
                                                   )
                                               )
                                           )
                                       }
                                   } else {
                                       if (ocorrencia.id == tipo_id) {
                                           if (ocorrencia.id == id?.toInt()) {
                                               position = LatLng(
                                                   ocorrencia.latitude.toDouble(),
                                                   ocorrencia.longitude.toDouble()
                                               )
                                               mMap.addMarker(MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao))
                                           } else {
                                               position = LatLng(
                                                   ocorrencia.latitude.toDouble(),
                                                   ocorrencia.longitude.toDouble()
                                               )
                                               mMap.addMarker(
                                                   MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao).icon(
                                                       BitmapDescriptorFactory.defaultMarker(
                                                           BitmapDescriptorFactory.HUE_GREEN
                                                       )
                                                   )
                                               )
                                           }
                                       }
                                   }
                               }else {
                                   if (tipo_id == 0) {
                                       Toast.makeText(this@MapsActivity, ocorrencia.latitude, Toast.LENGTH_SHORT).show()
                                       if (ocorrencia.id == id?.toInt()) {
                                           position = LatLng(
                                               ocorrencia.latitude.toDouble(),
                                               ocorrencia.longitude.toDouble()
                                           )
                                           mMap.addMarker(MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao))
                                       } else {
                                           position = LatLng(
                                               ocorrencia.latitude.toDouble(),
                                               ocorrencia.longitude.toDouble()
                                           )
                                           mMap.addMarker(
                                               MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao).icon(
                                                   BitmapDescriptorFactory.defaultMarker(
                                                       BitmapDescriptorFactory.HUE_BLUE
                                                   )
                                               )
                                           )
                                       }
                                   } else {
                                       if (ocorrencia.id == tipo_id) {
                                           if (ocorrencia.id == id?.toInt()) {
                                               position = LatLng(
                                                   ocorrencia.latitude.toDouble(),
                                                   ocorrencia.longitude.toDouble()
                                               )
                                               mMap.addMarker(MarkerOptions().position(position).title(ocorrencia.user + " - " + ocorrencia.titulo + " - " + ocorrencia.descricao))
                                           } else {
                                               position = LatLng(
                                                   ocorrencia.latitude.toDouble(),
                                                   ocorrencia.longitude.toDouble()
                                               )
                                               mMap.addMarker(
                                                   MarkerOptions().position(position).title(ocorrencia.user+ " - " + ocorrencia.titulo + " - " + ocorrencia.descricao).icon(
                                                       BitmapDescriptorFactory.defaultMarker(
                                                           BitmapDescriptorFactory.HUE_BLUE
                                                       )
                                                   )
                                               )
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
                   override fun onFailure(call: Call<List<User>>, t: Throwable) {
                       Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                   }
               })






                true


            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        // interval specifies the rate at which your app will like to receive updates. locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        /*
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        */
        setUpMap()
    }

    fun setUpMap() {                    // Minha localização atual
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        } else {
            // 1
            mMap.isMyLocationEnabled = true
            // 2
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                // 3
                if (location != null) {
                    lastLocation = location
                    //Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }

    }




    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */) }



    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    public override fun onResume()
    {
        super.onResume()
        startLocationUpdates()
    }

    public override fun onRestart() {
        super.onRestart()
        val extras = intent.extras
        val id = extras?.getString("id")
        val tipo_id = extras?.getString("tipo_id")
        val intent = Intent(this@MapsActivity, MapsActivity::class.java)
        intent.putExtra("id", id)


        startActivity(intent)
        finish()

    }

}