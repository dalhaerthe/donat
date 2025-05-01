package a.rav.donat

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate

class MainActivity : AppCompatActivity() {


    lateinit var donDate: LocalDate
    lateinit var nextDate: LocalDate


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var donDateEt = findViewById<EditText>(R.id.donDateEt)
        var nextDateEt = findViewById<EditText>(R.id.nextDateEt)



        donDate = readDonDate()!!
        nextDate = donDate.plusDays(14)
        donDateEt.setText(donDate.toString())

        nextDateEt.setText(nextDate.toString())
        var donBtn: Button = findViewById<Button>(R.id.donkabt)
        donBtn.setOnClickListener() {
            saveDonDate()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }


    }

    //odczyt daty donacji:

    @RequiresApi(Build.VERSION_CODES.O)
    fun readDonDate(): LocalDate? {
        //TODO

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val readDate = sharedPref.getString("data", "1999-01-01")

return LocalDate.parse(readDate)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveDonDate() {
        //zapis daty donacji i aktualizacja
        donDate = LocalDate.now()
        Log.d("aaa",donDate.toString())

        val sharedPr = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPr.edit()) {
            putString("donData", donDate.toString())
            apply()
        }


    }
}