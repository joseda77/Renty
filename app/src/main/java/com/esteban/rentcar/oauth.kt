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
import com.google.firebase.auth.FirebaseAuth
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
            goMainActivity()
        } else {
            Toast.makeText(this,"Error al iniciar sesión", Toast.LENGTH_LONG).show();
        }
    }
    private fun goMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
