package com.InventoryCHU.Inventory.FileManager.util;

public class ReportConstants {
    private ReportConstants(){}
    public static final String EMPTY = "";
    public static final String REPORT_TITLE="CENTRE HOSPITALIÉRE UNIVERSITAIRE HASSAN II \n" +
            " DIVISION DES SYSTEMES D'INFORMATION \n" +
            "Service des Infrastructures Systèmes et Réseaux";
    public static final String HEADER_IMAGE = "src/main/resources/img_1.png";
    public static final String REPORT_NO_TEXT = "Panne No. : ";
    public static final String REPORT_DATE_TEXT = "Fès le ";

    //MATERIAL DETAILS
    public static final String CENTER_TITLE = "Fiche de déclaration d'une panne informatique";
    public static final String MATERIAL_HEADER ="Matériel en panne :";
    public static final String MATERIAL_INFO = "Matériel";
    public static final String MARQUE_INFO = "Marque";
    public static final String N_INVENTAIRE_INFO = "N˚ d'inventaire";
    public static final String N_SERIE_INFO = "N˚ de Série";
    public static final String USER_INFO = "Utilisateur";
    public static final String EMPLACEMENT_INFO = "Emplacement";
    public static final String REMARQUE_INFO = "Remarque";

    //PANNE DETAILS
    public static final String PANNE_HEADER = "Motifs de la panne détectée :";
    public static final String PANNE_TYPE_INFO = "Type de panne";
    public static final String DETAILS_INFO = "Détails";

    //FINAL SECTION
    public static final String FINAL_REMARQUE_INFO = "Remarque :";
    public static final String TECH_SIGN = "Signature Techicien intervenant";
    public static final String SERVICE_SIGN = "Cachet et Signature du Service Demandeur";
}
