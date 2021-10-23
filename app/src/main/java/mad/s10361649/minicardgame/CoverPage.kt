package mad.s10361649.minicardgame

// Important : [This activity is not for assignment 1]

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Related View(s) : cover_page.xml, AndroidManifest.xml
// Related Class(es) : MiniCardGame.kt
// Related Drawable(s) : N/A
// Interactive Item(s) : Click TextView

// Cover Page only.
class CoverPage : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cover_page) //Set cover page to initial screen

        val sGame = findViewById<TextView>(R.id.startGame)

        sGame.setOnClickListener() {
            val i = Intent(this,MiniCardGame::class.java)
            startActivity(i)
        }
    }
}