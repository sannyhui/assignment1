package mad.s10361649.minicardgame

// Disclaimer : This application is for education purpose, not for commercial use.
// Version : 2.14 final
// Created by Sanny Hui between 15/8/2021 ~ 5/9/2021

import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.os.Bundle
import android.view.View
import android.widget.*
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

// Related Views : activity_main.xml, fade_in.xml, fade_out.xml, slide_in_left.xml, slide_in_right.xml,
//                 slide_out_left.xml, slide_out_right.xml, zoom_in1.xml, zoom_in2.xml, zoom_out.xml
//                 back_animator.xml, front_animator.xml, AndroidManifest.xml
// Related Classes : LogList.kt, HelpScreen.kt
// Related Drawable(s) : genshin_*.png, right_arrow.png, left_arrow.png
// Interactive Item(s) : Click Button & Image, Swipe Gesture (Left to Right & Right to Left),
// Animation(s) : Card flipping, Zoom in/out, Fade out TextView, Transit views
// Sound : card_flip.mp3

//Added GestureDetector as parent class [Not for assignment 1]
class MiniCardGame : AppCompatActivity() , GestureDetector.OnGestureListener {

    //Variables for Swipe gesture [This block of coding is not for assignment 1]
    private lateinit var gestureDetector: GestureDetector
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100

    //Declare "logList" ArrayList for the class [This block of coding is not for assignment 1]
    private var logList: ArrayList <String> = ArrayList() //Declare log array

    //Main program
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //For swipe gesture [This block of coding is not for assignment 1]
        gestureDetector = GestureDetector(this)

        //For fade out animation [This block of coding is not for assignment 1]
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val fadeOut = findViewById<TextView>(R.id.getPoint) //For fade out text

        //Declare local variables
        var drawRound: Int = 0; var totalScore: Int = 0 //Declare variable Round and Total
        var pokerCard1: Int = 13; var pokerCard2: Int = 13 //Initial card is the image of the card (13 = genshin_cover)
        var scorePoint: Int = 0; var compareResult: String = "No match found" //Declare score and result
        var scoreResult: String = ""; //Declare result string

        //Drawable resource files
        var card_file = arrayOf(
            R.drawable.genshin_a,
            R.drawable.genshin_2,
            R.drawable.genshin_3,
            R.drawable.genshin_4,
            R.drawable.genshin_5,
            R.drawable.genshin_6,
            R.drawable.genshin_7,
            R.drawable.genshin_8,
            R.drawable.genshin_9,
            R.drawable.genshin_10,
            R.drawable.genshin_j,
            R.drawable.genshin_q,
            R.drawable.genshin_k,
            R.drawable.genshin_cover
        )

        //Declare image views
        var totalScoreGet = findViewById<TextView>(R.id.TotalScore) //Totalscore textview
        var roundNumber = findViewById<TextView>(R.id.RoundNo) //Round no textview
        var picture1 = findViewById<ImageView>(R.id.imageView1) //Card no. 1
        var picture2 = findViewById<ImageView>(R.id.imageView2) //Card no. 2
        var picture3 = findViewById<ImageView>(R.id.imageView333) //Back of card 1
        var picture4 = findViewById<ImageView>(R.id.imageView444) //Back of card 2
        var drawResult = findViewById<TextView>(R.id.Result) //Result text
        var getPoint = findViewById<TextView>(R.id.GetPoint) //Point get textview
        val pButton = findViewById<Button>(R.id.playButton) //Play button

        //Setup for card flipping animation [This block of coding is not for assignment 1]
        lateinit var front_animation: AnimatorSet
        lateinit var back_animation: AnimatorSet
        var scale = applicationContext.resources.displayMetrics.density
        picture1.cameraDistance = 8000 * scale
        picture3.cameraDistance = 8000 * scale
        picture2.cameraDistance = 8000 * scale
        picture4.cameraDistance = 8000 * scale

