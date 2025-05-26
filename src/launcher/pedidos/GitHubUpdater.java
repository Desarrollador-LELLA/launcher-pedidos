/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package launcher.pedidos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author NOTEJUAN
 */
public class GitHubUpdater {

    private static final String API_URL = "https://api.github.com/repos/Desarrollador-LELLA/notas-ventas-pablo/releases/latest";
    private static final String CURRENT_VERSION = "1.0.0"; // Cambia esto con cada versión
    private static String latestDownloadUrl = null;

    public static boolean isUpdateAvailable() {

        try {

            HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
            conn.setRequestProperty("Accept", "application/vnd.github+json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            JSONObject release = new JSONObject(json.toString());
            String latestVersion = release.getString("tag_name").replace("v", "");
            latestDownloadUrl = release.getJSONArray("assets").getJSONObject(0).getString("browser_download_url");

            return !CURRENT_VERSION.equals(latestVersion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getLatestDownloadUrl() {
        return latestDownloadUrl;
    }
}
