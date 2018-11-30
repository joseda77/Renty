package com.esteban.rentcar

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import kotlinx.android.synthetic.main.activity_oauth.*

class oauth : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    var mAuth: FirebaseAuth? =null
    var callbackManager: CallbackManager? = null
    //Google
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001
    private var flag: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)
        //Facebook
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create()
        login_button.setReadPermissions("email", "public_profile")
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // Login exitoso con fb
                goMainActivity()
            }
            override fun onCancel() {
                // App code
            }
            override fun onError(exception: FacebookException) {
                // App code
            }
        })
        //Google
        val gso : GoogleSignInOptions = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_button.setOnClickListener{
            flag = 1
            signIn()
        }
    }
    //Facebook
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(flag ==0) {
            super.onActivityResult(requestCode, resultCode, data)
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }else
        {
            super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == RC_SIGN_IN) {
                val result : GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                handleSignInResult(result)
            }
        }
    }
    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient!!.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    private fun handleSignInResult(result: GoogleSignInResult /*completedTask: Task<GoogleSignInAccount>*/) {
        Log.i("Hola", result.toString())
        if(result.isSuccess) {
            // Authenticated
            val account : GoogleSignInAccount? = result.signInAccount
            Log.i("Hola", account.toString())
            Toast.makeText(this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
            getToken()
            goMainActivity()
        } else {
            Toast.makeText(this,"Error al iniciar sesión", Toast.LENGTH_LONG).show();
        }
    }
    private fun goMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun getToken(){
        val cUser = mAuth?.currentUser
        if (cUser==null) {
            /*val intent = Intent(this, LoginActivity::class.java)
            context.startActivity(intent)*/
            Toast.makeText(this, "EL usuario es nulo", Toast.LENGTH_LONG).show()
            //createLoginFragment()
        }else {
            //println(currentUser.phoneNumber)

            cUser.getIdToken(true).addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                override fun onComplete(task: Task<GetTokenResult>) {
                    if (task.isSuccessful()) {
                        val idToken = task.getResult()!!.token
                        // Send token to your backend via HTTPS
                        // ...
                        println("token has been refresh")
                    } else {
                        // Handle error -> task.getException();
                        println("Error Error Error Error Error Error")
                        Toast.makeText(this@oauth, "Error al obtener el token",Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

    }
}
