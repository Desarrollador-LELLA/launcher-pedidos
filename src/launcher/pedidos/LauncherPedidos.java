/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package launcher.pedidos;

/**
 *
 * @author NOTEJUAN
 */
public class LauncherPedidos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String appJar = "app/NotaPedidos.jar";
            String javafxPath = "C:\\javafx-sdk-22.0.1\\lib";

            String destinationDir = "app"; // carpeta destino donde se extrae todo

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
            pb.inheritIO(); // Muestra la salida en consola
            pb.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
