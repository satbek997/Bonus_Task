import java.util.ArrayList;
import java.util.List;

class RabinKarp {
    // A prime number for the hash function (prime number to reduce collisions)
    private static final int d = 256;  // Number of characters in the alphabet
    private static final int q = 101;  // A prime number for the modulus

    // Function to search for a pattern in the text using the Rabin-Karp algorithm
    public static List<Integer> search(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();
        int patternHash = 0;  // Hash of the pattern
        int textHash = 0;  // Hash of the current substring of the text
        int h = 1;
        List<Integer> result = new ArrayList<>();

        // Calculate h = d^(m-1) % q
        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % q;
        }

        // Compute the hash for the pattern and the first m characters of the text
        for (int i = 0; i < m; i++) {
            patternHash = (d * patternHash + pattern.charAt(i)) % q;
            textHash = (d * textHash + text.charAt(i)) % q;
        }

        // Sliding window algorithm to check for matches in the text
        for (int i = 0; i <= n - m; i++) {
            // If hashes match, check the substrings character by character
            if (patternHash == textHash) {
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    result.add(i);  // Add the index of the match
                }
            }

            // Compute the hash for the next substring of text
            if (i < n - m) {
                textHash = (d * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % q;
                if (textHash < 0) {
                    textHash = (textHash + q);
                }
            }
        }

        return result;
    }

    // Test the algorithm
    public static void main(String[] args) {
        String text1 = "ababcababcabc";
        String pattern1 = "abc";
        String text2 = "this is a test string";
        String pattern2 = "test";
        String text3 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String pattern3 = "aa";

        System.out.println("Results for Text1:");
        System.out.println(search(text1, pattern1));  // Expected output: [2, 7, 10]

        System.out.println("Results for Text2:");
        System.out.println(search(text2, pattern2));  // Expected output: [10]

        System.out.println("Results for Text3:");
        System.out.println(search(text3, pattern3));  // Expected output: [0, 1, 2, 3, ..., 98]
    }
}
