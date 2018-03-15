package test.aitemf.com.myapplication.mvp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.glomadrian.codeinputlib.CodeInput;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;
import com.xenione.digit.TabDigit;

import me.grantland.widget.AutofitTextView;
import test.aitemf.com.myapplication.R;


/**
 * Created by hengshao on 2017/12/20.
 */

public class DiscrollviewActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discrollview);
        final TabDigit tabDigit = findViewById(R.id.tabDigit1);
        tabDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabDigit.start();
            }
        });
        tabDigit.setChar(1);

        ViewCompat.postOnAnimationDelayed(tabDigit, new Runnable() {
            @Override
            public void run() {
                tabDigit.start();
                ViewCompat.postOnAnimationDelayed(tabDigit, this, 1000);
            }
        }, 1000);


        final AutofitTextView autoview = findViewById(R.id.autoview);
        final EditText edit = findViewById(R.id.edit);

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                autoview.setText(edit.getText().toString());
            }
        });

        ShimmerTextView shimmers = findViewById(R.id.shimmer_tv);
        Shimmer shimmer = new Shimmer();
        shimmer.setRepeatCount(0)
                .setDuration(500)
                .setStartDelay(300)
                .setRepeatCount(50)
                .setDirection(Shimmer.ANIMATION_DIRECTION_RTL);

        shimmer.start(shimmers);

        ShimmerFrameLayout container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmerAnimation();

        final SecretTextView textview1 = findViewById(R.id.textview);
        textview1.setDuration(5000);
        textview1.show();
        textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textview1.show();
            }
        });


        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect("SH",Color.RED,100);

        ImageView image = (ImageView) findViewById(R.id.image_view);
        image.setImageDrawable(drawable);
        final CodeInput cInput = (CodeInput) findViewById(R.id.codeinput);
        cInput.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(DiscrollviewActivity.this, cInput.getCode()[1].toString()+cInput.getCode()[2].toString(),Toast.LENGTH_LONG).show();
                return false;
            }
        });

        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);

        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "恒少", "帅", "还是帅");
        rotatable.setSize(35);
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent("This is ?", rotatable);

    }

}
