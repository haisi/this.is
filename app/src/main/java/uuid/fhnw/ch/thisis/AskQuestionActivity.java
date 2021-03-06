package uuid.fhnw.ch.thisis;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.Date;
import java.util.Random;

import uuid.fhnw.ch.thisis.business.DataService;
import uuid.fhnw.ch.thisis.business.Question;
import uuid.fhnw.ch.thisis.util.ImageUtils;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class AskQuestionActivity extends AppCompatActivity {

    private TextView optionalText;
    private ImageView removeImageButton;
    private ImageView newQuestionImage;

    private HashTagHelper mTextHashTagHelper;

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
        setContentView(R.layout.ask_question_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setStatusBarColor();

        final EditText titleEdit = (EditText) findViewById(R.id.questionTitle);
        final EditText descriptionEdit = (EditText) findViewById(R.id.questionDescriptionEdit);

        mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimary), null);
        mTextHashTagHelper.handle(titleEdit);

        removeImageButton = (ImageView) findViewById(R.id.removeImageButton);
        removeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                optionalText.setVisibility(View.VISIBLE);

                selectedImage = null;

                newQuestionImage.setImageResource(R.drawable.ic_menu_camera);

            }
        });

        newQuestionImage = (ImageView) findViewById(R.id.newQuestionImage);
        optionalText = (TextView) findViewById(R.id.optionallyAddImage);
        addListener(newQuestionImage);

        final Button saveButton = (Button) findViewById(R.id.askQuestionButton);
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
                newQuestion.setHastags(mTextHashTagHelper.getAllHashTags());

                String description = descriptionEdit.getText().toString();
                if (!description.trim().isEmpty()) {
                    newQuestion.setDescription(description);
                }

                if (selectedImage != null) {
                    newQuestion.setImageName(selectedImage);
                }

                Toast.makeText(getBaseContext(), "Saved your question!", Toast.LENGTH_LONG).show();

                DataService.INSTACNE.currentUser().addQuestion(newQuestion);

                DataService.INSTACNE.addAllQuestions(newQuestion);
                AskQuestionActivity.this.onBackPressed();
            }
        });

        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int titleLength = titleEdit.getText().length();
                if (titleLength > 0) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    String selectedImage = null;

    private final String[] images = {"cabel", "flowers", "plane"};

    private void addListener(final ImageView newQuestionImage) {

        final Context context = this;

        final Random random = new Random();

        newQuestionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionalText.setVisibility(View.GONE);
                removeImageButton.setVisibility(View.VISIBLE);

                Resources res = context.getResources();

                String nextImage = images[random.nextInt(images.length)];
                selectedImage = nextImage;

                int resId = res.getIdentifier(nextImage, "drawable", context.getPackageName());
                Bitmap bitmap = ImageUtils.decodeDownsampledBitmap(res, resId, newQuestionImage.getWidth(), newQuestionImage.getHeight());
                newQuestionImage.setImageBitmap(bitmap);
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
