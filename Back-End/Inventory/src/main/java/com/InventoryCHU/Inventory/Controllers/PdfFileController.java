package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.FileManager.model.AddressDetails;
import com.InventoryCHU.Inventory.FileManager.model.HeaderDetails;
import com.InventoryCHU.Inventory.FileManager.model.ProductTableHeader;
import com.InventoryCHU.Inventory.FileManager.service.PdfCommandCreator;
import com.InventoryCHU.Inventory.Models.Command;
import com.InventoryCHU.Inventory.Models.CommandDetail;
import com.InventoryCHU.Inventory.Models.Item;
import com.InventoryCHU.Inventory.Models.UserInfo;
import com.InventoryCHU.Inventory.Services.CommandService;
import com.InventoryCHU.Inventory.Services.UserInfoService;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pdfGeneration")
public class PdfFileController {

    @Autowired
    PdfCommandCreator pcc;

    @Autowired
    CommandService service;

    @Autowired
    UserInfoService userDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCommandPdfById(@PathVariable String id)
            throws FileNotFoundException {

        String test = null;
        //get Command Details
        Optional<Command> cmd = service.getCommandById(id);
        LocalDateTime ldt = LocalDateTime.now();
        if (cmd.isPresent()) {


            String pdfName = ldt + ".pdf";
            pcc.setPdfName(pdfName);
            pcc.createDocument();


            //Create Header Start
            HeaderDetails header = new HeaderDetails();
            header.setInvoiceNo(id).setInvoiceDate(cmd.get().getCommandDate().
                    format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))).
                    build();
            try {
                pcc.createHeader(header);
            }catch(MalformedURLException e){
                return ResponseEntity.unprocessableEntity().body(e.getMessage());
            }

            //Create Address Start
            AddressDetails addressDetails = new AddressDetails();
            Optional <UserInfo> userInfo = userDetailsService.findUserById(15);
            Optional <UserInfo> owner = userDetailsService.findUserById(cmd.get().getRecepient());
            addressDetails
                    .setBillingCompany(userInfo.get().getService())
                    .setBillingName(userInfo.get().getFirstName()+ " " +userInfo.get().getLastName())
                    .setBillingAddress("Route Immouwzer , face de cafe Ahel Fes \n Fes ,Morocco")
                    .setBillingEmail(userInfo.get().getEmail())
                    .setShippingName(owner.get().getFirstName()+" "+owner.get().getLastName())
                    .setShippingAddress(owner.get().getService()+" - ["+owner.get().getRoles()+"]")
                    .build();

            pcc.createAddress(addressDetails);
            //Address end

            //Product Start
            ProductTableHeader productTableHeader = new ProductTableHeader();
            pcc.createTableHeader(productTableHeader);
            List<CommandDetail> itemList = cmd.get().getInfos();
            itemList = pcc.modifyProductList(itemList);
            pcc.createProduct(itemList);

            //Condiftions d'utilisation
            List<String> TncList = new ArrayList<>();
            TncList.add("1. Ce document n'est valable que lorsqu'il contient les 5 tempons des responsables");
            TncList.add("2. Toute document presentee qui ne contient pas des tempon est Invalide");
            String imagePath="src/main/resources/img_1.png";
            pcc.createTnc(TncList,false,imagePath);

            test = "done";
        }
        if(test=="done")
        {
            Path path = Paths.get("src/main/resources/bon/"+ldt+".pdf");
            try {
                Resource resource = new UrlResource(path.toUri());
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            }catch(MalformedURLException mfu){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/file")
        public ResponseEntity<Object> getFile(){
        try {
            // Load the PDF file from the classpath
            String filePath = "/src/app.pdf";
            File pdfFile = new File(filePath);
             PdfDocument document = new PdfDocument(new PdfReader(pdfFile));
             return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(document);
        } catch (IOException e) {
            // Handle file not found or other I/O errors
            return ResponseEntity.notFound().build();
        }
    }
}
