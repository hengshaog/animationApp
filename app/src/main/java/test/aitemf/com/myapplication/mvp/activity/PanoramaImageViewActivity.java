package test.aitemf.com.myapplication.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;

import test.aitemf.com.myapplication.R;

/**
 * Created by hengshao on 2017/12/21.
 */

public class PanoramaImageViewActivity extends AppCompatActivity {
    private GyroscopeObserver gyroscopeObserver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panoramaimage);

        gyroscopeObserver = new GyroscopeObserver();
        // Set the maximum radian the device should rotate to show image's bounds.
        // It should be set between 0 and π/2.
        // The default value is π/9.
        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);

        final PanoramaImageView panoramaImageView = (PanoramaImageView) findViewById(R.id.panorama_image_view);
        // Set GyroscopeObserver for PanoramaImageView.
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
        panoramaImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                panoramaImageView.setImageDrawable(getResources().getDrawable(R.mipmap.horizontal3));
                return true;
            }
        });

        panoramaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panoramaImageView.setImageDrawable(getResources().getDrawable(R.mipmap.vertical1));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }
}
