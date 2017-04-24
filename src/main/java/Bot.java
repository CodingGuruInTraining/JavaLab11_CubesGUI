/**
 * Created by MarkFox on 4/23/2017.
 */
public class Bot {
    protected String botName;
    protected double botTime;

    @Override
    public String toString() {
        return this.botName + "'s best time: " + this.botTime;
    }

    Bot(String name, double time) {
        this.botName = name;
        this.botTime = time;
    }
}
