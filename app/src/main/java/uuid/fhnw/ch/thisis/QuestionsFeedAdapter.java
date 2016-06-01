package uuid.fhnw.ch.thisis;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.Date;
import java.util.List;
import java.util.Set;

import uuid.fhnw.ch.thisis.business.Question;
import uuid.fhnw.ch.thisis.util.DateUtil;
import uuid.fhnw.ch.thisis.util.ImageUtils;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class QuestionsFeedAdapter extends ArrayAdapter<Question> {

    private final Drawable answeredBg;
    private final Drawable notAnsweredBg;
    private final int darkFontColor;
    private final int lightFontColor;

    public QuestionsFeedAdapter(Context context, List<Question> objects) {
        super(context, R.layout.row_layout_question_feed, objects);

        answeredBg = getContext().getResources().getDrawable(R.drawable.answered_bg);
        notAnsweredBg = getContext().getResources().getDrawable(R.drawable.not_answered_bg);

        darkFontColor = getContext().getResources().getColor(R.color.default_font_color);
        lightFontColor = getContext().getResources().getColor(R.color.white_font_color);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.row_layout_question_feed, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Question question = getItem(position);
        String title = question.getTitle();
        Date createdDate = question.getCreated();
        int numberOfAnswers = question.getAnswers().size();
        boolean answered = question.isAnswered();
        if (answered) {
            holder.numberOfAnswers.setBackground(answeredBg);
            holder.numberOfAnswers.setTextColor(lightFontColor);

        } else {
            holder.numberOfAnswers.setBackground(notAnsweredBg);
            holder.numberOfAnswers.setTextColor(darkFontColor);
        }

        if (question.getImageName() != null) {
            Resources res = getContext().getResources();
            int resId = res.getIdentifier(question.getImageName(), "drawable", getContext().getPackageName());

            Bitmap bitmap = ImageUtils.decodeDownsampledBitmap(res, resId, holder.image.getWidth(), holder.image.getHeight());
            holder.image.setImageBitmap(bitmap);
        } else {
            holder.image.setVisibility(View.GONE);
        }

        holder.title.setText(title);

        HashTagHelper mTextHashTagHelper = HashTagHelper.Creator.create(getContext().getResources().getColor(R.color.colorPrimary), null);
        mTextHashTagHelper.handle(holder.title);

        holder.date.setText(DateUtil.getFormattedDate(createdDate));
        holder.numberOfAnswers.setText("Answers: " + String.valueOf(numberOfAnswers));
        holder.questionerName.setText(question.getQuestioner().getName());

        return convertView;
    }

    class ViewHolder {

        ImageView image;
        TextView questionerName;
        TextView title;
        TextView date;

        TextView numberOfAnswers;

        public ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.questionImage);
            title = (TextView) view.findViewById(R.id.questionTitle);
            questionerName = (TextView) view.findViewById(R.id.questionerName);
            date = (TextView) view.findViewById(R.id.questionAskedDate);

            numberOfAnswers = (TextView) view.findViewById(R.id.numberOfAnswers);
            view.setTag(this);
        }
    }

}
