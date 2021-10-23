package mad.s10361649.minicardgame

// Important : [This activity is not for assignment 1]

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

// Related View(s) : help_screen.xml, AndroidManifest.xml
// Related Class(es) : MiniCardGame.kt
// Related Drawable(s) : N/A
// Interactive Item(s) : Swipe Gesture (Left to Right & Right to Left)

class HelpScreen : AppCompatActivity() , GestureDetector.OnGestureListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_screen) //Set cover page to help screen
        gestureDetector = GestureDetector(this)
    }
    //Variable for Swipe gesture
    lateinit var gestureDetector : GestureDetector
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100

    //Setup Swipe gesture detector
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    //Setup functions to fulfill Abstract class requirement (Dummy codes)
    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }
    override fun onShowPress(p0: MotionEvent?) {
        return
    }
    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }
    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }
    override fun onLongPress(p0: MotionEvent?) {
        return
    }

    //When swipe gesture is detected, go back to MiniCardGame
    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                    val intent = Intent(this, MiniCardGame::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                }
            }
        }
        catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }
}