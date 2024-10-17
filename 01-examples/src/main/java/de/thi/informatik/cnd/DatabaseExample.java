package de.thi.informatik.cnd;

import java.sql.*;

public class DatabaseExample {
    protected static Connection initializeDatabase()
            throws SQLException, ClassNotFoundException {
        String dbURL = "jdbc:mariadb://localhost:3306/example";
        String dbUsername = "example";
        String dbPassword = "example";

        Connection con = DriverManager.getConnection(dbURL,
                dbUsername,
                dbPassword);

        String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100))";

        try(Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        }

        return con;
    }

    public static void main(String[] args) throws
            ClassNotFoundException, SQLException {
        Connection con = initializeDatabase();

        // insert
        PreparedStatement st1 = con.prepareStatement("insert into employees (name) values (?)");
        st1.setString(1, "Test");
        st1.executeUpdate();

        // select
        PreparedStatement st2 = con.prepareStatement("select id, name from employees");
        ResultSet resultSet = st2.executeQuery();
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            System.out.println("ID: " + id + ", Name: " + name);
        }
    }
}
