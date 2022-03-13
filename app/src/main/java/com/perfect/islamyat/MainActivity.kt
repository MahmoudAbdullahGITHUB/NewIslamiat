package com.perfect.islamyat


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.perfect.islamyat.models.myPrayerModel
import com.perfect.islamyat.ui.dashboard.SebhaFragment
import com.perfect.islamyat.ui.home.HomeFragment
import com.perfect.islamyat.ui.more.MoreFragment
import com.perfect.islamyat.ui.notifications.NotificationsFragment
import com.perfect.islamyat.ui.quran.QuranFragment
import com.perfect.islamyat.utils.*
import com.perfect.islamyat.utils.Constants.LOCATION_FRAGMENT
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), Constants, LocationHelper.LocationCallback {

    private val ESTEGHFAR_ALARM_0_ID = "0"
    private val ESTEGHFAR_ALARM_1_ID = "1"
    private val ESTEGHFAR_ALARM_2_ID = "2"
    private val ESTEGHFAR_ALARM_3_ID = "3"
    private var locationManager: LocationManager? = null
    public var cntVal = 0
    public var totCntVal = 0
    public var dbData: DBHelper? = null
    public var mLocationHelper: LocationHelper? = null
    public var mLastLocation: Location? = null
    public var appPreferences: SharedPreferences? = null
    public var levelNo: Int = 0
    public var azkar: Cursor? = null
    public var navView: BottomNavigationView? = null
    public var homeFragment: HomeFragment? = null
    public var inHomeScreen: Boolean = true

    // like static in java
    companion object compObjExample {
        public var age = 24
        lateinit var mMyPrayerModel: myPrayerModel
        lateinit var context: Context
    }

//    public static lateinit var mMyPrayerModel: myPrayerModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAGTMainFragment", "onCreate: ")

//        retrofitFetchData()
        context = this
        mMyPrayerModel = myPrayerModel()

        val locale = Locale("ar")
        Locale.setDefault(locale)
        val resources: Resources = getResources()
        val config: Configuration = resources.getConfiguration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())
        this.navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_sebha, R.id.navigation_zekr
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        this.navView?.setupWithNavController(navController)

//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        SharedPreferences prefs = context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE);
        Log.v("TEST TEST", "------- Main Activity")
//        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        val editor = appPreferences?.edit()
        editor?.clear()
        editor?.commit()
        appPreferences =
            applicationContext.getSharedPreferences("IslamyiatSettings", 0) // 0 - for private mode

        replaceFragments(HomeFragment::class.java)

//        checkInternetConnection()
        navView?.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_home -> {
//                        viewFragment(HomeFragment(), FRAGMENT_HOME)
                        replaceFragments(HomeFragment::class.java)
                        return true
                    }
                    R.id.navigation_more -> {
//                        viewFragment(OneFragment(), FRAGMENT_OTHER)
                        replaceFragments(MoreFragment::class.java)
                        return true
                    }
                    R.id.navigation_quran -> {
//                        viewFragment(TwoFragment(), FRAGMENT_OTHER)
                        replaceFragments(QuranFragment::class.java)
                        return true
                    }
                    R.id.navigation_sebha -> {
//                        viewFragment(TwoFragment(), FRAGMENT_OTHER)
                        replaceFragments(SebhaFragment::class.java)
                        return true
                    }
                    R.id.navigation_zekr -> {
//                        viewFragment(TwoFragment(), FRAGMENT_OTHER)
                        replaceFragments(NotificationsFragment::class.java)
                        return true
                    }


                }

                return false
            }
        })


//        try {
//            // Request location updates
//            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
//        } catch(ex: SecurityException) {
//            Log.d("myTag", "Security Exception, no location available")
//        }


//        val editor = appPreferences?.edit()
//
//        editor?.clear()
//
//        editor?.commit()
//
//        appPreferences =
//            applicationContext.getSharedPreferences("IslamyiatSettings", 0) // 0 - for private mode


        mLocationHelper =
            fragmentManager.findFragmentByTag(LOCATION_FRAGMENT) as LocationHelper?
        if (mLocationHelper == null) {
            mLocationHelper = LocationHelper.newInstance()
            fragmentManager.beginTransaction().add(mLocationHelper, LOCATION_FRAGMENT).commit()
        }
        dbData = DBHelper(this)
        dbData!!.openDataBase()
//        getDailyCnt()
//        applicationContext.startForegroundService(Intent(this, UpdateService::class.java))


    }

/*
    public fun retrofitFetchData() : myPrayerModel {
        mMyPrayerModel = myPrayerModel()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.aladhan.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)

        val timeStamp2 = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date())

        val call: Call<myPrayerModel> = apiInterface.getTimings(
            timeStamp2, 30.005493, 31.477898, 5
        )

        call.enqueue(object : Callback<myPrayerModel?> {
            override fun onResponse(
                call: Call<myPrayerModel?>,
                response: Response<myPrayerModel?>
            ) {
                if (BuildConfig.DEBUG && response.body() == null) {
                    error("Assertion failed")
                }
                mMyPrayerModel = response.body()!!
                Log.d("TAGWWW=", "onResponse: " + mMyPrayerModel.data.timings.Fajr)
                schedulePrayerTimesAlarms()


            }

            override fun onFailure(call: Call<myPrayerModel?>, t: Throwable) {
                Log.d("TAGtttt=", "onFailure: " + t.message)
            }
        })

        return mMyPrayerModel

    }
*/


    public fun fetchLocation() {
        if (mLocationHelper != null) {
            mLocationHelper!!.checkLocationPermissions()
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("textLocationChanged", "" + location.longitude + ":" + location.latitude)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }


    override open fun onLocationSettingsFailed(): Unit {}

    override fun onLocationChanged(location: Location) {
        Log.d("TAGonLocationChanged", "onLocationChanged1122: "+location+" --- ")
        mLastLocation = location
//        val home_fragment = supportFragmentManager.fragments[0].childFragmentManager.fragments[0] as HomeFragment
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.aladhan.com/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

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
//                homeFragment?.getPrayerTimes()
//                MainActivity.mMyPrayerModel.data.timings.Fajr = "17:21"
//                Log.d("TAGQQQQQ", "onCreateView: " + MainActivity.mMyPrayerModel.data.timings.Fajr)
//
//
//            }
//
//            override fun onFailure(call: Call<myPrayerModel?>, t: Throwable) {
//                Log.d("TAGtttt=", "onFailure: " + t.message)
//            }
//        })

        homeFragment?.getPrayerTimes()

        // NOT THE BEST SOLUTION, THINK OF SOMETHING ELSE
//        mAdapter = com.alimuzaffar.ramadanalarm.SalaatTimesActivity.ScreenSlidePagerAdapter(
//            fragmentManager,
//            0
//        )
//        mPager.setAdapter(mAdapter)
    }

    fun replaceFragments(fragmentClass: Class<*>) {
        var fragment: Fragment? = null
        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // Insert the fragment by replacing any existing fragment
        val backStateName = fragment!!.javaClass.name
//        val fragmentTag = backStateName
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            inHomeScreen = false
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment, fragmentClass.name)
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack("MainActivity")
            transaction.commit()
            if (fragmentClass.equals(HomeFragment::class.java) || fragmentClass.equals(SebhaFragment::class.java) || fragmentClass.equals(
                    QuranFragment::class.java
                ) || fragmentClass.equals(MoreFragment::class.java) || fragmentClass.equals(
                    NotificationsFragment::class.java
                )
            ) {
                nav_view.visibility = View.VISIBLE
                levelNo = 0
            }
//            levelNo++
//            if (levelNo>0)
            else {
                levelNo++
                nav_view.visibility = View.GONE
            }
        }
    }

    fun replaceFragments3(fragmentClass: Class<*>) {
        var fragment: Fragment? = null
        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // Insert the fragment by replacing any existing fragment
        val backStateName = fragment!!.javaClass.name
//        val fragmentTag = backStateName
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            inHomeScreen = false
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.add(R.id.nav_host_fragment, fragment, fragmentClass.name)
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack("MainActivity")
            transaction.commit()
            if (fragmentClass.equals(HomeFragment::class.java) || fragmentClass.equals(SebhaFragment::class.java) || fragmentClass.equals(
                    QuranFragment::class.java
                ) || fragmentClass.equals(MoreFragment::class.java) || fragmentClass.equals(
                    NotificationsFragment::class.java
                )
            ) {
                nav_view.visibility = View.VISIBLE
                levelNo = 0
            }
//            levelNo++
//            if (levelNo>0)
            else {
                levelNo++
                nav_view.visibility = View.GONE
            }
        }
    }


    fun checkInternetConnection(): Boolean {
        if (bNetworkHelper.isNetworkConnected(this)) {
            Toast.makeText(this, "تم الاتصال بالانترنت", Toast.LENGTH_LONG).show()
            Log.d("تم الاتصال بالانترنت", "checkConnType: ")
            return true
        } else {
            Toast.makeText(this, "لا يوجد اتصال بالانترنت", Toast.LENGTH_LONG).show()
            Log.d("لا يوجد اتصال بالانترنت", "checkConnType: ")
            return false
        }
    }


    fun schedulePrayerTimesAlarms() {

        val internetConnection = checkInternetConnection();
        Log.d("TAG:::::::::::::::::", "schedulePrayerTimesAlarms: " + internetConnection)
        if (internetConnection) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.aladhan.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiInterface = retrofit.create(ApiInterface::class.java)

            val timeStamp2 = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date())

            var besoMethod = appPreferences!!.getInt("prayerTimesCalcMethod", 4)

            var besoLastlatitude = appPreferences!!.getFloat("Lastlatitude", 24.774265f).toDouble()
            var besoLastLongituide =
                appPreferences!!.getFloat("LastLongituide", 46.738586f).toDouble()


            Log.d(
                "TAGFFFFFFFFFF",
                "schedulePrayerTimesAlarms: " + besoMethod + "wwsscc" + besoLastlatitude
            )

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
                    mMyPrayerModel = response.body()!!
                    scheduleWithInternet()
                }

                override fun onFailure(call: Call<myPrayerModel?>, t: Throwable) {
                    Log.d("TAGtttt=", "onFailure: " + t.message)
                    scheduleWithoutInternet()
                }
            })
        } else {
            Log.d("TAGTAGTAGTAGTAGTAG", "schedulePrayerTimesAlarms: ")
            scheduleWithoutInternet()
        }


    }


    fun scheduleWithInternet() {
        Log.d("TAGmain", "schedulePrayerTimesAlarms: " + age)

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pIntent(context, 100))
        alarmManager.cancel(pIntent(context, 101))
        alarmManager.cancel(pIntent(context, 102))
        alarmManager.cancel(pIntent(context, 110))
        alarmManager.cancel(pIntent(context, 120))
        alarmManager.cancel(pIntent(context, 121))
        alarmManager.cancel(pIntent(context, 130))
        alarmManager.cancel(pIntent(context, 131))
        alarmManager.cancel(pIntent(context, 140))
        alarmManager.cancel(pIntent(context, 141))
        alarmManager.cancel(pIntent(context, 150))
        alarmManager.cancel(pIntent(context, 151))
        val prefs =
            context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE)

        Log.d(
            "TAGfffag ", "setPrayerTimesAlarms: " + prefs.getInt(
                "prayerTimesCalcMethod",
                4
            )
        )
        Log.d("TAGQQQQQM", "onCreateView: " + mMyPrayerModel.data.timings.Fajr)


