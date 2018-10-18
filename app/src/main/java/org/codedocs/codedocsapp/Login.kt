package org.codedocs.codedocsapp

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.security.AccessController.getContext

class Login : AppCompatActivity() {
    var mAuth: FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth=FirebaseAuth.getInstance()
        val button=findViewById<Button>(R.id.loginButton)
        button.setOnClickListener{
            verification()
        }
        val already=findViewById<TextView>(R.id.registernow)
        already.setOnClickListener{
            register()
        }

    }
    private fun register(){
        val intent= Intent(this@Login,Register::class.java)
        startActivity(intent)
    }
    private fun verification(){
        val email=email_login.text.toString()
        val password=password_login.text.toString()
        if(email.isEmpty()){
            Toast.makeText(this,"Email Address Empty",Toast.LENGTH_LONG).show()
            return
        }
        if(password.length<=6){
            Toast.makeText(this,"Password Short",Toast.LENGTH_LONG).show()
            return
        }
        signin()

    }
    private fun signin(){
        val email=email_login.text.toString()
        val password=password_login.text.toString()
        mAuth!!.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult> {task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("signin", "signInWithEmail:success");
                        val user = mAuth!!.currentUser;
                        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                        val editor = preferences.edit()
                        editor.putString("email",email)
                        editor.putBoolean("status",true)
                        editor.putString("userid",user!!.uid)
                        editor.commit()
                        val intent= Intent(this@Login,MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("signin", "signInWithEmail:failure", task.getException());
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
        )



    }
}
