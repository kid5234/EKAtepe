/*
 * Copyright 2015 DimasInchidi @ Lembuswana Mudah Tersakiti.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package heketon.mt.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author DimasInchidi
 */
public class MySQL {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String DB_URL = "jdbc:postgresql://localhost:5432/heketonDB";
    private static final String USER = "heketonRoot";
    private static String PASS = "janganlihat";
    private ResultSet rs;
    private Connection con;
    Statement stmt;

    public Connection F_Koneksi() {
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database\n" + se.getMessage(), "Kesalahan Koneksi", JOptionPane.ERROR_MESSAGE);
            if (se.getMessage().contains("denied")) {
                PASS = JOptionPane.showInputDialog("Coba dengan password berbeda\nMasukan password");
                try {
                    con = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = con.createStatement();
                } catch (SQLException se2) {
                    System.exit(0);
                }
            }
            if (se.getMessage().contains("database")) {
                DB_URL = "jdbc:mysql://localhost/" + JOptionPane.showInputDialog("Masukan database yang digunakan");
                try {
                    con = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = con.createStatement();
                } catch (SQLException se2) {
                    System.exit(0);
                }
            }
            System.exit(0);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Koneksi tidak valid\n" + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return con;
    }

    public ResultSet Select(String SQL) {
        try {
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
        } catch (Exception ex) {
            rs = null;
        }
        return rs;
    }

    public boolean Update(String SQL) {
        try {
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            con.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void CloseCon() {
        try {
            con.close();
        } catch (SQLException ex) {
        }
    }
}
