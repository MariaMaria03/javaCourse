package javalab5;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;

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
      viewGroups(conn);
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
    
    changeItemFromFile("items.txt", conn);
    changeGroupFromFile("groups.txt", conn);
    viewItemsInGroup(conn, 3);
    viewItemsInGroup(conn, groupName);
    viewItemsInGroupByName(conn, groupName);
    if (addItemToGroup(conn, "Чистяков В.Р.", "Сборная России по борьбе")) {
      System.out.println("Запись добавлена");
    }
    else {
      System.out.println("Запись не добавлена. Уже есть или указанной группы не существует");
    }
    if (removeItemFromGroup(conn, "Георгиев А.Т.", "Сборная России по борьбе")) {
      System.out.println("Запись удалена");
    } else {
      System.out.println("Запись не удалена или ее не существует");
    }
    
  }
  
  private HashSet<String> viewGroups(Connection conn) throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rst = null;
    HashSet<String> res = new HashSet<String>();
    try {
      final String sql_groups = "SELECT * FROM ITEMGROUP ";
      rst = stmt.executeQuery(sql_groups);

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
        res.add(title);
      }
      stmt.close();
      return res;
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
      return res;
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
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    } finally {
        if (rst != null) {
          rst.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } 
  }
  
  private int getGroupID(String name, Connection conn) throws SQLException {
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
    }
    finally {
        if (rst != null) rst.close();
        if (stmt != null) stmt.close();
    }
    return id;
  }
  
  
  // Просмотр всех элементов группы по ID группы
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
  
  // Просмотр элементов группы по имени группы: 1 вариант - без join'а
  private void viewItemsInGroup(Connection conn, String groupName) throws SQLException {
    int groupId = getGroupID(groupName, conn);
    viewItemsInGroup(conn, groupId);
  }
  
  // Просмотр элементов группы по имени группы: 2 вариант - c join'ом
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
  
  // Проверяем существует ли таблица
  private boolean isExistTable(Connection conn, String query) throws SQLException {
    Statement stmt = conn.createStatement();
    try {
      stmt.execute(query);
    } catch (SQLException ex) {
      return false;
    }
    return true;
  }
  
  // Добавляем данные в таблицу Item
  private void insertDataItem(Connection conn) throws SQLException {
    String sqlInsertItem = "INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES(?,?)";
    String nI[] = {"Быстрова Т.А.", "Кротов П.Р.", "Самсонова Р.Р.", "Петров В.Р.",
      "Федорова Л.Д.", "Султанов Г.Ш.", "Антипова М.И.", "Никитин Д.С."};
    Integer groupItems[] = {1, 3, 2, 2, 1, 1, 3, 3};
    PreparedStatement stmtCreate = conn.prepareStatement(sqlInsertItem);
    for (int i = 0; i < nI.length; i++) {
      stmtCreate.setString(1, nI[i]);
      stmtCreate.setInt(2, groupItems[i]);
      stmtCreate.executeUpdate();
    }
  }
  
  private void createTablesIfNeeded(Connection conn) throws SQLException {
    Statement stmt = conn.createStatement();
    PreparedStatement stmtCreate = null;
    boolean isItemExist = isExistTable(conn, "SELECT * FROM ITEM ");
    final String sqlGroups = "CREATE TABLE ITEMGROUP "
        + "(ID INTEGER PRIMARY KEY generated always as identity,"
        + " TITLE VARCHAR(100) UNIQUE NOT NULL) ";
    final String sqlItems = " CREATE TABLE ITEM (ID INTEGER PRIMARY KEY generated always as identity, "
        + " NAME_FULL VARCHAR(100) UNIQUE NOT NULL, GROUPID INTEGER,"
        + " FOREIGN KEY (GROUPID) REFERENCES ITEMGROUP(ID) ON DELETE CASCADE)";
    String titleGr[] = {"Сборная России по биатлону", "Сборная России по баскетболу",
                        "Сборная России по борьбе"};
    String sqlInsertGroup = "INSERT INTO ITEMGROUP(TITLE) VALUES(?)";

    try {      
      if (isExistTable(conn, "SELECT * FROM ITEMGROUP ")) {
        System.out.println("Таблица ITEMGROUP уже существует");
      }
      else {
        if (isItemExist) stmt.executeUpdate(" DROP TABLE ITEM");
        stmt.executeUpdate(sqlGroups);
        stmt.executeUpdate(sqlItems);
        System.out.println("Создали таблицы: ITEM, ITEMGROUP");
        stmtCreate = conn.prepareStatement(sqlInsertGroup);
        for (int i = 0; i < titleGr.length; i++) {
          stmtCreate.setString(1, titleGr[i]);
          stmtCreate.executeUpdate();
        }
        insertDataItem(conn);
      }
      if (isItemExist) {
        System.out.println("Таблица ITEM уже существует");
      }
      else {
        stmt.executeUpdate(sqlItems);
        System.out.println("Создали таблицу ITEM");
        insertDataItem(conn);
      }
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    finally {
      try {
        if (stmt != null) stmt.close();
        if (stmtCreate != null) stmtCreate.close();
      }
      catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
    
  }
  
  private boolean addItemToGroup(Connection conn, String itemName, String groupName) throws SQLException {
    String sqlItem = "INSERT INTO ITEM(NAME_FULL,GROUPID) VALUES(?,?)";
    return changeItem(conn, itemName, groupName, sqlItem);
  }
  
  private boolean removeItemFromGroup(Connection conn, String itemName, String groupName) throws SQLException {
    String sqlItem = "DELETE FROM ITEM WHERE NAME_FULL=? AND GROUPID=?";
    return changeItem(conn, itemName, groupName, sqlItem);
  }
  
  // Выполняем запрос - добавление или удаление элемента
  private boolean changeItem(Connection conn, String itemName, String groupName, String query) throws SQLException {
    int groupId = getGroupID(groupName, conn);
     //stmt = null;

    try (PreparedStatement stmt = conn.prepareStatement(query)){
      stmt.setString(1, itemName);
      stmt.setInt(2, groupId);
      stmt.executeUpdate();
    } catch (SQLException ex) {
      return false;
    } 
    return true;
  }
  
  // Считываем строчки про Items  из файла - проводим общую транзакцию
  private void changeItemFromFile(String fileName, Connection conn) throws SQLException {
    BufferedReader br = null;
    int lineCount = 0;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251"));
      String s;
      boolean needCommit = true;
      conn.setAutoCommit(false);
      while ((s = br.readLine()) != null) {
        lineCount++;
        String[] itemGroupInfo = s.split("\t");
        if ("+".equals(itemGroupInfo[0])) {
          if (!addItemToGroup(conn, itemGroupInfo[1], itemGroupInfo[2])) {
            needCommit = false;
            break;
          }
        }
        else {
          if (!removeItemFromGroup(conn, itemGroupInfo[1], itemGroupInfo[2])) {
            needCommit = false;
            break;
          }
        }
      }
      if (needCommit) {
        conn.commit();
        System.out.println("Строчки из файла items.txt добавились");
      }
      else {
        conn.rollback();
        System.err.println("Строчки из файла items.txt не добавились");
      }
      conn.setAutoCommit(true);

    } catch (IOException ex) {
      System.out.println("Reading error in line " + lineCount);
      ex.printStackTrace();
    } finally {
      try {
        br.close();
      } catch (IOException ex) {
        System.out.println("Can not close");
      }
    }
  }
  
  // Считываем строчки про Group из файла - проводим общую транзакцию
  private void changeGroupFromFile(String fileName, Connection conn) throws SQLException {
    BufferedReader br = null;
    int lineCount = 0;
    PreparedStatement stmt = null;
    ResultSet rst = null;
    Set<String> addGroup = new HashSet<>();
    Set<String> removeGroup = new HashSet<>();
    try {
      try {
        br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251"));
        String s;
        conn.setAutoCommit(false);
        while ((s = br.readLine()) != null) {
          lineCount++;
          String[] groupInfo = s.split("\t");
          if ("+".equals(groupInfo[0])) {
            addGroup.add(groupInfo[1]);
          } else {
            removeGroup.add(groupInfo[1]);
          }
        }
      } catch (IOException ex) {
        Logger.getLogger(DBTester.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        try {
          br.close();
        } catch (IOException ex) {
          System.out.println("Can not close");
        }
      }
      
      String getGroups = "SELECT TITLE FROM ITEMGROUP";
      stmt = conn.prepareStatement(
          getGroups,
          ResultSet.TYPE_SCROLL_INSENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
      rst = stmt.executeQuery();
      
      while (rst.next()) {
        String title = rst.getString(1);
        if (removeGroup.contains(title)) {
          rst.deleteRow();
        }
        if (addGroup.contains(title)) {
          addGroup.remove(title);
        }
        
      }
      rst.moveToInsertRow();
      for (String gr : addGroup) {
          rst.updateString("TITLE", gr);
          rst.insertRow();
      }
      
//      HashSet<String> groups = viewGroups(conn);
//
//      Iterator<String> iterAddGroups = addGroup.iterator();
//      while (iterAddGroups.hasNext()) {
//        String group = iterAddGroups.next();
//        if(groups.contains(group)) {
//          addGroup.remove(group);
//        }
//      }
     // addGroup.removeAll(groups);
//      Iterator<String> iterRemoveGroups = removeGroup.iterator();
//      while (iterRemoveGroups.hasNext()) {
//        String group = iterRemoveGroups.next();
//        if (!groups.contains(group)) {
//          removeGroup.remove(group);
//        }
//      }
//
//      String sqlInsertGroup = "INSERT INTO ITEMGROUP(TITLE) VALUES(?)";
//      stmt = conn.prepareStatement(sqlInsertGroup);
//      for (String gr : addGroup) {
//        stmt.setString(1, gr);
//        stmt.executeUpdate();
//      }
//      String sqlDeleteGroup = "DELETE FROM ITEMGROUP WHERE TITLE=?";
//      stmt = conn.prepareStatement(sqlDeleteGroup);
//      for (String gr : removeGroup) {
//        stmt.setString(1, gr);
//        stmt.executeUpdate();
//      }
      conn.commit();
      System.out.println("Group change");

    } catch (SQLException ex) {
      conn.rollback();
      System.err.println("Group not change");
    } finally {
      try {
        conn.setAutoCommit(true);
        if (rst != null) rst.close();
        if (stmt != null) stmt.close();
      } catch (SQLException ex) {
        System.err.println(ex.getMessage());
      }
    }
  }
  
}
