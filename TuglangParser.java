import java.util.List;

public class TuglangParser {

    private List<TuglangLexer.Token> tokenler;
    private int indeks = 0;

    public TuglangParser(List<TuglangLexer.Token> tokenler) {
        this.tokenler = tokenler;
    }

    private TuglangLexer.Token simdikiToken() {
        if (indeks < tokenler.size())
            return tokenler.get(indeks);
        return null;
    }

    private boolean esles(TuglangLexer.TokenType beklenen) {
        TuglangLexer.Token token = simdikiToken();
        if (token != null && token.tip == beklenen) {
            indeks++;
            return true;
        }
        return false;
    }

    // <program> ::= <statement_list>
    public void parseProgram() {
        parseStatementList();
        if (simdikiToken() != null) {
            hata("Ekstra token bulundu: " + simdikiToken());
        } else {
            System.out.println("Program başarıyla ayrıştırıldı.");
        }
    }

    // <statement_list> ::= <statement> | <statement> <statement_list>
    private void parseStatementList() {
        while (simdikiToken() != null &&
               simdikiToken().tip == TuglangLexer.TokenType.ANAHTAR_KELIME) {
            parseStatement();
        }
    }

    // <statement> ::= <assignment> | <print> | <if> | <loop>
    private void parseStatement() {
        TuglangLexer.Token token = simdikiToken();
        if (token == null) return;

        if (token.deger.equals("belirle")) {
            parseAssignment();
        } else if (token.deger.equals("yaz")) {
            parsePrint();
        } else if (token.deger.equals("eger")) {
            parseIf();
        } else if (token.deger.equals("dongu")) {
            parseLoop();
        } else {
            hata("Geçersiz komut: " + token.deger);
        }
    }

    // <assignment> ::= 'belirle' <identifier> '=' <expression> ';'
    private void parseAssignment() {
        esles(TuglangLexer.TokenType.ANAHTAR_KELIME); // belirle
        if (!esles(TuglangLexer.TokenType.DEGİSKEN)) hata("Değişken bekleniyor.");
        if (!esles(TuglangLexer.TokenType.AYIRICI)) hata("'=' bekleniyor.");
        parseExpression();
        if (!esles(TuglangLexer.TokenType.AYIRICI)) hata("';' bekleniyor.");
    }

    // <print> ::= 'yaz' <expression> ';'
    private void parsePrint() {
        esles(TuglangLexer.TokenType.ANAHTAR_KELIME); // yaz
        parseExpression();
        if (!esles(TuglangLexer.TokenType.AYIRICI)) hata("';' bekleniyor.");
    }

    // <if> ::= 'eger' '(' <expression> ')' '{' <statement_list> '}'
    private void parseIf() {
        esles(TuglangLexer.TokenType.ANAHTAR_KELIME); // eger
        if (!esles(TuglangLexer.TokenType.PARANTEZ)) hata("'(' bekleniyor.");
        parseExpression();
        if (!esles(TuglangLexer.TokenType.PARANTEZ)) hata("')' bekleniyor.");
        if (!esles(TuglangLexer.TokenType.AYIRICI)) hata("'{' bekleniyor.");
        parseStatementList();
        if (!esles(TuglangLexer.TokenType.AYIRICI)) hata("'}' bekleniyor.");
    }

    // <loop> ::= 'dongu' '(' <expression> ')' '{' <statement_list> '}'
    private void parseLoop() {
        esles(TuglangLexer.TokenType.ANAHTAR_KELIME); // dongu
        if (!esles(TuglangLexer.TokenType.PARANTEZ)) hata("'(' bekleniyor.");
        parseExpression();
        if (!esles(TuglangLexer.TokenType.PARANTEZ)) hata("')' bekleniyor.");
        if (!esles(TuglangLexer.TokenType.AYIRICI)) hata("'{' bekleniyor.");
        parseStatementList();
        if (!esles(TuglangLexer.TokenType.AYIRICI)) hata("'}' bekleniyor.");
    }

    // <expression> ::= <term> (('+' | '-') <term>)*
    private void parseExpression() {
        parseTerm();
        while (simdikiToken() != null &&
               simdikiToken().tip == TuglangLexer.TokenType.OPERATOR &&
               (simdikiToken().deger.equals("+") || simdikiToken().deger.equals("-"))) {
            esles(TuglangLexer.TokenType.OPERATOR);
            parseTerm();
        }
    }

    // <term> ::= <factor> (('*' | '/') <factor>)*
    private void parseTerm() {
        parseFactor();
        while (simdikiToken() != null &&
               simdikiToken().tip == TuglangLexer.TokenType.OPERATOR &&
               (simdikiToken().deger.equals("*") || simdikiToken().deger.equals("/"))) {
            esles(TuglangLexer.TokenType.OPERATOR);
            parseFactor();
        }
    }

    // <factor> ::= <number> | <identifier> | '(' <expression> ')'
    private void parseFactor() {
        TuglangLexer.Token token = simdikiToken();
        if (token == null) return;

        if (token.tip == TuglangLexer.TokenType.SAYI || token.tip == TuglangLexer.TokenType.DEGİSKEN) {
            esles(token.tip);
        } else if (token.deger.equals("(")) {
            esles(TuglangLexer.TokenType.PARANTEZ);
            parseExpression();
            if (!esles(TuglangLexer.TokenType.PARANTEZ)) hata("')' bekleniyor.");
        } else {
            hata("Geçersiz ifade: " + token.deger);
        }
    }

    private void hata(String mesaj) {
        throw new RuntimeException(" Ayrıştırma hatası: " + mesaj);
    }

    // Test için main metodu
    public static void main(String[] args) {
        String kod = """
            belirle x = 5 + 2 ;
            yaz x ;
            eger (x) {
                yaz x ;
            }
            dongu (x) {
                yaz x ;
            }
            """;

        List<TuglangLexer.Token> tokenler = TuglangLexer.tokenize(kod);
        TuglangParser parser = new TuglangParser(tokenler);
        parser.parseProgram();
    }
}


