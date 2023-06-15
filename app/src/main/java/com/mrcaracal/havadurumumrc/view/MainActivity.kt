package com.mrcaracal.havadurumumrc.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.mrcaracal.havadurumumrc.R
import com.mrcaracal.havadurumumrc.databinding.ActivityMainBinding
import com.mrcaracal.havadurumumrc.viewmodel.MainViewModel
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.imgfavbtn
import kotlinx.android.synthetic.main.activity_main.imgnotfavbtn
import kotlinx.android.synthetic.main.activity_main.tv_city_name
import kotlinx.android.synthetic.main.activity_main.tv_degree
import kotlinx.android.synthetic.main.activity_main.tvuyari
import com.mrcaracal.havadurumumrc.model.FavoriteCity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    lateinit var listAdapter: ArrayAdapter<String>

    private lateinit var binding: ActivityMainBinding

    private lateinit var localGoogleMap: GoogleMap

    lateinit var manager: NotificationManager
    lateinit var channel: NotificationChannel
    lateinit var notificationBuilder: Notification.Builder

    private val channelId: String = "com.tutorials.localnotification"
    private val description: String = "Notification Sample Description"
    private val notoficationId = 1001
    private val requestCode = 1002

    private var weatherImageURL:String?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        val imgfavbtn=findViewById<ImageButton>(R.id.imgfavbtn)
        val imgnotfavbtn=findViewById<ImageButton>(R.id.imgnotfavbtn)


        val imgbtn=findViewById<ImageButton>(R.id.imageButton)
        val imgbtn2=findViewById<ImageButton>(R.id.imageButton2)
        val imgbtn4=findViewById<ImageButton>(R.id.imageButton4)
        val imgbtn5=findViewById<ImageButton>(R.id.imageButton5)

        val button=findViewById<Button>(R.id.btn_arti)
        val textView=findViewById<TextView>(R.id.tv_city_name)
        val favsehir1 = findViewById<TextView>(R.id.tv2fav1)
        val favsehir2 = findViewById<TextView>(R.id.tv2fav2)
        val favsehir3 = findViewById<TextView>(R.id.tv2fav3)
        val degree1 = findViewById<TextView>(R.id.tv1fav1)
        val degree2 = findViewById<TextView>(R.id.tv1fav2)
        val degree3 = findViewById<TextView>(R.id.tv1fav3)
        val ivfav1 = findViewById<ImageView>(R.id.ivfav1)
        val ivfav2 = findViewById<ImageView>(R.id.ivfav2)
        val ivfav3 = findViewById<ImageView>(R.id.ivfav3)



        val humidity_text1 = findViewById<TextView>(R.id.humidity_text)
        val humidity_text2 = findViewById<TextView>(R.id.tv_humidity)

        val feelslike_text1 = findViewById<TextView>(R.id.feelslike_text)
        val feelslike_text2 = findViewById<TextView>(R.id.tv_fl)

        val wind_text1 = findViewById<TextView>(R.id.wind_text)
        val wind_text2 = findViewById<TextView>(R.id.tv_wind)

        val pressure_text1 = findViewById<TextView>(R.id.pressure_text)
        val pressure_text2 = findViewById<TextView>(R.id.tv_pre)



        val favArrayList = ArrayList<FavoriteCity>()

