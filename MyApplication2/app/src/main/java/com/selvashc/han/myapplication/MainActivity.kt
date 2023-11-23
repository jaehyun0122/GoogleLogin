package com.selvashc.han.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.services.youtube.YouTubeScopes


class MainActivity : ComponentActivity() {

    private lateinit var mGoogleSignInAccount: GoogleSignInClient

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if(account != null){
            Log.d("jay", "already Login "+account.email)
        }else{
            Log.d("jay", "first")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(YouTubeScopes.YOUTUBEPARTNER))
            .requestServerAuthCode(getString(R.string.client_id))
            .build()

        mGoogleSignInAccount = GoogleSignIn.getClient(this, gso)

        val loginBtn = findViewById<SignInButton>(R.id.Login)
        loginBtn.setOnClickListener {
            signIn()
        }

        val logOutBtn = findViewById<Button>(R.id.Logout)
        logOutBtn.setOnClickListener {
            signOut()
        }


    }

    private fun signIn(){
        val signInIntent: Intent = mGoogleSignInAccount.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private fun signOut(){
        mGoogleSignInAccount.signOut()
            .addOnCompleteListener(this){
                Log.d("jay", "Logout")
            }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("jay", result.resultCode.toString())

        val data: Intent? = result.data
        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(data)

        handleSignData(task)
    }

    private fun handleSignData(completedTask: Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val authCode = account.serverAuthCode

            LoginRepository(this   ).getAccessToken(authCode!!)

        }catch (e: ApiException){
            Log.e("jay", "error "+e.printStackTrace())
        }

    }



}

