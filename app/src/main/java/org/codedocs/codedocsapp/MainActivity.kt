package org.codedocs.codedocsapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val fm=supportFragmentManager
    val ft=fm.beginTransaction()
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.xhome -> {
                val fragment=home()
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();

                return@OnNavigationItemSelectedListener true
            }
            R.id.xnotifs -> {
                val fragment=notifications()
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();

                return@OnNavigationItemSelectedListener true
            }
            R.id.xweird -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm=supportFragmentManager
        val ft=fm.beginTransaction()
        val fragment=home()
        ft.replace(R.id.container,fragment).addToBackStack(null).commit();
        val intent = Intent(this,fcm::class.java)
        startService(intent)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
