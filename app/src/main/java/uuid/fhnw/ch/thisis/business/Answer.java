package uuid.fhnw.ch.thisis.business;

import java.util.Date;

/**
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class Answer {

    public final String answer;
    public final Date created = new Date();
    public final User user;


    public Answer(String answer, User user) {
        this.answer = answer;
        this.user = user;
    }

}
