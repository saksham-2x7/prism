package com.prism.app.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

object GoogleAuthManager {

    // Ideally, this should be fetched from BuildConfig or R.string.default_web_client_id
    private const val WEB_CLIENT_ID = "YOUR_WEB_CLIENT_ID_HERE"

    private fun getSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestIdToken(WEB_CLIENT_ID)
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun getSignInIntent(context: Context): Intent {
        return getSignInClient(context).signInIntent
    }

    fun handleSignInResult(data: Intent?): GoogleSignInAccount? {
        return try {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.getResult(ApiException::class.java)
        } catch (e: ApiException) {
            null
        }
    }

    fun signOut(context: Context) {
        getSignInClient(context).signOut()
    }

    fun getCurrentUser(context: Context): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }
}
