/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Souza
 */
public class UFsJSON {
    private static final int HTTP_COD_SUCESSO = 200;
    
    public static void main(String[] args) throws IOException {
//        URL url = new URL("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        
//        if (con.getResponseCode() != HTTP_COD_SUCESSO) {
//                throw new RuntimeException("HTTP error code : "+ con.getResponseCode());
//        }
//
//        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
//        
//        System.out.println(br);
        
        try {
            String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados?orderBy=nome";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = "";
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }

            conn.disconnect();
            

            Gson gson = new Gson();
            Estado estado = gson.fromJson(new String(output.getBytes()), Estado.class);

            System.out.println(estado.getSigla());

        } catch (IOException ex) {
            ex.toString();
        }
    }
}
