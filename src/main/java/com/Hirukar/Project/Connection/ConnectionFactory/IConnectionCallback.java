package com.Hirukar.Project.Connection.ConnectionFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IConnectionCallback {
    public void callback(ResultSet rs) throws ClassNotFoundException, SQLException;
}