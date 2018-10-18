package org.codedocs.codedocsapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_register.*

/**
 * A login screen that offers login via email/password.
 */
class Register : AppCompatActivity(){
    private var mAuth: FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth=FirebaseAuth.getInstance()
        val button=findViewById<Button>(R.id.registerButton)
        button.setOnClickListener{
            verification()
        }
        val already=findViewById<TextView>(R.id.already_member)
        already.setOnClickListener(){
            signin()
        }

    }
    private fun signin(){
        val intent= Intent(this@Register,Login::class.java)
        startActivity(intent)
    }
    private fun verification(){
        val email=email_register.text.toString()
        val password=password_registration.text.toString()
        val conf=confirm_password.text.toString()
        if(email.isEmpty()){
            Toast.makeText(this,"Email Address Empty",Toast.LENGTH_LONG).show()
            return
        }
        if(password.length<=6){
            Toast.makeText(this,"Password Short",Toast.LENGTH_LONG).show()
            return
        }
       if(password != conf){
            Toast.makeText(this,"Confirmation Password do not Match",Toast.LENGTH_LONG).show()
            return
        }
        register()
    }
    private fun register(){
        val email=email_register.text.toString()
        val password=password_registration.text.toString()
        mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,object :OnCompleteListener<AuthResult>{
            override fun onComplete(task: Task<AuthResult>) {
                if(task.isSuccessful){
                    val user=mAuth!!.currentUser
                    val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                    val editor = preferences.edit()
                    editor.putString("email",email)
                    editor.putBoolean("status",true)
                    editor.putString("userid",user!!.uid)
                    editor.commit()
                    val intent= Intent(this@Register,MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Log.e( "createUserWithEmail", task.exception!!.toString());
                    Toast.makeText(applicationContext, task.exception!!.toString(),
                            Toast.LENGTH_SHORT).show()

                }
            }
        })
    }


}
