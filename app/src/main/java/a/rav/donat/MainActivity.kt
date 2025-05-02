package a.rav.donat

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {


    lateinit var donDate: LocalDate
    lateinit var nextDate: LocalDate
    lateinit var lek: LocalDate
    lateinit var donDateEt: TextView
    lateinit var nextDateEt: TextView
    lateinit var donBtn: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        donDateEt = findViewById<EditText>(R.id.donDateEt)
        nextDateEt = findViewById<EditText>(R.id.nextDateEt)
        donBtn = findViewById<Button>(R.id.donkabt)
var lekBtn=findViewById<Button>(R.id.lek)
lekBtn.setOnClickListener() {
    setLek()
}

        refreshView()
        donBtn.setOnClickListener() {
            saveDonDate()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun refreshView() {
        donDate = readDonDate()!!
        nextDate=readNextDate()!!
        donDateEt.text = donDate.toString()

        nextDateEt.text = nextDate.toString()
    }

    //odczyt daty donacji:

    @RequiresApi(Build.VERSION_CODES.O)
    fun readDonDate(): LocalDate? {
        //TODO

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val readDate = sharedPref.getString("donDate", "1999-01-01")

        return LocalDate.parse(readDate)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun readNextDate(): LocalDate? {
        //TODO

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val readDate = sharedPref.getString("nextDate", "1999-01-01")

        return LocalDate.parse(readDate)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveDonDate() {
        //zapis daty donacji i aktualizacja
        donDate = LocalDate.now()

        nextDate = donDate.plusDays(57)

        if (saveProps()) return
        refreshView()
    }

    private fun saveProps(): Boolean {
        val sharedPr = getPreferences(MODE_PRIVATE) ?: return true
        with(sharedPr.edit()) {
            putString("donDate", donDate.toString())
            putString("nextDate", nextDate.toString())

            apply()
        }
        return false
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setLek() {
        lek = LocalDate.now().plusDays(14).plusDays(50)
Log.d("aaa",lek.toString())
        if (lek > nextDate)
            nextDate = lek
        Log.d("aaa",(ChronoUnit.DAYS.between(lek, nextDate).toString()))
        saveProps()
        refreshView()
    }

}