package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.FileManager.reportPanne.Footer;
import com.InventoryCHU.Inventory.FileManager.reportPanne.Header;
import com.InventoryCHU.Inventory.FileManager.reportPanne.MaterialDetails;
import com.InventoryCHU.Inventory.FileManager.reportPanne.PanneDetails;
import com.InventoryCHU.Inventory.FileManager.service.ReportCreator;
import com.InventoryCHU.Inventory.Models.BreakDown;
import com.InventoryCHU.Inventory.Models.Inventory;
import com.InventoryCHU.Inventory.Models.Item;
import com.InventoryCHU.Inventory.Models.UserInfo;
import com.InventoryCHU.Inventory.Services.BreakDownService;
import com.InventoryCHU.Inventory.Services.InventoryService;
import com.InventoryCHU.Inventory.Services.ItemService;
import com.InventoryCHU.Inventory.Services.UserInfoService;
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
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("api/report")
public class ReportPanneController {
    @Autowired
    ReportCreator reportCreator;

    @Autowired
    BreakDownService breakDownService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserInfoService userInfoService;



    @GetMapping("/{id}")
    public ResponseEntity<Object> GetReport(@PathVariable("id") String id) throws FileNotFoundException {
        String test = null;
        LocalDateTime ldt = LocalDateTime.now();

        Optional<BreakDown> breakDown = breakDownService.getBreakDownById(id);
        if(breakDown.isPresent()) {
            BreakDown breakDown1 = breakDown.get();
            String pdfName = ldt + ".pdf";
            reportCreator.setPdfName(pdfName);
            reportCreator.createDocument();

            //Create Header start
            Header header = new Header();
            header.setReportNo(breakDown1.id);
            header.setReportDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            reportCreator.createHeader(header);

            //Create Material Details

            Optional<Item> item = itemService.getItemById(breakDown1.itemId);
            if(item.isPresent()){
                Inventory inventory = inventoryService.findInventoryByReference(item.get().getReference()).get();
                UserInfo userInfo = userInfoService.findUserById(breakDown1.declaredBy).get();
                MaterialDetails materialDetails = new MaterialDetails();
                materialDetails.setMaterialInfo(inventory.getItemType());
                materialDetails.setMarqueInfo(inventory.getMarque());
                materialDetails.setNInventaireInfo(item.get().getNInventaire());
                materialDetails.setNSerieInfo(inventory.getReference());
                materialDetails.setUserInfo(userInfo.getFirstName()+ " " +userInfo.getLastName());
                materialDetails.setEmplacementInfo(breakDown1.Service);
                materialDetails.setRemarqueInfo(breakDown1.description);
                reportCreator.createMaterialDetails(materialDetails);
            }

            //Create Panne Details
            PanneDetails panneDetails = new PanneDetails();
            panneDetails.setPanneTypeInfo(breakDown1.panneType);
            panneDetails.setDetailsInfo(breakDown1.report);
            reportCreator.createPanneDetails(panneDetails);

            //create Footer
            Footer footer = new Footer();
            footer.setRemaqueInfo(breakDown1.remarque);
            reportCreator.createFooter(footer);

            reportCreator.closeDocument();

            test = "done";
        }

        if(test=="done")
        {
            Path path = Paths.get("src/main/resources/report/"+ldt+".pdf");
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

