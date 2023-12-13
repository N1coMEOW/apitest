package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import java.lang.Math.abs

class one : AppCompatActivity(), GestureDetector.OnGestureListener {
    private var gestureDetectorCompat: GestureDetectorCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)
        gestureDetectorCompat = GestureDetectorCompat(this, this)

    }
    fun onMyButtonClick(view: View?) {

        val intent = Intent (this@one, two::class.java)
        startActivity(intent)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            gestureDetectorCompat!!.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        when(ClassifiedSwipeClass (e1!!, e2, distanceX, distanceY)
            .defineSwipe(150)) {
            SwipeVariant.SwipeLeft ->{
                val intent = Intent (this@one, two::class.java)
                startActivity(intent)
            }
            else -> {
            }
        }
        return true
    }

    override fun onLongPress(e: MotionEvent) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }
}