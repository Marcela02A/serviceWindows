/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussines;

import Entities.ConfigInfo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Marcela
 */
public class OrderFile extends Thread {

    private final SynchronizedObject sinchronizedValue;
    private ConfigInfo configInfo;

    public OrderFile(ConfigInfo ci, SynchronizedObject sinchronizedobjet) {
        configInfo = ci;
        sinchronizedValue = sinchronizedobjet;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sinchronizedValue.isChangeState();

                Stream<Path> _listFile = Files.list(Paths.get(getConfigInfo().getAccessRoute()));
                System.out.println("Lectura de carpeta");

                for (String value : getConfigInfo().getOrganizedDocExtension()) {
                    checkCarpet(value);
                }

                _listFile.forEach(path -> {
                    String carpetDestination;
                    if ((carpetDestination = isExtensionAllowed(path)) != null) {
                        System.out.println(path.toFile().getName());
                        try {
                            if (!Files.exists(Paths.get(getConfigInfo().getAccessRoute() + "\\" + carpetDestination + "\\" + path.toFile().getName()))) {
                                Path fileReplace = Files.createFile(Paths.get(getConfigInfo().getAccessRoute() + "\\" + carpetDestination + "\\" + path.toFile().getName()));
                                Files.copy(path, fileReplace, StandardCopyOption.REPLACE_EXISTING);
                            }
                            path.toFile().delete();
                        } catch (IOException ex) {
                            Logger.getLogger(OrderFile.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                Thread.sleep(getConfigInfo().getFrequencyTime());
            } catch (Exception ex) {
                Logger.getLogger(OrderFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String isExtensionAllowed(Path file) {
        String result = null;
        File _file = file.toFile();
        if (_file.isFile()) {
            String[] splitName = _file.getName().split("\\.");
            if (Arrays.asList(getConfigInfo().getOrganizedDocExtension()).contains(splitName[splitName.length - 1].toUpperCase())) {
                result = splitName[splitName.length - 1].toUpperCase();
            }
        }
        return result;
    }

    public void checkCarpet(String extension) throws IOException {
        Path directory = Paths.get(getConfigInfo().getAccessRoute() + extension.toUpperCase());
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }
    }

    /**
     * @return the configInfo
     */
    public ConfigInfo getConfigInfo() {
        return configInfo;
    }

    /**
     * @param configInfo the configInfo to set
     */
    public void setConfigInfo(ConfigInfo configInfo) {
        this.configInfo = configInfo;
    }
}
