package uuid.fhnw.ch.thisis;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import uuid.fhnw.ch.thisis.business.Answer;
import uuid.fhnw.ch.thisis.business.Question;
import uuid.fhnw.ch.thisis.business.User;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class ChatAdapter extends BaseAdapter {

    private static final int TYPE_MY_MESSAGE = 0;
    private static final int TYPE_ANSWER = 1;

    private final LayoutInflater mInflater;

    private List<Answer> mData = new ArrayList<>();

    private final Context context;
    private final Question question;

//    private Set<Integer> myMessagesPos = new TreeSet<>();
//    private Set<Integer> answerMessagesPos = new TreeSet<>();

    public ChatAdapter(Context context, Question question) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.context = context;
        this.question = question;

        mData = question.getAnswers();
    }

    @Override
    public int getItemViewType(int position) {
        Answer answer = mData.get(position);

        User answerer = answer.user;
        User questionAsker = question.getQuestioner();

        Log.d("ItemViewType", answerer + "-----" + questionAsker);

        if (answerer.getName().equals(questionAsker.getName())) {
            System.out.println("Match!!");
            return TYPE_MY_MESSAGE;
        } else {
            return TYPE_ANSWER;
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int type = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();

            if (type == TYPE_MY_MESSAGE) {
                convertView = mInflater.inflate(R.layout.row_layout_my_message, null);
                holder = new ViewHolder(convertView);
            } else {
                convertView = mInflater.inflate(R.layout.row_layout_answer, null);
                holder = new ViewHolder(convertView);
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        displayMessageValues(position, holder, type);

        return convertView;
    }

    private void displayMessageValues(int position, final ViewHolder holder, int type) {
        if (getItem(position) instanceof Answer) {
            Answer answer = (Answer) getItem(position);

            if (answer != null && holder != null) {
                holder.message.setText(answer.answer);

                Resources res = context.getResources();
                int resId = res.getIdentifier("user_image_" + answer.user.getName().toLowerCase(), "drawable", context.getPackageName());
                holder.profileImage.setImageResource(resId);

            }
        }
    }

    public void addAnswer(Answer newAnswer) {
        question.addAnswer(newAnswer);
        notifyDataSetChanged();
    }

    class ViewHolder {
        RelativeLayout wrapper;
        TextView message;
        TextView messageDate;
        CircularImageView profileImage;

        TextView dateSeparator;

        public ViewHolder(){}

        public ViewHolder(View view) {
            wrapper = (RelativeLayout) view.findViewById(R.id.chatConversationWrapper);

            message = (TextView) view.findViewById(R.id.chatConversationMessage);
            messageDate = (TextView) view.findViewById(R.id.chatConversationMessageDate);

            profileImage = (CircularImageView) view.findViewById(R.id.chatProfileImage);
            view.setTag(this);
        }
    }

}
