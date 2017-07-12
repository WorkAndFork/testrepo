import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by User on 31.05.2017.
 */
public class TableDAO {

  //  ScheduledTask scheduledTask = new ScheduledTask();

    Date now = new Date();
    Test mainTest = new Test();
    SimpleDateFormat checkDate = new SimpleDateFormat("dd.MM.yyyy");
    String todayDate = checkDate.format(now);


    public Connection c = null;

    public Connection getConnecion() throws java.sql.SQLException {
        if (c == null) {
            try {
                c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tasks",
                        "", "");
                c.setAutoCommit(false);
                System.out.println("db.opened");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                //System.exit(0);
            }
            return c;

        } else {
            return c;
        }

    }

    public void closeConnection () {

        if (c != null) {
            try {
                c.close();
                if (c.isClosed()) {
                    System.out.println("Closed");
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                //System.exit(0);
            }
        }
    }

    public void addNewName(String name, Integer userId) {
        Statement stmt = null;
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO public.\"Tasks\" (\"name\", \"userId\") " + "VALUES ('" + name + "', "+ userId +");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
    }

    public Integer checkLastTask (Integer userId) {
        Statement stmt = null;
        Integer maxId = 0;
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT MAX (\"id\") FROM public.\"Tasks\" WHERE \"userId\" = " + userId + ";");
            while (rs.next()){
                maxId = Integer.valueOf(rs.getString("max"));
            }
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
        return maxId;
    }

        public void addNewTask(Integer userId, String task) {
            Statement stmt = null;
            try {
                getConnecion();
                c.setAutoCommit(false);
                stmt = c.createStatement();
                String sql = "Update public.\"Tasks\" set \"task\" = '" + task + "' where \"userId\" = " + userId + " and \"id\" = " + checkLastTask(userId);
                stmt.executeUpdate(sql);
                stmt.close();
                c.commit();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                //System.exit(0);
            }
        }

    public void addNewTaskDate(Integer userId, String date) {
        Statement stmt = null;
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "Update public.\"Tasks\" set \"date\" = '" + date + "' where \"userId\" = " + userId + " and \"id\" = " + checkLastTask(userId) + ";";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
    }

    public String checkNameToUpload (Integer userId) {
        Statement stmt = null;
        Integer maxId = 0;
        String name = "s";
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT \"name\" FROM public.\"Tasks\" WHERE \"userId\" = " + userId + "and \"id\" = " + checkLastTask(userId) + ";");
            while (rs.next()){
                name = (rs.getString("name"));
            }
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
        return name;
    }

    public String checkTaskToUpload (Integer userId) {
        Statement stmt = null;
        Integer maxId = 0;
        String task = "s";
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT \"task\" FROM public.\"Tasks\" WHERE \"userId\" = " + userId + "and \"id\" = " + checkLastTask(userId) + ";");
            while (rs.next()){
                task = (rs.getString("task"));
            }
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
        return task;
    }

    public String checkDateToUpload (Integer userId) {
        Statement stmt = null;
        Integer maxId = 0;
        String date = "s";
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT \"date\" FROM public.\"Tasks\" WHERE \"userId\" = " + userId + "and \"id\" = " + checkLastTask(userId) + ";");
            while (rs.next()){
                date = (rs.getString("date"));
            }
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
        return date;
    }

    public String checkIdToUpload (Integer userId) {
        Statement stmt = null;
        Integer maxId = 0;
        String id = "s";
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT \"id\" FROM public.\"Tasks\" WHERE \"userId\" = " + userId + "and \"id\" = " + checkLastTask(userId) + ";");
            while (rs.next()){
                id = (rs.getString("id"));
            }
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
        return id;
    }

    public Integer checkUserId (Integer userId) {
        Statement stmt = null;
        Integer maxId = 0;
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT \"userId\" FROM public.\"Tasks\" WHERE \"userId\" = " + userId + ";");
            while (rs.next()){
                maxId = Integer.valueOf(rs.getString("max"));
            }
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
        return maxId;
    }

    public  ArrayList<String> containAllDates() {
        Statement stmt = null;
        String tmpDate = null;
        ArrayList<String> dates = new ArrayList<>();
        int i = 0;

        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT \"date\" from public.\"Tasks\";");
            while (rs.next()) {
                tmpDate = (rs.getString("date"));
                if (tmpDate == null) {
                    dates.add(i, "0");
                    i++;
                }
                else
                    {
                    dates.add(i, rs.getString("date"));
                    i++;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return dates;
    }

    public  ArrayList<String> getTodayDateTasks() {
        Statement stmt = null;
        String tmpTask = null;
        ArrayList<String> tasks = new ArrayList<>();
        int i = 0;

        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT \"task\" from public.\"Tasks\";");
            while (rs.next()) {
                tmpTask = (rs.getString("task"));
                if (tmpTask == null) {
                    tasks.add(i, "0");
                    i++;
                }
                else
                {
                    tasks.add(i, rs.getString("task"));
                    i++;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return tasks;
    }

    public  ArrayList<Integer> getTodayDateUserId() {
        Statement stmt = null;
        String tmpUserId = null;
        ArrayList<Integer> userId = new ArrayList<>();
        int i = 0;

        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT \"userId\" from public.\"Tasks\";");
            while (rs.next()) {
                tmpUserId = (rs.getString("userId"));
                if (tmpUserId == null) {
                    userId.add(i, Integer.valueOf("0"));
                    i++;
                }
                else
                {
                    userId.add(i, Integer.valueOf(rs.getString("userId")));
                    i++;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return userId;
    }

    public HashMap<Object, Object> getDataFromDBToCompare () {
        Statement stmt = null;
        Integer maxId = 0;
        HashMap<Object, Object> bdMap = new HashMap<>();
        Object key, task;
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT \"id\", \"task\" FROM public.\"Tasks\";");
            while (rs.next()){
                 key = (rs.getString("id"));
                 task = (rs.getString("task"));
                 bdMap.put(key, task);
            }
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
        return bdMap;
    }

    public void updateDbWithDifferences (){
        AnotherOperations operations = new AnotherOperations();
        HashMap<Object, Object> tempMap = new HashMap<>();
        tempMap = operations.getMapToUploadToDB();
        Statement stmt = null;
        try {
            getConnecion();
            c.setAutoCommit(false);
            stmt = c.createStatement();
            for (Object key : tempMap.keySet() ) {
                String sql = "Update public.\"Tasks\" set \"task\" = '" + tempMap.get(key) + "' where \"id\" = " + key + ";";

                stmt.executeUpdate(sql);
            }
            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
    }
}
