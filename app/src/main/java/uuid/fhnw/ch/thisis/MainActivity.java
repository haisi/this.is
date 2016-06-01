package uuid.fhnw.ch.thisis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import uuid.fhnw.ch.thisis.business.DataService;
import uuid.fhnw.ch.thisis.business.Question;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private QuestionsFeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                final Intent intent = new Intent(getBaseContext(), AskQuestionActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<Question> allQuestions = new ArrayList<>(DataService.INSTACNE.allQuestions);
        ArrayList<Question> onlyUnansweredQuestions = new ArrayList<>();
        for (Question question : allQuestions) {
            if (!question.isAnswered()) {
                onlyUnansweredQuestions.add(question);
            }
        }

        Collections.sort(onlyUnansweredQuestions, new Comparator<Question>() {
            @Override
            public int compare(Question lhs, Question rhs) {
                return rhs.getCreated().compareTo(lhs.getCreated());
            }
        });

        feedAdapter = new QuestionsFeedAdapter(getBaseContext(), onlyUnansweredQuestions);
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

        EditText subjects = (EditText) findViewById(R.id.subjects);
        final HashTagHelper mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimary), null);
        mTextHashTagHelper.handle(subjects);

        String subjectsStr = "";

        for (String subject : DataService.INSTACNE.subjects) {
            subjectsStr += "#" + subject + " ";
        }

        subjects.setText(subjectsStr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedAdapter.clear();

        ArrayList<Question> allQuestions = new ArrayList<>(DataService.INSTACNE.allQuestions);
        ArrayList<Question> onlyUnansweredQuestions = new ArrayList<>();
        for (Question question : allQuestions) {
            if (!question.isAnswered()) {
                onlyUnansweredQuestions.add(question);
            }
        }

        Collections.sort(onlyUnansweredQuestions, new Comparator<Question>() {
            @Override
            public int compare(Question lhs, Question rhs) {
                return rhs.getCreated().compareTo(lhs.getCreated());
            }
        });

        feedAdapter.addAll(new ArrayList<>(onlyUnansweredQuestions));
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.myQuestions) {
            final Intent intent = new Intent(getBaseContext(), MyQuestionsActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(MyQuestionsActivity.BUNDLE_KEY, MyQuestionsActivity.BUNDLE_QUESTIONS);
            intent.putExtras(mBundle);

            startActivity(intent);
        } else if (id == R.id.myAnswers) {
            final Intent intent = new Intent(getBaseContext(), MyQuestionsActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(MyQuestionsActivity.BUNDLE_KEY, MyQuestionsActivity.BUNDLE_ANSWERS);
            intent.putExtras(mBundle);

            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
