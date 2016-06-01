package uuid.fhnw.ch.thisis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.volokh.danylo.hashtaghelper.HashTagHelper;

import uuid.fhnw.ch.thisis.business.DataService;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class HashtagsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hashtags_activity);

        EditText subjects = (EditText) findViewById(R.id.subjects);

        final HashTagHelper mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimary), null);
        mTextHashTagHelper.handle(subjects);

        Button button = (Button) findViewById(R.id.showMeQuestions);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataService.INSTACNE.subjects.clear();
                DataService.INSTACNE.subjects.addAll(mTextHashTagHelper.getAllHashTags());

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
