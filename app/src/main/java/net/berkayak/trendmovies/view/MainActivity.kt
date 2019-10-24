package net.berkayak.trendmovies.view

import android.content.pm.ActivityInfo
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import net.berkayak.trendmovies.R
import net.berkayak.trendmovies.model.service.MovieServiceManager
import net.berkayak.trendmovies.utilities.Const

class MainActivity : AppCompatActivity() {
    var isTablet : Boolean = false
    lateinit var detailTrigger: DetailTrigger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is DetailFragment){ //for use communicator we got the reference
            detailTrigger = fragment.getDetailTrigger()
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1){
            super.onBackPressed()
        } else
            finish()

    }

    fun init(){
        isTablet = resources.getBoolean(R.bool.isTablet) //for detect device
        if (!isTablet){
            startFragment(ListFragment::class.java, null)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    //ListFragment notifity from here
    fun listItemSelected(movieID: Int){
        if (isTablet){ //if tablet we just communicate with interface
            if (detailTrigger != null)
                detailTrigger.movieSelected(movieID)
        } else { //if phone we send data with bundle
            var bnd = Bundle()
            bnd.putInt(Const.KEY_MOVIE_ID, movieID)
            startFragment(DetailFragment::class.java, bnd)
        }
    }

    //use this method just phone.
    fun <T: Fragment> startFragment(clazz: Class<T>, bnd: Bundle?) {
        var f: Fragment? = null
        try {
            f = clazz.newInstance() as Fragment
        } catch (e: InstantiationException) {
            f = null
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            f = null
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: ClassCastException){
            e.printStackTrace()
        }
        if (f != null) {
            f.arguments = bnd
            var fm = supportFragmentManager.beginTransaction()
            fm.replace(R.id.fragmentHolder, f, clazz.name)
            fm.addToBackStack(clazz.name)

            fm.commit()
        }
    }
}
