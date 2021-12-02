package co.udistrital.android.thomasmensageria.help;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TemplatePDF{

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);
    private int pagenumber;
    private static int ANCHO_TABLA = 2;


    public TemplatePDF(Context context) {
        this.context = context;
    }

    public void  openDocument(){
        createFile();
        try {
            document = new Document(PageSize.LEGAL);

            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

        }catch (Exception e){
            Log.e("openDocument", e.toString());
        }
    }

    private void createFile(){

        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");

        if (!folder.exists())
            folder.mkdirs();

        pdfFile = new File(folder, "CertificadoPDF.pdf");

    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);

    }

    public void addTitles(String title, String subTitle, String date) {
        try {
            paragraph = new Paragraph();
            addChildParagraph(new Paragraph(title, fTitle));
            addChildParagraph(new Paragraph(subTitle, fSubTitle));
            addChildParagraph(new Paragraph("Generado: " + date, fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addTitles", e.toString());
        }
    }

    public void addTitlesSingle(String subTitle) {
        try {
            paragraph = new Paragraph(subTitle, fSubTitle);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addTitles", e.toString());
        }
    }

    private void addChildParagraph(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addTitle(String title){
        try {
            paragraph = new Paragraph(title, fTitle);
            paragraph.setSpacingAfter(7);
            paragraph.setSpacingBefore(7);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

        }catch (Exception e) {
            Log.e("addParagraph", e.toString());
        }
    }

    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(7);
            paragraph.setSpacingBefore(7);
            document.add(paragraph);

        }catch (Exception e) {
            Log.e("addParagraph", e.toString());
        }
    }

    public void addParagraphText(String text){
        try {
            paragraph = new Paragraph(text);
            paragraph.setIndentationLeft(20);
            document.add(paragraph);

        }catch (Exception e) {
            Log.e("addParagraphText", e.toString());
        }
    }

    public void createTable(String[] header, ArrayList<String[]> clients){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC=0;
            while (indexC < header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBorder(0);
                pdfPTable.addCell(pdfPCell);
            }

            for (int indexRow =0; indexRow < clients.size(); indexRow++){
                String[] row = clients.get(indexRow);
                for (indexC=0; indexC< header.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    pdfPCell.setVerticalAlignment(Element.ALIGN_TOP);
                    pdfPCell.setBorder(0);
                    pdfPCell.setFixedHeight(20);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e) {
            Log.e("createTable", e.toString());
        }

    }

    public void separator(){
        try {
        document.add(Chunk.NEWLINE);
        document.add(new LineSeparator());
        }catch (Exception e) {
            Log.e("separator", e.toString());
        }
    }

    public void createTable(ArrayList<String[]> list){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(ANCHO_TABLA);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC=0;

            for (int indexRow =0; indexRow < list.size(); indexRow++) {
                String[] row = list.get(indexRow);
                for (indexC=0; indexC< ANCHO_TABLA; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC ]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setBorder(0);
                    pdfPCell.setFixedHeight(50);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e) {
            Log.e("createTable", e.toString());
        }

    }

    public void addImg(ByteArrayOutputStream stream){
        try{
            Image image = Image.getInstance(stream.toByteArray());
            image.setPaddingTop(122);
            image.setSpacingBefore(5);
            image.setSpacingAfter(5);
            image.scaleToFit(140,140);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
        }catch (Exception e){
            Log.e("addImg",e.toString());
        }
    }

    public void addImgFirm(ByteArrayOutputStream stream){
        try{
            Image image = Image.getInstance(stream.toByteArray());
            image.setPaddingTop(12);
            image.setSpacingBefore(2);
            image.setSpacingAfter(2);
            image.scaleToFit(140,120);
            image.setAlignment(Element.ALIGN_LEFT);
            image.setBorderColor(BaseColor.WHITE);
            image.setBackgroundColor(BaseColor.WHITE);
            image.setBorder(0);
            document.add(image);
        }catch (Exception e){
            Log.e("addImg",e.toString());
        }
    }


    public void addImg(String url){

        try {
            System.setProperty("http.agent", "Chrome");
            url = "https://kodejava.org/wp-content/uploads/2017/01/kodejava.png";
            Image image = Image.getInstance(new URL(url));

            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getRoutePDF(){
        return pdfFile.getAbsolutePath();
    }
}
