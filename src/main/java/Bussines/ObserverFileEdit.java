/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussines;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 *
 * @author Marcela
 */
public class ObserverFileEdit implements Runnable {

    private final Path directoryToWatch;
    private final WatchService watchService;
    private final SynchronizedObject synchronizedObjet;

    public ObserverFileEdit(String path, SynchronizedObject synchronizedValue) throws IOException {
        synchronizedObjet = synchronizedValue;
        directoryToWatch = Paths.get(path);
        if (directoryToWatch == null) {
            throw new UnsupportedOperationException("Directory not found");
        }
        watchService = directoryToWatch.getFileSystem().newWatchService();
        //directoryToWatch.register(watchService, new WatchEvent.Kind[]{ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY});
         directoryToWatch.register(watchService, new WatchEvent.Kind[]{ENTRY_MODIFY});
    }

    @Override
    public void run() {
        try {
            // Esperamos que algo suceda con el directorio
            WatchKey key = watchService.take();
            // Algo ocurrio en el directorio para los eventos registrados
            while (key != null) {
                for (WatchEvent event : key.pollEvents()) {
                    String eventKind = event.kind().toString();
                    String file = event.context().toString();
                    System.out.println("Event : " + eventKind + " in File " + file);
                }

                synchronizedObjet.setState(true);
                System.out.println("Detenemos escucha por modificación");
                synchronizedObjet.isChangeState();
                System.out.println("Reanudamos escucha");
                key.reset();
                key = watchService.take();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