//        val prayerTimes =
//            PrayTime.getPrayerTimes(
//                context,
//                prefs.getInt("prayerTimesCalcMethod", 4),
//                prefs.getFloat("Lastlatitude", 0f).toDouble(),
//                prefs.getFloat("LastLongituide", 0f).toDouble(),
//                PrayTime.TIME_24
//            )


        val editor = appPreferences?.edit()
        editor?.putString("beso_Fajr", mMyPrayerModel.data.timings.Fajr)
        editor?.putString("beso_Sunrise", mMyPrayerModel.data.timings.Sunrise)
        editor?.putString("beso_Dhuhr", mMyPrayerModel.data.timings.Dhuhr)
        editor?.putString("beso_Asr", mMyPrayerModel.data.timings.Asr)
        editor?.putString("beso_Maghrib", mMyPrayerModel.data.timings.Maghrib)
        editor?.putString("beso_Isha", mMyPrayerModel.data.timings.Isha)
        editor?.commit()

        val besoFajr: String? = appPreferences?.getString("beso_Fajr", "03:07")


        Log.d(
            "TAGwwwwwwwwqqqqqqqqq", "scheduleWithoutInternet: " + besoFajr + "isha " +
                    appPreferences?.getString("beso_Isha", "03:07")
        )


        Log.d("TAGbesoFajr222", "onResponse: " + besoFajr)
        var fajr_time = mMyPrayerModel.data.timings.Fajr
        if (fajr_time!!.substring(0, fajr_time.indexOf(":")).length == 1) fajr_time =
            "0$fajr_time"
        if (fajr_time.substring(fajr_time.indexOf(":") + 1).length == 1) fajr_time =
            fajr_time.replace(":", ":0")
        var sunrise_time = mMyPrayerModel.data.timings.Sunrise
        if (sunrise_time!!.substring(
                0,
                sunrise_time.indexOf(":")
            ).length == 1
        ) sunrise_time =
            "0$sunrise_time"
        if (sunrise_time.substring(sunrise_time.indexOf(":") + 1).length == 1) sunrise_time =
            sunrise_time.replace(":", ":0")
        var dhuhr_time = mMyPrayerModel.data.timings.Dhuhr
        if (dhuhr_time!!.substring(0, dhuhr_time.indexOf(":")).length == 1) dhuhr_time =
            "0$dhuhr_time"
        if (dhuhr_time.substring(dhuhr_time.indexOf(":") + 1).length == 1) dhuhr_time =
            dhuhr_time.replace(":", ":0")
        var asr_time = mMyPrayerModel.data.timings.Asr
        if (asr_time!!.substring(0, asr_time.indexOf(":")).length == 1) asr_time =
            "0$asr_time"
        if (asr_time.substring(asr_time.indexOf(":") + 1).length == 1) asr_time =
            asr_time.replace(":", ":0")
        var maghreb_time = mMyPrayerModel.data.timings.Maghrib
        if (maghreb_time!!.substring(
                0,
                maghreb_time.indexOf(":")
            ).length == 1
        ) maghreb_time =
            "0$maghreb_time"
        if (maghreb_time.substring(maghreb_time.indexOf(":") + 1).length == 1) maghreb_time =
            maghreb_time.replace(":", ":0")
        var isha_time = mMyPrayerModel.data.timings.Isha
        if (isha_time!!.substring(0, isha_time.indexOf(":")).length == 1) isha_time =
            "0$isha_time"
        if (isha_time.substring(isha_time.indexOf(":") + 1).length == 1) isha_time =
            isha_time.replace(":", ":0")

        print("dhuru time" + dhuhr_time)
        Log.d(
            "TAGDhur22ew",
            "schedulePrayerTimesAlarms: $dhuhr_time" + " --- " + mMyPrayerModel.code
        )
        Log.d("TAGDhur", "schedulePrayerTimesAlarms: $maghreb_time")


        //prayerTimesSpecialAlarmOffset
        // 100
        if (prefs.getBoolean("prayerTimesAlarmFajrOn", true)) {
            setPrayerAlarm(context, fajr_time, 100, 0)
            if (prefs.getBoolean("prayerTimesSpecialAlarmFajrOn", false)
            ) { // Special Fajr Alarm
                if (prefs.getBoolean("prayerTimesSpecialAlarmBefore", true)
                ) { // Special Fajr Alarm
                    Log.d("TAGBefore", "schedulePrayerTimesAlarms: ")
                    Log.d(
                        "TAGprayerTimeIqamaFajr1",
                        "schedulePrayerTimesAlarms: " + (prefs.getInt(
                            "prayerTimesIqamaFajr", 0
                        ) + 1)
                    )
                    Log.d(
                        "TAGprayerOffset", "schedulePrayerTimesAlarms: " +
                                (prefs.getInt(
                                    "prayerTimesSpecialAlarmOffset",
                                    0
                                ) + 1) * 1000 * 60 * 1
                    )

                    setPrayerAlarm(
                        context,
                        fajr_time,
                        102,
                        (prefs.getInt(
                            "prayerTimesSpecialAlarmOffset",
                            0
                        ) + 1) * 1000 * 60 * 5
                    )

//                    setPrayerAlarm(
//                        this,
//                        fajr_time,
//                        102,
//                        (prefs.getInt("prayerTimesIqamaFajr", 0) + 1) * 1000 * 60 * 5
//                    )
                } else {
                    Log.d(
                        "TAGprayerTimeIqamaFajr2",
                        "schedulePrayerTimesAlarms: " + (prefs.getInt(
                            "prayerTimesIqamaFajr",
                            0
                        ) + 1)
                    )

                    setPrayerAlarm(
                        context,
                        fajr_time,
                        102,
                        (prefs.getInt(
                            "prayerTimesSpecialAlarmOffset",
                            0
                        ) + 1) * 1000 * 60 * -5
                    )

//                    setPrayerAlarm(
//                        this,
//                        fajr_time,
//                        102,
//                        (prefs.getInt("prayerTimesIqamaFajr", 0) + 1) * 1000 * 60 * -5
//                    )
                }

            }

            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    fajr_time,
                    101,
                    (prefs.getInt("prayerTimesIqamaFajr", 0) + 2) * 1000 * 60 * 5
                )
            }
        }

        // 110
        if (prefs.getBoolean("prayerTimesAlarmSunriseOn", false)) {
            setPrayerAlarm(context, sunrise_time, 110, 0)
        }

        // 120
        if (prefs.getBoolean("prayerTimesAlarmDhuhrOn", true)) {
            setPrayerAlarm(context, dhuhr_time, 120, 0)
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    dhuhr_time,
                    121,
                    (prefs.getInt("prayerTimesIqamaDhuhr", 0) + 2) * 1000 * 60 * 5
                )
            }
        }

        // 130
        if (prefs.getBoolean("prayerTimesAlarmAsrOn", true)) {
            setPrayerAlarm(context, asr_time, 130, 0)
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    asr_time,
                    131,
                    (prefs.getInt("prayerTimesIqamaAsr", 0) + 2) * 1000 * 60 * 5
                )
            }
        }

        /**
         * 140
         */
        if (prefs.getBoolean("prayerTimesAlarmMaghrebOn", true)) {
            setPrayerAlarm(context, maghreb_time, 140, 0)
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    maghreb_time,
                    141,
                    (prefs.getInt("prayerTimesIqamaMaghreb", 0) + 2) * 1000 * 60 * 5
                )
            }
        }

        /**
         * 150
         */
        if (prefs.getBoolean("prayerTimesAlarmIshaOn", true)) {
            setPrayerAlarm(context, isha_time, 150, 0)
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    isha_time,
                    151,
                    (prefs.getInt("prayerTimesIqamaIsha", 0) + 2) * 1000 * 60 * 5
                )
            }
        }
    }


    fun scheduleWithoutInternet() {

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pIntent(context, 100))
        alarmManager.cancel(pIntent(context, 101))
        alarmManager.cancel(pIntent(context, 102))
        alarmManager.cancel(pIntent(context, 110))
        alarmManager.cancel(pIntent(context, 120))
        alarmManager.cancel(pIntent(context, 121))
        alarmManager.cancel(pIntent(context, 130))
        alarmManager.cancel(pIntent(context, 131))
        alarmManager.cancel(pIntent(context, 140))
        alarmManager.cancel(pIntent(context, 141))
        alarmManager.cancel(pIntent(context, 150))
        alarmManager.cancel(pIntent(context, 151))
        val prefs =
            context.getSharedPreferences("IslamyiatSettings", Context.MODE_PRIVATE)

        Log.d(
            "TAGfffag ", "setPrayerTimesAlarms: " + prefs.getInt(
                "prayerTimesCalcMethod",
                4
            )
        )


