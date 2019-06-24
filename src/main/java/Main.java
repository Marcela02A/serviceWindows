
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        File miDir = new File(".");
        ConfigDocRead cr = new ConfigDocRead(miDir.getCanonicalPath());
        ConfigInfo configInfo = cr.read();
        SynchronizedObject objectSynchronized = new SynchronizedObject();
        System.out.println("Ruta relativa de ejecución" + miDir.getCanonicalPath());
        Thread observerFile = new Thread(new ObserverFileEdit(miDir.getCanonicalPath() + "\\", objectSynchronized));
        OrderFile orderRute = new OrderFile(configInfo, objectSynchronized);

        observerFile.start();
        orderRute.start();
        while (true) {
            System.out.println("Esperamos algun cambio Main");
            objectSynchronized.waitChange();
            orderRute.setConfigInfo(cr.read());
            //se caraga de nuevo el archivo de configuracion.
            System.out.println("liberamos hilos ya se cargo la configuración Main");
            objectSynchronized.freeState();
        }
    }
}
