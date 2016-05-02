package uuid.fhnw.ch.thisis.business;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public enum DataService {

    INSTACNE;

    public Set<User> allUsers = new TreeSet<>();
    public Set<Question> allQuestions = new TreeSet<>();

    DataService() {

        User hans = new User(1, "Hans");
        User sandra = new User(2, "Sandra");
        User reto = new User(3, "Reto");


        Question hansQuestion1 = new Question(1, "What does this sign mean?");
        hansQuestion1.setQuestioner(hans);
        hans.addQuestion(hansQuestion1);

        Question hansQuestion2 = new Question(2, "Bla bla bla");
        hansQuestion2.setQuestioner(hans);
        hans.addQuestion(hansQuestion2);

        addAllQuestions(hansQuestion1, hansQuestion2);
        addAllUsers(hans, sandra, reto);

    }

    private void addAllQuestions(Question...questions) {
        for (Question question : questions) {
            allQuestions.add(question);
        }
    }

    private void addAllUsers(User...users) {
        for (User user : users) {
            allUsers.add(user);
        }
    }
}
