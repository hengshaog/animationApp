package test.aitemf.com.myapplication.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

import java.util.Collections;
import java.util.Comparator;

import test.aitemf.com.myapplication.R;

/**
 * Created by hengshao on 2017/12/18.
 */

public class BottomSheetActivityLayout extends AppCompatActivity {
    private BottomSheetLayout bottomSheetLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomsheetlayout);

        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        final TextView shareText = (TextView) findViewById(R.id.share_text);
        final Button shareButton = (Button) findViewById(R.id.share_button);
        Button custom = findViewById(R.id.custom_button);

        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TestFragment().show(getSupportFragmentManager(),R.id.bottomsheet);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(shareText.getWindowToken(), 0);

                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText.getText().toString());
                shareIntent.setType("text/plain");
                IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(BottomSheetActivityLayout.this, shareIntent, "Share with...", new IntentPickerSheetView.OnIntentPickedListener() {
                    @Override
                    public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                        bottomSheetLayout.dismissSheet();
                        startActivity(activityInfo.getConcreteIntent(shareIntent));
                    }
                });
                // Filter out built in sharing options such as bluetooth and beam.
                intentPickerSheet.setFilter(new IntentPickerSheetView.Filter() {
                    @Override
                    public boolean include(IntentPickerSheetView.ActivityInfo info) {
                        return !info.componentName.getPackageName().startsWith("com.android");
                    }
                });
                // Sort activities in reverse order for no good reason
                intentPickerSheet.setSortMethod(new Comparator<IntentPickerSheetView.ActivityInfo>() {
                    @Override
                    public int compare(IntentPickerSheetView.ActivityInfo lhs, IntentPickerSheetView.ActivityInfo rhs) {
                        return rhs.label.compareTo(lhs.label);
                    }
                });

                // Add custom mixin example
                Drawable customDrawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, null);
                IntentPickerSheetView.ActivityInfo customInfo = new IntentPickerSheetView.ActivityInfo(customDrawable, "Custom mix-in", BottomSheetActivityLayout.this, MainActivity.class);
                intentPickerSheet.setMixins(Collections.singletonList(customInfo));

                bottomSheetLayout.showWithSheetView(intentPickerSheet);
            }
        });
    }
}
