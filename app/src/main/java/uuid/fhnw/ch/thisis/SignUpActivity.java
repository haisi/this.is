package uuid.fhnw.ch.thisis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);
    }

    public void withFacebook(View view) {
        goToMainPage();
    }

    public void withTwitter(View view) {
        goToMainPage();
    }

    private void goToMainPage() {

        Toast.makeText(getApplicationContext(), "Successful sign up", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), HashtagsActivity.class);
        startActivity(intent);
        finish();
    }
}
