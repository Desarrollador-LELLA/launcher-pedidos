/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package launcher.pedidos;

import java.io.FileOutputStream;
import java.io.InputStream;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author NOTEJUAN
 */
public class Downloader {

    public static void download(String url, String path) throws Exception {
        try (InputStream in = new URL(url).openStream(); FileOutputStream out = new FileOutputStream(path)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
    
    public static void downloadAndUnzip(String url, String destDir) throws Exception {
        // Ruta temporal para el zip
        Path tempZipPath = Files.createTempFile("update", ".zip");

        // Descargar el archivo ZIP
        try (InputStream in = new URL(url).openStream();
             FileOutputStream out = new FileOutputStream(tempZipPath.toFile())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        // Descomprimir el ZIP
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(tempZipPath.toFile()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }

        Files.delete(tempZipPath); // Opcional: borrar el zip temporal
    }
}
