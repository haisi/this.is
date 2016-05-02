package uuid.fhnw.ch.thisis;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Set;

import uuid.fhnw.ch.thisis.business.Question;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class QuestionsFeedAdapter extends ArrayAdapter<Question> {

    public QuestionsFeedAdapter(Context context, List<Question> objects) {
        super(context, R.layout.row_layout_question_feed, objects);
    }

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
            holder.statusImage.setImageResource(R.drawable.ic_menu_camera);
        } else {
            holder.statusImage.setImageResource(R.drawable.ic_menu_send);
        }

        holder.title.setText(title);
        holder.date.setText(createdDate.toString());
        holder.numberOfAnswers.setText(String.valueOf(numberOfAnswers));

        return convertView;
    }

    class ViewHolder {

        ImageView image;
        TextView title;
        TextView date;

        ImageView statusImage;
        TextView numberOfAnswers;

        public ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.questionImage);
            title = (TextView) view.findViewById(R.id.questionTitle);
            date = (TextView) view.findViewById(R.id.questionAskedDate);

            statusImage = (ImageView) view.findViewById(R.id.questionStatusImage);
            numberOfAnswers = (TextView) view.findViewById(R.id.numberOfAnswers);
            view.setTag(this);
        }
    }

}