// Butonun tıklama olayını belirle
        imgfavbtn.setOnClickListener {
            // TextView'dan veriyi al
            val veri = tv_city_name.text.toString()
            val degree = tv_degree.text.toString()
            val weathertype = weatherImageURL

            if (favArrayList.size < 3) {
                // Veriyi ArrayList'e ekle
                val favoriteCity =FavoriteCity(veri,degree,weathertype!!)

                favArrayList.add(favoriteCity)

                Toast.makeText(this, "Veri eklendi: $veri", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Maksimum veri sayısına ulaşıldı. Veri eklenemiyor.", Toast.LENGTH_SHORT).show()
            }


            if (favArrayList.size <= 1) {
                // Veriyi ArrayList'e ekle
                favsehir1.setText(favArrayList[0].name)
                degree1.setText(favArrayList[0].degree)
                weathertype?.let{
                    Glide.with(this)
                        .load(weatherImageURL)
                        .into(ivfav1)

                }




            } else if (favArrayList.size == 2){

                favsehir2.setText(favArrayList[1].name)
                degree2.setText(favArrayList[1].degree)
                weathertype?.let{
                    Glide.with(this)
                        .load(weatherImageURL)
                        .into(ivfav2)

                }

            }

            else if (favArrayList.size == 3){
                favsehir3.setText(favArrayList[2].name)
                degree3.setText(favArrayList[2].degree)
                weathertype?.let{
                    Glide.with(this)
                        .load(weatherImageURL)
                        .into(ivfav3)

                }


            }



        }

// Sil butonunun tıklama olayını belirle
        imgnotfavbtn.setOnClickListener {
            val silinecekVeri = tv_city_name.text.toString()

            if (favArrayList.contains(silinecekVeri)) {
                // Veriyi ArrayList'ten sil
                favArrayList.remove(silinecekVeri)
                Toast.makeText(this, "Veri silindi: $silinecekVeri", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "ArrayList'te böyle bir veri bulunamadı.", Toast.LENGTH_SHORT).show()
            }
        }



        imgbtn.setOnClickListener {

            feelslike_text1.visibility = View.VISIBLE
            feelslike_text2.visibility = View.VISIBLE
            humidity_text1.visibility = View.INVISIBLE
            humidity_text2.visibility = View.INVISIBLE
            wind_text1.visibility = View.INVISIBLE
            wind_text2.visibility = View.INVISIBLE
            pressure_text1.visibility = View.INVISIBLE
            pressure_text2.visibility = View.INVISIBLE


        }

        imgbtn2.setOnClickListener {

            humidity_text1.visibility = View.VISIBLE
            humidity_text2.visibility = View.VISIBLE
            wind_text1.visibility = View.INVISIBLE
            wind_text2.visibility = View.INVISIBLE
            pressure_text1.visibility = View.INVISIBLE
            pressure_text2.visibility = View.INVISIBLE
            feelslike_text1.visibility = View.INVISIBLE
            feelslike_text2.visibility = View.INVISIBLE


        }

        imgbtn4.setOnClickListener {

            wind_text1.visibility = View.VISIBLE
            wind_text2.visibility = View.VISIBLE
            pressure_text1.visibility = View.VISIBLE
            pressure_text2.visibility = View.VISIBLE
            feelslike_text1.visibility = View.INVISIBLE
            feelslike_text2.visibility = View.INVISIBLE
            humidity_text1.visibility = View.INVISIBLE
            humidity_text2.visibility = View.INVISIBLE



        }

        imgbtn5.setOnClickListener {

            feelslike_text1.visibility = View.VISIBLE
            feelslike_text2.visibility = View.VISIBLE
            humidity_text1.visibility = View.VISIBLE
            humidity_text2.visibility = View.VISIBLE
            wind_text1.visibility = View.VISIBLE
            wind_text2.visibility = View.VISIBLE
            pressure_text1.visibility = View.VISIBLE
            pressure_text2.visibility = View.VISIBLE

        }





        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        tvuyari.setOnClickListener {



            val notificationTitle = "Uyarı Bildirimi"
            val notificationContent = "Hava durumu bilgisi"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//auto generated by idea
                channel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                channel.enableLights(true)
                channel.lightColor = Color.BLUE
                channel.enableVibration(true)

                manager.createNotificationChannel(channel)

                notificationBuilder = Notification.Builder(this, channelId)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationContent)
                    .setSmallIcon(R.drawable.notify)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.notify))

            } else {
                notificationBuilder = Notification.Builder(this)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationContent)
                    .setSmallIcon(R.drawable.notify)
                    .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.notify))

            }

            manager.notify(notoficationId, notificationBuilder.build())
        }

        button.setOnClickListener{
            val intent = Intent(this, Main2Activity::class.java)
            intent.putExtra("Name",favsehir1.text.toString())
            intent.putExtra("Name2",favsehir2.text.toString())
            intent.putExtra("Name3",favsehir3.text.toString())
            intent.putExtra("Name4",degree1.text.toString())
            intent.putExtra("Name5",degree2.text.toString())
            intent.putExtra("Name6",degree3.text.toString())
           /* intent.putExtra("Name",ivfav1.text.toString())
            intent.putExtra("Name",ivfav2.text.toString())
            intent.putExtra("Name",ivfav3.text.toString())*/

            startActivity(intent)


        }






        // ------- Searchview part Start
        val citiesList = viewmodel.loadAllCities()

        listAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,citiesList)

        binding.adapter = listAdapter

        if(binding.idSV.query.isEmpty()){
            binding.visibilityFlag = 0
        }
        binding.idSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.visibilityFlag = 1
                /*if (citiesList.contains(query)) {
                    listAdapter.filter.filter(query)
                } else {
                    Toast.makeText(this@MainActivity, "No City found..", Toast.LENGTH_LONG).show()
                }*/
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.visibilityFlag = 1
                if (newText!!.isEmpty()){
                    binding.visibilityFlag = 0
                }
                listAdapter.filter.filter(newText)
                return false
            }
        })

        binding.listViewCity.setOnItemClickListener { parent, view, position, id ->
            val element = parent.getItemAtPosition(position).toString() // The item that was clicked
            binding.idSV.setQuery(element,false)
            val cityName = element
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refreshData(cityName)
            getLiveData()
            binding.listViewCity.visibility = View.GONE

        }

        //-------------- Searchview part End





        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()



        var cName = GET.getString("cityName", "trabzon")?.toLowerCase()
        viewmodel.refreshData(cName!!)

        getLiveData()

        swipe_refresh_layout.setOnRefreshListener {
            ll_data.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            val cityName = GET.getString("cityName", cName)?.toLowerCase()
            viewmodel.refreshData(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }

    }

    private fun getLiveData() {

        viewmodel.weather_data.observe(this, Observer { data ->
            data?.let {
                ll_data.visibility = View.VISIBLE

                binding.tvCityCodeText = data.sys.country
                binding.tvCityNameText = data.name
                weatherImageURL = "https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png"

                Glide.with(this)
                    .load(weatherImageURL)
                    .into(img_weather_pictures)




                binding.tvDegreeText = data.main.temp.toString() + "°C"
                binding.tvHumidityText = data.main.humidity.toString() + "%"
                binding.tvWindText = data.wind.speed.toString() +"km/s"
                binding.tvPreText = data.main.pressure.toString()+"mb"
                binding.tvFlText = data.main.feelsLike.toString()+"°C"

            }
        })






        viewmodel.weather_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    ll_data.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })

        viewmodel.weather_loading.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    ll_data.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }

    /*
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setMinZoomPreference(6.0f)
        googleMap.setMaxZoomPreference(14.0f)
        localGoogleMap = googleMap
    }

     */

    /*

    fun zoomToCity(cityName:String){

        if(cityName == "ankara"){
            val city = LatLng(39.925533,32.866287)
            localGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 15f))
            localGoogleMap.addMarker(MarkerOptions().position(city).title(cityName))
        }
        else if(cityName == "sivas"){
            val city = LatLng(39.750359,37.015598)
            localGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 15f))
            localGoogleMap.addMarker(MarkerOptions().position(city).title(cityName))
        }



    }


     */


}