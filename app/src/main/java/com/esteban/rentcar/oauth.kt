package com.esteban.rentcar

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_oauth.*

class oauth : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {


    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)




        val gso : GoogleSignInOptions = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_button.setOnClickListener{
            signIn()
        }

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

            super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == RC_SIGN_IN) {
                val result : GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                handleSignInResult(result)
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
        if(result.isSuccess) {
            // Authenticated
            val account : GoogleSignInAccount? = result.signInAccount
            Toast.makeText(this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
            var intent:Intent = getIntent() //Intent mediante el que se llamó la actividad oauth
            intent.putExtra("idToken",account!!.idToken!!)
            val credential = GoogleAuthProvider.getCredential(account!!.idToken!!, null)
            mAuth!!.signInWithCredential(credential).addOnCompleteListener(this)
            {task ->
                if (task.isSuccessful) {
                    idToken = account!!.idToken!!
                    setResult(0,intent)
                    finish()
                } else {
                    Toast.makeText(this,"Error al guardar la cuenta en Firebase",
                            Toast.LENGTH_LONG).show()
                }

            }
        } else {
            Toast.makeText(this,"Error al iniciar sesión", Toast.LENGTH_LONG).show();
        }
    }

    companion object {
        var idToken = ""
        fun getToken(): String {
            return idToken
        }
    }

}
