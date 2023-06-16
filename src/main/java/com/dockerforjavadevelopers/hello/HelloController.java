package com.dockerforjavadevelopers.hello;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        usingRestTemplate();

        return "Hello World\n";
    }

    private void usingRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://sipd.kemendagri.go.id/api/vSIRUP?_apitoken=ba0b941de1358e57aeaf2e0dd86ecd1b&tahun=2023&kodewil=64.02&kodeskpd=1.06.0.00.0.00.01.0000", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                });

        if (response.getStatusCode().is2xxSuccessful()) {
            String jsonResponse = response.getBody();
            // Proses response JSON sesuai kebutuhan Anda
            System.out.println(jsonResponse);
        } else {
            System.out.println("Failed to fetch data from API. Status code: " + response.getStatusCode());
        }
    }

    private void usingHttpConnection() throws IOException {
        HttpsURLConnection httpsConnection = null;

        URL object = new URL("https://sipd.kemendagri.go.id/api/vSIRUP?_apitoken=ba0b941de1358e57aeaf2e0dd86ecd1b&tahun=2023&kodewil=64.02&kodeskpd=1.06.0.00.0.00.01.0000");
        httpsConnection = (HttpsURLConnection) object.openConnection();
        httpsConnection.setRequestProperty("Accept", "application/json");
        httpsConnection.setRequestMethod("GET");
        httpsConnection.setUseCaches(false);
        BufferedReader incoming = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = incoming.readLine())!= null){
            content.append(inputLine);
        }
    }
    
}
