import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by RomanNedz on 21.05.2017.
 */
public class StatesDAO {
    TableDAO dao = new TableDAO();

    public void stateUpdateAddName(Integer uId) {
        Statement stmt = null;
        try {
            dao.getConnecion();
            dao.c.setAutoCommit(false);
            stmt = dao.c.createStatement();
            String sql = "UPDATE public.\"States\" set \"state\" = 'AddName' WHERE \"userId\" = " + uId + ";";
            stmt.executeUpdate(sql);
            stmt.close();
            dao.c.commit();
         //   dao.closeConnection();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
    }

    public void stateUpdateAddTask(Integer uId) {
        Statement stmt = null;
        try {
            dao.getConnecion();
            dao.c.setAutoCommit(false);
            stmt = dao.c.createStatement();
            String sql = "UPDATE public.\"States\" set \"state\" = 'AddTask' WHERE \"userId\" = " + uId + ";";
            stmt.executeUpdate(sql);
            stmt.close();
            dao.c.commit();
         //   dao.closeConnection();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
    }

    public void stateUpdateAddDate(Integer uId) {
        Statement stmt = null;
        try {
            dao.getConnecion();
            dao.c.setAutoCommit(false);
            stmt = dao.c.createStatement();
            String sql = "UPDATE public.\"States\" set \"state\" = 'AddDate' WHERE \"userId\" = " + uId + ";";
            stmt.executeUpdate(sql);
           stmt.close();
            dao.c.commit();
         //   dao.closeConnection();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
    }

    public boolean checkIfExistsUser(Integer uId) {

        Statement stmt = null;
        boolean exists = false;

        try {
            dao.getConnecion();
            dao.c.setAutoCommit(false);
            System.out.println("O");
            stmt = dao.c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 'true' as \"Exists\" from public.\"States\" where \"userId\" =" + uId + ";");
            while (rs.next()) {
                exists = Boolean.valueOf((rs.getString("Exists")));
            }
            System.out.println(exists);
          //  dao.closeConnection();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
        return exists;
    }

    public String stateCheck(Integer uId) {
        Statement stmt = null;
        String state = "false";

        try {
            dao.getConnecion();
            dao.c.setAutoCommit(false);
            stmt = dao.c.createStatement();
            System.out.println("error check");
            ResultSet rs = stmt.executeQuery("SELECT \"state\" FROM \"States\" where \"userId\" = " + uId + ";");
            while (rs.next()) {
                state = rs.getString("state");
            }
            System.out.println("error checked");
          //  dao.closeConnection();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
        return state;
    }

    public void  stateInputUserId(Integer uId) {
        Statement stmt = null;
        if (uId != null) {
            try {
                dao.getConnecion();
                dao.c.setAutoCommit(false);


                stmt = dao.c.createStatement();
                String sql = "INSERT INTO public.\"States\" (\"userId\", \"state\") " + "VALUES (" + uId + ", 'AddName');";
                stmt.executeUpdate(sql);
                stmt.close();
                dao.c.commit();
                //    dao.closeConnection();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                //System.exit(0);
            }
            System.out.println("added");
        }
    }

}