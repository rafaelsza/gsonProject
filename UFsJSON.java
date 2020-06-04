/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Souza
 */
public class UFsJSON {
    public ArrayList<Estado> getUFs(){
        try {
            String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados?orderBy=nome";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(null, "Erro " + conn.getResponseCode() + " ao obter dados da URL " + url, "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = br.readLine();
            output = output.replace("[", "{ \"estados\": [");
            output = output.replace("]", "]}");
            
            System.out.println(output);
            
            conn.disconnect();

            JSONObject JsObject = new JSONObject(output);
            JSONArray ArrayEstados = JsObject.getJSONArray("estados");
            ArrayList<Estado> estados = new ArrayList();
            
            for (int i = 0; i < ArrayEstados.length(); ++i) {
              final JSONObject JsObjectEstado = ArrayEstados.getJSONObject(i);
              Estado estado = new Estado();
              estado.setId(JsObjectEstado.getInt("id"));
              estado.setSigla(JsObjectEstado.getString("sigla"));
              estado.setNome(JsObjectEstado.getString("nome"));
              estados.add(estado);
            }

            return estados;
        } catch (IOException ex) {
            ex.toString();
        }
        return null;
    }
}
