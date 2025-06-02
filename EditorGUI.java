import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EditorGUI extends JFrame {

    private JTextPane editor;
    private StyledDocument belge;

    public EditorGUI() {
        setTitle("Tuglang Editör");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        editor = new JTextPane();
        belge = editor.getStyledDocument();
        JScrollPane scroll = new JScrollPane(editor);
        add(scroll);

        // Yazı değişince renklendir
        belge.addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                renklendir();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                renklendir();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });

        // İlk stil ayarları
        stilHazirla();

        setVisible(true);
    }

    private void stilHazirla() {
        SimpleAttributeSet keyword = new SimpleAttributeSet();
        StyleConstants.setForeground(keyword, Color.BLUE);
        StyleConstants.setBold(keyword, true);
        belge.setCharacterAttributes(0, belge.getLength(), keyword, false);
    }

    // Tokenlara göre renklendir
    private void renklendir() {
        SwingUtilities.invokeLater(() -> {
            String kod = editor.getText();
            List<TuglangLexer.Token> tokenler = TuglangLexer.tokenize(kod);

            // Önce her şeyi siyah yap
            SimpleAttributeSet normal = new SimpleAttributeSet();
            StyleConstants.setForeground(normal, Color.BLACK);
            belge.setCharacterAttributes(0, kod.length(), normal, true);

            int konum = 0;
            for (TuglangLexer.Token token : tokenler) {
                SimpleAttributeSet stil = new SimpleAttributeSet();

                switch (token.tip) {
                    case ANAHTAR_KELIME -> StyleConstants.setForeground(stil, Color.BLUE);
                    case SAYI -> StyleConstants.setForeground(stil, new Color(128, 0, 0)); // koyu kırmızı
                    case OPERATOR -> StyleConstants.setForeground(stil, Color.ORANGE);
                    case DEGİSKEN -> StyleConstants.setForeground(stil, Color.MAGENTA);
                    case PARANTEZ, AYIRICI -> StyleConstants.setForeground(stil, Color.GRAY);
                    default -> StyleConstants.setForeground(stil, Color.BLACK);
                }

                // Token uzunluğu kadar renklendir
                belge.setCharacterAttributes(konum, token.deger.length(), stil, true);
                konum += token.deger.length();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditorGUI::new);
    }
}
