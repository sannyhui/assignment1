package mad.s10361649.minicardgame

// Important : [This activity is not for assignment 1]

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Related View(s) : loglist.xml, AndroidManifest.xml
// Related Class(es) : CustomAdapter.kt, MiniCardGame.kt
// Related Drawable(s) : N/A
// Interactive Item(s) : Click Button

class LogList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loglist)

        // Retrieve array result from MiniCardGame
        val lList = intent.getStringArrayListExtra("key") //Get ArrayList
        val logList = lList?.toList() //Convert from ArrayList to List with null acceptable

        // Define variables for class
        var customAdapter: CustomAdapter //Define adpater
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) //Define recyclerView
        val layoutManager = LinearLayoutManager(applicationContext) //Define layout manager
        val gBack = findViewById<Button>(R.id.goBack)

        // Establish Recycler View with array
        customAdapter = CustomAdapter(logList) //Pass array logList to adapter
        recyclerView.layoutManager = layoutManager //Set layout Manager
        recyclerView.adapter = customAdapter //Set adapter
        customAdapter.notifyDataSetChanged() //Update adapter

        // Back to MiniCardGame when BACK button is clicked
        gBack.setOnClickListener() {
            val intent = Intent(this, MiniCardGame::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
    }
}
