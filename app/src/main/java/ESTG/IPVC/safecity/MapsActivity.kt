package ESTG.IPVC.safecity

import  ESTG.IPVC.safecity.api.EndPoints
import ESTG.IPVC.safecity.api.ServiceBuilder
import ESTG.IPVC.safecity.api.User
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var users: List<User>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //inicializa fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getUsers()
        var position: LatLng

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    users = response.body()!!
                    for (user in users) {
                        position = LatLng(user.address.geo.lat.toString().toDouble(),
                            user.address.geo.lng.toString().toDouble())

                        mMap.addMarker( MarkerOptions().position(position) .title(user.address.suite + " - " + user.address.city)
                        )
                    }
                }
            }
                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                })
        locationCallback = object : LocationCallback()
        {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))


                Toast.makeText(this@MapsActivity, "Lat: " + loc.latitude.toString() + " Long : " + loc.longitude.toString(), Toast.LENGTH_SHORT).show()
                findViewById<TextView>(R.id.txtcoordenadas).setText("Lat: "+loc.latitude+ "- Long: "+loc.longitude)
                val address=getAddress(lastLocation.latitude,lastLocation.longitude)
                findViewById<TextView>(R.id.txtmorada).setText("Morada: " +address)
                findViewById<TextView>(R.id.txtdistancia).setText("Morada: " +calculateDistance(lastLocation.latitude,lastLocation.longitude,continenteLat,continenteLong).toString())
            }
        }
        createLocationRequest()

    }
    private fun getAddress(lat:Double,lng:Double):String{
        val geocoder= Geocoder(this)
        val list=geocoder.getFromLocation(lat,lng,1)
        return list[0].getAddressLine(0)
    }
    fun calculateDistance(lat1:Double,lng1:Double,lat2:Double,lng2:Double):Float{
  val results= FloatArray(1)
        Location.distanceBetween(lat1,lng1,lat2,lng2,results)
        return results[0]
    }
    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        // interval specifies the rate at which your app will like to receive updates. locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
                override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
       /* val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
                    setUpMap()
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
    fun setUpMap() {                    // Minha localização atual
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST_CODE)
            return
        }else {
            // 1
            mMap.isMyLocationEnabled = true
            // 2
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                // 3
                if (location != null) {
                    lastLocation = location
                    Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }

    }

}
