package co.edu.escuelaing.backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SearchController {
    private final SearchService service;

    public SearchController(SearchService service) {
        this.service = service;
    }

    @GetMapping(value = "/palindrome", produces = "application/json")
    public String palindrome(@RequestParam String input) {
        boolean result = service.palindrome(input);
        String output = String.valueOf(result);
        return toJson("palindrome", input, output);
    }

    @GetMapping(value = "/factorial" , produces = "application/json")
    public String factorial(@RequestParam String input) {
        int n = Integer.parseInt(input.trim());
        if (n < 0) {
            throw new IllegalArgumentException("no se permiten negativos");
        }
        int result = service.factorial(Integer.parseInt(input));
        String output = String.valueOf(result);
        return toJson("factorial", input, output);
    }

    private String toJson(String operation, String input, String output) {
        return "{\"operation\":\"" + operation + "\",\"input\":\"" + input +
                "\",\"output\":\"" + output + "\"}";
    }
}
