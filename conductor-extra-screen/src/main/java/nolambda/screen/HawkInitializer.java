package nolambda.screen;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

public class HawkInitializer {
    public static void init(@NonNull Context context) {
        Context appContext = context.getApplicationContext();
        Hawk.init(appContext)
                .setEncryption(new NoEncryption())
                .build();
    }
}