//        val prayerTimes =
//            PrayTime.getPrayerTimes(
//                context,
//                prefs.getInt("prayerTimesCalcMethod", 4),
//                prefs.getFloat("Lastlatitude", 0f).toDouble(),
//                prefs.getFloat("LastLongituide", 0f).toDouble(),
//                PrayTime.TIME_24
//            )


//        val editor = prefs?.edit()
//        editor?.putString("beso_Fajr", "11:17")
////        editor?.putString("beso_Dhuhr", "11:08")
////        editor?.putString("beso_Asr", "11:05")
////        editor?.putString("beso_Maghrib", mMyPrayerModel.data.timings.Maghrib)
////        editor?.putString("beso_Isha", mMyPrayerModel.data.timings.Isha)
//        editor?.commit()


//        appPreferences?.getString("esteghfarAlarmStartTime", "")

        var besoFajr: String? = prefs?.getString("beso_Fajr", "03:07")
        var besoSunrise: String? = prefs?.getString("beso_Sunrise", "04:53")
        var besoDhuhr: String? = prefs?.getString("beso_Dhuhr", "11:55")
        var besoAsr: String? = prefs?.getString("beso_Asr", "15:30")
        var besoMaghrib: String? = prefs?.getString("beso_Maghrib", "18:57")
        var besoIsha: String? = prefs?.getString("beso_Isha", "20:30")


