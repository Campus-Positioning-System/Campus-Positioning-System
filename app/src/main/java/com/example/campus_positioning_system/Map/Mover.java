package com.example.campus_positioning_system.Map;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;

import com.ortiz.touchview.TouchImageView;

public class Mover extends HandlerThread {
    private ObjectAnimator animator;
    private TouchImageView view;

    private Float x,y;
    private Float lastX,lastY;

    private Handler handler;

    private final Path path;

    public Mover(String name, Float lastX, Float lastY) {
        super(name);
        this.lastX = lastX;
        this.lastY = lastY;
        this.x = (float) 0.0;
        this.y = (float) 0.0;
        this.path = new Path();
    }

    public void setView(TouchImageView view) {
        this.view = view;
    }

    public void setNewPosition(Float x, Float y) {
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
        this.handler = new Handler();
        Looper.loop();
    }

    public void animationStart() {
        handler.postDelayed(() -> {
            System.out.println("Dot moving to: " + x + " " + y);

            path.moveTo(x, y);
            animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
            animator.start();
             /*
             path.lineTo(x, y);
            animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
            animator.setDuration(1500);
            animator.start();
            */
            view.setX(x);
            view.setY(y);
        },50);
    }
}
