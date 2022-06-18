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

    public Mover(String name) {
        super(name);
    }

    public void setView(TouchImageView view) {
        this.view = view;
    }

    public void setNewPosition(Float x, Float y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void run() {
        Looper.prepare();
        new Handler().post(() -> {
            System.out.println("Dot moving to: " +  x + " " + y);
            Path path = new Path();
            path.lineTo(x,y);
            animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
            animator.setDuration(2000);
            animator.start();
            view.setX(x);
            view.setY(y);
        });
        Looper.loop();
    }
}
