/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author migue
 */
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class PDFCreator {
    static ArrayList<BufferedImage> imagenesDeGraficas = new ArrayList();
    
    public static void addImage(BufferedImage imagen){
        imagenesDeGraficas.add(imagen);
    }
    
    public static void generatePdf(String outputFilename, String headerText) {
        // Create a new document
        Document document = new Document();
        try {
            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFilename));

            document.open();
            
            Paragraph header = new Paragraph(headerText);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            
            for(int i = 0; i < imagenesDeGraficas.size(); i++){
                Image chartPdfImage2 = Image.getInstance(imagenesDeGraficas.get(i), null);
                document.add(chartPdfImage2);
            }            
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            // Close the document
            document.close();
        }
    }

}
