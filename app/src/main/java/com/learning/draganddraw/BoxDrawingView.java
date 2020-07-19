package com.learning.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";

    private Box currentBox;
    private List<Box> boxList = new ArrayList<>();
    private Paint boxPaint;
    private Paint backgroundPaint;

    //  Used when Creating View in code
    public BoxDrawingView(Context context){
        this(context, null);
    }

    //  used when inflating view from xml
    public BoxDrawingView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        boxPaint = new Paint();
        boxPaint.setColor(0x22ff0000);
        backgroundPaint = new Paint();
        backgroundPaint.setColor((0xfff8efe0));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                currentBox = new Box(current);
                boxList.add(currentBox);
                break;

            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if (currentBox != null){
                    currentBox.setCurrent(current);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                currentBox = null;
                break;

            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                currentBox = null;
                break;
        }
        Log.i(TAG, action + " at x=" + current.x + " at y=" + current.y);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(backgroundPaint);
        for (Box box: boxList){
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, boxPaint);
        }
    }
}
