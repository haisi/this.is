package uuid.fhnw.ch.thisis.business;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public enum DataService {

    INSTACNE;

    public Set<User> allUsers = new TreeSet<>();
    public Set<Question> allQuestions = new TreeSet<>();

    private final User hans;
    private final User sandra;
    private final User reto;

    DataService() {

        hans = new User(1, "Hans");
        sandra = new User(2, "Sandra");
        reto = new User(3, "Reto");

        Question hansQuestion1 = new Question(1, "What does this sign mean?");
        hansQuestion1.setDescription("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.");
        hansQuestion1.setQuestioner(hans);
        hansQuestion1.setImageName("japanese_sign");
        hans.addQuestion(hansQuestion1);

        addChatMessagesToQuestion1(hansQuestion1);

        Question hansQuestion2 = new Question(2, "Bla bla bla");
        hansQuestion2.setQuestioner(hans);
        hans.addQuestion(hansQuestion2);

        addAllQuestions(hansQuestion1, hansQuestion2);
        addAllUsers(hans, sandra, reto);

    }

    private void addChatMessagesToQuestion1(Question hansQuestion1) {
        hansQuestion1.addAnswer(new Answer("Hello Hans, my japanese isn't greate but I think it means", sandra));
        hansQuestion1.addAnswer(new Answer("<<Dont eat in the train>>", sandra));

        hansQuestion1.addAnswer(new Answer("It says that you shouln't eat in the train", reto));

        hansQuestion1.addAnswer(new Answer("Thanks guys", hans));
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
