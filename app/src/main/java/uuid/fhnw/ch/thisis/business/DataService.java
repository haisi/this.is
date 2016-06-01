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
        hansQuestion1.setDescription("I'm at the kyoto central station and saw this sign. What does it mean?");
        hansQuestion1.setQuestioner(hans);
        hansQuestion1.setImageName("japanese_sign");
        hansQuestion1.setAnswered(true);
        hans.addQuestion(hansQuestion1);

        addChatMessagesToQuestion1(hansQuestion1);

        Question hansQuestion2 = new Question(2, "What kind of #drinking games are there?");
        hansQuestion2.setQuestioner(hans);
        hansQuestion2.setDescription("We are in a group watching a movie and would love to here you suggestions!");
        hans.addQuestion(hansQuestion2);
        addChatMessagesToQuestion2(hansQuestion2);

        Question sandraQuestion = new Question(3, "What can I #cook with this?");
        sandraQuestion.setQuestioner(sandra);
        sandraQuestion.setImageName("fridge");
        sandra.addQuestion(sandraQuestion);

        addAllQuestions(hansQuestion1, hansQuestion2, sandraQuestion);
        addAllUsers(hans, sandra, reto);

    }

    private void addChatMessagesToQuestion2(Question hansQuestion2) {
        hansQuestion2.addAnswer(new Answer("How about beer pong?", reto));
        hansQuestion2.addAnswer(new Answer("I agree with reto. Beer pong is greate fun!", sandra));
    }

    private void addChatMessagesToQuestion1(Question hansQuestion1) {
        hansQuestion1.addAnswer(new Answer("Hello Hans, my japanese isn't greate but I think it means", sandra));
        hansQuestion1.addAnswer(new Answer("<<Dont eat in the train>>", sandra));

        hansQuestion1.addAnswer(new Answer("It says that you shouln't eat in the train", reto));

        hansQuestion1.addAnswer(new Answer("Thanks guys", hans));
    }

    public void addAllQuestions(Question...questions) {
        for (Question question : questions) {
            allQuestions.add(question);
        }
    }

    private void addAllUsers(User...users) {
        for (User user : users) {
            allUsers.add(user);
        }
    }

    public User currentUser() {
        return hans;
    }
}
