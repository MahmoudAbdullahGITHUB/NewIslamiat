package com.perfect.islamyat.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.perfect.islamyat.BuildConfig
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import com.perfect.islamyat.models.myPrayerModel
import com.perfect.islamyat.ui.AboutFragment
import com.perfect.islamyat.ui.ContactUsFragment
import com.perfect.islamyat.ui.PrayerSettingsFragment
import com.perfect.islamyat.utils.ApiInterface
import com.perfect.islamyat.utils.AppSettings
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var textView: TextView

    //    public final var dhuhrTime = "";
    var asrTime = ""
    lateinit var next_prayer: TextView
    lateinit var previous_prayer: TextView
    lateinit var next_prayer_time: TextView
    lateinit var previous_prayer_time: TextView
    lateinit var next_prayer_remaining: TextView
    lateinit var today_day: TextView
    lateinit var fajr_time: TextView
    lateinit var prayer_times_panel: LinearLayout
    lateinit var prayers_panel: LinearLayout
    lateinit var hijri_date_day: TextView
    lateinit var city_name_tv: TextView
    lateinit var city_name_tv2: TextView
    lateinit var settings_btn: ImageView
    lateinit var menu_btn: ImageView
    lateinit var hijri_date_month: TextView
    lateinit var hijri_date_year: TextView
    lateinit var gregorian_date_day: TextView
    lateinit var gregorian_date_month: TextView
    lateinit var gregorian_date_year: TextView
    lateinit var fajr_alarm: ImageView
    lateinit var shourouk_time: TextView
    lateinit var shourouk_alarm: ImageView
    lateinit var zuhr_alarm: ImageView
    lateinit var zuhr_time: TextView
    lateinit var asr_alarm: ImageView
    lateinit var asr_time: TextView
    lateinit var maghrib_alarm: ImageView
    lateinit var maghrib_time: TextView
    lateinit var isha_alarm: ImageView
    lateinit var isha_time: TextView
    lateinit var about_ll: LinearLayout
    lateinit var settings_ll: LinearLayout
    lateinit var progrsessBar: ProgressBar
    public var appPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("TAGTHomeFragment", "onCreate: ")

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        this.progrsessBar = root.findViewById(R.id.simpleProgressBar)
        this.textView = root.findViewById(R.id.text_home)

        appPreferences =
            activity?.getSharedPreferences("IslamyiatSettings", 0) // 0 - for private mode

        this.next_prayer = root.findViewById(R.id.next_prayer)
        this.previous_prayer = root.findViewById(R.id.previous_prayer)

        this.next_prayer_time = root.findViewById(R.id.next_prayer_time)
        this.previous_prayer_time = root.findViewById(R.id.previous_prayer_time)

        this.next_prayer_remaining = root.findViewById(R.id.next_prayer_remaining)
        prayer_times_panel = root.findViewById(R.id.prayer_times_panel)
        prayers_panel = root.findViewById(R.id.prayers_panel)
        this.today_day = root.findViewById(R.id.today_day)
        this.hijri_date_day = root.findViewById(R.id.hijri_date_day)
        this.city_name_tv = root.findViewById(R.id.city_name_tv)
        this.city_name_tv2 = root.findViewById(R.id.city_name_tv2)
        this.settings_btn = root.findViewById(R.id.settings_btn)
        this.menu_btn = root.findViewById(R.id.menu_btn)
        this.hijri_date_month = root.findViewById(R.id.hijri_date_month)
        this.hijri_date_year = root.findViewById(R.id.hijri_date_year)
        this.gregorian_date_day = root.findViewById(R.id.gregorian_date_day)
        this.gregorian_date_month = root.findViewById(R.id.gregorian_date_month)
        this.gregorian_date_year = root.findViewById(R.id.gregorian_date_year)
        this.fajr_time = root.findViewById(R.id.fajr_time)
        this.fajr_alarm = root.findViewById(R.id.fajr_alarm)
        this.shourouk_time = root.findViewById(R.id.shourouk_time)
        this.shourouk_alarm = root.findViewById(R.id.shourouk_alarm)
        this.zuhr_alarm = root.findViewById(R.id.zuhr_alarm)
        this.zuhr_time = root.findViewById(R.id.zuhr_time)
        this.asr_alarm = root.findViewById(R.id.asr_alarm)
        this.asr_time = root.findViewById(R.id.asr_time)
        this.maghrib_alarm = root.findViewById(R.id.maghrib_alarm)
        this.maghrib_time = root.findViewById(R.id.maghrib_time)
        this.isha_alarm = root.findViewById(R.id.isha_alarm)
        this.isha_time = root.findViewById(R.id.isha_time)
        this.settings_ll = root.findViewById(R.id.settings_ll)
        this.about_ll = root.findViewById(R.id.about_ll)
        // Instantiate the RequestQueue.


        // Set Calc Methods
        val settings = AppSettings.getInstance(context)
        settings.setCalcMethodFor(1, 1)
        settings.setCalcMethodFor(2, 2)
        settings.setCalcMethodFor(3, 3)
        settings.setCalcMethodFor(4, 4)
        settings.setCalcMethodFor(5, 5)

        (activity as MainActivity).homeFragment = this
        (activity as MainActivity).inHomeScreen = true
        (activity as MainActivity).nav_view.visibility = View.VISIBLE
        (activity as MainActivity).nav_view.getMenu().getItem(2).setChecked(true)
        (activity as MainActivity).fetchLocation()
//        val notifyIntent = Intent(context,NotificationReceiver::class.java)
        if ((activity as MainActivity).esteghfarAlarmOn()!!) {
            print("enterffff3")
            Log.d("enterffff3", "onCreateView: enterffff3")
            (activity as MainActivity).cancelEsteghfarAlarms()
            Log.d("TAGonCreateView", "onCreateView: ")
//            (activity as MainActivity).scheduleAlarm(
//                (activity as MainActivity).esteghfarAlarmSounds()!!.indexOf("1") + 1
//            )
        } else {
            (activity as MainActivity).cancelEsteghfarAlarms()
        }

        MainActivity.age = 26