//        besoFajr = "14:35"
//        besoDhuhr = "14:37"
//        besoAsr = "14:39"
//        besoMaghrib = "14:30"
//        besoIsha = "14:31"
//        besoFajr = "14:27"


        Log.d("TAGwwwwwwwwqqqqqqqqqNo", "scheduleWithoutInternet: " + besoFajr + "isha " + besoIsha)

        var fajr_time = besoFajr
        if (fajr_time!!.substring(0, fajr_time.indexOf(":")).length == 1) fajr_time =
            "0$fajr_time"
        if (fajr_time.substring(fajr_time.indexOf(":") + 1).length == 1) fajr_time =
            fajr_time.replace(":", ":0")
        var sunrise_time = besoSunrise
        if (sunrise_time!!.substring(
                0,
                sunrise_time.indexOf(":")
            ).length == 1
        ) sunrise_time =
            "0$sunrise_time"
        if (sunrise_time.substring(sunrise_time.indexOf(":") + 1).length == 1) sunrise_time =
            sunrise_time.replace(":", ":0")
        var dhuhr_time = besoDhuhr
        if (dhuhr_time!!.substring(0, dhuhr_time.indexOf(":")).length == 1) dhuhr_time =
            "0$dhuhr_time"
        if (dhuhr_time.substring(dhuhr_time.indexOf(":") + 1).length == 1) dhuhr_time =
            dhuhr_time.replace(":", ":0")
        var asr_time = besoAsr
        if (asr_time!!.substring(0, asr_time.indexOf(":")).length == 1) asr_time =
            "0$asr_time"
        if (asr_time.substring(asr_time.indexOf(":") + 1).length == 1) asr_time =
            asr_time.replace(":", ":0")
        var maghreb_time = besoMaghrib
        if (maghreb_time!!.substring(
                0,
                maghreb_time.indexOf(":")
            ).length == 1
        ) maghreb_time =
            "0$maghreb_time"
        if (maghreb_time.substring(maghreb_time.indexOf(":") + 1).length == 1) maghreb_time =
            maghreb_time.replace(":", ":0")
        var isha_time = besoIsha
        if (isha_time!!.substring(0, isha_time.indexOf(":")).length == 1) isha_time =
            "0$isha_time"
        if (isha_time.substring(isha_time.indexOf(":") + 1).length == 1) isha_time =
            isha_time.replace(":", ":0")

        print("dhuru time" + dhuhr_time)
        Log.d(
            "TAGDhur22ew",
            "schedulePrayerTimesAlarms: $dhuhr_time" + " --- " + mMyPrayerModel.code
        )
        Log.d("TAGMaghreb", "schedulePrayerTimesAlarms: $maghreb_time")


        //prayerTimesSpecialAlarmOffset
        // 100
        if (prefs.getBoolean("prayerTimesAlarmFajrOn", true)) {
            setPrayerAlarm(context, fajr_time, 100, 0)
            if (prefs.getBoolean(
                    "prayerTimesSpecialAlarmFajrOn",
                    false
                )
            ) { // Special Fajr Alarm
                if (prefs.getBoolean(
                        "prayerTimesSpecialAlarmBefore",
                        true
                    )
                ) { // Special Fajr Alarm
                    Log.d("TAGBefore", "schedulePrayerTimesAlarms: ")
                    Log.d(
                        "TAGprayerTimeIqamaFajr1",
                        "schedulePrayerTimesAlarms: " + (prefs.getInt(
                            "prayerTimesIqamaFajr",
                            0
                        ) + 1)
                    )
                    Log.d(
                        "TAGprayerOffset", "schedulePrayerTimesAlarms: " +
                                (prefs.getInt(
                                    "prayerTimesSpecialAlarmOffset",
                                    0
                                ) + 1) * 1000 * 60 * 1
                    )

                    setPrayerAlarm(
                        context,
                        fajr_time,
                        102,
                        (prefs.getInt(
                            "prayerTimesSpecialAlarmOffset",
                            0
                        ) + 1) * 1000 * 60 * 5
                    )

//                    setPrayerAlarm(
//                        this,
//                        fajr_time,
//                        102,
//                        (prefs.getInt("prayerTimesIqamaFajr", 0) + 1) * 1000 * 60 * 5
//                    )
                } else {
                    Log.d(
                        "TAGprayerTimeIqamaFajr2",
                        "schedulePrayerTimesAlarms: " + (prefs.getInt(
                            "prayerTimesIqamaFajr",
                            0
                        ) + 1)
                    )

                    setPrayerAlarm(
                        context,
                        fajr_time,
                        102,
                        (prefs.getInt(
                            "prayerTimesSpecialAlarmOffset",
                            0
                        ) + 1) * 1000 * 60 * -5
                    )

//                    setPrayerAlarm(
//                        this,
//                        fajr_time,
//                        102,
//                        (prefs.getInt("prayerTimesIqamaFajr", 0) + 1) * 1000 * 60 * -5
//                    )
                }

            }

            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    fajr_time,
                    101,
                    (prefs.getInt("prayerTimesIqamaFajr", 0) + 2) * 1000 * 60 * 5
                )
            }
        }

        // 110
        if (prefs.getBoolean("prayerTimesAlarmSunriseOn", false)) {
            setPrayerAlarm(context, sunrise_time, 110, 0)
        }

        // 120
        if (prefs.getBoolean("prayerTimesAlarmDhuhrOn", true)) {
            setPrayerAlarm(context, dhuhr_time, 120, 0)
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    dhuhr_time,
                    121,
                    (prefs.getInt("prayerTimesIqamaDhuhr", 0) + 2) * 1000 * 60 * 5
                )
            }
        }

        // 130
        if (prefs.getBoolean("prayerTimesAlarmAsrOn", true)) {
            setPrayerAlarm(context, asr_time, 130, 0)
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    asr_time,
                    131,
                    (prefs.getInt("prayerTimesIqamaAsr", 0) + 2) * 1000 * 60 * 5
                )
            }
        }

        /**
         * 140
         */
        if (prefs.getBoolean("prayerTimesAlarmMaghrebOn", true)) {
            setPrayerAlarm(context, maghreb_time, 140, 0)
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    maghreb_time,
                    141,
                    (prefs.getInt("prayerTimesIqamaMaghreb", 0) + 2) * 1000 * 60 * 5
                )
            }
        }

        /**
         * 150
         */
        if (prefs.getBoolean("prayerTimesAlarmIshaOn", true)) {
            setPrayerAlarm(context, isha_time, 150, 0)
            if (prefs.getBoolean("prayerTimesIqamaOn", false)) {
                setPrayerAlarm(
                    context,
                    isha_time,
                    151,
                    (prefs.getInt("prayerTimesIqamaIsha", 0) + 2) * 1000 * 60 * 5
                )
            }
        }

    }


    //    SuppressLint("SimpleDateFormat")
    fun setPrayerAlarm(
        context: Context?, prayerTime: String, prayerID: Int, offset: Int
    ) {

        Log.d("TAGEnteredKamMllee", "setPrayerAlarm: $prayerTime")

        val alarmManager =
            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var prayerName = ""
        try {

            /*

//            val calPrayerTime = Calendar.getInstance()
            Log.d("TAGsubintid", "setPrayerAlarm: $prayerID")
//            Log.d("TAGsubint1", "setPrayerAlarm: " + prayerTime.substring(0, 2).toInt())
//            Log.d("TAGsubint2", "setPrayerAlarm: " + prayerTime.substring(0, 2))
//            Log.d("TAGsubint3", "setPrayerAlarm: $prayerTime")
//            calPrayerTime[Calendar.HOUR_OF_DAY] = prayerTime.substring(0, 2).toInt()
//            calPrayerTime[Calendar.MINUTE] = prayerTime.substring(3, 5).toInt()
//            Log.d("TAGsubintcal", "setPrayerAlarm: " + calPrayerTime[Calendar.HOUR_OF_DAY])

            val currentTime = Date(System.currentTimeMillis())
//            val date = Calendar.getInstance()
//            val timeInSecs = date.timeInMillis
//            val afterAdding10Mins = Date(timeInSecs + 2 * 60 * 1000)

//            val calendar = Calendar.getInstance()
//
//            var timeMilli2 = calendar.timeInMillis
//            println("Time in milliseconds using Calendar: $timeMilli2")
////            timeMilli2 += 120000
//
//            Log.d("TAGwwwwwwwcal", "setPrayerAlarm: ${timeMilli2 + 120000}")
//            Log.d("TAGwwwwwww", "setPrayerAlarm: $calPrayerTime")
//            Log.d("TAGwwwwwwwq", "setPrayerAlarm: ${currentTime.time}")
//            Log.d("TAGwwwwwwwTTTT", "setPrayerAlarm: ${calPrayerTime.time}")
//
//            if (currentTime.compareTo(calPrayerTime.time) > 0) {
//                Log.d("TAGCompar", "setPrayerAlarm: $prayerID logined")
//
//                /**
//                 * why ? to make alarm next day
//                 */
//                calPrayerTime.add(Calendar.DAY_OF_MONTH, 1)
//            }

            */

            when (prayerID) {
                100 -> prayerName = "Fajr Azan"
                101 -> prayerName = "Fajr Iqama"
                110 -> prayerName = "Sunrise Azan"
                120 -> prayerName = "Dhuhr Azan"
                121 -> prayerName = "Dhuhr Iqama"
                130 -> prayerName = "Asr Azan"
                131 -> prayerName = "Asr Iqama"
                140 -> prayerName = "Maghreb Azan"
                141 -> prayerName = "Maghreb Iqama"
                150 -> prayerName = "Isha Azan"
                151 -> prayerName = "Isha Iqama"
                else -> {
                }
            }

/*
//            Log.d("TAGqqqqqqqqqq", "setPrayerAlarm: " + calPrayerTime.timeInMillis)
//            calPrayerTime.add(Calendar.MILLISECOND, offset)

//            calPrayerTime.timeInMillis = System.currentTimeMillis();
//            calPrayerTime.set(Calendar.HOUR_OF_DAY, 1);
//            calPrayerTime.set(Calendar.MINUTE, 52);


//            calPrayerTime.set(Calendar.HOUR_OF_DAY,1)
//            calPrayerTime.set(Calendar.MINUTE, 30)
//
//            Log.v("Alarm Set: ", " For $prayerName")
//            Log.v("Alarm Set: ", "at " + calPrayerTime.time)

//            val intent = Intent(applicationContext, NotificationReceiver::class.java)
//            var sender: PendingIntent = PendingIntent.getBroadcast(
//                applicationContext,
//                prayerID,
//                intent,
//                0
//            );

*/


            val calPrayerTime = Calendar.getInstance()
            calPrayerTime[Calendar.HOUR_OF_DAY] = prayerTime.substring(0, 2).toInt()
            calPrayerTime[Calendar.MINUTE] = prayerTime.substring(3, 5).toInt()
            val currentTime = Date(System.currentTimeMillis())
            if (currentTime > calPrayerTime.time) {
                Log.d("TAGCheckEnter", "setPrayerAlarm: " + prayerName)
                calPrayerTime.add(Calendar.DAY_OF_MONTH, 1)
            }


            /*****
             * what i want to achieve now is to change here all calendars with offset 0
             *
             * but if the current time now is 11:45 the fajr calender time offset
             * should be 0 or 1
             * calendar5.add(Calendar.DAY_OF_MONTH, 1)
             *
             */


            calPrayerTime.add(Calendar.MILLISECOND, offset)
            Log.v("Alarm Set: ", " For $prayerName")
            Log.v("Alarm Set: ", "at " + calPrayerTime.time)


//            alarmManager.setExact(
//                AlarmManager.RTC_WAKEUP,
//                calPrayerTime.timeInMillis,
//                pIntent(context, prayerID)
//            )


            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calPrayerTime.timeInMillis,
                24*60*60*1000,
                pIntent(context, prayerID)
            )



            /**
             * start broadcast at first
             */

//            val alarmManager6 =
//                this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//            val intent6 = Intent(this, NotificationReceiver::class.java)
//            val pendingIntent2 = PendingIntent.getBroadcast(
//                this.applicationContext,
//                prayerID,
//                intent6,
//                0
//            )
////            intent6.putExtra("id", 40);
//            intent6.putExtra("id", prayerID);
//
//            alarmManager6.setExact(AlarmManager.RTC_WAKEUP, calendar5.timeInMillis, pendingIntent2);

/*
//            alarmManager6.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis() + 2* 60000,
//                2*60*1000,
//                pendingIntent2
////                AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+calendar5.timeInMillis, 2*60000, pendingIntent2
//            )


            /**
             * start services directly
             */
            /*
//            val intent5 = Intent(this, CustomIntentService::class.java)
//            val pendingIntent = PendingIntent.getService(
//                this.applicationContext,
//                prayerID,
//                intent5,
//                0
//            )
//        intent5.putExtra("id", prayerID);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+2*60*1000, pendingIntent)
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis() ,
//                calendar5.timeInMillis,
//                pendingIntent
////            AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+calendar5.timeInMillis, 4*60000, pendingIntent
//            )
            */
            /*
//            alarmManager.set(
//                AlarmManager.RTC_WAKEUP,
//                 calendar5.timeInMillis,
//                pendingIntent
//            )

//            alarmManager5.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis() + 120000,
//                calendar5.timeInMillis,
//                pendingIntent
//                //AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+calendar5.timeInMillis, 4*60000, pendingIntent
//            )

/*


//            val calendar2: Calendar = Calendar.getInstance().apply {
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, 2)
//                    set(Calendar.MINUTE, 11)
//            }

//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calPrayerTime.timeInMillis,
//                AlarmManager.INTERVAL_DAY, sender);

//            val calendar4 = Calendar.getInstance()
//            calendar4.timeInMillis = java.lang.System.currentTimeMillis()
//            calendar4.set(Calendar.HOUR_OF_DAY, 2)
//            calendar4.set(Calendar.MINUTE, 42)
//
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar4.timeInMillis,sender)

//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar2.timeInMillis,
//                1000 * 60 *2,
//                sender
//            )


            //            alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(System.currentTimeMillis()
//                + calendar2.timeInMillis,sender),sender)


            // this alarm not work in low battery
//            alarmManager.setExactAndAllowWhileIdle(
//                AlarmManager.RTC_WAKEUP, calendar2.timeInMillis,
//                sender
//                /*pIntent(context, prayerID)*/
//            )


            //            alarmManager.setAlarmClock(
//                AlarmClockInfo(
//                    calPrayerTime.timeInMillis,
//                    pIntent(context, prayerID)
//                ), pIntent(context, prayerID)
//            )


 */
*/

            */
        } catch (e: java.lang.Exception) {
        }
    }


    fun scheduleAlarm(id: Int) {
        Log.d("TAGSchedule", "scheduleAlarm: ")

        val alarmManager =
            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        var startTime: String? = appPreferences?.getString("esteghfarAlarmStartTime", "")
        var endTime: String? = appPreferences?.getString("esteghfarAlarmEndTime", "")

        var startTimeHour = startTime!!.substring(0, 2).toInt()
        var startTimeMinute = startTime.substring(3, 5).toInt()

        var endTimeHour = endTime!!.substring(0, 2).toInt()
        var endTimeMinute = endTime.substring(3, 5).toInt()

        Log.d("TAGGstartTimestart", startTime)
        Log.d("TAGGendTimeend", endTime)
        Log.d("TAGstartTimeHour", "scheduleAlarm: " + startTimeHour)
        Log.d("TAGstartTimeMinute", "scheduleAlarm: " + startTimeMinute)
        Log.d("TAGendTimeHour", "scheduleAlarm: " + endTimeHour)
        Log.d("TAGendTimeMinute", "scheduleAlarm: " + endTimeMinute)


//        val minutes: Int = startTime!!.get(0).toInt() //first element
//        val seconds: Int = startTime!!.get(1).toInt() //second element
//        val duration = 60 * minutes + seconds //add up our values
//        Log.d("TAGGstartTimeminutes", minutes.toString())

        var esteghfarAlarmRepetitionPeriod: Int? =
            appPreferences?.getInt("esteghfarAlarmRepetitionPeriod", -1)

        var sec: Long? = TimeUnit.MILLISECONDS.toSeconds(600000)
        print("sec = " + sec!! / 60)
        Log.d("repeatnum4", esteghfarAlarmRepetitionPeriod.toString())

        val repeatListener: Long?
        when (esteghfarAlarmRepetitionPeriod) {
            0 -> repeatListener = 60000 * 15
            1 -> repeatListener = 60000 * 30
            2 -> repeatListener = 60000 * 45
            3 -> repeatListener = 60000 * 60 // one hour
            4 -> repeatListener = 60000 * 60 * 2
            5 -> repeatListener = 60000 * 60 * 3
            6 -> repeatListener = 60000 * 60 * 4
            7 -> repeatListener = 60000 * 60 * 6
            else -> {
                repeatListener = -1
            }
        }
        Log.d("repeatListener", repeatListener.toString())

        var zekrCount: Int = 1

        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        var sender: PendingIntent = PendingIntent.getBroadcast(applicationContext, id, intent, 0);

        var calendar5: Calendar = Calendar.getInstance()

        calendar5[Calendar.HOUR_OF_DAY] = startTimeHour
        calendar5[Calendar.MINUTE] = startTimeMinute
        calendar5.add(Calendar.DAY_OF_MONTH, 0)

        Log.d("TAGSAla", "scheduleAlarm: " + calendar5[Calendar.HOUR_OF_DAY])

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar5.timeInMillis, sender);
/*
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            repeatListener * zekrCount,
//            repeatListener,
//            sender
//        )

        /*
//        zekrCount++
//        val alarmManager3 =
//            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent3 = Intent(applicationContext, NotificationReceiver::class.java)
//        var sender3: PendingIntent = PendingIntent.getBroadcast(applicationContext, 3, intent3, 0);
//        alarmManager3.setRepeating(AlarmManager.RTC_WAKEUP, 10000, repeatListener, sender3)
//
//        zekrCount++
//        val alarmManager4 =
//            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent4 = Intent(applicationContext, NotificationReceiver::class.java)
//        var sender4: PendingIntent = PendingIntent.getBroadcast(applicationContext, 4, intent4, 0);
//        alarmManager4.setRepeating(AlarmManager.RTC_WAKEUP, 10000, repeatListener, sender4)
//
//        zekrCount++
//        val alarmManager5 =
//            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent5 = Intent(applicationContext, NotificationReceiver::class.java)
//        var sender5: PendingIntent = PendingIntent.getBroadcast(applicationContext, 5, intent5, 0);
//        alarmManager5.setRepeating(AlarmManager.RTC_WAKEUP, 10000, repeatListener, sender5)
//
//        zekrCount++
//        val alarmManager6 =
//            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent6 = Intent(applicationContext, NotificationReceiver::class.java)
//        var sender6: PendingIntent = PendingIntent.getBroadcast(applicationContext, 6, intent6, 0);
//        alarmManager6.setRepeating(AlarmManager.RTC_WAKEUP, 10000, repeatListener, sender6)
//
//        zekrCount++
//        val alarmManager7 =
//            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent7 = Intent(applicationContext, NotificationReceiver::class.java)
//        var sender7: PendingIntent = PendingIntent.getBroadcast(applicationContext, 7, intent7, 0);
//        alarmManager7.setRepeating(AlarmManager.RTC_WAKEUP, 10000, repeatListener, sender7)
//
//        zekrCount++
//        val alarmManager8 =
//            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent8 = Intent(applicationContext, NotificationReceiver::class.java)
//        var sender8: PendingIntent = PendingIntent.getBroadcast(applicationContext, 8, intent8, 0);
//        alarmManager8.setRepeating(AlarmManager.RTC_WAKEUP, 10000, repeatListener, sender8)
//
//        zekrCount++
//        val alarmManager9 =
//            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent9 = Intent(applicationContext, NotificationReceiver::class.java)
//        var sender9: PendingIntent = PendingIntent.getBroadcast(applicationContext, 9, intent9, 0);
//        alarmManager9.setRepeating(AlarmManager.RTC_WAKEUP, 10000, repeatListener, sender9)


//        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(System.currentTimeMillis()
//                + startTime,pIntent(this,id)),pIntent(this,id))
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP, ,
//            pIntent(this,id)
//        )
        */
        */
    }


    fun saveEsteghfarAlarm(
        alarmOn: Boolean, startTime: String, endTime: String, repetitionPeriod: Int
    ) {
        Log.d("TAGsaveEsteghfarAlarm", "saveEsteghfarAlarm: ")
        print("enterffff0")

        val editor = appPreferences?.edit()

        editor?.clear()
        var soundnum2: String? = appPreferences?.getString("esteghfarAlarmSounds", "111111111")
        Log.d("TAGGsoundnum2", soundnum2)
        /***/
//        editor?.commit()


        var soundnum223: String? = appPreferences?.getString("esteghfarAlarmSounds", "111111111")
        Log.d("TAGGsoundnum223", soundnum223)


        if (!alarmOn) {
            print("why alarmOn = " + alarmOn)
            cancelEsteghfarAlarms()
        } else {
            /**
             *
             */

            cancelEsteghfarAlarms()

            /***/
//            editor?.remove("esteghfarAlarmStartTime")?.commit();

            var soundnum22: String? = appPreferences?.getString("esteghfarAlarmSounds", "111111111")
            Log.d("TAGGsoundnum22", soundnum22)


            Log.d("TAGGS1", startTime)
            editor?.putBoolean("esteghfarAlarmOn", alarmOn)
            editor?.putString("esteghfarAlarmStartTime", startTime)
            editor?.putString("esteghfarAlarmEndTime", endTime)
            editor?.putInt("esteghfarAlarmRepetitionPeriod", repetitionPeriod)

            Log.d("TAGsaveEsteghfarAlarmO", alarmOn.toString())
            Log.d("TAGsaveEsteghfarAlarmS", startTime)
            Log.d("TAGsaveEsteghfarAlarmE", endTime)
            Log.d("TAGsaveEsteghfarAlarmR", repetitionPeriod.toString())
            Log.d(
                "TAGappPreferencesget",
                appPreferences?.getString("esteghfarAlarmSounds", "10000001")
            )



            print("mon Id = " + esteghfarAlarmSounds()!!.indexOf("1") + " oooo")
            var dd: String = "mesr"
            Log.d("TAGG", "mesr")
            Log.d("TAGG", (esteghfarAlarmSounds()!!.indexOf("1") + 1).toString())
            print("mesr = " + dd.indexOf("r"))
            var itn: Int = esteghfarAlarmSounds()!!.indexOf("1") + 1
            Log.d("TAGGitn", esteghfarAlarmSounds())
            Log.d("shieck4", appPreferences?.getString("esteghfarAlarmEndTime", ""))
            editor?.commit()


            /**
             * must make for loop to shedule all alarms
             */
            scheduleAlarm(esteghfarAlarmSounds()!!.indexOf("1") + 1)

            Log.d(
                "TAGsounddooIndex",
                "saveEsteghfarAlarm: " + (esteghfarAlarmSounds()!!.indexOf("1") + 1)
            )

        }
        // 0 : 15 Minutes - 1 : 30 Minutes - 2: 45 Minutes - 3: 1 Hour - 4: 2 Hours - 5: 3 Hours - 6: 4 Hours - 7: 6 Hours
        editor?.commit()
        Log.d("shieck2", repetitionPeriod.toString())

        Log.d("shieck5", appPreferences?.getInt("esteghfarAlarmRepetitionPeriod", 500).toString())

    }


    fun cancelEsteghfarAlarms() {
        val alarmManager =
            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pIntent(this, 1))
        alarmManager.cancel(pIntent(this, 2))
        alarmManager.cancel(pIntent(this, 3))
        alarmManager.cancel(pIntent(this, 4))
        alarmManager.cancel(pIntent(this, 5))
        alarmManager.cancel(pIntent(this, 6))
        alarmManager.cancel(pIntent(this, 7))
        alarmManager.cancel(pIntent(this, 8))
        alarmManager.cancel(pIntent(this, 9))

    }


    fun pIntent(context: Context?, id: Int): PendingIntent? {
        Log.d("TAGpIn", "pIntent: ")
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("id", id)
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun replaceFragmentsWithDetail(fragmentClass: Class<*>, title: String, details: String) {
        var fragment: Fragment? = null
        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            inHomeScreen = false
            var bundle = Bundle()
            bundle.putString("ARG_PARAM1", title)
            bundle.putString("ARG_PARAM2", details)
            fragment.arguments = bundle
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack("MainActvity")
            levelNo++
            if (levelNo > 0)
                nav_view.visibility = View.GONE
            transaction.commit()
        }
    }

    fun replaceFragmentsWithDetail2(
        fragmentClass: Class<*>, title: String, details: String, color: String
    ) {
        var fragment: Fragment? = null
        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            inHomeScreen = false
            var bundle = Bundle()
            bundle.putString("ARG_PARAM1", title)
            bundle.putString("ARG_PARAM2", details)
            bundle.putString("ARG_PARAM3", color)
            fragment.arguments = bundle
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack("MainActvity")
            levelNo++
            if (levelNo > 0)
                nav_view.visibility = View.GONE
            transaction.commit()
        }
    }

    fun esteghfarAlarmOn(): Boolean? {
        return appPreferences?.getBoolean("esteghfarAlarmOn", false)
    }

    fun esteghfarAlarmStartTime(): String? {
        return appPreferences?.getString("esteghfarAlarmStartTime", "")
    }

    fun esteghfarAlarmEndTime(): String? {
        return appPreferences?.getString("esteghfarAlarmEndTime", "")
    }

    fun esteghfarAlarmRepetitionPeriod(): Int? {
        return appPreferences?.getInt("esteghfarAlarmRepetitionPeriod", 0)
    }

    fun esteghfarAlarmSounds(): String? {
        Log.d("index11d", "0111".indexOf("1").toString())
        var soundnum: String? = appPreferences?.getString("esteghfarAlarmSounds", "111111111")
        Log.d("TAGGsoundnum", soundnum)

        return appPreferences?.getString("esteghfarAlarmSounds", "111111111")
    }


    fun saveEsteghfarAlarmSounds(sequence: String) {
        Log.d("TAGGs", sequence)

        val editor = appPreferences?.edit()
        editor?.putString("esteghfarAlarmSounds", sequence)
        editor?.commit()
    }

    fun prayerTimes24(): Boolean? {
        return appPreferences?.getBoolean("prayerTimes24", true)
    }

    fun savePrayerTimes24(time24: Boolean) {
        val editor = appPreferences?.edit()
        editor?.putBoolean("prayerTimes24", time24)
        editor?.commit()
    }


    // get prayerTimesSound
    fun prayerTimesSound(): Int? {
        // make 15 السريحي is default
        print("intt " + appPreferences?.getInt("prayerTimesSound", 12))
        return appPreferences?.getInt("prayerTimesSound", 12)
    }

    // set prayerTimesSound
    fun savePrayerTimesSound(sound: Int) {
        val editor = appPreferences?.edit()
        editor?.putInt("prayerTimesSound", sound)
        /**
         * i added it
         */
        Log.d("saveprayer", "savePrayerTimesSound: " + sound)
        editor?.apply()
        Log.d("saveprayer", "savePrayerTimesSound: " + sound)

//        editor?.apply()
    }


    fun prayerTimesIqamaFajr(): Int? {
        return appPreferences?.getInt("prayerTimesIqamaFajr", 0)
    }

    fun setPrayerTimesIqamaFajr(offset: Int) {
        val editor = appPreferences?.edit()
        editor?.putInt("prayerTimesIqamaFajr", offset)
        editor?.commit()
    }

    fun prayerTimesIqamaDhuhr(): Int? {
        return appPreferences?.getInt("prayerTimesIqamaDhuhr", 0)
    }

    fun setPrayerTimesIqamaDhuhr(offset: Int) {
        val editor = appPreferences?.edit()
        editor?.putInt("prayerTimesIqamaDhuhr", offset)
        editor?.commit()
    }

    fun prayerTimesIqamaAsr(): Int? {
        return appPreferences?.getInt("prayerTimesIqamaAsr", 0)
    }

    fun setPrayerTimesIqamaAsr(offset: Int) {
        val editor = appPreferences?.edit()
        editor?.putInt("prayerTimesIqamaAsr", offset)
        editor?.commit()
    }

    fun prayerTimesIqamaMaghreb(): Int? {
        return appPreferences?.getInt("prayerTimesIqamaMaghreb", 0)
    }

    fun setPrayerTimesIqamaMaghreb(offset: Int) {
        val editor = appPreferences?.edit()
        editor?.putInt("prayerTimesIqamaMaghreb", offset)
        editor?.commit()
    }

    fun prayerTimesIqamaIsha(): Int? {
        return appPreferences?.getInt("prayerTimesIqamaIsha", 0)
    }


    fun setLatitude(latitude: Float) {
        val editor = appPreferences?.edit()
        editor?.putFloat("Lastlatitude", latitude)
        editor?.commit()
    }

    fun setLongitude(longitude: Float) {
        val editor = appPreferences?.edit()
        editor?.putFloat("LastLongituide", longitude)
        editor?.commit()
    }

    fun setCityName(cityName:String){
        val editor = appPreferences?.edit()
        editor?.putString("besoCityName", cityName)
        editor?.apply()
    }


    fun setPrayerTimesIqamaIsha(offset: Int) {
        val editor = appPreferences?.edit()
        editor?.putInt("prayerTimesIqamaIsha", offset)
        editor?.commit()
    }

    fun setPrayerTimesIqamaOn(isOn: Boolean) {
        val editor = appPreferences?.edit()
        editor?.putBoolean("prayerTimesIqamaOn", isOn)
        editor?.commit()
        schedulePrayerTimesAlarms()
    }

    fun isPrayerTimesIqamaOn(): Boolean? {
        return appPreferences?.getBoolean("prayerTimesIqamaOn", false)
    }

    fun prayerTimesCalcMethod(): Int? {
        return appPreferences?.getInt("prayerTimesCalcMethod", 4)
    }

    fun prayerTimesAlarmFajrOn(): Boolean? {
        return appPreferences?.getBoolean("prayerTimesAlarmFajrOn", true)
    }

    fun prayerTimesAlarmSunriseOn(): Boolean? {
        return appPreferences?.getBoolean("prayerTimesAlarmSunriseOn", false)
    }

    fun prayerTimesAlarmDhuhrOn(): Boolean? {
        return appPreferences?.getBoolean("prayerTimesAlarmDhuhrOn", true)
    }

    fun prayerTimesAlarmAsrOn(): Boolean? {
        return appPreferences?.getBoolean("prayerTimesAlarmAsrOn", true)
    }

    fun prayerTimesAlarmMaghrebOn(): Boolean? {
        return appPreferences?.getBoolean("prayerTimesAlarmMaghrebOn", true)
    }

    fun prayerTimesAlarmIshaOn(): Boolean? {
        return appPreferences?.getBoolean("prayerTimesAlarmIshaOn", true)
    }

    fun prayerTimesSpecialAlarmFajrOn(): Boolean? {
        return appPreferences?.getBoolean("prayerTimesSpecialAlarmFajrOn", false)
    }

    fun prayerTimesSpecialAlarmBefore(): Boolean? {
        return appPreferences?.getBoolean("prayerTimesSpecialAlarmBefore", true)
    }

    fun prayerTimesSpecialAlarmOffset(): Int? {
        Log.d(
            "TAGoffset", "prayerTimesSpecialAlarmOffset: " + appPreferences?.getInt(
                "prayerTimesSpecialAlarmOffset",
                0
            )
        )
        return appPreferences?.getInt("prayerTimesSpecialAlarmOffset", 0)
    }

    /****
     * i changed here
     */
    fun prayerTimesSpecialAlarmFajrSound(): Int? {
//        return appPreferences?.getInt("prayerTimesSpecialAlarmFajrSound", 0)
        return appPreferences?.getInt("prayerTimesSpecialAlarmFajrSound", 1)

    }

    fun saveFajrSpecialAlarmSettings(
        specialAlarmOn: Boolean, specialAlarmBefore: Boolean, specialAlarmInterval: Int
    ) {
        val editor = appPreferences?.edit()
        editor?.putBoolean("prayerTimesSpecialAlarmFajrOn", specialAlarmOn)
        editor?.putBoolean("prayerTimesSpecialAlarmBefore", specialAlarmBefore)
        editor?.putInt("prayerTimesSpecialAlarmOffset", specialAlarmInterval)
        editor?.commit()
        schedulePrayerTimesAlarms()
    }

    fun getDailyCnt(): Int? {
        val lastSaveTime = appPreferences?.getLong("last_save_day", 0)
        val today: Int = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        if (lastSaveTime!! < today) {
            val editor = appPreferences?.edit()
            editor?.putLong("last_save_day", today.toLong())
            editor?.putInt("dailySeb7aCount", 0)
            editor?.commit()
            return 0
        } else {
            return appPreferences?.getInt("dailySeb7aCount", 0)
        }
    }

    fun saveDailyCnt(reset: Boolean) {
        val lastSaveTime = appPreferences?.getLong("last_save_day", 0)
        val today: Int = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val editor = appPreferences?.edit()
        if (lastSaveTime!! < today) {
            editor?.putLong("last_save_day", today.toLong())
        }
        if (!reset)
            editor?.putInt("dailySeb7aCount", getDailyCnt()?.plus(1)!!)
        else
            editor?.putInt("dailySeb7aCount", 0)
        editor?.commit()
    }

    fun saveFajrSpecialAlarmSoundSettings(specialAlarmSound: Int) {
        val editor = appPreferences?.edit()
        editor?.putInt("prayerTimesSpecialAlarmFajrSound", specialAlarmSound)
        editor?.commit()
    }


