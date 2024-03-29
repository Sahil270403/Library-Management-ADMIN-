package com.example.librarymanagementadmin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
            auth = FirebaseAuth.getInstance()
            progressBar = findViewById(R.id.progressBar1)


            val check = GoogleSignIn.getLastSignedInAccount(this)

            if(check!=null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }


            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this , gso)

            findViewById<Button>(R.id.gSignInBtn).setOnClickListener {
                progressBar.visibility = View.VISIBLE
                signInGoogle()
            }
        }
        private fun signInGoogle(){
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)

        }

        private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if (result.resultCode == Activity.RESULT_OK){

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
                Toast.makeText(this,"Signing IN", Toast.LENGTH_SHORT ).show()

            }
        }

        private fun handleResults(task: Task<GoogleSignInAccount>) {
            if (task.isSuccessful){
                val account : GoogleSignInAccount? = task.result
                if (account != null){
                    updateUI(account)
                }
            }
            else{
                Toast.makeText(this, task.exception.toString() , Toast.LENGTH_LONG).show()
            }
        }


        private fun updateUI(account: GoogleSignInAccount) {
            val credential = GoogleAuthProvider.getCredential(account.idToken , null)

            auth.signInWithCredential(credential).addOnCompleteListener{
                if(it.isSuccessful){
//                    ProgressBar.visibility = View.GONE
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()

                }
            }

        }

}