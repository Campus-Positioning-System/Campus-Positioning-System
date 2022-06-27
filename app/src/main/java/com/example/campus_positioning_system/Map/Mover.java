package com.example.campus_positioning_system.Map;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.ortiz.touchview.TouchImageView;

import java.util.Objects;


/*
*   Moves a given View to a given Position
*
*   View:
*   View Class or Class which extends View (for Example TouchImageView, TextView etc.)
*
*   Position:
*   is defined by Display Pixels x and y axe
*   Example:
*   - px(X): 0 px(Y): 0
*     is the bottom left corner
*   - X: DisplayWidth Y: DisplayHeight
*     is the top right corner
 */

public class Mover extends HandlerThread {
    private ObjectAnimator animator;
    private View view;

    private Float x,y;
    private Float lastX,lastY;

    private static Handler handler;

    private Path path;

    public Mover(String name, Float lastX, Float lastY) {
        super(name);
        this.lastX = lastX;
        this.lastY = lastY;
        this.x = (float) 0.0;
        this.y = (float) 0.0;
        this.path = new Path();

    }

    public void setView(View view) {
        this.view = view;
    }

    public void setNewPosition(Float x, Float y) {
        this.lastX = this.x;
        this.lastY = this.y;
        this.x = x;
        this.y = y;
    }

    public void setOldPosition(Float lastX, Float lastY) {
        this.lastX = lastX;
        this.lastY = lastY;
    }


    @Override
    public void run() {
        Looper.prepare();
        if (handler == null)
            handler = new Handler();
        Looper.loop();
    }

    public synchronized void animationStart() {
        //System.out.println("Dot moving to: " + x + " " + y);
        //System.out.println("From: " + lastX + " " + lastY);
        this.path = new Path();

        if(Objects.equals(lastX, x) && Objects.equals(lastY, y)) {
        } else {

            handler.post(() -> {
                path.moveTo(lastX,lastY);
                path.lineTo(x,y);
                path.moveTo(x,y);

                path.close();
                animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path).setDuration(10);
                animator.start();
            });
            view.setX(x);
            view.setY(y);
        }
    }
}
