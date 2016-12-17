package javalab5;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBTester {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    new DBTester().test();
  }
  
  private static final Logger logger = Logger.getLogger(DBTester.class.getName());

  private Connection connectToDB() throws SQLException {
    String driverName = "org.apache.derby.jdbc.ClientDriver40";
    try {
      Class.forName(driverName).newInstance();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "ERROR: Driver <{0}> not found", driverName);
      System.exit(0);
    }

    //  jdbc - url:
    String dbUrl = "jdbc:derby://localhost:1527/groupdb";

    Properties connInfo = new Properties();
    connInfo.put("javaclient", "app");
    // connInfo.put("schema", "APP");
    connInfo.put("java", "app");

    Connection conn = null;
    conn = DriverManager.getConnection(dbUrl, "javaclient", "java");
    return conn;
  }
  
  void test() {
    Connection conn = null;
    try {
      conn = connectToDB();
      System.out.println(conn.getSchema());

      createTablesIfNeeded(conn);
      viewGroups(conn);
      viewItems(conn);
      doWork(conn);
      viewItems(conn);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException ex) {
        logger.log(Level.SEVERE, "Can't close connection", ex);
      }
    }
  }
  
  private void doWork(Connection conn) throws SQLException {
    String groupName = "Сборная России по баскетболу";
    int groupId = getGroupID(groupName, conn);
    if (groupId == -1) {
      System.err.println("Группы с именем " + groupName + " не существует");
    }
    else {
      System.out.println("ID группы " + groupName + " : " + groupId);
    }
    
    viewItemsInGroup(conn, 3);
    viewItemsInGroupByName(conn, groupName);
    addItemToGroup(conn, "Ветров К.А.", groupName);
    
  }
  
  private void viewGroups(Connection conn) throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rst = null;
    
    try {
      final String sql_groups = "SELECT * FROM ITEMGROUP ";
      rst = stmt.executeQuery(sql_groups);

      //System.out.println("rst= " + rst.toString());
      ResultSetMetaData meta = rst.getMetaData();
      System.out.println("Таблица " + meta.getTableName(1));
      System.out.printf("headers %10s | %30s\n", meta.getColumnName(1),
                                                 meta.getColumnName(2));

      while (rst.next()) {
        System.out.printf("row %3d:", rst.getRow());
        int id;
        String title;

        id = rst.getInt(1);
        title = rst.getString("TITLE");
        System.out.printf("%10d | %30s\n", id, title);
      }
      stmt.close();
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    finally {
      try {
        if (rst != null) rst.close();
        if (stmt != null) stmt.close();
      } catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
  }
  
  private void viewItems(Connection conn) throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rst = null;

    try {
      final String sqlGroups = "SELECT NAME_FULL, ITEMGROUP.TITLE AS GROUP_TITLE"
                                + " FROM ITEM "
                                + " LEFT OUTER JOIN ITEMGROUP ON ITEM.GROUPID = ITEMGROUP.ID ";
      rst = stmt.executeQuery(sqlGroups);
      ResultSetMetaData meta = rst.getMetaData();
      System.out.println("Таблица " + meta.getTableName(1));
      System.out.printf("headers %30s | %30s\n", meta.getColumnName(1),
                                                 meta.getColumnName(2));

      while (rst.next()) {
        System.out.printf("row %3d:", rst.getRow());
        int id;
        String name, groupName;

        //id = rst.getInt(1);
        name = rst.getString("NAME_FULL");
        groupName = rst.getString("GROUP_TITLE");
        System.out.printf("%30s | %30s\n", name, groupName);
      }
      stmt.close();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    } finally {
      try {
        if (rst != null) {
          rst.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
  }
  
  private int getGroupID(String name, Connection conn) {
    PreparedStatement stmt = null;
    ResultSet rst = null;
    int id = -1;

    try {
      final String sqlGroup = "SELECT ID FROM ITEMGROUP WHERE TITLE=?";
      stmt = conn.prepareStatement(sqlGroup);
      stmt.setString(1, name);
      rst = stmt.executeQuery();
      
      if (rst.next()) {
        id = rst.getInt(1);
      }
      stmt.close();
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    finally {
      try {
        if (rst != null) rst.close();
        if (stmt != null) stmt.close();
      }
      catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
    return id;
  }
  
  private void viewItemsInGroup(Connection conn, int groupid) {
    PreparedStatement stmt = null;
    ResultSet rst = null;

    try {
      final String sqlGroup = "SELECT ID, NAME_FULL, GROUPID FROM ITEM WHERE GROUPID=?";
      stmt = conn.prepareStatement(sqlGroup);
      stmt.setInt(1, groupid);
      rst = stmt.executeQuery();
      if (rst.next()) {
        ResultSetMetaData meta = rst.getMetaData();
        System.out.println("Таблица элементов группы " + groupid);
        System.out.printf("headers %5s | %15s | %5s\n", meta.getColumnName(1),
                                                        meta.getColumnName(2),
                                                        meta.getColumnName(3));

        do {
          System.out.printf("row %3d:", rst.getRow());
          int id, groupId;
          String name;

          id = rst.getInt(1);
          name = rst.getString("NAME_FULL");
          groupId = rst.getInt("GROUPID");
          System.out.printf(" %5d | %15s | %5d\n", id, name, groupId);
        } while (rst.next());
      }
      else {
        System.out.println("Элементов нет");
      }
      
      stmt.close();
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    finally {
      try {
        if (rst != null) rst.close();
        if (stmt != null) stmt.close();
      }
      catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
  }
  
  private void viewItemsInGroupByName(Connection conn, String groupName) {
    PreparedStatement stmt = null;
    ResultSet rst = null;

    try {
      final String sqlGroup = "SELECT NAME_FULL, ITEMGROUP.TITLE AS TITLE_GR"
                              + " FROM ITEM "
                              + " INNER JOIN ITEMGROUP ON ITEM.GROUPID = ITEMGROUP.ID "
                              + " WHERE ITEMGROUP.TITLE=?";
      stmt = conn.prepareStatement(sqlGroup);
      stmt.setString(1, groupName);
      rst = stmt.executeQuery();
      if (rst.next()) {
        ResultSetMetaData meta = rst.getMetaData();
        System.out.println("Таблица элементов группы с именем " + groupName);
        System.out.printf("headers %15s | %30s\n", meta.getColumnName(1),
                                                         meta.getColumnName(2));

        do {
          System.out.printf("row %3d:", rst.getRow());
          int id;
          String name, groupN;

          //id = rst.getInt(1);
          name = rst.getString("NAME_FULL");
          groupN = rst.getString("TITLE_GR");
          System.out.printf(" %15s | %30s\n", name, groupN);
        } while (rst.next());
      } else {
        System.out.println("Элементов нет");
      }

      stmt.close();
    } 
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    finally {
      try {
        if (rst != null) {
          rst.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      }
      catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
  }
  
  private void createTablesIfNeeded(Connection conn) throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rst = null;

    try {
      final String sql_groups = "SELECT * FROM ITEMS ";
      System.out.println(stmt.execute(sql_groups));
      //ResultSetMetaData meta = rst.getMetaData();
      System.out.println("Таблиfgdfца ");
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    finally {
      try {
        if (rst != null) rst.close();
        if (stmt != null) stmt.close();
      }
      catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
    
  }
  
  private boolean addItemToGroup(Connection conn, String itemName, String groupName) {
    int groupId = getGroupID(groupName, conn);
    PreparedStatement stmt = null;
    ResultSet rst = null;
    
    try {
      conn.setAutoCommit(false);
      final String sqlItem = "INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES(?,?)";
      stmt = conn.prepareStatement(sqlItem);
      stmt.setString(1, itemName);
      stmt.setInt(2, groupId);
      stmt.executeUpdate();
      conn.commit();
      
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    finally {
      try {
        if (rst != null) rst.close();
        if (stmt != null) stmt.close();
      } catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
    
    return true;
  }
  
}
