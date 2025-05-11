package a.rav.donat

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
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
    lateinit var note: String
    lateinit var noteTv: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        donDateEt = findViewById<EditText>(R.id.donDateEt)
        nextDateEt = findViewById<EditText>(R.id.nextDateEt)
        noteTv = findViewById<EditText>(R.id.noteInp)
        donBtn = findViewById<Button>(R.id.donkabt)
        var noteBtn=findViewById<Button>(R.id.noteBt)
        noteBtn.setOnClickListener {
            addNote()
        }
        var lekBtn = findViewById<Button>(R.id.lek)
        lekBtn.setOnClickListener() {
            setLek()
        }

        readProps()
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
    private fun readProps() {

        val sharedPref = getPreferences(Context.MODE_PRIVATE)


        donDate = LocalDate.parse(sharedPref.getString("donDate", "1999-01-01"))

        nextDate = LocalDate.parse(sharedPref.getString("nextDate", "1999-01-01"))
        lek = LocalDate.parse(sharedPref.getString("lekDate", "1999-01-01"))
        note=sharedPref.getString("note", "").toString()
        donDateEt.text = donDate.toString()
        nextDateEt.text = nextDate.toString()
        noteTv.text=note

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveDonDate() {
        //zapis daty donacji i aktualizacja
        donDate = LocalDate.now()

        nextDate = donDate.plusDays(57)

        if (saveProps()) return
        readProps()
    }

    private fun saveProps(): Boolean {
        if (lek != null)
            if (lek > nextDate)
                nextDate = lek


        val sharedPr = getPreferences(MODE_PRIVATE) ?: return true
        with(sharedPr.edit()) {
            putString("donDate", donDate.toString())
            putString("nextDate", nextDate.toString())
            putString("note", note)
            if (lek != null)
                putString("lekDate", lek.toString())

            apply()
            Log.d("aaa", lek.toString())
        }
        return false
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setLek() {
        lek = LocalDate.now().plusDays(14)
        Log.d("aaa", lek.toString())
        Log.d("aaa", (ChronoUnit.DAYS.between(lek, nextDate).toString()))
        saveProps()
        readProps()
    }

}



private fun MainActivity.addNote() {
    //dodaje notkę o ważnych wydarzeniach, jak zabiegi od ostatniego razu
    val inflater=layoutInflater
    val  noteDlgBld= AlertDialog.Builder(this)

    //val  saveNote= DialogInterface.OnClickListener({ Log.d("aaa","bbb")})
    fun saveNote(x: String){
        Log.d("aaa note1",x)
        noteTv.text=x
        val sharedPr = getPreferences(MODE_PRIVATE)
        with(sharedPr.edit()) {
            putString("note", x)
            apply()
        }   }
    val dlg: EditText
    with (noteDlgBld) {

        setTitle("pytacz")
        setMessage("Dodaj notkę")
        val dlgLayout = inflater.inflate(R.layout.notedialog, null)

        dlg = dlgLayout.findViewById<EditText>(R.id.noteInp)
        dlg.setText(note)
        setView(dlgLayout)
        setPositiveButton("OK") { dialogInterface, x ->
            note=dlg.text.toString()
            saveNote(note)
            Log.d("aaa notein",note)
        }

        show()
    }
        note=dlg.text.toString()
        Log.d("aaa note2",note)


}
