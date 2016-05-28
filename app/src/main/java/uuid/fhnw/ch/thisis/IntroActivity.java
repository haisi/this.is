package uuid.fhnw.ch.thisis;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class IntroActivity extends AppIntro {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        addSlide(CustomAppIntroFragment.newInstance("this.is", "Browse questions asked by you and others", R.drawable.intro_1, R.color.bg1));
        addSlide(CustomAppIntroFragment.newInstance("Chat with the questioner", "In an interactive chat you can answer and chat with the questioner and other answerers.", R.drawable.intro_2, R.color.bg2));
        addSlide(CustomAppIntroFragment.newInstance("Ask your own questions", "You can also ask own question and add an image wiht it.", R.drawable.intro_3, R.color.bg3));

        super.setBarColor(getResources().getColor(R.color.colorAccent));

        setStatusBarColor();

        showSkipButton(false);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void onSkipPressed() {

    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        startActivity(new Intent(getBaseContext(), SignUpActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
