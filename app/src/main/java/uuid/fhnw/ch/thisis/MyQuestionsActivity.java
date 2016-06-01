package uuid.fhnw.ch.thisis;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uuid.fhnw.ch.thisis.business.Answer;
import uuid.fhnw.ch.thisis.business.DataService;
import uuid.fhnw.ch.thisis.business.Question;
import uuid.fhnw.ch.thisis.business.User;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class MyQuestionsActivity extends AppCompatActivity {

    public final static String BUNDLE_KEY = "KEY";
    public final static String BUNDLE_QUESTIONS = "My questions";
    public final static String BUNDLE_ANSWERS = "My answers";

    private QuestionsFeedAdapter feedAdapter;
    private boolean showMyQuestions;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_question_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setStatusBarColor();

        showMyQuestions = getIntent().getExtras().getString(BUNDLE_KEY, BUNDLE_QUESTIONS).equals(BUNDLE_QUESTIONS);

        if (showMyQuestions) {
            setTitle(BUNDLE_QUESTIONS);
        } else {
            setTitle(BUNDLE_ANSWERS);
        }

        User currentUser = DataService.INSTACNE.currentUser();
        ArrayList<Question> allQuestions = new ArrayList<>(DataService.INSTACNE.allQuestions);

        if (showMyQuestions) {
            filterOnlyMyQuestions(currentUser, allQuestions);
        } else {
            filterOnlyMyAnswers(currentUser, allQuestions);
        }

        feedAdapter = new QuestionsFeedAdapter(getBaseContext(), allQuestions);
        final ListView questionFeed = (ListView) findViewById(R.id.questionFeed);
        questionFeed.setAdapter(feedAdapter);

        questionFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question selectedQuestion = feedAdapter.getItem(position);

                final Intent intent = new Intent(getBaseContext(), QuestionActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putLong("selected_question", selectedQuestion.getId());
                intent.putExtras(mBundle);

                startActivity(intent);
            }
        });

    }

    private void filterOnlyMyAnswers(User currentUser, ArrayList<Question> allQuestions) {
        List<Question> questionsToRemove = new ArrayList<>();

        for (Question question : allQuestions) {
            boolean hasAnsweredThisQuestion = false;

            for (Answer answer : question.getAnswers()) {
                hasAnsweredThisQuestion = answer.user.equals(currentUser);
            }

            if (!hasAnsweredThisQuestion) {
                questionsToRemove.add(question);
            }
        }

        allQuestions.removeAll(questionsToRemove);
    }

    private void filterOnlyMyQuestions(User currentUser, ArrayList<Question> allQuestions) {

        List<Question> questionsToRemove = new ArrayList<>();

        for (Question question : allQuestions) {
            if (!question.getQuestioner().equals(currentUser)) {
                questionsToRemove.add(question);
            }
        }

        allQuestions.removeAll(questionsToRemove);
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedAdapter.clear();

        User currentUser = DataService.INSTACNE.currentUser();
        ArrayList<Question> allQuestions = new ArrayList<>(DataService.INSTACNE.allQuestions);

        if (showMyQuestions) {
            filterOnlyMyQuestions(currentUser, allQuestions);
        } else {
            filterOnlyMyAnswers(currentUser, allQuestions);
        }

        feedAdapter.addAll(allQuestions);

        feedAdapter.notifyDataSetChanged();
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