//        MainActivity.mMyPrayerModel = (activity as MainActivity).retrofitFetchData()
//        Thread.sleep(10_000)  // wait for 1 second

        (activity as MainActivity).schedulePrayerTimesAlarms()
        getPrayerTimes()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.aladhan.com/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val apiInterface = retrofit.create(ApiInterface::class.java)
//
//        val timeStamp2 = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date())
//
//        val call: Call<myPrayerModel> = apiInterface.getTimings(
//            timeStamp2, 30.005493, 31.477898, 5
//        )
//
//        call.enqueue(object : Callback<myPrayerModel?> {
//            override fun onResponse(
//                call: Call<myPrayerModel?>,
//                response: Response<myPrayerModel?>
//            ) {
//                if (BuildConfig.DEBUG && response.body() == null) {
//                    error("Assertion failed")
//                }
//                MainActivity.mMyPrayerModel = response.body()!!
//                Log.d("TAGWWW=", "onResponse: " + MainActivity.mMyPrayerModel.data.timings.Fajr)
//                (activity as MainActivity).schedulePrayerTimesAlarms()
//                MainActivity.mMyPrayerModel.data.timings.Fajr = "14:35"
//                Log.d("TAGQQQQQ", "onCreateView: "+MainActivity.mMyPrayerModel.data.timings.Fajr)
//
//
//            }
//
//            override fun onFailure(call: Call<myPrayerModel?>, t: Throwable) {
//                Log.d("TAGtttt=", "onFailure: " + t.message)
//            }
//        })


//        (activity as MainActivity).schedulePrayerTimesAlarms()
//        alarmManager.set(
//            AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+20000,
//            pIntent(context,1)
        settings_ll.setOnClickListener {
            (activity as MainActivity).replaceFragments(PrayerSettingsFragment::class.java)
        }

        about_ll.setOnClickListener(View.OnClickListener {
            val dropDownMenu =
                PopupMenu(activity, menu_btn)
            dropDownMenu.getMenuInflater()
                .inflate(R.menu.home_menu, dropDownMenu.getMenu())
//            showMenu.setText("DropDown Menu")
            dropDownMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.menu_about -> {
                        (activity as MainActivity).replaceFragments(AboutFragment::class.java)
                    }
                    R.id.menu_contactus -> {
                        (activity as MainActivity).replaceFragments(ContactUsFragment::class.java)
                    }
                    R.id.menu_share -> {
                        try {
                            val shareIntent = Intent(Intent.ACTION_SEND)
                            shareIntent.type = "text/plain"
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "تطبيق إسلاميات")
                            var shareMessage =
                                "\nتحميل تطبيق إسلاميات\n\n"
                            shareMessage =
                                """
                            ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}                                
                                """.trimIndent()
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                            startActivity(Intent.createChooser(shareIntent, "choose one"))
                        } catch (e: Exception) {
                            //e.toString();
                        }
                    }
//                    Toast.makeText(
//                        activity,
//                        "You have clicked " + item!!.getTitle(),
//                        Toast.LENGTH_LONG
//                    ).show()
                }
                true
            })
            dropDownMenu.show()
        })
