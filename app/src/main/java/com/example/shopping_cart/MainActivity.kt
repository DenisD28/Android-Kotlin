package com.example.shopping_cart

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shopping_cart.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialize Firebase Auth
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val isConnected = isNetworkConnected(applicationContext)

        if (isConnected) {
            binding.btnProductsList.visibility = View.INVISIBLE
        } else {
            binding.btnStart.visibility = View.INVISIBLE
            binding.btnRegistrar.visibility = View.INVISIBLE
            binding.btnProductsList.visibility = View.VISIBLE
        }

        binding.btnProductsList.setOnClickListener{
            val callAllProductActivity = Intent(this, ShoppingListActivity::class.java)
            startActivity(callAllProductActivity)
        }


        binding.btnStart.setOnClickListener {
            val gmail = binding.TextEmail.text.toString()
            val password = binding.TextPassword.text.toString()

            val verifyGmail = validateGmail(gmail)

            if (verifyGmail) {
                accessAccount(gmail, password)
                binding.TextEmail.setText("")
                binding.TextPassword.setText("")
            }
        }

        binding.btnRegistrar.setOnClickListener {
            val gmail = binding.TextEmail.text.toString()
            val password = binding.TextPassword.text.toString()

            val verifyGmail = validateGmail(gmail)

            if (verifyGmail) {
                createAccount(gmail, password)
                binding.TextEmail.setText("")
                binding.TextPassword.setText("")
            }
        }

    }

    fun validateGmail(gmail: String): Boolean {
        // Patrón de expresión regular para verificar si es una dirección de correo electrónico válida
        val pattern = Pattern.compile("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")

        // Verificar si el email coincide con el patrón
        val isEmailValid = pattern.matcher(gmail).matches()

        if (!isEmailValid) {
            Toast.makeText(this, "El correo ingresado no es valid", Toast.LENGTH_SHORT).show()
            binding.TextEmail.setText("");
            return false
        }
        return true
    }


    fun accessAccount(gmail: String, password: String) {
        auth.signInWithEmailAndPassword(gmail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("test", "signInWithEmail:success")
                    val user = auth.currentUser

                    val callAllProductActivity = Intent(this, AllProductActivity::class.java)
                    startActivity(callAllProductActivity)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("test", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

    fun createAccount(gmail: String, password: String) {
        auth.createUserWithEmailAndPassword(gmail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("test", "createUserWithEmail:success")
                    Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show()

                    val callAllProductActivity = Intent(this, AllProductActivity::class.java)
                    startActivity(callAllProductActivity)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("test", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
//                    updateUI(null)
                }
            }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Comprobar la disponibilidad de la red
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
