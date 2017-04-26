import java.sql.*;
import java.util.LinkedList;

/**
 * Created by MarkFox on 4/23/2017.
 */
public class CubesDB {
    // Defines the driver to be used.
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // Connection string.
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cubes";
    static final String USER = "";
    static final String PASSWORD = "";

    // Defines table name variable along with its columns.
    private String table_name;
    private String name_col;
    private String time_col;

    protected Connection connection;
    protected Statement statement;

    protected String prepStatInsert;
    protected PreparedStatement psInsert;
    protected String prepStatUpdate;
    protected PreparedStatement psUpdate;

    protected ResultSet rs;
    protected String fetchAll;
    protected LinkedList<Bot> allBots;
    protected String tempName;
    protected double tempTime;

    // Getters and Setters
    public String getTempName() {
        return tempName;
    }
    public void setTempName(String tempName) {
        this.tempName = tempName;
    }
    public double getTempTime() {
        return tempTime;
    }
    public void setTempTime(double tempTime) {
        this.tempTime = tempTime;
    }



    CubesDB() {
        table_name = "cubestbl";
        name_col = "Bot_Name";
        time_col = "Time_Taken";

        allBots = new LinkedList<Bot>();

        connectDB();



    }

    protected void connectDB() {
        try (Connection connection2 = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
        Statement statement2 = connection2.createStatement()) {

            connection = connection2;
            statement = statement2;

            String dropTable = "drop table if exists " + table_name;
            statement.executeUpdate(dropTable);

            String commandText = "create table if not exists " + table_name + " (" + name_col + " varchar(50), " + time_col + " double)";
            statement.executeUpdate(commandText);
            System.out.println("Table has been created maybe.");

// TODO remove static values once database is set up.
            // Makes two arrays of static values.
            String[] allNames = {"Cubestormer II robot", "Fakhri Raihaan (using his feet)", "Ruxin Liu (age 3)", "Mats Valk (human record holder)"};
            double[] allTimes = {5.270, 27.93, 99.33, 6.27};

            prepStatInsert = "insert into " + table_name + " values ( ? , ? )";
            psInsert = connection.prepareStatement(prepStatInsert);
            prepStatUpdate = "update " + table_name + " set " + time_col + " = ? where " + name_col + " = ?";
            psUpdate = connection.prepareStatement(prepStatUpdate);

            for (int x = 0; x < allNames.length; x++) {
                psInsert.setString(1, allNames[x]);
                psInsert.setDouble(2, allTimes[x]);
                psInsert.executeUpdate();
                allBots.add(new Bot(allNames[x], allTimes[x]));
            }

            fetchAll = "select * from " + table_name;
            rs = statement.executeQuery(fetchAll);


        }
        catch (SQLException anError) {
            System.out.println("There was an error with the query.");
            anError.printStackTrace();
        }
    }


}
