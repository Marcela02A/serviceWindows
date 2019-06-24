/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussines;

import Entities.ConfigInfo;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcela
 */
public class ConfigDocRead {

    private final String route;
    private final String archive = "\\Config.json";

    public ConfigDocRead(String routeVal) {
        route = routeVal;
    }

    public ConfigInfo read() throws IOException {
        ConfigInfo result = null;
        StringBuilder sb = new StringBuilder();

        File _file = new File(route + archive);
        if (_file.exists()) {
            try (BufferedReader bf = new BufferedReader(new FileReader(route + archive))) {
                String line;
                while ((line = bf.readLine()) != null) {
                    sb.append(line);
                }
                bf.close();
                result = new Gson().fromJson(sb.toString(), ConfigInfo.class);

                System.out.println(sb);
            } catch (Exception ex) {
                Logger.getLogger(OrderFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            _file.createNewFile();
        }
        return result;
    }
}
