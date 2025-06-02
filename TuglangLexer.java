import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TuglangLexer {

    // Token türleri
    public enum TokenType {
        ANAHTAR_KELIME, // belirle, yaz, eger, dongu
        DEGİSKEN,       // a, x, toplam_sayi
        SAYI,           // 123, 99
        OPERATOR,       // + - * /
        AYIRICI,        // = ; { }
        PARANTEZ,       // ( )
        BOSLUK,         // atlanır
        BILINMEYEN      // hata durumu
    }

    // Token sınıfı
    public static class Token {
        public final TokenType tip;
        public final String deger;

        public Token(TokenType tip, String deger) {
            this.tip = tip;
            this.deger = deger;
        }

        @Override
        public String toString() {
            return String.format("[%s -> \"%s\"]", tip.name(), deger);
        }
    }

    // Regex sıralaması önemli! Önce daha uzun eşleşmeleri dene.
    private static final Pattern[] REGEXLER = {
        Pattern.compile("^(belirle|yaz|eger|dongu)"),     // ANAHTAR_KELIME
        Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*"),       // DEGİSKEN
        Pattern.compile("^\\d+"),                         // SAYI
        Pattern.compile("^[+\\-*/]"),                     // OPERATOR
        Pattern.compile("^[=;{}]"),                       // AYIRICI
        Pattern.compile("^[()]"),                         // PARANTEZ
        Pattern.compile("^\\s+"),                         // BOSLUK
        Pattern.compile("^.")                             // BILINMEYEN (tek karakter)
    };

    // Tokenize etme metodu
    public static ArrayList<Token> tokenize(String kod) {
        ArrayList<Token> tokenler = new ArrayList<>();

        while (!kod.isEmpty()) {
            boolean eslesti = false;

            for (int i = 0; i < REGEXLER.length; i++) {
                Matcher matcher = REGEXLER[i].matcher(kod);
                if (matcher.find()) {
                    String eslesen = matcher.group();

                    TokenType tip = TokenType.values()[i];
                    if (tip != TokenType.BOSLUK) {
                        tokenler.add(new Token(tip, eslesen));
                    }

                    kod = kod.substring(matcher.end());
                    eslesti = true;
                    break;
                }
            }

            if (!eslesti) {
                // Belirsiz karakter varsa döngüden çık
                System.err.println("Tokenize edilemeyen karakter: " + kod.charAt(0));
                break;
            }
        }

        return tokenler;
    }

    // Test için main metodu
    public static void main(String[] args) {
        String ornekKod = "belirle x = 10 + 20 ; yaz x ;";
        ArrayList<Token> sonuc = tokenize(ornekKod);
        for (Token token : sonuc) {
            System.out.println(token);
        }
    }
}
