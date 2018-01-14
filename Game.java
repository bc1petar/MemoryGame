import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;                                 // DODAVANJE POTREBNIH BIBLIOTEKA
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

@SuppressWarnings("serial")
class Board extends JFrame {                                       // KLASA BOARD NASLEDJUJE KARAKTERISTIKE JFRAME-A

    private List<Card> cards;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer t;

    public Board() {

        int pairs = 10;                                                     // PRAVLJENJE BROJA PAROVA I LISTA KARATA <CARD> KOJE SADRZE VRIJEDNOSTI ISTIH TIPA <INTEGER>
        List<Card> cardsList = new ArrayList<Card>();
        List<Integer> cardVals = new ArrayList<Integer>();

        for (int i = 0; i < pairs; i++) {
            cardVals.add(i);
            cardVals.add(i);
        }
        Collections.shuffle(cardVals);

        for (int val : cardVals) {
            Card c = new Card();
            c.setId(val);
            c.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    selectedCard = c;
                    doTurn();
                }
            });
            cardsList.add(c);
        }
        this.cards = cardsList;
        // POSTAVLJANJE TAJMERA
        t = new Timer(750, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                checkCards();
            }
        });

        t.setRepeats(false);

        // POSTAVLJANJE TABLICE
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(4, 5));
        for (Card c : cards) {
            pane.add(c);
        }
        setTitle("Memory");
    }

    public void doTurn() {
        if (c1 == null && c2 == null) {
            c1 = selectedCard;
            c1.setText(String.valueOf(c1.getId()));
        }

        if (c1 != null && c1 != selectedCard && c2 == null) {
            c2 = selectedCard;
            c2.setText(String.valueOf(c2.getId()));
            t.start();
        }
    }

    public void checkCards() {
        if (c1.getId() == c2.getId()) {//match condition
            c1.setEnabled(false);                     // U SLUCAJU DA POGODIS JEDAN PAR BROJEVA TI BROJEVI POSTAJU ZALEDJENI I OSTAJU VIDLJIVI
            c2.setEnabled(false);
            c1.setMatched(true);
            c2.setMatched(true);
            if (this.isGameWon()) {
                JOptionPane.showMessageDialog(this, "You win!");
                System.exit(0);
            }
        } else {
            c1.setText("");                     // SAKRIVA BROJEVE
            c2.setText("");
        }
        c1 = null;                                           // RESETOVANJE C1 I C2
        c2 = null;
    }

    public boolean isGameWon(){
        for(Card c: this.cards){
            if (c.getMatched() == false){
                return false;
            }
        }
        return true;
    }
}
@SuppressWarnings("serial")
class Card extends JButton {

    private int id;
    private boolean matched = false;

    // SETERI I GETERI ZA KARTICE 
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    public boolean getMatched() {
        return this.matched;
    }
}

public class Game {                                               // MAIN METOD SA POZIVIMA TABLE I OSNOVNIM PARAMETRIMA KOJI SE POZIVAJU NAKON KREIRANJA OBJEKTA ZA BOARD
    public static void main(String[] args) {
        Board b = new Board();
        b.setPreferredSize(new Dimension(500, 500));
        b.setLocation(500, 250);
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.pack();
        b.setVisible(true);
    }
}
