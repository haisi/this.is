package uuid.fhnw.ch.thisis;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.HashSet;
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
        HashTagHelper mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimary), null);
        mTextHashTagHelper.handle(titleView);

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

                Set<User> users = new HashSet<>();
                for (Answer answer : question.getAnswers()) {
                    if (!answer.user.equals(DataService.INSTACNE.currentUser())) {
                        users.add(answer.user);
                    }
                }

                if (users.size() == 0) {
                    Toast.makeText(this, "Closing question", Toast.LENGTH_SHORT).show();
                    question.setAnswered(true);
                    onBackPressed();
                    return true;
                }

                LinearLayout container = new LinearLayout(this);
                container.setPadding(20, 0, 20, 0);
                container.setOrientation(LinearLayout.VERTICAL);

                float imageSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, this.getResources().getDisplayMetrics());

                for (User user : users) {

                    CircularImageView circularImageView = new CircularImageView(this);
                    circularImageView.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0f));

                    circularImageView.getLayoutParams().height = (int) imageSize;
                    circularImageView.getLayoutParams().width = (int) imageSize;

                    Resources res = this.getResources();
                    int resId = res.getIdentifier("user_image_" + user.getName().toLowerCase(), "drawable", this.getPackageName());
                    circularImageView.setImageResource(resId);

                    TextView name = new TextView(this);
                    name.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                    name.setText(user.getName());

                    Switch toggle = new Switch(this);
                    toggle.setTextOff("");
                    toggle.setTextOn("");

                    LinearLayout row = new LinearLayout(this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.addView(circularImageView);
                    row.addView(name);
                    row.addView(toggle);

                    container.addView(row);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(container);
                builder
                        .setTitle("Who was helpful")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                question.setAnswered(true);
                                Toast.makeText(QuestionActivity.this, "Closing question", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // leave empty
                            }
                        });

                builder.create().show();

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
