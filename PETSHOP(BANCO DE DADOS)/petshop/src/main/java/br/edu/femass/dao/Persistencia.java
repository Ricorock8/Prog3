package br.edu.femass.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Persistencia {
    protected final String ENDERECO = "localhost";
    protected final String DATABASE = "petshop";
    protected final String PORTA = "5432";
    protected final String USUARIO = "postgres";
    protected final String SENHA = "postgres";

    protected Connection getConexao() throws SQLException {
        String url = "jdbc:postgresql://" + ENDERECO + ":" + PORTA + "/" + DATABASE;
        Connection conn = DriverManager.getConnection(url, USUARIO, SENHA);
        return conn;   
    }

    protected PreparedStatement getPreparedStatement(String sql) throws SQLException {
        PreparedStatement ps = getConexao().prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        return ps;
    }
    
}
