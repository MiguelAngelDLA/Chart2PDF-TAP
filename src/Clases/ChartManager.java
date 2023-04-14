/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author migue
 */
public class ChartManager {
    ArrayList<File> ArchivosAGraficar;
    SQLHelper conexion = new SQLHelper();
    public ChartManager(){
        ArchivosAGraficar = new ArrayList();

    }
    
    public void setArchivo1(File archivo){
        if(ArchivosAGraficar.size() >= 1){
            ArchivosAGraficar.set(0, archivo);
        }
        ArchivosAGraficar.add(archivo);
    }
    
    public void setArchivo2(File archivo){
        if(ArchivosAGraficar.size() >= 2){
            ArchivosAGraficar.set(1, archivo);
        }
        ArchivosAGraficar.add(archivo);
    }
    
    public void setArchivo3(File archivo){
        if(ArchivosAGraficar.size() >= 3){
            ArchivosAGraficar.set(2, archivo);
        }
        ArchivosAGraficar.add(archivo);

    }
    
    
    public File getArchivo1(){
        return ArchivosAGraficar.get(0);
    }
    
    public File getArchivo2(){
        return ArchivosAGraficar.get(1);
    }
    public File getArchivo3(){
        return ArchivosAGraficar.get(2);
    }
    
    
    public void createChartEsperanzaDeVida(JPanel panel){
        // Connect to the database
        try{
        conexion.conectar();
        
        // Retrieve the data from the table
        Statement stmt = (Statement) conexion.conectar().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CountryName, "
                + "1960s, 1961s, 1962s, 1963s, 1964s, 1965s, 1966s, 1967s, 1968s, 1969s, "
                + "1970s, 1971s, 1972s, 1973s, 1974s, 1975s, 1976s, 1977s, 1978s, 1979s, "
                + "1980s, 1981s, 1982s, 1983s, 1984s, 1985s, 1986s, 1987s, 1988s, 1989s, "
                + "1990s, 1991s, 1992s, 1993s, 1994s, 1995s, 1996s, 1997s, 1998s, 1999s, "
                + "2000s, 2001s, 2002s, 2003s, 2004s, 2005s, 2006s, 2007s, 2008s, 2009s, "
                + "2010s, 2011s, 2012s, 2013s, 2014s, 2015s, 2016s, 2017s, 2018s, 2019s, 2020s "
                + "FROM EsperanzaDeVidaPorPais WHERE CountryName IN ('Mexico', 'United States', 'China')");
        // Create the XYSeriesCollection object
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Add the data to the XYSeriesCollection object
        while (rs.next()) {
            XYSeries series = new XYSeries(rs.getString("CountryName"));
            
            for(int i = 1960; i < 2021; i++){
               series.add(i, rs.getDouble( +i + "s")); 
            }
            
            dataset.addSeries(series);
        }

        // Create the chart using the XYSeriesCollection object
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Esperanza de Vida por País",
                "Año",
                "Esperanza de Vida",
                dataset);
        
        BufferedImage imagen = chart.createBufferedImage(500, 500);
        PDFCreator.addImage(imagen);
        // Display the chart in a frame or pan
        ChartPanel panelGraf = new ChartPanel(chart);
        panelGraf.setSize(panel.getSize());
        panel.add(panelGraf);
        panel.validate();

        conexion.conectar().close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
    }
    }
        
    public void createChartGDPPerCapita(JPanel panel){
        try{
        conexion.conectar();
        
        Statement stmt = (Statement) conexion.conectar().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CountryName, "
                + "1960s, 1961s, 1962s, 1963s, 1964s, 1965s, 1966s, 1967s, 1968s, 1969s, "
                + "1970s, 1971s, 1972s, 1973s, 1974s, 1975s, 1976s, 1977s, 1978s, 1979s, "
                + "1980s, 1981s, 1982s, 1983s, 1984s, 1985s, 1986s, 1987s, 1988s, 1989s, "
                + "1990s, 1991s, 1992s, 1993s, 1994s, 1995s, 1996s, 1997s, 1998s, 1999s, "
                + "2000s, 2001s, 2002s, 2003s, 2004s, 2005s, 2006s, 2007s, 2008s, 2009s, "
                + "2010s, 2011s, 2012s, 2013s, 2014s, 2015s, 2016s, 2017s, 2018s, 2019s, 2020s "
                + "FROM GDPPerCapita WHERE CountryName IN ('Mexico', 'United States', 'China')");

        XYSeriesCollection dataset = new XYSeriesCollection();

        while (rs.next()) {
            XYSeries series = new XYSeries(rs.getString("CountryName"));
            
            for(int i = 1960; i < 2021; i++){
               series.add(i, rs.getDouble( +i + "s")); 
            }
            
            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYAreaChart("GDP por País",
                "Año",
                "GDP",
                dataset);
                        
        BufferedImage imagen = chart.createBufferedImage(500, 500);
        PDFCreator.addImage(imagen);
        ChartPanel panelGraf = new ChartPanel(chart);
        panelGraf.setSize(panel.getSize());
        panel.add(panelGraf);
        panel.validate();

        conexion.conectar().close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
    }
    }
    
    public void createChartUnemploymentRate(JPanel panel){
        try{
        conexion.conectar();
        
        Statement stmt = (Statement) conexion.conectar().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CountryName, "  
                + "1991s, 1992s, 1993s, 1994s, 1995s, 1996s, 1997s, 1998s, 1999s, "
                + "2000s, 2001s, 2002s, 2003s, 2004s, 2005s, 2006s, 2007s, 2008s, 2009s, "
                + "2010s, 2011s, 2012s, 2013s, 2014s, 2015s, 2016s, 2017s, 2018s, 2019s, 2020s "
                + "FROM TasaDeDesempleo WHERE CountryName IN ('Mexico', 'United States', 'China')");
        XYSeriesCollection dataset = new XYSeriesCollection();

        while (rs.next()) {
            XYSeries series = new XYSeries(rs.getString("CountryName"));
            
            for(int i = 1991; i < 2021; i++){
               series.add(i, rs.getDouble( +i + "s")); 
            }
            
            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYLineChart("Desempleo por País",
                "Año",
                "Desempleo",
                dataset);
                        
        BufferedImage imagen = chart.createBufferedImage(500, 500);
        PDFCreator.addImage(imagen);
        ChartPanel panelGraf = new ChartPanel(chart);
        panelGraf.setSize(panel.getSize());
        panel.add(panelGraf);
        panel.validate();

        conexion.conectar().close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
    }
    }


}