//    prayerTimesAlarmFajrOn

    fun setPrayerTimesAlarmOn(prayerID: Int, isOn: Boolean) {
        val editor = appPreferences?.edit()
        when (prayerID) {
            1 -> editor?.putBoolean("prayerTimesAlarmFajrOn", isOn)
            2 -> editor?.putBoolean("prayerTimesAlarmSunriseOn", isOn)
            3 -> editor?.putBoolean("prayerTimesAlarmDhuhrOn", isOn)
            4 -> editor?.putBoolean("prayerTimesAlarmAsrOn", isOn)
            5 -> editor?.putBoolean("prayerTimesAlarmMaghrebOn", isOn)
            6 -> editor?.putBoolean("prayerTimesAlarmIshaOn", isOn)
        }
        editor?.commit()
        schedulePrayerTimesAlarms()
    }

    fun setPrayerTimesCalcMethod(methodId: Int) {
        val editor = appPreferences?.edit()
        editor?.putInt("prayerTimesCalcMethod", methodId)
        editor?.commit()
    }

    override fun onBackPressed() {
////        if ((nav_view.selectedItemId == R.id.navigation_zekr || nav_view.selectedItemId == R.id.navigation_sebha || nav_view.selectedItemId == R.id.navigation_more) && levelNo==0) {
//////            startActivity(Intent(this, MainActivity::class.java))
//////            Toast.makeText(this,supportFragmentManager.backStackEntryCount,Toast.LENGTH_LONG).show()
////            navView?.getMenu()?.getItem(2)?.setChecked(true)
//////            super.onBackPressed()
//////            navView?.selectedItemId = R.id.navigation_sebha
////        } else if (nav_view.selectedItemId == R.id.navigation_home && levelNo==1) {
//////            supportFragmentManager.popBackStack()
////            startActivity(Intent(this, MainActivity::class.java))
////        } else if (nav_view.selectedItemId == R.id.navigation_home && levelNo>=1) {
////            super.onBackPressed()
////        } else if (nav_view.selectedItemId == R.id.navigation_home) {
////            super.onBackPressed()
////        } else {
//////            supportFragmentManager.popBackStack("MainActivity",POP_BACK_STACK_INCLUSIVE)
//            super.onBackPressed()
////        }
//        if (levelNo>0) levelNo --
//        if (levelNo == 0)
//        nav_view.visibility = View.VISIBLE

        if (supportFragmentManager.backStackEntryCount == 1) homeFragment?.onResume()
//        else if (nav_view.getMenu().getItem(2).isChecked && inHomeScreen)
        else if (nav_view.getMenu().getItem(2).isChecked && !inHomeScreen) finish()
        super.onBackPressed()
    }


    //    fun checkLocationPermissions() {
    //        if (PermissionUtil.hasSelfPermission(
    //                this,
    //                Manifest.permission.ACCESS_FINE_LOCATION
    //            )
    //        ) {
    //            initAppAfterCheckingLocation()
    //        } else {
    //            // UNCOMMENT TO SUPPORT ANDROID M RUNTIME PERMISSIONS
    ////      Intent intent = mActivity.getPackageManager().buildRequestPermissionsIntent(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
    ////      startActivityForResult(intent, REQUEST_LOCATION);
    //            if (!LocationHelper.sLoationPermissionDenied) {
    //                requestPermissions(
    //                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
    //                    REQUEST_LOCATION
    //                )
    //            }
    //        }
    //    }


    //    fun onTaskRemoved(rootIntent: Intent?) {
    //        //Set what to do when task is removed
    //        super.onTaskRemoved(rootIntent)
    //    }

    //    override fun onDestroy() {
    //        //What to do when service i destroyed
    //        scheduleAlarm()
    //        super.onDestroy()
    //    }

    override fun onStop() {
        super.onStop()
//        scheduleAlarm()
    }


}


