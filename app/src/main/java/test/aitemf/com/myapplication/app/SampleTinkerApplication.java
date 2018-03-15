package test.aitemf.com.myapplication.app;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by hengshao on 2018/1/15.
 */

public class SampleTinkerApplication extends TinkerApplication {

    public SampleTinkerApplication(){
        super(ShareConstants.TINKER_ENABLE_ALL, "test.aitemf.com.myapplication.app.MyApplication",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
