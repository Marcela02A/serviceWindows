
import Bussines.ConfigDocRead;
import Bussines.ObserverFileEdit;
import Bussines.OrderFile;
import Bussines.SynchronizedObject;
import Entities.ConfigInfo;
import com.sun.prism.impl.PrismSettings;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marcela
 */
public class Main {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        //System.getProperty("user.home")
        start(System.getProperty("user.dir"));
        
    }
    
    public static void start(String pathInit) throws IOException, InterruptedException {
        
        //Declaramos Objetos necesarios
        ConfigDocRead _configDocRead = new ConfigDocRead(pathInit);
        SynchronizedObject objectSynchronized = new SynchronizedObject();
        Thread observerFile = new Thread(new ObserverFileEdit(pathInit + "\\", objectSynchronized));
        ConfigInfo configInfo = _configDocRead.read();
        OrderFile orderRute = new OrderFile(configInfo, objectSynchronized);
        
        System.out.println("Ruta relativa de ejecución " + pathInit);
        
        //Iniciamos hilos
        observerFile.start();
        orderRute.start();
        
        while (true) {
            System.out.println("Esperamos algun cambio Main");
            objectSynchronized.waitChange();
            orderRute.setConfigInfo(_configDocRead.read());
            //se caraga de nuevo el archivo de configuracion.
            System.out.println("liberamos hilos ya se cargo la configuración Main");
            objectSynchronized.freeState();
        }
    }
}
