package uuid.fhnw.ch.thisis;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import uuid.fhnw.ch.thisis.business.Answer;
import uuid.fhnw.ch.thisis.business.DataService;
import uuid.fhnw.ch.thisis.business.Question;
import uuid.fhnw.ch.thisis.business.User;

/**
 * Displays a question.
 *
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class QuestionActivity extends AppCompatActivity {

    private TextView titleView;
    private TextView descriptionView;
    private ImageView imageView;
    private ListView chatList;
    private EditText chatEditText;
    private ImageButton sendButton;

    private Question question;
    private ChatAdapter mAdapter;

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

        chatList = (ListView) findViewById(R.id.chatList);
        TextView emptyListText = (TextView) findViewById(R.id.emptyChatTextView);
        chatList.setEmptyView(emptyListText);

        chatEditText = (EditText) findViewById(R.id.chatConversationEditText);
        sendButton = (ImageButton) findViewById(R.id.sendMessageButton);

        for (Question question : allQuestions) {
            if (question.getId() == selectedQuestionId) {

                this.question = question;

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

        mAdapter = new ChatAdapter(this, question);
        chatList.setAdapter(mAdapter);

        setListViewHeightBasedOnChildren(chatList);

        addSendButtonListener();

        chatList.setFocusable(false);
    }

    private void addSendButtonListener() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatEditText.getText().toString();

                if (!message.isEmpty()) {
                    final User currentUser = DataService.INSTACNE.currentUser();
                    final Answer newAnswer = new Answer(message, currentUser);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            chatEditText.getText().clear();

                            mAdapter.addAnswer(newAnswer);

                            setListViewHeightBasedOnChildren(chatList);
                        }
                    }, 300);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (question.getQuestioner().getName().equals(DataService.INSTACNE.currentUser().getName())
                && !question.isAnswered()) {
            getMenuInflater().inflate(R.menu.my_question_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.closeQuestionItem:
                // // FIXME: 22.05.2016 add dialog to select helpful users
                Toast.makeText(this, "Closing question", Toast.LENGTH_SHORT).show();
                question.setAnswered(true);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight=0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        RelativeLayout.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }
}
