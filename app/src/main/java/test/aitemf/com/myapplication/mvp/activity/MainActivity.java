package test.aitemf.com.myapplication.mvp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.materialrangebar.RangeBar;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.gelitenight.waveview.library.WaveView;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nispok.snackbar.Snackbar;
import com.orhanobut.logger.Logger;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import test.aitemf.com.myapplication.R;
import test.aitemf.com.myapplication.mvp.view.WaveProgressView;

public class MainActivity extends AppCompatActivity {
    private RadioButton mradioButton;
    private Switch mswitch1;
    private MaterialMenuView materialMenuView;
    private int              materialButtonState;
    private DrawerLayout drawerLayout;
    private int              actionBarMenuState;
    private boolean          direction;
    private RangeBar         rangebar;
    private Button seekBar;
    private TextView textView;
    private WaveView waveview;
    private AnimatorSet mAnimatorSet;
    private RangeBar rangbar2;
    private WaveProgressView mwaveview;
    private TextView mtextview_num;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mwaveview = findViewById(R.id.waveviews);
        mtextview_num = findViewById(R.id.textview_num);

        mwaveview.setTextView(mtextview_num);
        mwaveview.setDrawSecondWave(true);
        mwaveview.setOnAnimationLisener(new WaveProgressView.OnAnimationListener() {
            @Override
            public String howToChangText(float interpolatedTime, float updateNum, float maxNum) {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String s = decimalFormat.format(interpolatedTime * updateNum / maxNum * 100);
                return s + " %";
            }

            @Override
            public float howToChangWaveHeight(float percent, float waveHeight) {
                return (1 - percent) * waveHeight;
            }
        });

        rangbar2 = findViewById(R.id.rangbar2);


        rangbar2.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                mwaveview.setProgressNum(90,3000);
            }
        });


        waveview = findViewById(R.id.waveview);
// amplitude animation.
// wave grows big then grows small, repeatedly
        waveview.setBorder(5,Color.WHITE);
        waveview.setWaveColor(
                Color.parseColor("#28f16d7a"),
                Color.parseColor("#3cf16d7a"));

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,PanoramaImageViewActivity.class);
                startActivity(intent);
                return false;
            }
        });
        seekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,BottomSheetActivityLayout.class);
                        startActivity(intent);
                    }
        });
        initAnimation();

        textView = findViewById(R.id.target);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FABLAyoutActivity.class);
                startActivity(intent);
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,DiscrollviewActivity.class);
                startActivity(intent);
                return false;
            }
        });


        mradioButton = findViewById(R.id.radioButton);
        mradioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShowcaseView(mradioButton);
            }
        });
        mradioButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,VoiceActivity.class);
                startActivity(intent);
                return true;
            }
        });
        mswitch1 = findViewById(R.id.switch1);
        mswitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"第20次,成功!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ButtonActivity.class);
                startActivity(intent);
            }
        });
        mswitch1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,VideActivity.class);
                startActivity(intent);
                return true;
            }
        });

//        setSupportActionBar(toolbar);
        materialMenuView = (MaterialMenuView) findViewById(R.id.material_menu_button);
        materialMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialButtonState = generateState(materialButtonState);
                materialMenuView.animateIconState(intToState(materialButtonState));
            }
        });

        materialMenuView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this,TextSurfaceActivty.class);
                startActivity(intent);
                return false;
            }
        });


        rangebar = findViewById(R.id.rangebar);

        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                Snackbar.with(getApplicationContext()) // context
                        .text("index："+leftPinValue) // text to display
                        .actionColor(Color.WHITE)
                        .actionLabel("OK")
                        .textColor(Color.WHITE)
                        .duration(1000)
                        .animation(true)
                        .color(Color.RED)
                        .show(MainActivity.this);
            }
        });


        start();

//        MaterialMenuDrawable materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
//        toolbar.setNavigationIcon(materialMenu);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                 random state
//                actionBarMenuState = generateState(actionBarMenuState);
//                getMaterialMenu(toolbar).animateIconState(intToState(actionBarMenuState));
//
//            }
//        });

        // Demo view initialization
