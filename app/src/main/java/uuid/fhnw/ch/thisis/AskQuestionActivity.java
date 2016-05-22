package uuid.fhnw.ch.thisis;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import uuid.fhnw.ch.thisis.business.DataService;
import uuid.fhnw.ch.thisis.business.Question;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class AskQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_question_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final EditText titleEdit = (EditText) findViewById(R.id.questionTitle);
        final EditText descriptionEdit = (EditText) findViewById(R.id.questionDescriptionEdit);

        Button saveButton = (Button) findViewById(R.id.askQuestionButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdit.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Title musn't be empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                Question newQuestion = new Question(new Date().getTime(), title);
                newQuestion.setQuestioner(DataService.INSTACNE.currentUser());

                String description = descriptionEdit.getText().toString();
                if (!description.trim().isEmpty()) {
                    newQuestion.setDescription(description);
                }

                Toast.makeText(getBaseContext(), "Saved your question!", Toast.LENGTH_LONG).show();

                DataService.INSTACNE.currentUser().addQuestion(newQuestion);

                DataService.INSTACNE.addAllQuestions(newQuestion);
                AskQuestionActivity.this.onBackPressed();
            }
        });

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
