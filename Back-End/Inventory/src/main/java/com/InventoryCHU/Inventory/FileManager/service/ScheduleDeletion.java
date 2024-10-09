package com.InventoryCHU.Inventory.FileManager.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ScheduleDeletion {
    @Scheduled(cron = "0 * * * * ?")
    public void executeHourlyTask(){
        System.out.println("Suppression des fichiers Pdf ..!");
        clearFolder("src/main/resources/report");
        clearFolder("src/main/resources/bon");

    }

    public void clearFolder(String folderPath){
        System.out.println("Suppression de : "+folderPath);
        File reportFolder = new File(folderPath);
        if(reportFolder.exists() && reportFolder.isDirectory()){
            //trouver tous les fichiers dans le dossier
            File[] files = reportFolder.listFiles();
            if(files != null){
                for(File file : files){
                    if(file.isFile()){
                        boolean deleted = file.delete();
                        if(deleted)
                            System.out.println("Supprimé avec succes : "+ file.getName());
                        else
                            System.out.println("erreur de Supression : "+file.getName());
                    }
                }
            }
        }
        else System.out.println("le dossier : "+folderPath+" non trouvé..!");

    }
}
