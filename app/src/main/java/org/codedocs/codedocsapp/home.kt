package org.codedocs.codedocsapp

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.EventLog
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.llno
import org.codedocs.codedocsapp.R.id.invisible

import java.net.URL
import java.util.*
import kotlin.concurrent.timerTask




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [home.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [home.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class home : Fragment() {
    var db = FirebaseFirestore.getInstance()
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var mView: View? = null
    var nname: String? = null
    var hdesc: String? = null
    var hname: String? = null
    var ndesc: String? = null
    var ncount: Int? = null
    var hcount: Int? = null
    var i: Int = 0
    var j: Int = 0
    var nurl:String?=null
    var hurl:String?=null
    var progd:ProgressDialog?=null




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_home, container, false)

       activity!!.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progd= ProgressDialog(context)
        progd!!.setMessage("Loading")
        progd!!.show()
        progd!!.setCancelable(false)
        progd!!.setCanceledOnTouchOutside(false)
        gethelc()
        return mView
    }






    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }




    fun gethelc(){
        var notifs = db.collection("hcount").document("count")
        notifs.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                hcount = Integer.parseInt(task.result.get("count").toString())

                Timer().schedule(object : TimerTask() {
                    override fun run () {
                        j = hcount as Int
                        gethels()

                    }
                }, 3000)



            }

        }
    }
    fun gethels() {
        while (j>0) {
            var hl = db.collection("hlights").document(j.toString())
            hl.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hname=task.result.get("name").toString()
                    hdesc=task.result.get("desc").toString()


                    tvh(hname!!,hdesc!!)
                }

            }
            j--
        }

        progd!!.dismiss()


        getActivity()!!.runOnUiThread(object:Runnable {
                public override fun run() {
                    getActivity()!!.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            })




    }
    fun tvh(str:String,desc:String){
        val tvt=TextView(getContext())
        val tvd=TextView(getContext())
        tvt.setText(str)
        tvt.setHeight(100)
        tvt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
        tvt.setTextColor(Color.parseColor("#F5F5F5"))
        tvd.setText(desc)
        tvd.setHeight(100)
        tvd.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
        tvd.setTextColor(Color.parseColor("#000000"))
        llhe.addView(tvt)
        llhe.addView(tvd)

    }

}