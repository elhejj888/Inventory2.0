package com.InventoryCHU.Inventory.FileManager.service;

import com.InventoryCHU.Inventory.FileManager.model.AddressDetails;
import com.InventoryCHU.Inventory.FileManager.model.HeaderDetails;
import com.InventoryCHU.Inventory.FileManager.model.MyFooter;
import com.InventoryCHU.Inventory.FileManager.model.ProductTableHeader;
import com.InventoryCHU.Inventory.Models.CommandDetail;
import com.InventoryCHU.Inventory.Models.Item;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfCommandCreator {

    Document document;
    PdfDocument pdfDocument;
    String pdfName;
    float threecol=190f;
    float twocol=285f;
    float twocol150=twocol+150f;
    float twocolumnWidth[]={twocol150,twocol};
    float threeColumnWidth[]={threecol,threecol,threecol};
    float fullwidth[]={threecol*3};

    public PdfCommandCreator(String pdfName){
        this.pdfName=pdfName;
    }
    public PdfCommandCreator(){}
    public void setPdfName(String pdfName){
        this.pdfName=pdfName;
    }
    public void createDocument() throws FileNotFoundException {
        String fullPath = new File("src/main/resources/bon",pdfName).getAbsolutePath();
        PdfWriter pdfWriter=new PdfWriter(fullPath);
        pdfDocument=new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        this.document=new Document(pdfDocument);
    }

    public   void createTnc(List<String> TncList, Boolean lastPage, String imagePath) {
        if(lastPage) {
            float threecol = 285f;
            float fullwidth[] = {threecol * 2};
            Table tb = new Table(fullwidth);
            tb.addCell(new Cell().add("TERMS AND CONDITIONS\n").setBold().setBorder(Border.NO_BORDER));
            for (String tnc : TncList) {

                tb.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER));
            }

            document.add(tb);
        }else {
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new MyFooter(document,TncList,imagePath));
        }

        document.close();
    }

    public void createProduct(List<CommandDetail> productList) {
        float threecol=190f;
        float fullwidth[]={threecol*3};
        Table threeColTable2=new Table(twocolumnWidth);
        float totalSum=getTotalSum(productList);
        for (CommandDetail product:productList)
        {
            threeColTable2.addCell(new Cell().add(product.itemType).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(String.valueOf(product.quantity)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        }

        document.add(threeColTable2.setMarginBottom(20f));
        float onetwo[]={twocol,twocol};
        Table threeColTable4=new Table(onetwo);
        threeColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        threeColTable4.addCell(new Cell().add(fullwidthDashedBorder(fullwidth)).setBorder(Border.NO_BORDER));
        document.add(threeColTable4);

        Table threeColTable3=new Table(twocolumnWidth);
        threeColTable3.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        threeColTable3.addCell(new Cell().add(String.valueOf(totalSum)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginRight(15f);

        document.add(threeColTable3);
        document.add(fullwidthDashedBorder(fullwidth));
        document.add(new Paragraph("\n"));
        document.add(getDividerTable(fullwidth).setBorder(new SolidBorder(Color.GRAY,1)).setMarginBottom(15f));
    }

    public float getTotalSum(List<CommandDetail> productList) {
        return  (float)productList.stream().mapToLong((p)-> (long) (p.quantity)).sum();
    }

    public List<CommandDetail> getDummyProductList()
    {
        List<CommandDetail> productList=new ArrayList<>();
        productList.add(new CommandDetail("Apple Mac Mini",2));
        productList.add(new CommandDetail("Ecran Dell",4));
        productList.add(new CommandDetail("Clavier",2));
        productList.add(new CommandDetail("Souris",3));
        productList.add(new CommandDetail("Serveur",5));
        productList.add(new CommandDetail("Bureau",2));
        return productList;
    }

    public void createTableHeader(ProductTableHeader productTableHeader) {
        Paragraph producPara=new Paragraph("Products");
        document.add(producPara.setBold());
        Table threeColTable1=new Table(twocolumnWidth);
        threeColTable1.setBackgroundColor(Color.BLACK,0.7f);

        threeColTable1.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        document.add(threeColTable1);
    }

    public void createAddress(AddressDetails addressDetails) {
        Table twoColTable=new Table(twocolumnWidth);
        twoColTable.addCell(getBillingandShippingCell(addressDetails.getBillingInfoText()));
        //twoColTable.addCell(getBillingandShippingCell(addressDetails.getShippingInfoText()));
        document.add(twoColTable.setMarginBottom(12f));
        //iNFO FIRST ROW
        Table twoColTable2=new Table(twocolumnWidth);
        twoColTable2.addCell(getCell10fLeft(addressDetails.getBillingCompanyText(),true));
        twoColTable2.addCell(getCell10fLeft(addressDetails.getBillingNameText(),true));
        twoColTable2.addCell(getCell10fLeft(addressDetails.getBillingCompany(),false));
        twoColTable2.addCell(getCell10fLeft(addressDetails.getBillingName(),false));

        //twoColTable2.addCell(getCell10fLeft(addressDetails.getShippingName(),false));
        document.add(twoColTable2);



        float oneColoumnwidth[]={twocol150};

        Table oneColTable1=new Table(oneColoumnwidth);
//        oneColTable1.addCell(getCell10fLeft(addressDetails.getBillingAddressText(),true));
//        oneColTable1.addCell(getCell10fLeft(addressDetails.getBillingAddress(),false));
        oneColTable1.addCell(getCell10fLeft(addressDetails.getBillingEmailText(),true));
        oneColTable1.addCell(getCell10fLeft(addressDetails.getBillingEmail(),false));
        document.add(oneColTable1.setMarginBottom(10f));
        document.add(fullwidthDashedBorder(fullwidth));
    }

    public void createHeader(HeaderDetails header) throws MalformedURLException {
        // Adjust the column widths for the table: 40% for the image and 60% for the invoice details
        float[] adjustedColumnWidths = {twocol * 0.48f, twocol * 0.52f};

        // Create the table with the new width proportions
        Table table = new Table(adjustedColumnWidths);

        // Convert the image path String to an Image object
        String imgPath = header.getInvoiceHeaderImg(); // This returns the image path as a String
        ImageData imageData = ImageDataFactory.create(imgPath); // Load the image from the path
        Image img = new Image(imageData); // Create the Image object

        // Option 1: Set fixed width and height for the image (e.g., 150x75)
        img.setWidth(150); // Set desired width
        img.setHeight(75); // Set desired height

        // Option 2: Scale the image proportionally to fit within a specific width and height
        // img.scaleToFit(100, 50); // This will resize the image proportionally

        // Add the resized image to the first cell in the table (40% width)
        table.addCell(new Cell().add(img).setBorder(Border.NO_BORDER));

        // Create a nested table for the invoice details
        Table nestedTable = new Table(new float[]{twocol / 2, twocol / 2});
        nestedTable.addCell(getHeaderTextCell(header.getInvoiceNoText())).setMarginLeft(45f);
        nestedTable.addCell(getHeaderTextCellValue(header.getInvoiceNo()));
        nestedTable.addCell(getHeaderTextCell(header.getInvoiceDateText())).setMarginLeft(45f);
        nestedTable.addCell(getHeaderTextCellValue(header.getInvoiceDate()));

        // Add the nested table to the second cell (60% width)
        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

        // Set the border
        Border gb = new SolidBorder(header.getBorderColor(), 2f);
        document.add(table);
        document.add(getNewLineParagraph());
        document.add(getDividerTable(fullwidth).setBorder(gb));
        document.add(getNewLineParagraph());
    }


    public List<CommandDetail> modifyProductList(List<CommandDetail> productList) {
        Map<String,CommandDetail> map=new HashMap<>();
        productList.forEach((i)->{
            if(map.containsKey(i.itemType))
            {
                i.setQuantity(map.getOrDefault(i.itemType,null).getQuantity()+i.getQuantity());
                map.put(i.itemType,i);
            }else
            {
                map.put(i.getItemType(),i);
            }
        });
        return map.values().stream().collect(Collectors.toList());


    }

    static  Table getDividerTable(float[] fullwidth)
    {
        return new Table(fullwidth);
    }
    static Table fullwidthDashedBorder(float[] fullwidth)
    {
        Table tableDivider2=new Table(fullwidth);
        Border dgb=new DashedBorder(Color.GRAY,0.5f);
        tableDivider2.setBorder(dgb);
        return tableDivider2;
    }
    static  Paragraph getNewLineParagraph()
    {
        return new Paragraph("\n");
    }
    static Cell getHeaderTextCell(String textValue)
    {

        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
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
