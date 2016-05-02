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
    private Date created;

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

    @Override
    public int compareTo(Question another) {
        return created.compareTo(another.getCreated());
    }
}
