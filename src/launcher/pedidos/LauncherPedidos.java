/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package launcher.pedidos;

import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 *
 * @author NOTEJUAN
 */
public class LauncherPedidos {
    // VERSION 0.4.2
    public static void main(String[] args) {
        try {
            String destinationDir = "app";
            String appJar = "app/NotaPedidos.jar";
            String javafxPath = "C:\\javafx-sdk-22.0.1\\lib";

            String installedVersion = null;
            boolean jarExists = new java.io.File(appJar).exists();

            if (jarExists) {
                installedVersion = getJarManifestVersion(appJar);
            }

            String latestVersion = GitHubUpdater.fetchLatestVersion();

            boolean shouldUpdate = !jarExists || GitHubUpdater.isNewerVersion(installedVersion, latestVersion);

            if (shouldUpdate) {
                System.out.println("Actualización disponible. Descargando nueva versión...");
                Downloader.downloadAndUnzip(GitHubUpdater.getLatestDownloadUrl(), destinationDir);
                System.out.println("Actualización completada.");
            } else {
                System.out.println("La aplicación está actualizada.");
            }

            System.out.println("Ejecutando aplicación...");
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "--module-path", javafxPath,
                    "--add-modules", "javafx.controls,javafx.fxml",
                    "-jar", appJar
            );
            pb.inheritIO();
            pb.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getJarManifestVersion(String jarPath) {
        try (JarFile jarFile = new JarFile(jarPath)) {
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                return manifest.getMainAttributes().getValue("Implementation-Version");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
