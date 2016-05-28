package uuid.fhnw.ch.thisis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Date;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class LoadActivity extends Activity {

    static {
        if (BuildConfig.DEBUG) {
            MIN_MILLISECONDS_OF_SPLASH_SCREEN = 0;
        } else {
            MIN_MILLISECONDS_OF_SPLASH_SCREEN = 2000;
        }
    }

    private static final long MIN_MILLISECONDS_OF_SPLASH_SCREEN;
    private Date startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        startTime = new Date();

//        if (false) {
        if (BuildConfig.DEBUG) {
            openNextActivityWhenMinTimeHasPassed(IntroActivity.class);
        } else {
            openNextActivityWhenMinTimeHasPassed(SignUpActivity.class);
        }
    }

    /**
     * Checks if the splash screen has been displayed the min amount of milliseconds
     * and starts the passed activity.
     *
     * @param clazz activity to start
     */
    private void openNextActivityWhenMinTimeHasPassed(Class clazz) {
        final Intent intent = new Intent(getBaseContext(), clazz);

        long timeSpend = new Date().getTime() - startTime.getTime();

        if(timeSpend < MIN_MILLISECONDS_OF_SPLASH_SCREEN) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    LoadActivity.this.finish();
                }
            }, MIN_MILLISECONDS_OF_SPLASH_SCREEN - timeSpend);

        } else {
            startActivity(intent);
            this.finish();
        }
    }

}