        //Zoom in & out animation for card 1 & 2 [This block of coding is not for assignment 1]
        var zoomPicture1: Boolean = false; var zoomPicture2: Boolean = false //Zoom in/out checking
        picture1.setOnClickListener() {
            if (!zoomPicture1) {
                picture1.bringToFront()
                val animZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in1)
                picture1.startAnimation(animZoomIn)
                zoomPicture1 = true
            }
            else {
                val animZoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
                picture1.startAnimation(animZoomOut)
                zoomPicture1 = false
            }
        }
        picture2.setOnClickListener() {
            if (!zoomPicture2) {
                picture2.bringToFront()
                val animZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in2)
                picture2.startAnimation(animZoomIn)
                zoomPicture2 = true
            }
            else {
                val animZoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
                picture2.startAnimation(animZoomOut)
                zoomPicture2 = false
            }
        }

        //Setup PLAY button OnClickListener
        pButton.setOnClickListener() {

            // Play card flipping sound [This block of coding is not for assignment 1]
            var mediaPlayer = MediaPlayer.create(applicationContext, R.raw.card_flip) //
            mediaPlayer.start()

            // Force zoom out card when PLAY button is clicked [This block of coding is not for assignment 1]
            if (zoomPicture1) {
                val animZoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
                picture1.startAnimation(animZoomOut)
                zoomPicture1 = false
            }
            if (zoomPicture2) {
                val animZoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
                picture2.startAnimation(animZoomOut)
                zoomPicture2 = false
            }

            //Generate random card 1 & 2
            pokerCard1 = (0..12).random()
            pokerCard2 = (0..12).random()

            //Renew card images
            picture1.setImageResource(card_file[pokerCard1])
            picture2.setImageResource(card_file[pokerCard2])

            //Flipping for cards 2 [This block of coding is not for assignment 1]
            front_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
            back_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet
            front_animation.setTarget(picture4)
            back_animation.setTarget(picture2)
            front_animation.start()
            back_animation.start()

            //Flipping for cards 1 [This block of coding is not for assignment 1]
            front_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
            back_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet
            front_animation.setTarget(picture3)
            back_animation.setTarget(picture1)
            front_animation.start()
            back_animation.start()

            //Set initial color to blue (for positive point)
            getPoint.setTextColor(Color.parseColor("#2196F3"))
            fadeOut.setTextColor(Color.parseColor("#2196F3"))

            //Logic to calculate score
            if (pokerCard1 == pokerCard2) { //Cards are same
                compareResult = "Pair of ${pokerCard1+1}" //Show "Pair of card number + 1" by default
                when (pokerCard1) { //Check score
                    0 -> {scorePoint = 50; compareResult = "Pair of A"} //Get 50 points
                    9 -> scorePoint = 10 //Get 10 points
                    10 -> {scorePoint = 20; compareResult = "Pair of J"} //Get 20 points
                    11 -> {scorePoint = 20; compareResult = "Pair of Q"} //Get 20 points
                    12 -> {scorePoint = 20; compareResult = "Pair of K"} //Get 20 points
                    else -> scorePoint = 5 //Get 5 points
                }
            } else if (pokerCard1 == 0 || pokerCard2 == 0) { //Get 2 points if single Ace
                scorePoint = 2 ; compareResult = "One Ace"
            } else {
                scorePoint = -1 ; scoreResult = "Lose 1 point" ; compareResult = "No match" //You lose
                //Change color to red (for negative point)
                getPoint.setTextColor(Color.parseColor("#ff0000"))
                fadeOut.setTextColor(Color.parseColor("#ff0000"))
            }

            //Accumulate score and round
            totalScore += scorePoint ; drawRound ++

            //Store record to ArrayList for RecyclerView [This block of coding is not for assignment 1]
            logList.add(0,"Round $drawRound\n$compareResult   $scorePoint point   Total $totalScore")

            //Change color to Red if Total score is negative
            if (totalScore < 0) {
                totalScoreGet.setTextColor(Color.parseColor("#ff0000"))
            }
            else {
                totalScoreGet.setTextColor(Color.parseColor("#000000"))
            }

            //Start fade out effect [This block of coding is not for assignment 1]
            fadeOut.startAnimation(animation) //[Not for assignment 1]

            //Show getPoint
            if (scorePoint > 1) {
                fadeOut.text = "+"+scorePoint.toString() //Display positive score result [Not for assignment 1]
                scoreResult = "Win $scorePoint points" //Display Win message
            } else {
                fadeOut.text = scorePoint.toString() //Display negative score result [Not for assignment 1]
            }

            // [This block of coding is not for assignment 1]
            Handler().postDelayed({
                fadeOut.visibility = View.GONE
            }, 1000)

            //Put variables to text views
            roundNumber.text = "Round $drawRound" ; drawResult.text = compareResult
            getPoint.text = scoreResult ; totalScoreGet.text = totalScore.toString()
        }
    }

    //Setup Swipe gesture detector [This block of coding is not for assignment 1]
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    //Setup functions to fulfill Abstract class requirement (Dummy codes) [This block of coding is not for assignment 1]
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

    //Define left to right and right to left action [This block of coding is not for assignment 1]
    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (abs(diffX) > abs(diffY)) { //Compare gesture starting and ending point
                if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) { //Left to Right
                        val intent = Intent(this, LogList::class.java) //To LogList
                        intent.putStringArrayListExtra("key",logList) //Pass ArrayList to LogList
                        setResult(RESULT_OK, intent)
                        startActivity(intent) //Switch screen
                        overridePendingTransition( //Transit screen animation
                            R.anim.slide_in_left,
                            R.anim.slide_out_right
                        )
                    }
                    else { //Right to left
                        val intent = Intent(this, HelpScreen::class.java) //To HelpScreen
                        startActivity(intent) //Switch screen
                        overridePendingTransition( //Transit screen animation
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                        )
                    }
                }
            }
        }
        catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }
}
