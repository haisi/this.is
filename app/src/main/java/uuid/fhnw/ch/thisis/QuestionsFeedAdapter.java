package uuid.fhnw.ch.thisis;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import uuid.fhnw.ch.thisis.business.Question;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class QuestionsFeedAdapter extends ArrayAdapter<Question> {

    public QuestionsFeedAdapter(Context context, int resource, List<Question> objects) {
        super(context, resource, objects);
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

        return convertView;
    }

    class ViewHolder {
        // TextView title;

        public ViewHolder(View view){
            // title = (TextView) view.findViewById(R.id.mysdfsd);
            view.setTag(this);
        }
    }

}
