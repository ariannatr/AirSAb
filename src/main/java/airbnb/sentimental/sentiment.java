package airbnb.sentimental;

/**
 * Created by Σταυρίνα on 28/9/2017.
 */
public class sentiment {
    public int getsentiment(String comment)
    {
        nlp.init();
        int retval=nlp.findSentiment(comment);
        System.out.println(comment + " : " + retval);
        return retval;
    }
}
