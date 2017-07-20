package mituo.wshoto.com.mituo;

import android.os.Environment;

/**
 * Created by user on 2017/6/30.
 */

public class Config {
    public static final String PATH_MOBILE = Environment.getExternalStorageDirectory().getPath() + "/mituo";


    public static final String PATH_SD = System.getenv("SECONDARY_STORAGE") + "/mituo";
}
