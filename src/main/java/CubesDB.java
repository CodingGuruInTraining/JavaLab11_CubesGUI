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
    static final String USER = "mf8";
    static final String PASSWORD = "pw1";

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
    protected String prepStatDelete;
    protected PreparedStatement psDelete;


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
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
        Statement statement = connection.createStatement()) {

//            connection = connection2;
//            statement = statement2;

//            String dropTable = "drop table if exists " + table_name;
//            statement.executeUpdate(dropTable);

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

            fetchAll = "select * from " + table_name;
            rs = statement.executeQuery(fetchAll);

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




            rs.close();
            connection.close();
            statement.close();
        }
        catch (SQLException anError) {
            System.out.println("There was an error with the query.");
            anError.printStackTrace();
        }
    }

    protected void makeChanges(int option, String name, double time) {
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String prepStat = "";
            PreparedStatement prepStatement;

            switch (option) {
                case 1:
                    prepStat = "insert into " + table_name + " values ( ? , ? )";
                    prepStatement = connection.prepareStatement(prepStat);
                    prepStatement.setString(1, name);
                    prepStatement.setDouble(2, time);
                    prepStatement.executeUpdate();
                    break;
                case 2:
                    prepStat = "update " + table_name + " set " + time_col + " = ? where " + name_col + " = ?";
                    prepStatement = connection.prepareStatement(prepStat);
                    prepStatement.setDouble(1, time);
                    prepStatement.setString(2, name);
                    prepStatement.executeUpdate();
                    break;
                case 3:
                    prepStat = "delete from " + table_name + " where " + name_col + " like ?";
                    prepStatement = connection.prepareStatement(prepStat);
                    prepStatement.setString(1, name);
                    prepStatement.executeUpdate();
                    break;
            }

            connection.close();
            statement.close();
//            PreparedStatement prepStatement = connection.prepareStatement(prepStat);

        }
        catch (SQLException err) {
            System.out.println("There was an error with the query.");
            err.printStackTrace();
        }
    }


}
