package com.InventoryCHU.Inventory.FileManager.reportPanne;

import com.InventoryCHU.Inventory.FileManager.util.ReportConstants;
import lombok.Data;
import lombok.ToString;

import java.awt.*;

@Data
@ToString
public class MaterialDetails {
    String publicTitle = ReportConstants.CENTER_TITLE;
    String materialHeader = ReportConstants.MATERIAL_HEADER;
    String materialInfoText = ReportConstants.MATERIAL_INFO;
    String materialInfo = ReportConstants.EMPTY;
    String marqueInfoText = ReportConstants.MARQUE_INFO;
    String marqueInfo = ReportConstants.EMPTY;
    String nInventaireInfoText = ReportConstants.N_INVENTAIRE_INFO;
    String nInventaireInfo = ReportConstants.EMPTY;
    String nSerieInfoText = ReportConstants.N_SERIE_INFO;
    String nSerieInfo = ReportConstants.EMPTY;
    String userInfoText = ReportConstants.USER_INFO;
    String userInfo = ReportConstants.EMPTY;
    String emplacementInfoText = ReportConstants.EMPLACEMENT_INFO;
    String emplacementInfo = ReportConstants.EMPTY;
    String remarqueInfoText = ReportConstants.REMARQUE_INFO;
    String remarqueInfo = ReportConstants.EMPTY;
    Color borderColor = Color.BLACK;

    public MaterialDetails build(){return this;}
}
