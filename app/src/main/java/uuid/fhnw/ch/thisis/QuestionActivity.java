package uuid.fhnw.ch.thisis;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Set;

import uuid.fhnw.ch.thisis.business.DataService;
import uuid.fhnw.ch.thisis.business.Question;

/**
 * Displays a question.
 *
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class QuestionActivity extends AppCompatActivity {

    private TextView titleView;
    private TextView descriptionView;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final long selectedQuestionId = getIntent().getExtras().getLong("selected_question", 0);
        System.out.println("Selected question: " + selectedQuestionId);
        Set<Question> allQuestions = DataService.INSTACNE.allQuestions;
        System.out.println(allQuestions.toString());


        titleView = (TextView) findViewById(R.id.questionTitle);
        imageView = (ImageView) findViewById(R.id.questionImageView);
        descriptionView = (TextView) findViewById(R.id.questionDescription);

        for (Question question : allQuestions) {
            if (question.getId() == selectedQuestionId) {
                titleView.setText(question.getTitle());


                if (question.getImageName() != null) {
                    Resources res = getResources();
                    int resId = res.getIdentifier(question.getImageName(), "drawable", getPackageName());
                    imageView.setImageResource(resId);
                } else {
                    imageView.setVisibility(View.GONE);
                }

                descriptionView.setText(question.getDescription());
                break;
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
