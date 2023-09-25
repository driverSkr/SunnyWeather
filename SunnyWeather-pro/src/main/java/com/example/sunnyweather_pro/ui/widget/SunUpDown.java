package com.example.sunnyweather_pro.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.annotation.Nullable;

import com.example.sunnyweather_pro.R;

@SuppressLint("DrawAllocation")
public class SunUpDown extends View {

    private Paint rPaint; // 绘制矩形的画笔
    private Paint progressPaint; // 绘制圆弧的画笔
    private Bitmap sunIcon; // 太阳图标
    private float sweepAngle; // 圆弧经过的角度
    private CircleBarAnim anim;

    public SunUpDown(Context context) {
        this(context, null);
    }

    public SunUpDown(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rPaint = new Paint();
        rPaint.setStyle(Paint.Style.STROKE); // 只描边，不填充
        rPaint.setColor(Color.RED);

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE); // 只描边，不填充
        progressPaint.setColor(Color.YELLOW); // 将弧线颜色设置为黄色
        progressPaint.setStrokeWidth(5); // 设置线条宽度为5像素
        progressPaint.setAntiAlias(true); // 设置抗锯齿

        // 使用 DashPathEffect 来实现虚线效果，参数为虚线的样式（数组）和偏移量
        progressPaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));

        // 加载太阳图标
        sunIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_header);

        anim = new CircleBarAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = 50;
        float y = 50;
        RectF rectF = new RectF(x, y, x + 300, y + 300); // 建一个大小为300 * 300的正方形区域

        // 绘制矩形
        canvas.drawRect(rectF, rPaint);

        // 计算太阳图标的位置
        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        float sunRadius = Math.min(rectF.width(), rectF.height()) / 2f;

        float sunX = (float) (centerX + sunRadius * Math.cos(Math.toRadians(180 + sweepAngle)));
        float sunY = (float) (centerY + sunRadius * Math.sin(Math.toRadians(180 + sweepAngle)));

        // 绘制圆弧虚线
        canvas.drawArc(rectF, 180, sweepAngle, false, progressPaint);

        // 绘制太阳图标，确保太阳图标位于虚线上方
        canvas.drawBitmap(sunIcon, sunX - sunIcon.getWidth() / 2f, sunY - sunIcon.getHeight() / 2f, null);
    }

    public class CircleBarAnim extends Animation {

        public CircleBarAnim() {
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            sweepAngle = interpolatedTime * 180;
            postInvalidate();
        }
    }

    // 写个方法给外部调用，用来设置动画时间
    public void setProgressNum(int time) {
        anim.setDuration(time);
        this.startAnimation(anim);
    }
}