//        drawerLayout.postDelayed(new Runnable() {
//            @Override public void run() {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        }, 1500);
    }

    public void start() {
        waveview.setShowWave(true);
        mAnimatorSet.start();
    }

    private void initAnimation() {
        List<Animator> animators = new ArrayList<>();

        // horizontal animation.
        // wave waves infinitely.
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                waveview, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);

        // vertical animation.
        // water level increases from 0 to center of WaveView
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                waveview, "waterLevelRatio", 0f, 0.5f);
        waterLevelAnim.setDuration(10000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        animators.add(waterLevelAnim);

        // amplitude animation.
        // wave grows big then grows small, repeatedly
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                waveview, "amplitudeRatio", 0.0001f, 0.05f);
        amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(5000);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        animators.add(amplitudeAnim);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
    }


    private static MaterialMenuDrawable.IconState intToState(int state) {
        switch (state) {
            case 0:
                return MaterialMenuDrawable.IconState.BURGER;
            case 1:
                return MaterialMenuDrawable.IconState.ARROW;
            case 2:
                return MaterialMenuDrawable.IconState.X;
            case 3:
                return MaterialMenuDrawable.IconState.CHECK;
        }
        throw new IllegalArgumentException("Must be a number [0,3)");
    }

    private static int generateState(int previous) {
        int generated = new Random().nextInt(4);
        return generated != previous ? generated : generateState(previous);
    }

//    private static MaterialMenuDrawable getMaterialMenu(Toolbar toolbar) {
//        return (MaterialMenuDrawable) toolbar.getNavigationIcon();
//    }

    private void showShowcaseView(RadioButton radioButton) {
        ViewTarget target = new ViewTarget(R.id.target, this);
        final ShowcaseView s = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setTarget(target)
                .setContentTitle("yes，我就是邵恒")
                .setContentText("宇宙无敌，超级帅")
                .hideOnTouchOutside()
                .setShowcaseEventListener(new OnShowcaseEventListener() {
                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                        Log.e("onShowcaseViewDidHide","onShowcaseViewDidHide");
                        ViewTarget target1 = new ViewTarget(R.id.seekBar, MainActivity.this);
                        final ShowcaseView s2 = new ShowcaseView.Builder(MainActivity.this)
                                .blockAllTouches()
                                .withNewStyleShowcase()
                                .setTarget(target1)
                                .setContentTitle("这就是我")
                                .setContentText("我就在着")
                                .setStyle(R.style.CustomShowcaseTheme2)
                                .setShowcaseEventListener(new OnShowcaseEventListener() {
                                    @Override
                                    public void onShowcaseViewHide(ShowcaseView showcaseView) {
                                        ViewTarget target2 = new ViewTarget(R.id.seekBar, MainActivity.this);
                                        Button button = new Button(MainActivity.this);
                                        button.setBackgroundResource(R.drawable.ciclr);
                                        button.setText("YES");
                                        final ShowcaseView s3 = new ShowcaseView.Builder(MainActivity.this)
                                                .blockAllTouches()
                                                .setTarget(target2)
                                                .hideOnTouchOutside()
                                                .setContentTitle("这就是我")
                                                .setContentText("我就在着")
                                                .replaceEndButton(button)
                                                .build();
                                        s3.clearAnimation();
                                        s3.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                s3.hide();
                                                Intent intent = new Intent(MainActivity.this,AndroidSwipeLayoutActivity.class);
                                                startActivity(intent);
                                            }
                                        });

                                    }

                                    @Override
                                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                                    }

                                    @Override
                                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                                    }

                                    @Override
                                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                                    }
                                })
                                .build();
                        s2.setButtonText("完成");
                        s2.clearAnimation();

                    }

                    @Override
                    public void onShowcaseViewShow(ShowcaseView showcaseView) {
                        Log.e("onShowcaseViewShow","onShowcaseViewShow");
                    }

                    @Override
                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {
                        Log.e("s","onShowcaseViewTouchBlocked");
                    }
                })
                .build();
        s.setButtonText("下一步");
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.hide();
            }
        });
        s.clearAnimation();
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.show();
            }
        });
    }
}
