package co.edu.escuelaing.backend;

import org.springframework.stereotype.Service;

@Service
public class SearchService {

    public boolean palindrome(String word) {
        word = word.toLowerCase();
        String[] letters = word.split("");
        int size = letters.length;
        for (int i = 0; i < size; i++) {
            if (i <= size/2 && !letters[i].equals(letters[size-1-i])) {
                return false;
            }
        }
        return true;
    }

    public int factorial(int n) {
        int result = 1;
        if (n == 0) {
            return 1;
        }
        for (int i = 1; i<=n; i++) {
            result = result*i;
        }
        return result;
    }

}
