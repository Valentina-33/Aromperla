package co.edu.escuelaing;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class ProxyController {

    private final String[] backends = System.getenv()
            .getOrDefault("BACKENDS", "http://localhost:45000,http://localhost:45001")
            .split(",");

    @GetMapping(value = "/palindrome", produces = "application/json")
    public ResponseEntity<String> palindrome(@RequestParam String input) {
        return forward("/palindrome?input=" + encode(input));
    }

    @GetMapping(value = "/factorial", produces = "application/json")
    public ResponseEntity<String> factorial(@RequestParam String input) {
        return forward("/factorial?input=" + encode(input));
    }

    private ResponseEntity<String> forward(String pathAndQuery) {
        for (String backend : backends) {
            String url = backend + pathAndQuery;
            System.out.println("Reenviando a " + url);
            try {
                HttpURLConnection con = (HttpURLConnection) URI.create(url).toURL().openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(2000);
                int status = con.getResponseCode();
                InputStream is = status < 400 ? con.getInputStream() : con.getErrorStream();
                return ResponseEntity.status(status).body(new String(is.readAllBytes(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.out.println("Falló " + backend + ", probando el siguiente");
            }
        }
        return ResponseEntity.status(503).body("{\"error\" : \"backend no responde\"}");
    }

    private String encode(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

}
