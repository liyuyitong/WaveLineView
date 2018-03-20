package com.freefish.wavelineview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by user on 16/10/28.
 */

public class WaveLineView extends View {
    private static final int DEF_HEIGHT = 60;
    private Paint waveFirstPaint;
    private Paint waveSecondPaint;
    private Paint waveThirdPaint;
    //振幅
    private float firstWaveAmPlifier= 20;
    private float secondWaveAmplifier = 10;
    private float thirdWaveAmplifier = 5;
    //频率
    private float firstWaveFrequency = 1.6f;
    private float secondWaveFrequency = 1.2f;
    private float thirdWaveFrequency = 0.6f;
    //角度
    private float firstWavePhase = 40;
    private float secondWavePhase = 30;
    private float thirdWavePhase = 20;
    private int waveLineWidth;
    private int viewHeight;
    private int viewWidth;
    private float viewCenterY;

    private int waveFirstColor;
    private int waveSecondColor;
    private int waveThirdColor;

    public WaveLineView(Context context) {
        this(context, null);
    }


    public WaveLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WaveLineView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.WaveLineView_wave_first_color:
                    waveFirstColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.WaveLineView_wave_second_color:
                    waveSecondColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.WaveLineView_wave_third_color:
                    waveThirdColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.WaveLineView_wave_line_width:
                    waveLineWidth = typedArray.getDimensionPixelOffset(attr, 0);
                    break;
                case R.styleable.WaveLineView_wave_amplifier:
//                    waveAmplifier = typedArray.getFloat(attr, 0);
                    break;
                case R.styleable.WaveLineView_wave_phase:
//                    wavePhase = typedArray.getFloat(attr, 0);
                    break;
                case R.styleable.WaveLineView_wave_frequency:
//                    waveFrequency = typedArray.getFloat(attr, 0);
                    break;
            }
        }
        typedArray.recycle();
        initTools();
    }

    private void initTools() {
        waveFirstPaint = new Paint();
        waveFirstPaint.setAntiAlias(true);
        waveFirstPaint.setStyle(Paint.Style.FILL);
        waveFirstPaint.setStrokeJoin(Paint.Join.MITER);
        waveFirstPaint.setStrokeCap(Paint.Cap.BUTT);
        waveFirstPaint.setColor(waveFirstColor);
        waveFirstPaint.setStrokeWidth(waveLineWidth);

        waveSecondPaint = new Paint();
        waveSecondPaint.setColor(waveSecondColor);
        waveSecondPaint.setStrokeCap(Paint.Cap.BUTT);
        waveSecondPaint.setStrokeJoin(Paint.Join.MITER);
        waveSecondPaint.setStyle(Paint.Style.FILL);
        waveSecondPaint.setStrokeWidth(waveLineWidth);
        waveSecondPaint.setAntiAlias(true);

        waveThirdPaint = new Paint();
        waveThirdPaint.setColor(waveThirdColor);
        waveThirdPaint.setStrokeCap(Paint.Cap.BUTT);
        waveThirdPaint.setStrokeJoin(Paint.Join.MITER);
        waveThirdPaint.setStyle(Paint.Style.FILL);
        waveThirdPaint.setStrokeWidth(waveLineWidth);
        waveThirdPaint.setAntiAlias(true);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < viewWidth - 1; i++) {
            canvas.drawLine((float) i, viewCenterY - firstWaveAmPlifier * (float) (Math.sin(firstWavePhase * 2 * (float) Math.PI / 360.0f + 2 * Math.PI * firstWaveFrequency * i / viewWidth)), (float) (i + 1), viewCenterY - firstWaveAmPlifier * (float) (Math.sin(firstWavePhase * 2 * (float) Math.PI / 360.0f + 2 * Math.PI * firstWaveFrequency * (i + 1) / viewWidth)), waveFirstPaint);
            canvas.drawLine((float) i, viewCenterY - secondWaveAmplifier * (float) (Math.sin(secondWavePhase * 2 * (float) Math.PI / 360.0f + 2 * Math.PI * secondWaveFrequency * i / viewWidth)), (float) (i + 1), viewCenterY - secondWaveAmplifier * (float) (Math.sin(secondWavePhase * 2 * (float) Math.PI / 360.0f + 2 * Math.PI * secondWaveFrequency * (i + 1) / viewWidth)), waveSecondPaint);
            canvas.drawLine((float) i, viewCenterY - thirdWaveAmplifier * (float) (Math.sin(thirdWavePhase * 2 * (float) Math.PI / 360.0f + 2 * Math.PI * thirdWaveFrequency * i / viewWidth)), (float) (i + 1), viewCenterY - thirdWaveAmplifier * (float) (Math.sin(thirdWavePhase * 2 * (float) Math.PI / 360.0f + 2 * Math.PI * thirdWaveFrequency * (i + 1) / viewWidth)), waveThirdPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
//        viewCenterY = viewHeight / 2;
        viewCenterY = 2 * viewHeight / 3;
        firstWaveAmPlifier = (firstWaveAmPlifier * 2 > viewHeight) ? (viewHeight / 2) : firstWaveAmPlifier;
        secondWaveAmplifier = (secondWaveAmplifier * 2 > viewHeight) ? (viewHeight / 2) : secondWaveAmplifier;
        thirdWaveAmplifier = (thirdWaveAmplifier * 2 > viewHeight) ? (viewHeight / 2) : thirdWaveAmplifier;
        waveAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeaure;

        if (heightMeasureMode == MeasureSpec.AT_MOST || heightMeasureMode == MeasureSpec.UNSPECIFIED) {
            heightMeaure = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_HEIGHT, getResources().getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeaure, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void waveAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.F, 0F);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float aFloat = Float.valueOf(animation.getAnimatedValue().toString());
                firstWavePhase = 360.F * aFloat;
                secondWavePhase = 360.F * aFloat;
                thirdWavePhase = 360.F * aFloat;
//                Log.e(WaveLineView.class.getSimpleName(), "角度："+wavePhase);
                invalidate();
            }
        });
        valueAnimator.start();
    }

}
