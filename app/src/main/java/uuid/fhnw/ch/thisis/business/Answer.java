package uuid.fhnw.ch.thisis.business;

import java.util.Date;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class Answer implements Comparable<Answer> {

    private final long id;
    private final String answer;
    private final Date created;

    private Question question;
    private User user;


    public Answer(long id, String answer, Date created, Question question, User user) {
        this.id = id;
        this.answer = answer;
        this.created = created;
        this.question = question;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public Date getCreated() {
        return created;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return id == answer.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Answer another) {
        return created.compareTo(another.getCreated());
    }
}