//    fun replaceFragmentsWithDetail(fragmentClass: Class<*>,title : String,details:String) {
//        var fragment: Fragment? = null
//        try {
//            fragment = fragmentClass.newInstance() as Fragment
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        val fragmentManager: FragmentManager = supportFragmentManager
//        if (fragment != null) {
//            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
//            var bundle = Bundle()
//            bundle.putString("ARG_PARAM1", title)
//            bundle.putString("ARG_PARAM2", details)
//            fragment.arguments = bundle
//            transaction.replace(R.id.container, fragment)
//            transaction.addToBackStack("MainActvity")
//            levelNo++
//            if (levelNo>0)
//                nav_view.visibility = View.GONE
//            transaction.commit()
//        }
//    }


/*
    @SuppressLint("SimpleDateFormat")
    fun setPrayerAlarm2(
        context: Context?, prayerTime: String, prayerID: Int, offset: Int
    ) {

        Log.d("TAGEnteredKamMllee", "setPrayerAlarm: $prayerTime")

        val alarmManager =
            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var prayerName = ""
        try {

            /*

//            val calPrayerTime = Calendar.getInstance()
            Log.d("TAGsubintid", "setPrayerAlarm: $prayerID")
//            Log.d("TAGsubint1", "setPrayerAlarm: " + prayerTime.substring(0, 2).toInt())
//            Log.d("TAGsubint2", "setPrayerAlarm: " + prayerTime.substring(0, 2))
//            Log.d("TAGsubint3", "setPrayerAlarm: $prayerTime")
//            calPrayerTime[Calendar.HOUR_OF_DAY] = prayerTime.substring(0, 2).toInt()
//            calPrayerTime[Calendar.MINUTE] = prayerTime.substring(3, 5).toInt()
//            Log.d("TAGsubintcal", "setPrayerAlarm: " + calPrayerTime[Calendar.HOUR_OF_DAY])

            val currentTime = Date(System.currentTimeMillis())
//            val date = Calendar.getInstance()
//            val timeInSecs = date.timeInMillis
//            val afterAdding10Mins = Date(timeInSecs + 2 * 60 * 1000)

//            val calendar = Calendar.getInstance()
//
//            var timeMilli2 = calendar.timeInMillis
//            println("Time in milliseconds using Calendar: $timeMilli2")
////            timeMilli2 += 120000
//
//            Log.d("TAGwwwwwwwcal", "setPrayerAlarm: ${timeMilli2 + 120000}")
//            Log.d("TAGwwwwwww", "setPrayerAlarm: $calPrayerTime")
//            Log.d("TAGwwwwwwwq", "setPrayerAlarm: ${currentTime.time}")
//            Log.d("TAGwwwwwwwTTTT", "setPrayerAlarm: ${calPrayerTime.time}")
//
//            if (currentTime.compareTo(calPrayerTime.time) > 0) {
//                Log.d("TAGCompar", "setPrayerAlarm: $prayerID logined")
//
//                /**
//                 * why ? to make alarm next day
//                 */
//                calPrayerTime.add(Calendar.DAY_OF_MONTH, 1)
//            }

            */

            when (prayerID) {
                100 -> prayerName = "Fajr Azan"
                101 -> prayerName = "Fajr Iqama"
                110 -> prayerName = "Sunrise Azan"
                120 -> prayerName = "Dhuhr Azan"
                121 -> prayerName = "Dhuhr Iqama"
                130 -> prayerName = "Asr Azan"
                131 -> prayerName = "Asr Iqama"
                140 -> prayerName = "Maghreb Azan"
                141 -> prayerName = "Maghreb Iqama"
                150 -> prayerName = "Isha Azan"
                151 -> prayerName = "Isha Iqama"
                else -> {
                }
            }