//        )
        return root
    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).nav_view.getMenu().getItem(2).setChecked(true)
        (activity as MainActivity).nav_view.visibility = View.VISIBLE

        Log.d("TAGResume", "onResume: ")
        /**
         * i added on 30/6/2021
         */
        getPrayerTimes()
    }

    fun getPrayerTimes() {
        if ((activity as MainActivity).mLastLocation != null) {

            Log.d("TAGLastLocNot", "getPrayerTimes: ")

            (activity as MainActivity).setLatitude((activity as MainActivity).mLastLocation!!.latitude.toFloat());
            (activity as MainActivity).setLongitude((activity as MainActivity).mLastLocation!!.longitude.toFloat());
            try {
                val geocoder = Geocoder(activity, Locale("ar"))
                val addresses: List<Address> = geocoder.getFromLocation(
                    (activity as MainActivity).mLastLocation!!.latitude,
                    (activity as MainActivity).mLastLocation!!.longitude,
                    1
                )
                val cityName: String =
                    addresses[0].adminArea.replace("منطقة", "").replace("محافظة", "")
                city_name_tv.text = cityName
                city_name_tv2.text = cityName
                (activity as MainActivity).setCityName(cityName);

            } catch (e: java.lang.Exception) {
                Log.d("TAGCatch", "getPrayerTimes: " + 1)
                var besoCity = appPreferences!!.getString("besoCityName", "الرياض")

                city_name_tv.text = besoCity
                city_name_tv2.text = besoCity
            }
//            var prayer_time = PrayTime.TIME_24
//            if (!(activity as MainActivity).prayerTimes24()!!) {
//                prayer_time = PrayTime.TIME_12
//            }
//            val prayerTimes = PrayTime.getPrayerTimes(
//                activity,
//                (activity as MainActivity).prayerTimesCalcMethod()!!,
//                (activity as MainActivity).mLastLocation!!.latitude,
//                (activity as MainActivity).mLastLocation!!.longitude,
//                PrayTime.TIME_24
//            )

//            val displayPrayerTimes = PrayTime.getPrayerTimes(
//                activity,
//                (activity as MainActivity).prayerTimesCalcMethod()!!,
//                (activity as MainActivity).mLastLocation!!.latitude,
//                (activity as MainActivity).mLastLocation!!.longitude,
//                prayer_time
//            )

//            fajr_time.text = displayPrayerTimes.get("Fajr")//Fajr.substring(0,5)
//            fajr_time.text = displayPrayerTimes.get("Fajr")//Fajr.substring(0,5)
//            shourouk_time.text = displayPrayerTimes.get("Sunrise")//.Sunrise.substring(0,5)
//            zuhr_time.text = displayPrayerTimes.get("Dhuhr")//Dhuhr.substring(0,5)
//            asr_time.text = displayPrayerTimes.get("Asr")//Asr.substring(0,5)
//            maghrib_time.text = displayPrayerTimes.get("Maghrib")//.Maghrib.substring(0,5)
//            isha_time.text = displayPrayerTimes.get("Isha")//.Isha.substring(0,5)

        } else {
            var besoCity = appPreferences!!.getString("besoCityName", "الرياض")
            city_name_tv.text = besoCity
            city_name_tv2.text = besoCity
        }


        val internetConnection = (activity as MainActivity).checkInternetConnection()
        Log.d("TAGItern", "getPrayerTimes: " + internetConnection)
        if (internetConnection) {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.aladhan.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiInterface = retrofit.create(ApiInterface::class.java)

            val timeStamp2 = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date())
            Log.d("TAGtimeStamp2", "getPrayerTimes: " + timeStamp2)
            var besoMethod = appPreferences!!.getInt("prayerTimesCalcMethod", 4)

            var besoLastlatitude = appPreferences!!.getFloat("Lastlatitude", 24.774265f).toDouble()
            var besoLastLongituide =
                appPreferences!!.getFloat("LastLongituide", 46.738586f).toDouble()

            Log.d("TAGtimeStamp2besoLatude", "getPrayerTimes: " + besoLastlatitude)

            val call: Call<myPrayerModel> = apiInterface.getTimings(
                timeStamp2, besoLastlatitude, besoLastLongituide, besoMethod
            )

            call.enqueue(object : Callback<myPrayerModel?> {
                override fun onResponse(
                    call: Call<myPrayerModel?>,
                    response: Response<myPrayerModel?>
                ) {
                    if (BuildConfig.DEBUG && response.body() == null) {
                        error("Assertion failed")
                    }
                    MainActivity.mMyPrayerModel = response.body()!!
                    Log.d(
                        "TAGWWW=",
                        "onResponse: " + MainActivity.mMyPrayerModel.data.timings.Fajr
                    )

                    var besoFajr: String? = MainActivity.mMyPrayerModel.data.timings.Fajr
                    var besoSunrise: String? = MainActivity.mMyPrayerModel.data.timings.Sunrise
                    var besoDhuhr: String? = MainActivity.mMyPrayerModel.data.timings.Dhuhr
                    var besoAsr: String = MainActivity.mMyPrayerModel.data.timings.Asr
                    var besoMaghrib: String = MainActivity.mMyPrayerModel.data.timings.Maghrib
                    var besoIsha: String = MainActivity.mMyPrayerModel.data.timings.Isha


                    fajr_time.text = besoFajr
                    shourouk_time.text = besoSunrise
                    zuhr_time.text = besoDhuhr


                    displayTimesIn12_24(besoAsr,besoMaghrib,besoIsha)




                    Log.d(
                        "TAGQQQQQ",
                        "onCreateView: " + MainActivity.mMyPrayerModel.data.timings.Fajr
                    )


                }

                override fun onFailure(call: Call<myPrayerModel?>, t: Throwable) {
                    Log.d("TAGtttt=", "onFailure: " + t.message)

                    val besoFajr: String? = appPreferences?.getString("beso_Fajr", "03:07")
                    val besoSunrise: String? = appPreferences?.getString("beso_Sunrise", "04:53")
                    val besoDhuhr: String? = appPreferences?.getString("beso_Dhuhr", "11:55")
                    val besoAsr: String = appPreferences!!.getString("beso_Asr", "15:30")!!
                    val besoMaghrib: String = appPreferences!!.getString("beso_Maghrib", "18:57")!!
                    val besoIsha: String = appPreferences!!.getString("beso_Isha", "20:30")!!

                    Log.d("TAGDDDDDDDDDD", "getPrayerTimes: " + besoFajr)

                    fajr_time.text = besoFajr
                    shourouk_time.text = besoSunrise
                    zuhr_time.text = besoDhuhr

                    displayTimesIn12_24(besoAsr,besoMaghrib,besoIsha)

//                    asr_time.text = besoAsr
//                    maghrib_time.text = besoMaghrib
//                    isha_time.text = besoIsha

                }
            })


        } else {
            appPreferences?.getString("esteghfarAlarmStartTime", "")

            val besoFajr: String? = appPreferences?.getString("beso_Fajr", "03:07")
            val besoSunrise: String? = appPreferences?.getString("beso_Sunrise", "04:53")
            val besoDhuhr: String? = appPreferences?.getString("beso_Dhuhr", "11:55")
            val besoAsr: String = appPreferences!!.getString("beso_Asr", "15:30")!!
            val besoMaghrib: String = appPreferences!!.getString("beso_Maghrib", "18:57")!!
            val besoIsha: String = appPreferences!!.getString("beso_Isha", "20:30")!!

            Log.d("TAGDDDDDDDDDD", "getPrayerTimes: " + besoFajr)

            fajr_time.text = besoFajr
            shourouk_time.text = besoSunrise
            zuhr_time.text = besoDhuhr

            displayTimesIn12_24(besoAsr,besoMaghrib,besoIsha)


//            asr_time.text = besoAsr
//            maghrib_time.text = besoMaghrib
//            isha_time.text = besoIsha

        }


        val gCal = GregorianCalendar(Calendar.getInstance().timeZone)
        val uCal: Calendar = UmmalquraCalendar()
        uCal.time = gCal.time
        hijri_date_day.text = uCal.get(Calendar.DAY_OF_MONTH).toString()
        hijri_date_month.text = uCal.getDisplayName(
            Calendar.MONTH,
            Calendar.LONG,
            Locale("ar")
        )
        hijri_date_year.text = uCal.get(Calendar.YEAR).toString() + " هـ"
        gregorian_date_day.text = gCal.get(Calendar.DAY_OF_MONTH).toString()
        gregorian_date_month.text =
            gCal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("ar"))
        gregorian_date_year.text = gCal.get(Calendar.YEAR).toString() + " م"
        today_day.text = uCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale("ar"))

        prayers_panel.visibility = View.VISIBLE
        prayer_times_panel.visibility = View.VISIBLE
        if ((activity as MainActivity).prayerTimesAlarmFajrOn()!!) {
            fajr_alarm.setBackgroundResource(R.drawable.soundon)
            fajr_alarm.tag = 1
        } else {
            fajr_alarm.setBackgroundResource(R.drawable.soundoff)
            fajr_alarm.tag = 0
        }
        if ((activity as MainActivity).prayerTimesAlarmSunriseOn()!!) {
            shourouk_alarm.setBackgroundResource(R.drawable.soundon)
            shourouk_alarm.tag = 1
        } else {
            shourouk_alarm.setBackgroundResource(R.drawable.soundoff)
            shourouk_alarm.tag = 0
        }
        if ((activity as MainActivity).prayerTimesAlarmDhuhrOn()!!) {
            zuhr_alarm.setBackgroundResource(R.drawable.soundon)
            zuhr_alarm.tag = 1
        } else {
            zuhr_alarm.setBackgroundResource(R.drawable.soundoff)
            zuhr_alarm.tag = 0
        }
        if ((activity as MainActivity).prayerTimesAlarmAsrOn()!!) {
            asr_alarm.setBackgroundResource(R.drawable.soundon)
            asr_alarm.tag = 1
        } else {
            asr_alarm.setBackgroundResource(R.drawable.soundoff)
            asr_alarm.tag = 0
        }
        if ((activity as MainActivity).prayerTimesAlarmMaghrebOn()!!) {
            maghrib_alarm.setBackgroundResource(R.drawable.soundon)
            maghrib_alarm.tag = 1
        } else {
            maghrib_alarm.setBackgroundResource(R.drawable.soundoff)
            maghrib_alarm.tag = 0
        }
        if ((activity as MainActivity).prayerTimesAlarmIshaOn()!!) {
            isha_alarm.setBackgroundResource(R.drawable.soundon)
            isha_alarm.tag = 1
        } else {
            isha_alarm.setBackgroundResource(R.drawable.soundoff)
            isha_alarm.tag = 0
        }
        fajr_alarm.setOnClickListener {
            if (it.tag == 1) {
                it.setBackgroundResource(R.drawable.soundoff)
                (activity as MainActivity).setPrayerTimesAlarmOn(1, false)
                it.tag = 0
            } else {
                it.setBackgroundResource(R.drawable.soundon)
                (activity as MainActivity).setPrayerTimesAlarmOn(1, true)
                it.tag = 1
            }
        }
        shourouk_alarm.setOnClickListener {
            if (it.tag == 1) {
                it.setBackgroundResource(R.drawable.soundoff)
                (activity as MainActivity).setPrayerTimesAlarmOn(2, false)
                it.tag = 0
            } else {
                it.setBackgroundResource(R.drawable.soundon)
                (activity as MainActivity).setPrayerTimesAlarmOn(2, true)
                it.tag = 1
            }
        }
        zuhr_alarm.setOnClickListener {
            if (it.tag == 1) {
                it.setBackgroundResource(R.drawable.soundoff)
                (activity as MainActivity).setPrayerTimesAlarmOn(3, false)
                it.tag = 0
            } else {
                it.setBackgroundResource(R.drawable.soundon)
                (activity as MainActivity).setPrayerTimesAlarmOn(3, true)
                it.tag = 1
            }
        }
        asr_alarm.setOnClickListener {
            if (it.tag == 1) {
                it.setBackgroundResource(R.drawable.soundoff)
                (activity as MainActivity).setPrayerTimesAlarmOn(4, false)
                it.tag = 0
            } else {
                it.setBackgroundResource(R.drawable.soundon)
                (activity as MainActivity).setPrayerTimesAlarmOn(4, true)
                it.tag = 1
            }
        }
        maghrib_alarm.setOnClickListener {
            if (it.tag == 1) {
                it.setBackgroundResource(R.drawable.soundoff)
                (activity as MainActivity).setPrayerTimesAlarmOn(5, false)
                it.tag = 0
            } else {
                it.setBackgroundResource(R.drawable.soundon)
                (activity as MainActivity).setPrayerTimesAlarmOn(5, true)
                it.tag = 1
            }
        }
        isha_alarm.setOnClickListener {
            if (it.tag == 1) {
                it.setBackgroundResource(R.drawable.soundoff)
                (activity as MainActivity).setPrayerTimesAlarmOn(6, false)
                it.tag = 0
            } else {
                it.setBackgroundResource(R.drawable.soundon)
                (activity as MainActivity).setPrayerTimesAlarmOn(6, true)
                it.tag = 1
            }
        }
        next_prayer.text = "الفجر"
        getNextPrayer(/*prayerTimes,*/ next_prayer, next_prayer_remaining)

    }

    private fun displayTimesIn12_24(besoAsr: String, besoMaghrib: String, besoIsha: String) {
        val besoTime24: Boolean = appPreferences!!.getBoolean("prayerTimes24", true)
        if (besoTime24) {
            asr_time.text = besoAsr
            maghrib_time.text = besoMaghrib
            isha_time.text = besoIsha
        } else {
            /** to get the date always in english language */
            val locale = Locale("en")
            val _24HourSDF = SimpleDateFormat("HH:mm", locale)
            val _12HourSDF = SimpleDateFormat("hh:mm", locale)

            var _24HourDt: Date = _24HourSDF.parse(besoAsr)!!
            var asrTime = _12HourSDF.format(_24HourDt)

            _24HourDt = _24HourSDF.parse(besoMaghrib)!!
            var maghribTime = _12HourSDF.format(_24HourDt)

            _24HourDt = _24HourSDF.parse(besoIsha)!!
            var ishaTime = _12HourSDF.format(_24HourDt)


            Log.d("TAGbesoTime12", "onResponse: " + _12HourSDF.format(_24HourDt))
            asr_time.text = asrTime
            maghrib_time.text = maghribTime
            isha_time.text = ishaTime

        }

    }

    fun getNextPrayer(
        /*: LinkedHashMap<String, String>,*/
        next_prayer: TextView,
        next_prayer_remaining: TextView
    ) {
        val c = Calendar.getInstance()
        val currentHour = c.get(Calendar.HOUR_OF_DAY)
        val currentMin = c.get(Calendar.MINUTE)
        val start = Time(currentHour, currentMin, 0)
        Log.d("TAGStart ", "getNextPrayer: " + start)
//        val fajrTime = Time(
//            prayerTimes.get("Fajr")!!.substring(0, prayerTimes.get("Fajr")!!.indexOf(":")).toInt(),
//            prayerTimes.get("Fajr")!!.substring(
//                prayerTimes.get("Fajr")!!.indexOf(":") + 1,
//                prayerTimes.get("Fajr")!!.indexOf(":") + 3
//            ).toInt(),
//            0
//        )
//
//        val dhuhrTime = Time(
//            prayerTimes.get("Dhuhr")!!.substring(0, prayerTimes.get("Dhuhr")!!.indexOf(":"))
//                .toInt(),
//            prayerTimes.get("Dhuhr")!!.substring(
//                prayerTimes.get("Dhuhr")!!.indexOf(":") + 1,
//                prayerTimes.get("Dhuhr")!!.indexOf(":") + 3
//            ).toInt(),
//            0
//        )
//        val asrTime = Time(
//            prayerTimes.get("Asr")!!.substring(0, prayerTimes.get("Asr")!!.indexOf(":")).toInt(),
//            prayerTimes.get("Asr")!!.substring(
//                prayerTimes.get("Asr")!!.indexOf(":") + 1,
//                prayerTimes.get("Asr")!!.indexOf(":") + 3
//            ).toInt(),
//            0
//        )
//        val sunriseTime = Time(
//            prayerTimes.get("Sunrise")!!.substring(0, prayerTimes.get("Sunrise")!!.indexOf(":"))
//                .toInt(),
//            prayerTimes.get("Sunrise")!!.substring(
//                prayerTimes.get("Sunrise")!!.indexOf(":") + 1,
//                prayerTimes.get("Sunrise")!!.indexOf(":") + 3
//            ).toInt(),
//            0
//        )
//        val maghribTime = Time(
//            prayerTimes.get("Maghrib")!!.substring(0, prayerTimes.get("Maghrib")!!.indexOf(":"))
//                .toInt(),
//            prayerTimes.get("Maghrib")!!.substring(
//                prayerTimes.get("Maghrib")!!.indexOf(":") + 1,
//                prayerTimes.get("Maghrib")!!.indexOf(":") + 3
//            ).toInt(),
//            0
//        )
//        val ishaTime = Time(
//            prayerTimes.get("Isha")!!.substring(0, prayerTimes.get("Isha")!!.indexOf(":")).toInt(),
//            prayerTimes.get("Isha")!!.substring(
//                prayerTimes.get("Isha")!!.indexOf(":") + 1,
//                prayerTimes.get("Isha")!!.indexOf(":") + 3
//            ).toInt(),
//            0
//        )

        val prefs =
            MainActivity.context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE)

        var besoFajr: String? = prefs?.getString("beso_Fajr", "03:07")
        var besoSunrise: String? = prefs?.getString("beso_Sunrise", "04:53")
        var besoDhuhr: String? = prefs?.getString("beso_Dhuhr", "11:55")
        var besoAsr: String? = prefs?.getString("beso_Asr", "15:30")
        var besoMaghrib: String? = prefs?.getString("beso_Maghrib", "18:57")
        var besoIsha: String? = prefs?.getString("beso_Isha", "20:30")


        val fajrTime = Time(
            besoFajr!!.substring(0, besoFajr.indexOf(":")).toInt(),
            besoFajr.substring(
                besoFajr.indexOf(":") + 1,
                besoFajr.indexOf(":") + 3
            ).toInt(),
            0
        )

        val sunriseTime = Time(
            besoSunrise!!.substring(0, besoSunrise.indexOf(":")).toInt(),
            besoSunrise.substring(
                besoSunrise.indexOf(":") + 1,
                besoSunrise.indexOf(":") + 3
            ).toInt(),
            0
        )

        val dhuhrTime = Time(
            besoDhuhr!!.substring(0, besoDhuhr.indexOf(":")).toInt(),
            besoDhuhr.substring(
                besoDhuhr.indexOf(":") + 1,
                besoDhuhr.indexOf(":") + 3
            ).toInt(),
            0
        )

        val asrTime = Time(
            besoAsr!!.substring(0, besoAsr.indexOf(":")).toInt(),
            besoAsr.substring(
                besoAsr.indexOf(":") + 1,
                besoAsr.indexOf(":") + 3
            ).toInt(),
            0
        )

        val maghribTime = Time(
            besoMaghrib!!.substring(0, besoMaghrib.indexOf(":")).toInt(),
            besoMaghrib.substring(
                besoMaghrib.indexOf(":") + 1,
                besoMaghrib.indexOf(":") + 3
            ).toInt(),
            0
        )

        val ishaTime = Time(
            besoIsha!!.substring(0, besoIsha.indexOf(":")).toInt(),
            besoIsha.substring(
                besoIsha.indexOf(":") + 1,
                besoIsha.indexOf(":") + 3
            ).toInt(),
            0
        )


        val remainingTimeFajr = difference(start, fajrTime)
        val remainingTimeDhuhr = difference(start, dhuhrTime)
        val remainingTimeSunrise = difference(start, sunriseTime)
        val remainingTimeAsr = difference(start, asrTime)
        val remainingTimeMaghrib = difference(start, maghribTime)
        val remainingTimeIsha = difference(start, ishaTime)
        var maxdiff = 100;

        if (start >= fajrTime && start < sunriseTime) {
            next_prayer.text = "الشروق"
            next_prayer_time.text = sunriseTime.toString()
            previous_prayer.text = "الفجر"
            previous_prayer_time.text = fajr_time.toString();
            next_prayer_remaining.text = remainingTimeSunrise.toString().substring(0, 5)

//            val date1: Date? = SimpleDateFormat("hh:mm").parse(
//                remainingTimeSunrise.toString().substring(
//                    0,
//                    5
//                )
//            )
//            var timeInMilliseconds: Long = date1!!.time
//            val alltime = difference(sunriseTime, fajrTime)!!.time
//            Log.d("TARRRRRRRRRRRG", "getNextPrayer: ${-timeInMilliseconds}")
//            Log.d("TARRRRRRRRRRRG2", "getNextPrayer: ${-alltime}")
//            progrsessBar.max = abs(alltime).toInt()
//            progrsessBar.progress = abs(timeInMilliseconds).toInt()


            val const1 = difference(fajrTime, sunriseTime).toString().substring(0, 2).toInt() * 60 +
                    difference(fajrTime, sunriseTime).toString().substring(3, 5).toInt()

            val const2 = remainingTimeSunrise.toString().substring(0, 2).toInt() * 60 +
                    remainingTimeSunrise.toString().substring(3, 5).toInt()

            progrsessBar.max = const1
            progrsessBar.progress = const1 - const2


        } else if (start >= sunriseTime && start < dhuhrTime) {
            next_prayer.text = "الظهر"
            previous_prayer.text = "الشروق"
            next_prayer_time.text = dhuhrTime.toString().substring(0, 5);
            previous_prayer_time.text = sunriseTime.toString().substring(0, 5);
            next_prayer_remaining.text = remainingTimeDhuhr.toString().substring(0, 5)

//            Log.d("TARRRRRRRRRRRG", "getNextPrayer: $date1")
//            val date1: Date? = SimpleDateFormat("hh:mm").parse(
//                remainingTimeDhuhr.toString().substring(
//                    0,
//                    5
//                )
//            )
//            var timeInMilliseconds: Long = date1!!.time
//            val alltime = difference(sunriseTime, dhuhrTime)!!.time
//            Log.d("TARRRRRRRRRRRG00", "getNextPrayer: ${-timeInMilliseconds}")
//            Log.d("TARRRRRRRRRRRG002", "getNextPrayer: ${-alltime}")
//            progrsessBar.max = abs(alltime).toInt()
//            progrsessBar.progress = abs(timeInMilliseconds).toInt()


            val const1 =
                difference(sunriseTime, dhuhrTime).toString().substring(0, 2).toInt() * 60 +
                        difference(sunriseTime, dhuhrTime).toString().substring(3, 5).toInt()

            val const2 = remainingTimeDhuhr.toString().substring(0, 2).toInt() * 60 +
                    remainingTimeDhuhr.toString().substring(3, 5).toInt()

            progrsessBar.max = const1
            progrsessBar.progress = const1 - const2


        }


        /****/

        else if (start >= dhuhrTime && start < asrTime) {
            next_prayer.text = "العصر"
            previous_prayer.text = "الظهر"
            next_prayer_time.text = asrTime.toString().substring(0, 5);
            previous_prayer_time.text = dhuhrTime.toString().substring(0, 5);
            next_prayer_remaining.text = remainingTimeAsr.toString().substring(0, 5)


//            val sdf = SimpleDateFormat("dd-M-yyyy hh:mm:ss")


//            val date1: Date? = SimpleDateFormat("hh:mm yyyy").parse(
//                remainingTimeAsr.toString().substring(0, 5) + " 2021"
//            )
//
//            val date2: Date? = SimpleDateFormat("hh:mm yyyy").parse(
//                difference(dhuhrTime, asrTime).toString().substring(0, 5) + " 2021"
//            )
//
//            val date = "Tue Apr 23 16:08:28 GMT+05:30 2013"
//
//            val formatter: DateTimeFormatter =
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
//                } else {
//                    TODO("VERSION.SDK_INT < O")
//                }
////            val localDate: LocalDateTime = LocalDateTime.parse(date, formatter)
////            val timeInMilliseconds2 = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
////            Log.d("TAGAAA", "Date in milli :: FOR API >= 26 >>> " + timeInMilliseconds2);
//
//
//            Log.d(
//                "TAGremainingTimeAsr",
//                "getNextPrayer: " + remainingTimeAsr.toString().substring(0, 5)
//            )
//            Log.d("TAGstartTimeAsr", "getNextPrayer: " + start + " data1 " + date1)
//            Log.d(
//                "TAGDiffre",
//                "getNextPrayer: " + difference(dhuhrTime, asrTime).toString().substring(0, 5)
//            )
//
//            Log.d("TAGAsrToInt", "getNextPrayer: "+remainingTimeAsr.toString().substring(0,2).toInt())
//            Log.d("TAGAsrToInt10", "getNextPrayer: "+(remainingTimeAsr.toString().substring(0,2).toInt()*5)+131)


            val const1 = difference(dhuhrTime, asrTime).toString().substring(0, 2).toInt() * 60 +
                    difference(dhuhrTime, asrTime).toString().substring(3, 5).toInt()

            val const2 = remainingTimeAsr.toString().substring(0, 2).toInt() * 60 +
                    remainingTimeAsr.toString().substring(3, 5).toInt()

            Log.d("TAGconst1", "getNextPrayer: " + const1 + " --- " + const2)

//            var timeInMilliseconds: Long = date1!!.time
////            var timeInMilliseconds2: Long = difference(start,asrTime)!!.time
//            val alltime = difference(dhuhrTime, asrTime)!!.time
//            Log.d("TARRRRRRRRRRRG", "getNextPrayer: ${-timeInMilliseconds}")
//            Log.d("TARRRRRRRRRRRG2", "getNextPrayer: ${-alltime}")
//            Log.d("TARRRRRRRRRRRG3", "getNextPrayer: ${difference(dhuhrTime, asrTime)}")
//            Log.d("TARRRRRRRRRRRG4", "getNextPrayer: ${difference(dhuhrTime, asrTime).toString()}")
//            Log.d("TARRRRRRRRRRRG5", "getNextPrayer: ${date2!!.time}")
//
//            var timeInMilliseconds3 = abs(date2.time) - abs(timeInMilliseconds)
//            Log.d("TAGtimeInMilliseconds3", "getNextPrayer: "+timeInMilliseconds3)
//            val prog: Double = abs(timeInMilliseconds3) / abs(date2.time) * 100.0
//                    Log.d("TAGPrecentage", "getNextPrayer: " + prog)
//
////            progrsessBar.max = abs(date2.time).toFloat().roundToInt()
////            progrsessBar.progress = abs(timeInMilliseconds).toFloat().roundToInt()
//
//            var prog2 = const2/const1 *100
//
//            Log.d("TARRRRRRRRRRRG5", "getNextPrayer: $prog2 "+ "--==--" +(const1-const2)/const1*100)


            progrsessBar.max = const1
            progrsessBar.progress = const1 - const2

        }


        /****/


        /***/


        else if (start >= asrTime && start < maghribTime) {
            Log.d("TAG", "getNextPrayer: ")
            next_prayer.text = "المغرب"
            previous_prayer.text = "العصر"
            next_prayer_time.text = maghribTime.toString().substring(0, 5);
            previous_prayer_time.text = asrTime.toString().substring(0, 5);
            next_prayer_remaining.text = remainingTimeMaghrib.toString().substring(0, 5)

//            val date1: Date? = SimpleDateFormat("hh:mm").parse(
//                remainingTimeMaghrib.toString().substring(
//                    0,
//                    5
//                )
//            )
//            var timeInMilliseconds: Long = date1!!.time
//            val alltime = difference(asrTime, maghribTime)!!.time
//            Log.d(
//                "TARRRRRRRRRRRG3", "getNextPrayer: ${
//                    abs(timeInMilliseconds)
//                }"
//            )
//            Log.d("TARRRRRRRRRRRG4", "getNextPrayer: ${alltime}")
//            progrsessBar.max = abs(alltime).toInt()
//            progrsessBar.progress = abs(timeInMilliseconds).toInt()

            val const1 = difference(asrTime, maghribTime).toString().substring(0, 2).toInt() * 60 +
                    difference(asrTime, maghribTime).toString().substring(3, 5).toInt()

            val const2 = remainingTimeMaghrib.toString().substring(0, 2).toInt() * 60 +
                    remainingTimeMaghrib.toString().substring(3, 5).toInt()

            progrsessBar.max = const1
            progrsessBar.progress = const1 - const2


        }

        /***/

        else if (start >= maghribTime && start < ishaTime) {
            next_prayer.text = "العشاء"
            previous_prayer.text = "المغرب"
            next_prayer_time.text = ishaTime.toString().substring(0, 5);
            previous_prayer_time.text = maghribTime.toString().substring(0, 5);
            next_prayer_remaining.text = remainingTimeIsha.toString().substring(0, 5)

//            val date1: Date? = SimpleDateFormat("hh:mm").parse(
//                remainingTimeIsha.toString().substring(
//                    0,
//                    5
//                )
//            )
//            var timeInMilliseconds: Long = date1!!.time
//            val alltime = difference(maghribTime, ishaTime)!!.time
//            Log.d("TARRRRRRRRRRRG", "getNextPrayer: ${-timeInMilliseconds}")
//            Log.d("TARRRRRRRRRRRG2", "getNextPrayer: ${-alltime}")
//            progrsessBar.max = abs(alltime).toInt()
//            progrsessBar.progress = abs(timeInMilliseconds).toInt()


            val const1 = difference(maghribTime, ishaTime).toString().substring(0, 2).toInt() * 60 +
                    difference(maghribTime, ishaTime).toString().substring(3, 5).toInt()

            val const2 = remainingTimeIsha.toString().substring(0, 2).toInt() * 60 +
                    remainingTimeIsha.toString().substring(3, 5).toInt()

            progrsessBar.max = const1
            progrsessBar.progress = const1 - const2


        } else {
            next_prayer.text = "الفجر"
            previous_prayer.text = "العشاء"
            next_prayer_time.text = fajrTime.toString().substring(0, 5);
            previous_prayer_time.text = sunriseTime.toString().substring(0, 5);
            next_prayer_remaining.text = remainingTimeFajr.toString().substring(0, 5)
            progrsessBar.setProgress(10)

//            val date1: Date? = SimpleDateFormat("hh:mm").parse(
//                remainingTimeFajr.toString().substring(
//                    0,
//                    5
//                )
//            )
//            var timeInMilliseconds: Long = date1!!.time
//            val alltime = difference(ishaTime, fajrTime)!!.time
//            Log.d("TARRRRRRRRRRRG", "getNextPrayer: ${-timeInMilliseconds}")
//            Log.d("TARRRRRRRRRRRG2", "getNextPrayer: ${-alltime}")
//            progrsessBar.max = abs(alltime).toInt()
//            progrsessBar.progress = abs(timeInMilliseconds).toInt()

            val const1 = difference(ishaTime, fajrTime).toString().substring(0, 2).toInt() * 60 +
                    difference(ishaTime, fajrTime).toString().substring(3, 5).toInt()

            val const2 = remainingTimeFajr.toString().substring(0, 2).toInt() * 60 +
                    remainingTimeFajr.toString().substring(3, 5).toInt()

            progrsessBar.max = const1
            progrsessBar.progress = const1 - const2

        }


    }

    /*
//    fun getNextPrayer (prayerTimes: LinkedHashMap<String,String>) {
//        val c = Calendar.getInstance()
//        val currentHour = c.get(Calendar.HOUR_OF_DAY)
//        val currentMin = c.get(Calendar.MINUTE)
//        val start = Time(currentHour, currentMin, 0)
//        var stop = Time(prayerTimes.get("Fajr")!!.substring(0,2).toInt(),prayerTimes.get("Fajr")!!.substring(3,5).toInt(),0)
//
//        if (currentHour < prayerTimes.Fajr.substring(0,2).toInt()) {
//            next_prayer.text = "الفجر"
//        } else if (currentHour <= prayerTimes.Sunrise.substring(0,2).toInt() && currentHour >= prayerTimes.Fajr.substring(0,2).toInt()) {
//            if (currentHour==prayerTimes.Sunrise.substring(0,2).toInt() && currentHour==prayerTimes.Fajr.substring(0,2).toInt()) {
//                if (currentMin < prayerTimes.Fajr.substring(3,5).toInt()) {
//                    next_prayer.text = "الفجر"
//                    next_prayer_remaining.text = (prayerTimes.Fajr.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Fajr.substring(3,5).toInt() - currentMin).toString()
//                } else {
//                    next_prayer.text = "الشروق"
//                    next_prayer_remaining.text = (prayerTimes.Sunrise.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Sunrise.substring(3,5).toInt() - currentMin).toString()
//                }
//            } else {
//                next_prayer.text = "الشروق"
//                next_prayer_remaining.text = (prayerTimes.Sunrise.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Sunrise.substring(3,5).toInt() - currentMin).toString()
//            }
//        } else if (currentHour <= prayerTimes.Dhuhr.substring(0,2).toInt() && currentHour >= prayerTimes.Sunrise.substring(0,2).toInt()) {
//            if (currentHour==prayerTimes.Dhuhr.substring(0,2).toInt() && currentHour==prayerTimes.Sunrise.substring(0,2).toInt()) {
//                if (currentMin < prayerTimes.Sunrise.substring(3,5).toInt()) {
//                    next_prayer.text = "الشروق"
//                    next_prayer_remaining.text = (prayerTimes.Sunrise.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Sunrise.substring(3,5).toInt() - currentMin).toString()
//                } else {
//                    next_prayer.text = "الظهر"
//                    next_prayer_remaining.text = (prayerTimes.Dhuhr.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Dhuhr.substring(3,5).toInt() - currentMin).toString()
//                }
//            } else {
//                next_prayer.text = "الظهر"
//                next_prayer_remaining.text = (prayerTimes.Dhuhr.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Dhuhr.substring(3,5).toInt() - currentMin).toString()
//            }
//        } else if (currentHour <= prayerTimes.Asr.substring(0,2).toInt() && currentHour >= prayerTimes.Dhuhr.substring(0,2).toInt()) {
//            if (currentHour==prayerTimes.Asr.substring(0,2).toInt() && currentHour==prayerTimes.Dhuhr.substring(0,2).toInt()) {
//                if (currentMin < prayerTimes.Dhuhr.substring(3,5).toInt()) {
//                    next_prayer.text = "الظهر"
//                    next_prayer_remaining.text = (prayerTimes.Dhuhr.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Dhuhr.substring(3,5).toInt() - currentMin).toString()
//                } else {
//                    next_prayer.text = "العصر"
//                    next_prayer_remaining.text = (prayerTimes.Asr.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Asr.substring(3,5).toInt() - currentMin).toString()
//                }
//            } else {
//                next_prayer.text = "العصر"
//                next_prayer_remaining.text = (prayerTimes.Asr.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Asr.substring(3,5).toInt() - currentMin).toString()
//            }
//        } else if (currentHour <= prayerTimes.Maghrib.substring(0,2).toInt() && currentHour >= prayerTimes.Asr.substring(0,2).toInt()) {
//            if (currentHour==prayerTimes.Maghrib.substring(0,2).toInt() && currentHour==prayerTimes.Asr.substring(0,2).toInt()) {
//                if (currentMin < prayerTimes.Asr.substring(3,5).toInt()) {
//                    next_prayer.text = "العصر"
//                    next_prayer_remaining.text = (prayerTimes.Asr.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Asr.substring(3,5).toInt() - currentMin).toString()
//                } else {
//                    next_prayer.text = "المغرب"
//                    next_prayer_remaining.text = (prayerTimes.Maghrib.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Maghrib.substring(3,5).toInt() - currentMin).toString()
//                }
//            } else {
//                next_prayer.text = "المغرب"
//                next_prayer_remaining.text = (prayerTimes.Maghrib.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Maghrib.substring(3,5).toInt() - currentMin).toString()
//            }
//        } else if (currentHour <= prayerTimes.Isha.substring(0,2).toInt() && currentHour >= prayerTimes.Maghrib.substring(0,2).toInt()) {
//            if (currentHour==prayerTimes.Isha.substring(0,2).toInt() && currentHour==prayerTimes.Maghrib.substring(0,2).toInt()) {
//                if (currentMin < prayerTimes.Maghrib.substring(3,5).toInt()) {
//                    next_prayer.text = "المغرب"
//                    next_prayer_remaining.text = (prayerTimes.Maghrib.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Maghrib.substring(3,5).toInt() - currentMin).toString()
//                } else {
//                    next_prayer.text = "العشاء"
//                    next_prayer_remaining.text = (prayerTimes.Isha.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Isha.substring(3,5).toInt() - currentMin).toString()
//                }
//            } else {
//                next_prayer.text = "العشاء"
//                (prayerTimes.Isha.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Isha.substring(3,5).toInt() - currentMin).toString()
//            }
//        } else if (currentHour > prayerTimes.Isha.substring(0,2).toInt()) {
//            next_prayer.text = "الفجر"
//            (prayerTimes.Midnight.substring(0,2).toInt() - currentHour).toString() + ":" + (prayerTimes.Midnight.substring(3,5).toInt() - currentMin).toString()
//        }
//        next_prayer_remaining.text = difference(start,stop).toString()
//        next_prayer_remaining.visibility = View.GONE
//        next_prayer_remaining.text = difference(start,stop).toString()
//    }
*/

    fun difference(start: Time, stop: Time): Time? {
        val diff = Time(0, 0, 0)

        // if start second is greater
        // convert minute of stop into seconds
        // and add seconds to stop second
        if (start.seconds > stop.seconds) {
            --stop.minutes
            stop.seconds += 60
        }
        diff.seconds = stop.seconds - start.seconds

        // if start minute is greater
        // convert stop hour into minutes
        // and add minutes to stop minutes
        if (start.minutes > stop.minutes) {
            --stop.hours
            stop.minutes += 60
        }
        diff.minutes = stop.minutes - start.minutes
        diff.hours = stop.hours - start.hours

        // return the difference time
        return diff
    }
}