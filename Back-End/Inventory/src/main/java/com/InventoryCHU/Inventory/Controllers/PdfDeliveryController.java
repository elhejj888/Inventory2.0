package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.FileManager.model.AddressDetails;
import com.InventoryCHU.Inventory.FileManager.model.HeaderDetails;
import com.InventoryCHU.Inventory.FileManager.model.ProductTableHeader;
import com.InventoryCHU.Inventory.FileManager.service.PdfCommandCreator;
import com.InventoryCHU.Inventory.FileManager.service.PdfDeliveryCreator;
import com.InventoryCHU.Inventory.Models.Command;
import com.InventoryCHU.Inventory.Models.CommandDetail;
import com.InventoryCHU.Inventory.Models.DeliveryDetails;
import com.InventoryCHU.Inventory.Models.UserInfo;
import com.InventoryCHU.Inventory.Services.CommandService;
import com.InventoryCHU.Inventory.Services.UserInfoService;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/delivery")
public class PdfDeliveryController {
    @Autowired
    PdfDeliveryCreator pdc;

    @Autowired
    CommandService service;

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDeliveryById(@PathVariable String id)
            throws FileNotFoundException {
        String test = null;
        //get  Commande Details
        Optional<Command> cmd = service.getCommandById(id);
        LocalDateTime ldt = LocalDateTime.now();
        if(cmd.isPresent()){
            String pdfName = ldt+".pdf";
            pdc.setPdfName(pdfName);
            pdc.createDocument();

            //Header Start
            HeaderDetails header = new HeaderDetails();
            header.setInvoiceNo(id).setInvoiceDate(cmd.get().getCommandDate().toLocalDate().
                            format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).
                    build();

            try{
                pdc.createHeader(header);
            }catch(MalformedURLException e){
                return ResponseEntity.unprocessableEntity().body(e.getMessage());
            }

            //Infos  Section
            AddressDetails addressDetails = new AddressDetails();
            Optional<UserInfo> owner = userInfoService.findUserById(cmd.get().getRecepient());
            List<String> signatures = cmd.get().getSignatures();
            String names="" ;
            for(String signature : signatures) {
            names += signature + "\n";
            }
            addressDetails
                    .setShippingName(names)
                    .setBillingName(owner.get().getFirstName()+" "+owner.get().getLastName())
                    .setBillingCompany(owner.get().getService())
                    .setBillingEmail(owner.get().getEmail())
                    .setShippingAddress(cmd.get().getReceptionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                    .build();
            pdc.createAddress(addressDetails);

            //Prodcuts List
            ProductTableHeader productTableHeader = new ProductTableHeader();
            pdc.createTableHeader(productTableHeader);
            List<CommandDetail> itemList = cmd.get().getInfos();
            List<CommandDetail> deliveredList = cmd.get().getDeliveredItems();
            List<DeliveryDetails> deliveryDetails = new ArrayList<>();
            for (int i = 0 ; i<itemList.size();i++){
                DeliveryDetails dd = new DeliveryDetails(itemList.get(i).itemType, itemList.get(i).quantity , deliveredList.get(i).quantity);
                deliveryDetails.add(dd);
            }
            deliveryDetails = pdc.modifyProductList(deliveryDetails);
            pdc.createProduct(deliveryDetails);

            //Condiftions d'utilisation
            List<String> TncList = new ArrayList<>();
            TncList.add("1. Ce document n'est valable que lorsqu'il contient les 5 tempons des responsables");
            TncList.add("2. Toute document presentee qui ne contient pas des tempon est Invalide");
            String imagePath="src/main/resources/img_1.png";
            pdc.createTnc(TncList,false,imagePath);

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
}

