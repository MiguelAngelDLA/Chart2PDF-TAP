/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author migue
 */
public class SQLHelper {
    private Connection conexion;
    
    public SQLHelper(){
        this.conexion = null;
    }
    
    //Para conectarse a la base de datos
    public Connection conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (com.mysql.jdbc.Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/PRUEBA_PROYECTO","root","");
        }catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, "No se pudo hacer la conexion");
        }
        
        return conexion;
    }

    
    public void createTableFromCSV(String csvFilePath, String nombreTabla) {
        try {
            conectar();
            Statement stmt = (Statement) conectar().createStatement();
            // Read the CSV file
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            String line;
            String tableName = nombreTabla;
            int numColumns = -1;

            // Get the column names from the first line of the file
            if ((line = br.readLine()) != null) {
                String[] columnsArray = line.split(",");
                numColumns = columnsArray.length;
                String columns = "(";
                for (String column : columnsArray) {
                    columns += column + " VARCHAR(255), ";
                }
                columns = columns.substring(0, columns.length() - 2) + ")";

                // Create the table
                String createTableSQL = "CREATE TABLE " + tableName + columns;
                stmt.executeUpdate(createTableSQL);
            }

            // Insertar Datos en la tabla
            int lineNum = 2; // Empieza en la segunda linea, después de los titulos
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != numColumns) {
                    throw new Exception("Number of columns in line " + lineNum + " does not match header row");
                }
                String values = "";
                for (String value : data) {
                    values += "'" + value + "', ";
                }
                values = values.substring(0, values.length() - 2);

                String insertSQL = "INSERT INTO " + tableName + " VALUES (" + values + ")";
                stmt.executeUpdate(insertSQL);

                lineNum++;
            }

            JOptionPane.showMessageDialog(null, "Table created and data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
