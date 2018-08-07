package org.codedocs.codedocsapp


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var id:String?=null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.xhome -> {
                var newFrag: Fragment?=null
                val fm=supportFragmentManager
                val ft=fm.beginTransaction()
                val oldfragment=fm.findFragmentByTag(""+id)
                if(oldfragment!=null)
                    ft.hide(oldfragment)
                newFrag=fm.findFragmentByTag("1")
                if(newFrag==null){
                    newFrag=home()
                    ft.add(R.id.container,newFrag,"1")

                }


                ft.show( newFrag );
                ft.commit();
                id= 1.toString()
                return@OnNavigationItemSelectedListener true
            }
            R.id.xnotifs -> {
                var newFrag: Fragment?=null
                val fm=supportFragmentManager
                val ft=fm.beginTransaction()
                val oldfragment=fm.findFragmentByTag(""+id)
                if(oldfragment!=null)
                    ft.hide(oldfragment)
                newFrag=fm.findFragmentByTag("2")
                if(newFrag==null){
                    newFrag=notifications()
                    ft.add(R.id.container,newFrag,"2")

                }


                ft.show( newFrag );
                ft.commit();
                id= 2.toString()
                return@OnNavigationItemSelectedListener true
            }
            R.id.xweird -> {
                var newFrag: Fragment?=null
                val fm=supportFragmentManager
                val ft=fm.beginTransaction()
                val oldfragment=fm.findFragmentByTag(""+id)
                if(oldfragment!=null)
                    ft.hide(oldfragment)
                newFrag=fm.findFragmentByTag("3")
                if(newFrag==null){
                    newFrag=calander()
                    ft.add(R.id.container,newFrag,"3")

                }


                ft.show( newFrag );
                ft.commit();
                id= 3.toString()
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
        id= 1.toString()
        ft.replace(R.id.container,fragment,id).addToBackStack(null).commit();
        id= 1.toString()
        val intent = Intent(this,fcm::class.java)
        startService(intent)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
