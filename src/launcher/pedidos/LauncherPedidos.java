/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package launcher.pedidos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 *
 * @author NOTEJUAN
 */
public class LauncherPedidos {

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        try {
            String appJar = "app/NotaPedidos.jar";
            String javafxPath = "C:\\javafx-sdk-22.0.1\\lib";

            String destinationDir = "app";

            if (GitHubUpdater.isUpdateAvailable()) {
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
    }*/
    public static void main(String[] args) {
        try {
            String appJar = "app/NotaPedidos.jar";
            String javafxPath = "C:\\javafx-sdk-22.0.1\\lib";

            String installedVersion = null;
            boolean jarExists = new java.io.File(appJar).exists();

            if (jarExists) {
                installedVersion = getJarManifestVersion(appJar);
            }

            String latestVersion = GitHubUpdater.fetchLatestVersion();

            boolean shouldUpdate = !jarExists || isNewerVersion(installedVersion, latestVersion);

            if (shouldUpdate) {
                System.out.println("Actualización disponible. Descargando nueva versión...");
                Downloader.download(GitHubUpdater.getLatestDownloadUrl(), appJar);
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

    /*public static String getInstalledJarVersion(String jarPath) {
        try (java.util.jar.JarFile jarFile = new java.util.jar.JarFile(jarPath)) {
            java.util.jar.JarEntry entry = jarFile.getJarEntry("version.txt");
            if (entry != null) {
                try (InputStream in = jarFile.getInputStream(entry); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    return reader.readLine().trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // No se pudo leer la versión
    }*/

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

    public static boolean isNewerVersion(String currentVersion, String latestVersion) {
        if (currentVersion == null) {
            return true;
        }

        String[] currentParts = currentVersion.split("\\.");
        String[] latestParts = latestVersion.split("\\.");

        int length = Math.max(currentParts.length, latestParts.length);

        for (int i = 0; i < length; i++) {
            int currentPart = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
            int latestPart = i < latestParts.length ? Integer.parseInt(latestParts[i]) : 0;

            if (latestPart > currentPart) {
                return true;
            }
            if (latestPart < currentPart) {
                return false;
            }
        }
        return false;
    }
}
