package com.InventoryCHU.Inventory.FileManager.reportPanne;

import com.InventoryCHU.Inventory.FileManager.util.ReportConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Header {
    String reportTitle = ReportConstants.REPORT_TITLE;
    String reportNoText = ReportConstants.REPORT_NO_TEXT;
    String reportDateText = ReportConstants.REPORT_DATE_TEXT;
    String ReportImg = ReportConstants.HEADER_IMAGE;
    String reportNo = ReportConstants.EMPTY;
    String reportDate = ReportConstants.EMPTY;
    Color borderColor = Color.BLACK;

    public Header build() { return this; }




}
