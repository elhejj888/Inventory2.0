package com.InventoryCHU.Inventory.FileManager.reportPanne;

import com.InventoryCHU.Inventory.FileManager.util.ReportConstants;
import lombok.Data;
import lombok.ToString;

import java.awt.*;

@Data
@ToString
public class Footer {
    String remarqueText = ReportConstants.FINAL_REMARQUE_INFO;
    String remaqueInfo = ReportConstants.EMPTY;
    String techSignText = ReportConstants.TECH_SIGN;
    String techSign = ReportConstants.EMPTY;
    String serviceSignText = ReportConstants.SERVICE_SIGN;
    String serviceSign = ReportConstants.EMPTY;
    Color borderColor = Color.BLACK;

    public Footer build(){return this;}
}
