package com.InventoryCHU.Inventory.FileManager.reportPanne;

import com.InventoryCHU.Inventory.FileManager.util.ReportConstants;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PanneDetails {
    String panneHeader = ReportConstants.PANNE_HEADER;
    String panneTypeText = ReportConstants.PANNE_TYPE_INFO;
    String panneTypeInfo = ReportConstants.EMPTY;
    String detailsText = ReportConstants.DETAILS_INFO;
    String detailsInfo = ReportConstants.EMPTY;

    public PanneDetails build(){return this;}
}
