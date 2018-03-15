package test.aitemf.com.myapplication.mvp.activity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.aitemf.com.myapplication.R;

/**
 * Created by hengshao on 2017/12/20.
 */

public class TestsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.testfragment, container, false);
        return view;
    }
}
