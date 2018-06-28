package org.codedocs.codedocsapp

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.notification__layout.*
import java.util.*
import android.view.Gravity
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import kotlinx.android.synthetic.main.popup.*
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.widget.Button


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [notifications.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [notifications.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class notifications : Fragment() {
    var db = FirebaseFirestore.getInstance()
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var mView: View? = null
    var nname: String? = null
    var ndesc: String? = null
    var fdesc: String? = null
    var ncount: Int? = null
    var i: Int = 0
    var j: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_notifications, container, false)
        getnotifc()
        return mView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {

        }
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment notifications.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                notifications().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }



    fun tvn(name:String?,desc:String?,fdesc:String?){
        var cardView:CardView= CardView(getContext()!!)
        var linearLayout:LinearLayout=LinearLayout(getContext()!!)
        var tvName:TextView=TextView(getContext()!!)
        tvName!!.setText(name)
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
        tvName.setTextColor(Color.parseColor("#000000"))
        var tvDesc:TextView=TextView(getContext())
        tvDesc!!.setText(desc)
        tvDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
        tvDesc.setTextColor(Color.parseColor("#ffffff"))
        linearLayout!!.setOrientation(LinearLayout.VERTICAL)
        linearLayout!!.addView(tvName)
        linearLayout!!.addView(tvDesc)
        linearLayout.setBackgroundColor(Color.parseColor("#546E7A"))

        cardView!!.addView(linearLayout)
        cardView!!.setOnClickListener{
            initiatePopupWindow(cardView!!,name,fdesc)

        }

        var ncont:LinearLayout=ncontainer

        ncont.addView(cardView)
        ncont.setOnClickListener{
            initiatePopupWindow(cardView!!,name,desc)
        }

    }

    private fun initiatePopupWindow(v: View,name: String?,desc:String?) {
        try {Log.e("blehpop1","f1")
            //We need to get the instance of the LayoutInflater, use the context of this activity
            val inflater = getContext()!!.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val customView = inflater.inflate(R.layout.popup, null)
            var tname=customView.findViewById<TextView>(R.id.ptitle)
            var tdesc=customView.findViewById<TextView>(R.id.pdesc)
            var tbutton=customView.findViewById<Button>(R.id.pcbutton)

            var mPopupWindow = PopupWindow(
                    customView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            tname.setText(name)
            tdesc.setText(desc)
            mPopupWindow.setFocusable(true);
            mPopupWindow.setTouchable(true);
            tbutton.setOnClickListener{
                mPopupWindow.dismiss()
            }


            mPopupWindow.showAtLocation(v, Gravity.CENTER,0,0);

            // display the popup in the center



            mPopupWindow.update();


        } catch (e: Exception) {
            Log.e("blehpop2",e.message)
        }

    }
    fun getnotifc() {

        var notifs = db.collection("count").document("counts")
        notifs.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                ncount = Integer.parseInt(task.result.get("count").toString())
                Log.e("bleh2", "success")
                Log.e("bleh", "" + ncount)
                Timer().schedule(object : TimerTask() {
                    override fun run () {
                        i = ncount as Int
                        getnotis()

                    }
                }, 3000)



            }
        }


    }


    fun getnotis() {
        var k=i
        var l=(k-3)
        while (i>0||i==1||i==2||i==3) {
            var notifs = db.collection("notifs").document(i.toString())
            notifs.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    nname=task.result.get("name").toString()
                    ndesc=task.result.get("desc").toString()
                    fdesc=task.result.get("fdesc").toString()

                    Log.e("bleh5",nname)
                    Log.e("bleh6",ndesc)
                    tvn(nname!!,ndesc!!,fdesc)


                }

            }
            i--
        }


    }

}
