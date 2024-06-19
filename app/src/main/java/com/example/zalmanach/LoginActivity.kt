package com.example.zalmanach

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zalmanach.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // ------------------------ Variablen Deklarieren, später Initialisiert ------------------------
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --------------------------- SharedPreferences initialisieren ----------------------------
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Prüfen, ob der Benutzer bereits eingeloggt ist und Start der Main, ansonsten -false
        if (sharedPreferences.getBoolean("is_logged_in", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // ---------------- Die Eingaben des Benutzers in einen String Konvertieren ----------------
        binding.btnLogin.setOnClickListener {
            val email = binding.tieUserEmail.text.toString()
            val password = binding.tiePasswort.text.toString()
            val passwordRepeat = binding.tiePasswortRepeat.text.toString()

            // Anmeldeinformationen Überprüfen und speichern
            if (isValidateEmailAndPassword(email, password, passwordRepeat)) {
                sharedPreferences.edit().apply {
                    putString("email", email)
                    putBoolean("is_logged_in", true)
                    apply()
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    // ---------------------------- Überprüft die Anmeldeinformationen -----------------------------
    private fun isValidateEmailAndPassword(email: String, password: String, passwordRepeat: String): Boolean {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ungültige Email Adresse", Toast.LENGTH_LONG).show()
            return false
        }

        if (!isPasswordValid(password)) {
            Toast.makeText(this, "Passwort muss min. 8 Zeichen, 1 Zahl, 1 Großbuchstaben und 1 Sonderzeichen enthalten", Toast.LENGTH_LONG).show()
            return false
        }

        if (password != passwordRepeat) {
            Toast.makeText(this, "Passwörter müssen Identisch sein!", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    // ...  die Passwortkriterien
    private fun isPasswordValid(password: String): Boolean {
        return when {
            password.isBlank() || password.contains(" ") -> false
            password.length < 8 -> false
            !password.any { it.isDigit() } -> false
            !password.any { it.isUpperCase() } -> false
            !password.any { it.isLowerCase() } -> false
            !password.any { it in "!@#\$%^&*()-_=+{}[]|:;\"'<>,.?/" } -> false
            else -> true
        }
    }
}
