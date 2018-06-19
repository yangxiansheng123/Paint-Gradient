package com.test.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RadarGradientView extends View {


    public RadarGradientView(Context context) {
        super(context);

        // 画圆用到的paint
        mPaintCircle = new Paint();
        // 描边
        mPaintCircle.setStyle(Paint.Style.STROKE);
        // 宽度
        mPaintCircle.setStrokeWidth(1);
        // 透明度
        mPaintCircle.setAlpha(100);
        // 抗锯齿
        mPaintCircle.setAntiAlias(true);
        // 设置颜色 亮钢兰色
        mPaintCircle.setColor(Color.parseColor("#B0C4DE"));

        // 扫描用到的paint
        mPaintRadar = new Paint();
        // 填充
        mPaintRadar.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintRadar.setAntiAlias(true);

        post(run);
    }

    public RadarGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    private int mWidth, mHeight;

    /**
     * 5个圆
     */
    private float[] pots = {0.05f, 0.1f, 0.15f, 0.2f, 0.25f};
    /**
     * 扫描渲染shader
     */
    private Shader scanShader;
    /**
     * 旋转需要的矩阵
     */
    private Matrix matrix = new Matrix();
    /**
     * 扫描速度
     */
    private int scanSpeed = 5;
    /**
     * 扫描旋转的角度
     */
    private int scanAngle;
    /**
     * 画圆用到的paint
     */
    private Paint mPaintCircle;
    /**
     * 扫描用到的paint
     */
    private Paint mPaintRadar;



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < pots.length; i++) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * pots[i], mPaintCircle);
        }

        canvas.save();
        scanShader = new SweepGradient(mWidth / 2, mHeight / 2,
                new int[]{Color.TRANSPARENT, Color.parseColor("#84B5CA")}, null);
        // 设置着色器
        mPaintRadar.setShader(scanShader);
        canvas.concat(matrix);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * pots[4], mPaintRadar);

        canvas.restore();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 取屏幕的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mWidth = mHeight = Math.min(mWidth, mHeight);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            // 旋转角度 对360取余
            scanAngle = (scanAngle + scanSpeed) % 360;
            // 旋转矩阵
            matrix.postRotate(scanSpeed, mWidth / 2, mHeight / 2);
            // 通知view重绘
            invalidate();
            // 调用自身 重复绘制
            postDelayed(run, 50);
        }
    };

}
