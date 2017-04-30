import java.sql.*;
import java.util.LinkedList;

/**
 * This Class acts as a manager for a rubik's cube solver database and GUI.
 */
public class CubesDB {
    // Defines the driver to be used.
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // Connection string.
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cubes";
    static final String USER = "mf";
    static final String PASSWORD = "pw";

    // Defines table name variable along with its columns.
    private String table_name;
    private String name_col;
    private String time_col;

//    protected Connection connection;
//    protected Statement statement;
//
//    protected String prepStatInsert;
//    protected PreparedStatement psInsert;
//    protected String prepStatUpdate;
//    protected PreparedStatement psUpdate;
//    protected String prepStatDelete;
//    protected PreparedStatement psDelete;

    // Defines variables for using database.
    protected ResultSet rs;
    protected String fetchAll;
    protected LinkedList<Bot> allBots;
    protected String tempName;
    protected double tempTime;

    // Getters and Setters.
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

    // Constructor.
    CubesDB() {
        // Sets static strings for table properties.
        table_name = "cubestbl";
        name_col = "Bot_Name";
        time_col = "Time_Taken";

        // Creates a new LinkedList to hold Bot objects.
        allBots = new LinkedList<Bot>();
        // Runs connect method.
        connectDB();
    }

    protected void connectDB() {
        // Connects to database using variables defined above.
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
        Statement statement = connection.createStatement()) {

//            connection = connection2;
//            statement = statement2;

//            String dropTable = "drop table if exists " + table_name;
//            statement.executeUpdate(dropTable);

            // Makes a SQL string to create table if it doesn't already exist.
            String commandText = "create table if not exists " + table_name + " (" + name_col + " varchar(50), " + time_col + " double)";
            statement.executeUpdate(commandText);
            System.out.println("Table has been created maybe.");

// TODO remove static values once database is set up.
//            // Makes two arrays of static values.
//            String[] allNames = {"Cubestormer II robot", "Fakhri Raihaan (using his feet)", "Ruxin Liu (age 3)", "Mats Valk (human record holder)"};
//            double[] allTimes = {5.270, 27.93, 99.33, 6.27};

//            prepStatInsert = "insert into " + table_name + " values ( ? , ? )";
//            psInsert = connection.prepareStatement(prepStatInsert);
//            prepStatUpdate = "update " + table_name + " set " + time_col + " = ? where " + name_col + " = ?";
//            psUpdate = connection.prepareStatement(prepStatUpdate);

            // Sets select all query string and performs query.
            fetchAll = "select * from " + table_name;
            rs = statement.executeQuery(fetchAll);

            // Uses ResultSet to create a new Bot object for each database entry.
            // These objects are then added to the LinkedList.
            while (rs.next()) {
                String botname = rs.getString(name_col);
                double bottime = rs.getDouble(time_col);
                allBots.add(new Bot(botname, bottime));
            }
//            for (int x = 0; x < allNames.length; x++) {
//                psInsert.setString(1, allNames[x]);
//                psInsert.setDouble(2, allTimes[x]);
//                psInsert.executeUpdate();
//                allBots.add(new Bot(allNames[x], allTimes[x]));
//            }



            // Closes connection.
            rs.close();
            connection.close();
            statement.close();
        }
        // Catch for SQL errors.
        catch (SQLException anError) {
            System.out.println("There was an error with the query.");
            anError.printStackTrace();
        }
    }

    // Method for editing database depending on selected option.
    protected void makeChanges(int option, String name, double time) {
        // Makes connection to database.
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Starts blank prepared statement that will be edited by
            // switch case.
            String prepStat = "";
            PreparedStatement prepStatement;

            // Based on option, defines the SQL string and adds parameters to prepared statement.
            switch (option) {
                case 1:     // ADD NEW ENTRY
                    prepStat = "insert into " + table_name + " values ( ? , ? )";
                    prepStatement = connection.prepareStatement(prepStat);
                    prepStatement.setString(1, name);
                    prepStatement.setDouble(2, time);
                    prepStatement.executeUpdate();
                    break;
                case 2:     // UPDATE EXISTING ENTRY
                    prepStat = "update " + table_name + " set " + time_col + " = ? where " + name_col + " = ?";
                    prepStatement = connection.prepareStatement(prepStat);
                    prepStatement.setDouble(1, time);
                    prepStatement.setString(2, name);
                    prepStatement.executeUpdate();
                    break;
                case 3:     // DELETE ENTRY
                    prepStat = "delete from " + table_name + " where " + name_col + " like ?";
                    prepStatement = connection.prepareStatement(prepStat);
                    prepStatement.setString(1, name);
                    prepStatement.executeUpdate();
                    break;
            }

            // Closes connection.
            connection.close();
            statement.close();
//            PreparedStatement prepStatement = connection.prepareStatement(prepStat);

        }
        // Catch for SQL errors.
        catch (SQLException err) {
            System.out.println("There was an error with the query.");
            err.printStackTrace();
        }
    }
}
