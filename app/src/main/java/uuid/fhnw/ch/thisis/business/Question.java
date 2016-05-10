package uuid.fhnw.ch.thisis.business;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class Question implements Comparable<Question> {

    private final long id;
    private final String title;
    private User questioner;
    private Date created = new Date();
    private boolean answered;

    private String description;
    private String imageName;

    private Set<Answer> answers = new TreeSet<>();

    public Question(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getQuestioner() {
        return questioner;
    }

    public void setQuestioner(User questioner) {
        this.questioner = questioner;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return id == question.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Question another) {

        return Long.valueOf(id).compareTo(another.getId());
    }
}
