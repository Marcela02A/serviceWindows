/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussines;

import Entities.ConfigInfo;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Marcela
 */
public class ConfigDocRead {

    private final String route;
    private final String archive = "\\config.json";

    public ConfigDocRead(String routeVal) {
        route = routeVal;
    }

    public ConfigInfo read() {
        ConfigInfo result = new ConfigInfo();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bf = new BufferedReader(new FileReader(route + archive))) {
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            result = new Gson().fromJson(sb.toString(), ConfigInfo.class);

            System.out.println(sb);
        } catch (Exception e) {
        }
        return result;
    }
}