/*
//            Log.d("TAGqqqqqqqqqq", "setPrayerAlarm: " + calPrayerTime.timeInMillis)
//            calPrayerTime.add(Calendar.MILLISECOND, offset)

//            calPrayerTime.timeInMillis = System.currentTimeMillis();
//            calPrayerTime.set(Calendar.HOUR_OF_DAY, 1);
//            calPrayerTime.set(Calendar.MINUTE, 52);


//            calPrayerTime.set(Calendar.HOUR_OF_DAY,1)
//            calPrayerTime.set(Calendar.MINUTE, 30)
//
//            Log.v("Alarm Set: ", " For $prayerName")
//            Log.v("Alarm Set: ", "at " + calPrayerTime.time)

//            val intent = Intent(applicationContext, NotificationReceiver::class.java)
//            var sender: PendingIntent = PendingIntent.getBroadcast(
//                applicationContext,
//                prayerID,
//                intent,
//                0
//            );

*/


            var calendar5: Calendar = Calendar.getInstance()

            calendar5[Calendar.HOUR_OF_DAY] = prayerTime.substring(0, 2).toInt()
            calendar5[Calendar.MINUTE] = prayerTime.substring(3, 5).toInt()

            Log.d(
                "TAGsubintidyyy5",
                ": ------- ${
                    prayerTime.substring(0, 2).toInt()
                } -------  ${calendar5.get(Calendar.HOUR_OF_DAY)}"
            )


            Log.d(
                "TAGsubintidyyy",
                ": ------- $prayerName -------  ${calendar5.get(Calendar.MINUTE)}"
            )


            /*****
             * what i want to achieve now is to change here all calendars with offset 0
             *
             * but if the current time now is 11:45 the fajr calender time offset
             * should be 0 or 1
             * calendar5.add(Calendar.DAY_OF_MONTH, 1)
             *
             */

            val sdf = SimpleDateFormat("hh:mm")
            val currentDate = sdf.format(Date())
            System.out.println(" C DATE is  " + currentDate + " hh " + prayerID)
            if (calendar5.after(currentDate)) {
                Log.d("TAGFinally", "setPrayerAlarm: ")
                calendar5.add(Calendar.DAY_OF_MONTH, 1)
            }
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > prayerTime.substring(0, 2)
                    .toInt()
            ) {
                Log.d("TAGCompar", "setPrayerAlarm: $prayerID loginedd")

                calendar5.add(Calendar.DAY_OF_MONTH, 1)
            } else if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == prayerTime.substring(
                    0, 2
                )
                    .toInt() && Calendar.getInstance()
                    .get(Calendar.MINUTE) >= prayerTime.substring(3, 5)
                    .toInt()
            ) {
                Log.d("TAGCompar2", "setPrayerAlarm: $prayerID loginedd")

                calendar5.add(Calendar.DAY_OF_MONTH, 1)
            } else {
                Log.d(
                    "TAGCompar",
                    "setPrayerAlarm: $prayerID elseed +  ${calendar5.timeInMillis}"
                )
                calendar5.add(Calendar.DAY_OF_MONTH, 0)
            }


            /**
             * start broadcast at first
             */

            val alarmManager6 =
                this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent6 = Intent(this, NotificationReceiver::class.java)
            val pendingIntent2 = PendingIntent.getBroadcast(
                this.applicationContext,
                prayerID,
                intent6,
                0
            )
