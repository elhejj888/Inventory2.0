package com.InventoryCHU.Inventory.FileManager.service;

import com.InventoryCHU.Inventory.FileManager.reportPanne.Footer;
import com.InventoryCHU.Inventory.FileManager.reportPanne.Header;
import com.InventoryCHU.Inventory.FileManager.reportPanne.MaterialDetails;
import com.InventoryCHU.Inventory.FileManager.reportPanne.PanneDetails;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@Service
@Data
public class ReportCreator {
    Document document;
    PdfDocument pdfDocument;
    String pdfName;
    float threecol = 190f;
    float twocol = 285f;
    float twocol150 = twocol+150f;
    float twoColumnWidth[] = {twocol , twocol};
    float threeColumnWidth[] = {threecol , threecol , threecol};
    float fullwidth[] = {threecol*3};
    float twocolEnThree[] = {190f , 380f};

    public ReportCreator(String pdfName){
        this.pdfName = pdfName;
    }
    public ReportCreator(){}

    public void createDocument() throws FileNotFoundException {
        String fullPath = new File("src/main/resources/report", pdfName).getAbsolutePath();
        PdfWriter pdfWriter = new PdfWriter(fullPath);
        pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        this.document = new Document(pdfDocument);
    }

    public void createHeader(Header header) {

        Table table = new Table(twoColumnWidth);
        table.addCell(new Cell().add(header.getReportTitle()).setFontSize(10f)
                .setBold().setBorder(Border.NO_BORDER));
        try {
            String imagePath = header.getReportImg();
            ImageData imageData = ImageDataFactory.create(imagePath);
            Image pdfImage = new Image(imageData);
            pdfImage.scaleToFit(115f,95f);
            float xPosition = pdfDocument.getDefaultPageSize().getWidth() - 180f; // 110 to include some margin
            float yPosition = pdfDocument.getDefaultPageSize().getHeight() - 80f; // 110 to include margin from top
            pdfImage.setFixedPosition(xPosition, yPosition);

            table.addCell(new Cell().add(pdfImage).setMarginRight(0f).setBorder(Border.NO_BORDER));
        }catch (MalformedURLException e){
            System.out.println(e.getMessage());
        }
        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(header.getReportDateText() + " " + header.getReportDate())
                .setBorder(Border.NO_BORDER));
        document.add(table);
        document.add(getNewLineParagraph());
    }

    //Create Material Details Section
    public void createMaterialDetails(MaterialDetails materialDetails){
        Paragraph paragraph = new Paragraph(materialDetails.getPublicTitle()).
                setTextAlignment(TextAlignment.CENTER).setFontSize(16f);
        Paragraph paragraph1 = new Paragraph(materialDetails.getMaterialHeader())
                .setTextAlignment(TextAlignment.LEFT).setBold();

        document.add(paragraph);
        document.add(paragraph1);

        Table twoColTable = new Table(twocolEnThree);
        Border gb = new SolidBorder(Color.BLACK,1f);
        twoColTable.addCell(new Cell().add(materialDetails.getMaterialInfoText()).
                setBorder(gb).setBold());
        twoColTable.addCell(new Cell().add(materialDetails.getMaterialInfo())
                .setBorder(gb));
        twoColTable.addCell(new Cell().add(materialDetails.getMarqueInfoText()).
                setBorder(gb).setBold());
        twoColTable.addCell(new Cell().add(materialDetails.getMarqueInfo())
                .setBorder(gb));
        twoColTable.addCell(new Cell().add(materialDetails.getNInventaireInfoText()).
                setBorder(gb).setBold());
        twoColTable.addCell(new Cell().add(materialDetails.getNInventaireInfo())
                .setBorder(gb));
        twoColTable.addCell(new Cell().add(materialDetails.getNSerieInfoText()).
                setBorder(gb).setBold());
        twoColTable.addCell(new Cell().add(materialDetails.getNSerieInfo())
                .setBorder(gb));
        twoColTable.addCell(new Cell().add(materialDetails.getUserInfoText()).
                setBorder(gb).setBold());
        twoColTable.addCell(new Cell().add(materialDetails.getUserInfo())
                .setBorder(gb));
        twoColTable.addCell(new Cell().add(materialDetails.getEmplacementInfoText()).
                setBorder(gb).setBold());
        twoColTable.addCell(new Cell().add(materialDetails.getEmplacementInfo())
                .setBorder(gb));
        twoColTable.addCell(new Cell().add(materialDetails.getRemarqueInfoText()).
                setBorder(gb).setBold());
        twoColTable.addCell(new Cell().add(materialDetails.getRemarqueInfo())
                .setBorder(gb));

        document.add(twoColTable);
        document.add(getNewLineParagraph());
    }

    //Create Panne Details
    public void createPanneDetails(PanneDetails panneDetails){
         Paragraph paragraph1 = new Paragraph(panneDetails.getPanneHeader())
                .setTextAlignment(TextAlignment.LEFT).setBold();


        document.add(paragraph1);

        Border gb = new SolidBorder(Color.BLACK,1f);

        Table twoColTable = new Table(twocolEnThree);
        twoColTable.addCell(new Cell().add(panneDetails.getPanneTypeText()).
                setBorder(gb).setBold());
        twoColTable.addCell(new Cell().add(panneDetails.getPanneTypeInfo())
                .setBorder(gb));
        twoColTable.addCell(new Cell().add(panneDetails.getDetailsText()).
                setBorder(gb).setBold());
        twoColTable.addCell(new Cell().add(panneDetails.getDetailsInfo())
                .setBorder(gb));

        document.add(twoColTable);

    }

    //create Last Section
    public void createFooter(Footer footer){
        Text boldRemarqueText = new Text(footer.getRemarqueText()).setBold();

        // Create a normal Text object for the second part
        Text normalRemarqueInfo = new Text(footer.getRemaqueInfo());

        // Add both Text objects to a Paragraph
        Paragraph paragraph = new Paragraph()
                .add(boldRemarqueText)  // Add the bold text first
                .add(normalRemarqueInfo)  // Add the normal text
                .setTextAlignment(TextAlignment.LEFT);  // Align the entire paragraph to the left

        document.add(paragraph);
        document.add(getNewLineParagraph());

        Border gb = new SolidBorder(Color.BLACK,1f);

        Table table = new Table(twoColumnWidth);
        table.addCell(new Cell().add(footer.getTechSignText()).
                setBorder(gb).setBold());
        table.addCell(new Cell().add(footer.getServiceSignText()).setBold()
                .setBorder(gb));
        table.addCell(new Cell().add("\n \n \n \n \n").setBorder(gb));
        table.addCell(new Cell().add("\n \n \n \n \n").setBorder(gb));

        document.add(table);
        document.add(getNewLineParagraph());

    }

    // Add this method to close the document and PdfDocument
    public void closeDocument() {
        if (document != null) {
            document.close();  // This ensures the PDF content is flushed and written to the file
        }
    }

    static  Table getDividerTable(float[] fullwidth)
    {
        return new Table(fullwidth);
    }
    static Paragraph getNewLineParagraph()
    {
        return new Paragraph("\n");
    }

    static Cell getHeaderTextCell(String textValue)
    {

        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }
    static Cell getHeaderTextCellValue(String textValue)
    {


        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }
    static Cell getBillingandShippingCell(String textValue)
    {

        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static  Cell getCell10fLeft(String textValue,Boolean isBold){
        Cell myCell=new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return  isBold ?myCell.setBold():myCell;

    }


}
