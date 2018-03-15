package test.aitemf.com.myapplication.mvp.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.dd.processbutton.iml.ActionProcessButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nispok.snackbar.Snackbar;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.r0adkll.slidr.Slidr;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.File;

import test.aitemf.com.myapplication.R;


/**
 * Created by hengshao on 2017/12/17.
 */

public class ButtonActivity extends AppCompatActivity{
    private ActionProcessButton btnSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        Slidr.attach(this);
        Logger.addLogAdapter(new AndroidLogAdapter());

        Toast.makeText(this,"新版本！",Toast.LENGTH_LONG).show();



        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setErrorText("Error");
        circularButton1.setText("UPLOAD");
        circularButton1.setCompleteText("Succees");
        circularButton1.setIdleText("开始");
        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    circularButton1.setProgress(50);
                } else if (circularButton1.getProgress() == 50) {
                    circularButton1.setProgress(100);
                } else if (circularButton1.getProgress() == -1) {
                    circularButton1.setProgress(0);
                } else {
                    circularButton1.setProgress(-1);
                }
                Snackbar.with(getApplicationContext()) // context
                        .text("index：" + circularButton1.getProgress()) // text to display
                        .actionColor(Color.WHITE)
                        .actionLabel("OK")
                        .textColor(Color.WHITE)
                        .duration(1000)
                        .animation(true)
                        .color(Color.RED)
                        .show(ButtonActivity.this);
            }
        });


        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSignIn.getProgress() == 0) {
                    btnSignIn.setProgress(50);
                } else if (btnSignIn.getProgress() == 50) {
                    btnSignIn.setProgress(80);
                } else if (btnSignIn.getProgress() == 80) {
                    btnSignIn.setProgress(100);
                } else if (btnSignIn.getProgress() == -1) {
                    btnSignIn.setProgress(0);
                } else {
                    btnSignIn.setProgress(-1);
                }
                Snackbar.with(getApplicationContext()) // context
                        .text("index：" + btnSignIn.getProgress()) // text to display
                        .actionColor(Color.WHITE)
                        .actionLabel("OK")
                        .textColor(Color.WHITE)
                        .duration(1000)
                        .animation(true)
                        .color(Color.RED)
                        .show(ButtonActivity.this);
            }

        });


        String[] data1 = new String[50];
        for (int i = 0; i < data1.length; i++)
            data1[i] = "item:" + i;

        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, data1);
        ListView mainListView = (ListView) findViewById(R.id.list);
        mainListView.setAdapter(adapter1);


        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
        actionC.setTitle("Hide/Show Action above");
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        final FloatingActionButton removeAction = (FloatingActionButton) findViewById(R.id.button_remove);
        removeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
            }
        });

        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(Color.WHITE);

        // Test that FAMs containing FABs with visibility GONE do not cause crashes
        findViewById(R.id.button_gone).setVisibility(View.GONE);

        final FloatingActionButton actionEnable = (FloatingActionButton) findViewById(R.id.action_enable);
        actionEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        FloatingActionsMenu rightLabels = (FloatingActionsMenu) findViewById(R.id.right_labels);
        FloatingActionButton addedOnce = new FloatingActionButton(this);
        addedOnce.setTitle("Added once");
        rightLabels.addButton(addedOnce);

        FloatingActionButton addedTwice = new FloatingActionButton(this);
        addedTwice.setTitle("Added twice");
        rightLabels.addButton(addedTwice);
        rightLabels.removeButton(addedTwice);
        rightLabels.addButton(addedTwice);
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

}
