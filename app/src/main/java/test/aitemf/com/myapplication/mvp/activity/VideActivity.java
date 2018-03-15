package test.aitemf.com.myapplication.mvp.activity;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.appyvet.materialrangebar.RangeBar;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.nightonke.boommenu.BoomMenuButton;
import com.race604.drawable.wave.WaveDrawable;
import com.roger.catloadinglibrary.CatLoadingView;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.skyfishjy.library.RippleBackground;
import com.wang.avi.AVLoadingIndicatorView;

import dmax.dialog.SpotsDialog;
import test.aitemf.com.myapplication.R;
import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by hengshao on 2017/12/21.
 */

public class VideActivity extends AppCompatActivity {
    private CatLoadingView mView;
    private BoomMenuButton boomMenuButton;
    private ExplosionField mExplosionField;
    private int e = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vide);
        final NumberProgressBar progress = findViewById(R.id.progress);
        final RoundCornerProgressBar progressBar = findViewById(R.id.progress1);
        mView = new CatLoadingView();

        final ImageView imageView2 = (ImageView) findViewById(R.id.image2);
        final WaveDrawable chromeWave = new WaveDrawable(this, R.mipmap.icon);
        imageView2.setImageDrawable(chromeWave);
        chromeWave.setIndeterminate(true);

        progress.setMax(100);
        RangeBar rangebar = findViewById(R.id.rangebar);
        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                progress.setProgress(Integer.parseInt(leftPinValue));
                progressBar.setProgress(Float.parseFloat(leftPinValue));

            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(),"iconfont.ttf");
        TextView textView = findViewById(R.id.text);
        textView.setTypeface(typeface);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new SpotsDialog(VideActivity.this);
                dialog.show();
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mView.show(getSupportFragmentManager(), "");
                return true;
            }
        });

        final FABProgressCircle fabProgressCircle = findViewById(R.id.fabProgressCircle);
        fabProgressCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabProgressCircle.show();
            }
        });

        AVLoadingIndicatorView view = findViewById(R.id.avi);
        view.show();
        AVLoadingIndicatorView view1 = findViewById(R.id.avi1);
        view1.show();

        SpringSystem springSystem = SpringSystem.create();

// Add a spring to the system.
        final Spring spring = springSystem.createSpring();
        final RippleBackground rippleBackground = findViewById(R.id.content);
        final ShineButton shineButton = findViewById(R.id.po_image1);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rippleBackground.startRippleAnimation();
                spring.setEndValue(2);
                if (e == 1){
                    shineButton.setShapeResource(R.raw.heart);
                    shineButton.init(VideActivity.this);
                    shineButton.showAnim();
                    e++;
                }else if (e == 2){
                    shineButton.setShapeResource(R.raw.star);
                    e++;
                    shineButton.init(VideActivity.this);
                    shineButton.showAnim();
                }else if (e == 3){
                    shineButton.setShapeResource(R.raw.smile);
                    shineButton.init(VideActivity.this);
                    e++;
                    shineButton.showAnim();
                }else if (e == 4){
                    shineButton.setShapeResource(R.raw.like);
                    shineButton.init(VideActivity.this);
                    e = 1;
                    shineButton.showAnim();
                }
            }
        });





// Add a listener to observe the motion of the spring.
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.7f);
                imageView2.setScaleX(scale);
                imageView2.setScaleY(scale);
            }
        });

// Set the spring in motion; moving from 0A to 1
        spring.setEndValue(0.5);
        SpringConfig springConfig = new SpringConfig(100,10);
        spring.setSpringConfig(springConfig);


        mExplosionField = ExplosionField.attach2Window(this);
        addListener(findViewById(R.id.root));
    }


    private void addListener(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                addListener(parent.getChildAt(i));
            }
        } else {
            root.setClickable(true);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExplosionField.explode(v);
                    v.setOnClickListener(null);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void reset(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                reset(parent.getChildAt(i));
            }
        } else {
            root.setScaleX(1);
            root.setScaleY(1);
            root.setAlpha(1);
        }
    }


}
