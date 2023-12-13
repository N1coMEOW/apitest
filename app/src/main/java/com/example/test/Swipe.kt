package com.example.test

import android.view.MotionEvent
import java.lang.Math.abs

enum class SwipeVariant{SwipeLeft, SwipeRight, SwipeUp, SwipeDown, RandomSwipe}

class ClassifiedSwipeClass {

    private var fromPoint: MotionEvent;
    private var toPoint: MotionEvent;

    private var distanceX: Float;
    private var distanceY: Float;

    constructor(fromPoint: MotionEvent, toPoint: MotionEvent, distanceX: Float, distanceY: Float) {
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
    }

    public fun defineSwipe(allowableError: Int): SwipeVariant {
        var DeltaX = toPoint.x - fromPoint.x;
        var DeltaY = toPoint.y - fromPoint.y;
        if (abs(DeltaY) > abs(DeltaX)) {
            if (abs(DeltaY) <= allowableError)
            else if (DeltaY > 0) return SwipeVariant.SwipeUp;
            else if (DeltaY < 0) return SwipeVariant.SwipeDown;
        } else {
            if (abs(DeltaX) <= allowableError)
            else if (DeltaX > 0) return SwipeVariant.SwipeRight;
            else if (DeltaX < 0) return SwipeVariant.SwipeLeft;
        }
        return SwipeVariant.RandomSwipe;
    }
}