//            intent6.putExtra("id", 40);
            intent6.putExtra("id", prayerID);

            alarmManager6.setExact(AlarmManager.RTC_WAKEUP, calendar5.timeInMillis, pendingIntent2);

/*
//            alarmManager6.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis() + 2* 60000,
//                2*60*1000,
//                pendingIntent2
////                AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+calendar5.timeInMillis, 2*60000, pendingIntent2
//            )


            /**
             * start services directly
             */
            /*
//            val intent5 = Intent(this, CustomIntentService::class.java)
//            val pendingIntent = PendingIntent.getService(
//                this.applicationContext,
//                prayerID,
//                intent5,
//                0
//            )
//        intent5.putExtra("id", prayerID);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+2*60*1000, pendingIntent)
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis() ,
//                calendar5.timeInMillis,
//                pendingIntent
////            AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+calendar5.timeInMillis, 4*60000, pendingIntent
//            )
            */
            /*
//            alarmManager.set(
//                AlarmManager.RTC_WAKEUP,
//                 calendar5.timeInMillis,
//                pendingIntent
//            )

//            alarmManager5.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                System.currentTimeMillis() + 120000,
//                calendar5.timeInMillis,
//                pendingIntent
//                //AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+calendar5.timeInMillis, 4*60000, pendingIntent
//            )

/*


//            val calendar2: Calendar = Calendar.getInstance().apply {
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, 2)
//                    set(Calendar.MINUTE, 11)
//            }

//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calPrayerTime.timeInMillis,
//                AlarmManager.INTERVAL_DAY, sender);

//            val calendar4 = Calendar.getInstance()
//            calendar4.timeInMillis = java.lang.System.currentTimeMillis()
//            calendar4.set(Calendar.HOUR_OF_DAY, 2)
//            calendar4.set(Calendar.MINUTE, 42)
//
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar4.timeInMillis,sender)

//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar2.timeInMillis,
//                1000 * 60 *2,
//                sender
//            )


            //            alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(System.currentTimeMillis()
//                + calendar2.timeInMillis,sender),sender)


            // this alarm not work in low battery
//            alarmManager.setExactAndAllowWhileIdle(
//                AlarmManager.RTC_WAKEUP, calendar2.timeInMillis,
//                sender
//                /*pIntent(context, prayerID)*/
//            )


            //            alarmManager.setAlarmClock(
//                AlarmClockInfo(
//                    calPrayerTime.timeInMillis,
//                    pIntent(context, prayerID)
//                ), pIntent(context, prayerID)
//            )


 */
*/

            */
        } catch (e: java.lang.Exception) {
        }
    }
*/

