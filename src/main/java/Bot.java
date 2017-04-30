/**
 * This Class simply defines a Bot object. It holds simple data
 * about each Bot and defines an override toString method for
 * displaying in JList.
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
