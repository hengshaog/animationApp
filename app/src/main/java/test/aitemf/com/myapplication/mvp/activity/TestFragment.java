package test.aitemf.com.myapplication.mvp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipboard.bottomsheet.commons.BottomSheetFragment;

import test.aitemf.com.myapplication.R;

/**
 * Created by hengshao on 2017/12/16.
 */

public class TestFragment extends BottomSheetFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.testfragment, container, false);
        view.setMinimumHeight(1000);
        view.setBackgroundColor(Color.WHITE);
        return view;
    }

}