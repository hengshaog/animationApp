package test.aitemf.com.myapplication.mvp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.TextView;

import java.security.PrivateKey;
import java.util.logging.Logger;

import test.aitemf.com.myapplication.R;

/**
 * Created by hengshao on 2018/2/27.
 */

public class WaveProgressView  extends View{
    private Paint mwavePaint;
    private Path mwavePath;
    private Paint mcirclePaint;//圆形京都条框画笔
    private Bitmap mbitmap;//缓存bitmap
    private Canvas mbitmapCanvas;

    private float mwaveWidth;
    private float mwaveHeight;

    private int mwaveNum; //波浪数量
    private int mdefaultSize; //自定义view默认的宽高
    private int mmaxHeight;//为了看到薄凉效果，给定一个比填充物稍高的高度

    private int viewSize;//重新测量后View实际的宽高

    private WaveProgressAnim mwaveProgressAnim;
    private float mpercent;//进度条占比
    private float mprogressNum;//可以更新的进度条数值
    private float mmaxNum;//进度条最大值

    private float mwaveMovingDistance;//波浪平移的距离

    private int mwaveColor;//波浪颜色
    private int mbgColor;//背景进度框颜色

    private TextView mtextView;
    private OnAnimationListener monAnimationListener;

    private int msenondWaveColor;//第二层波浪颜色
    private boolean misDrawSecondWave;//是否绘制第二层波浪
    private Paint msecondWavePaint;
    private Paint mbigCirl;

    private Path mbigPath;




    public interface OnAnimationListener{
        String howToChangText(float interpolatedTime, float updateNum,float maxNum);
        float howToChangWaveHeight(float percent, float waveHeight);
    }

    public void setOnAnimationLisener(OnAnimationListener onAnimationLisener){
        this.monAnimationListener = onAnimationLisener;
    }

    public void setTextView(TextView textView){
        this.mtextView = textView;
    }

    public WaveProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public void setDrawSecondWave(boolean isDrawSecondWave){
        this.misDrawSecondWave = isDrawSecondWave;
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveProgressView);
        mwaveWidth = typedArray.getDimension(R.styleable.WaveProgressView_wave_width,DpOrPxUtils.dip2px(context,25));
        mwaveHeight = typedArray.getDimension(R.styleable.WaveProgressView_wave_height,DpOrPxUtils.dip2px(context,5));
        msenondWaveColor = typedArray.getColor(R.styleable.WaveProgressView_second_color,Color.GREEN);
        mdefaultSize = DpOrPxUtils.dip2px(context,200);
        mmaxHeight = DpOrPxUtils.dip2px(context,650);
        mwaveNum = (int) Math.ceil(Double.parseDouble(String.valueOf(mdefaultSize / mwaveWidth / 2))); //波浪的数量需要进一取整，所以使用Math.ceil函数
        mwaveColor = typedArray.getColor(R.styleable.WaveProgressView_wave_color,Color.GREEN);
        mbgColor = typedArray.getColor(R.styleable.WaveProgressView_wave_bg,Color.GRAY);
        mbigPath = new Path();

        mbigCirl = new Paint();
        mbigCirl.setColor(Color.BLACK);
        mbigCirl.setAntiAlias(true);

        msecondWavePaint = new Paint();
        msecondWavePaint.setColor(msenondWaveColor);
        msecondWavePaint.setAntiAlias(true);

        msecondWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        misDrawSecondWave = false;


        mwavePath = new Path();

        mwavePaint = new Paint();
        mwavePaint.setColor(mwaveColor);
        mwavePaint.setAntiAlias(true);


        mpercent = 0;
        mprogressNum = 0;
        mmaxNum = 100;
        mwaveProgressAnim = new WaveProgressAnim();

        mwaveProgressAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (mpercent == mprogressNum / mmaxNum){
                    mwaveProgressAnim.setDuration(2000);
                }

            }
        });
        mwaveMovingDistance = 0;
        mwavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//根据绘制顺序的不同选择相应的模式即可

        mcirclePaint = new Paint();
        mcirclePaint.setColor(mbgColor);
        mcirclePaint.setAntiAlias(true);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(mdefaultSize, heightMeasureSpec);
        int width  = measureSize(mdefaultSize, widthMeasureSpec);

        int min = Math.min(width,height);
        setMeasuredDimension(min + 30,min + 30);
        viewSize = min ;
        mwaveNum = (int) Math.ceil(Double.parseDouble(String.valueOf(viewSize / mwaveWidth / 2)));
    }

    public int measureSize(int defaultSize, int measureSpec){
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY){
            result = specSize;
        }else if (specMode == MeasureSpec.AT_MOST){
            result = Math.min(result,specSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawPath(getWavePath(),mwavePaint);
        Log.e("自定义view",viewSize+"");
        Bitmap mbitmaps = Bitmap.createBitmap(viewSize+50 ,viewSize +50 ,Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(mbitmaps);
        canvas1.drawCircle(viewSize/2 + 5 , viewSize/2 + 5  ,viewSize/2 + 5,mbigCirl);
//        canvas1.drawPath(getBigCirclePath(),mbigCirl);
        mbitmap = Bitmap.createBitmap(viewSize ,viewSize,Bitmap.Config.ARGB_8888);
        mbitmapCanvas = new Canvas(mbitmap);
        mbitmapCanvas.drawCircle(viewSize/2 + 5 ,viewSize/2 + 5,viewSize/2 - 5,mcirclePaint);
        mbitmapCanvas.drawPath(getWavePath(),mwavePaint);

        if (misDrawSecondWave){
            mbitmapCanvas.drawPath(getSecondWavePath(),msecondWavePaint);
        }
        canvas.drawBitmap(mbitmaps,0,0, null);
        canvas.drawBitmap(mbitmap, 0 , 0 ,null);
    }


    private Path getSecondWavePath(){
        float changWaveHeight = mwaveHeight;

        if (monAnimationListener != null){
            changWaveHeight = monAnimationListener.howToChangWaveHeight(mpercent,mwaveHeight) == 0 && mpercent < 1 ? mwaveHeight : monAnimationListener.howToChangWaveHeight(mpercent,mwaveHeight);
        }

        mwavePath.reset();
        //移动到左上方，也就是p3点
        mwavePath.moveTo(0,(1-mpercent) * viewSize);
        //移动到左下方，也就是p2点
        mwavePath.lineTo(0,viewSize);
        //移动到右下方，也就是p1点
        mwavePath.lineTo(viewSize,viewSize);
        //移动到右上方，也就是p0点
        mwavePath.lineTo(viewSize + mwaveMovingDistance, (1-mpercent) * viewSize);

        //从p0开始向p3方向绘制波浪曲线（注意绘制二阶贝塞尔曲线控制点和终点x坐标的正负值)
        for (int i = 0; i < mwaveNum * 2; i++) {
            mwavePath.rQuadTo(-mwaveWidth/2 , changWaveHeight, -mwaveWidth, 0 );
            mwavePath.rQuadTo(-mwaveWidth/2, -changWaveHeight, - mwaveWidth, 0);
        }
        mwavePath.close();
        return mwavePath;
    }

    private Path getWavePath(){
        mwavePath.reset();
        //移动到右上方，也就是p0点
        mwavePath.moveTo(viewSize + 10, (1 - mpercent) * (viewSize + 10));//让p0p1的长度随percent的增加而增加（注意这里y轴方向默认是向下的
        //移动到右下方，也就是p1点
        mwavePath.lineTo(viewSize + 10, viewSize + 10);
        //移动到左下边，也就是p2点
        mwavePath.lineTo(0,viewSize + 10);
        //移动到左上方，也就是p3点
        mwavePath.lineTo(0,(1-mpercent) * viewSize + 10);//让p3p2的长度随percent的增加而增加（注意这里y轴方向默认是向下的）

        //从p3开始向p0方向绘制波浪曲线
//        for (int i = 0; i < mwaveNum; i++) {
//            mwavePath.rQuadTo(mwaveWidth/2, mwaveHeight,mwaveWidth , 0);
//            mwavePath.rQuadTo(mwaveWidth/2, - mwaveHeight, mwaveWidth ,   0);
//        }
        mwavePath.lineTo(-mwaveMovingDistance +10, (1 - mpercent) * viewSize + 10);

        float changeWaveHeight = mwaveHeight;
        if (monAnimationListener != null){
            changeWaveHeight = monAnimationListener.howToChangWaveHeight(mpercent,mwaveHeight) == 0 && mpercent < 1 ? mwaveHeight:monAnimationListener.howToChangWaveHeight(mpercent,mwaveHeight);
        }

        for (int i = 0; i < mwaveNum * 2; i++) {
            mwavePath.rQuadTo(mwaveWidth /2, changeWaveHeight, mwaveWidth ,0);
            mwavePath.rQuadTo(mwaveWidth /2, -changeWaveHeight , mwaveWidth , 0);
        }

        mwavePath.close();
        return mwavePath;
    }

    public class WaveProgressAnim extends Animation{
        public WaveProgressAnim(){}

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //波浪高度到达最大值后就不需要循环了，只需让波浪曲线平移循环即可
            if (mpercent < mprogressNum / mmaxNum) {
                mpercent = interpolatedTime * mprogressNum / mmaxNum;
                if (mtextView != null && monAnimationListener != null)
                    mtextView.setText(monAnimationListener.howToChangText(interpolatedTime,mprogressNum,mmaxNum));
            }
            mwaveMovingDistance = interpolatedTime * mwaveNum * mwaveWidth *2;
            postInvalidate();
            Log.e("sss","时间："+interpolatedTime);
            Log.e("sss","时间："+t.toShortString());
        }
    }

    public void setProgressNum(float progressNum,int time){
        this.mprogressNum = progressNum;

        mpercent = 0;
        mwaveProgressAnim.setDuration(time);
        mwaveProgressAnim.setRepeatCount(Animation.INFINITE);//无限循环
        mwaveProgressAnim.setInterpolator(new LinearInterpolator());//让动画匀速播放，不然会出现波浪平移停顿的现象
        this.startAnimation(mwaveProgressAnim);
    }
}
