package com.Hirukar.Project.Connection.ConnectionFactory;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public final class DatabaseConnection {
    private final String HOST;
    private final int PORT;
    private final String DATABASE;
    private final String DRIVER;
    private final String URL;
    private final String USER;
    private final String PASS;
    private final String CONECTOR;

    private static DatabaseConnection instance;
     
    public static DatabaseConnection getInstance(){
        if(instance == null)
            instance = new DatabaseConnection();
        return instance;
    }

    private DatabaseConnection() {
        Dotenv env = Dotenv.load();
        this.HOST = env.get("DB_HOST");
        this.PORT = Integer.parseInt(env.get("DB_PORT"));
        this.DATABASE = env.get("DB_DATABASE");
        this.USER = env.get("DB_USER");
        this.PASS = env.get("DB_PASS");
        this.DRIVER = env.get("DB_DRIVER");
        this.CONECTOR = env.get("DB_CONECTOR");
        this.URL = this.CONECTOR + this.HOST + ":" + this.PORT + "/" + this.DATABASE + "?useTimezone=true&serverTimezone=UTC";
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(this.URL, this.USER, this.PASS);
    }

    private void close(Connection CON) throws SQLException {
        if (CON != null) 
            CON.close();
    }

    private void close(Connection CON,PreparedStatement stmt) throws SQLException {
        if (stmt != null)
            stmt.close();
        close(CON);
    }

    private void close(Connection CON,ResultSet rs, PreparedStatement stmt) throws SQLException {
        if (rs != null)
            rs.close();
        close(CON,stmt);
    }

    public void connect(String sql, Object[] params, IConnectionCallback callback) throws ClassNotFoundException, SQLException {
        long time = System.currentTimeMillis();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            stmt = con.prepareStatement(sql);
            if(params != null)
                for(int i=0; i< params.length; i++)
                    bind(i+1,stmt,params[i]);
            rs = stmt.executeQuery();
            if(callback != null){
                if(rs.next())
                    callback.callback(rs);
                else
                    throw new SQLException("nada encontrado");
            }
        }catch(ClassNotFoundException | SQLException e){
            throw e;
        }finally{
            close(con,rs,stmt);
            System.out.println("tempo de conexão =" + (System.currentTimeMillis()-time) + "ms");
        }
    }

    public void connect(String sql, Object[] params) throws ClassNotFoundException, SQLException {
        long time = System.currentTimeMillis();
        Connection con = null;
        PreparedStatement stmt = null;
        try{
            con = getConnection();
            stmt = con.prepareStatement(sql);
            if(params != null)
                for(int i=0; i< params.length; i++)
                    bind(i + 1, stmt, params[i]);
            stmt.executeUpdate();
        }catch(ClassNotFoundException | SQLException e){
            throw e;
        }finally{
            close(con,stmt);
            System.out.println("tempo de conexão =" + (System.currentTimeMillis()-time) + "ms");
        }
    }

    private void bind(int i, PreparedStatement stmt, Object obj) throws SQLException {
        Class<?> var = obj.getClass();
        if (Integer.class.equals(var))
            stmt.setInt(i, (int) obj);
        else if (Double.class.equals(var))
            stmt.setDouble(i, (double) obj);
        else
            stmt.setString(i, obj.toString());
    }
}
