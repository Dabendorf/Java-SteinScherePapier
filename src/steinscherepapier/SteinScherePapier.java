package steinscherepapier;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 * Dies ist die einzige Klasse des SteinScherePapier-Spiels. Sie steuert saemtliche Spielalgorithmen sowie die gesamte Grafik.
 * 
 * @author Lukas Schramm
 * @version 1.0
 * 
 */
public class SteinScherePapier {
	
	private static JFrame frame1 = new JFrame("SchnickSchnackSchnuck");
	private JButton buttonstein = new JButton("Stein");
	private JButton buttonschere = new JButton("Schere");
	private JButton buttonpapier = new JButton("Papier");
	private JButton buttonbrunnen = new JButton("Brunnen");
	private JButton buttonechse = new JButton("Echse");
	private JButton buttonspock = new JButton("Spock");
	private ArrayList<JButton> buttonliste = new ArrayList<JButton>();
	private int modus;
	private int spieler, ki;
	private int pktspieler = 0, pktki = 0, rundenzahl = 0;
	private JLabel labelrunden = new JLabel("Runde: "+rundenzahl);
	private JLabel labelspieler = new JLabel("Spieler: "+pktspieler);
	private JLabel labelki = new JLabel("Computer: "+pktki);
	private JLabel labelauswertung = new JLabel();
	private ArrayList<JLabel> labelliste = new ArrayList<JLabel>();
	
	public SteinScherePapier() {
		regelfrage();
		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(250,90+30*buttonliste.size());
		frame1.setResizable(false);
		Container cp = frame1.getContentPane();
		cp.setLayout(null);
		
		for(JButton b: buttonliste) {
			final int n = buttonliste.indexOf(b);
			b.setBounds(10, 10+30*n, 90, 25);
			b.setVisible(true);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					spieler = n;
					spielrunde();
				}
			});
			cp.add(b);
			labelauswertung.setBounds(17, 10+30*(n+1), 600, 50);
		}
		labelauswertung.setVisible(true);
		cp.add(labelauswertung);
		
		for(JLabel l:labelliste) {
			final int n = labelliste.indexOf(l);
			l.setBounds(120, 10+30*n, 100, 25);
			l.setVisible(true);
			cp.add(l);
		}
		
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	/**
	 * Diese Methode fragt anfangs den Spieler, ob er Klassisch, mit Brunnen oder mit Echse und Spock spielen moechte.<br>
	 * Je nach Auswahl werden die dazu passenden Buttons in eine Liste geschrieben und die Grafik generiert.
	 */
	private void regelfrage() {
		JRadioButton klassisch = new JRadioButton("Klassisch");
	    JRadioButton brunnen = new JRadioButton("Mit Brunnen");
	    JRadioButton echsespock = new JRadioButton("Mit Echse & Spock");
	    ButtonGroup bg = new ButtonGroup();
	    bg.add(klassisch);
	    klassisch.setSelected(true);
	    bg.add(brunnen);
	    bg.add(echsespock);
		Object[] parameter = {klassisch, brunnen, echsespock};
		JOptionPane pane = new JOptionPane(parameter, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
		pane.createDialog(null, "Wähle Deine Einstellungen").setVisible(true);
		
		buttonliste.add(buttonstein);
		buttonliste.add(buttonschere);
		buttonliste.add(buttonpapier);
		if(klassisch.isSelected()) {
			modus = 1;
		} else if(brunnen.isSelected()) {
			modus = 2;
			buttonliste.add(buttonbrunnen);
		} else {
			modus = 3;
			buttonliste.add(buttonechse);
			buttonliste.add(buttonspock);
		}
		labelliste.add(labelrunden);
		labelliste.add(labelspieler);
		labelliste.add(labelki);
	}
	
	/**
	 * Diese Methode generiert, abhaengig vom Spielmodus eine Zahl von 0 bis (2 bis 4), welche die Auswahl des Computers repraesentiert.
	 */
	private void spielrunde() {
		Random randki = new Random();
		switch(modus) {
		case 1:
			for(int n=0;n<100;n++) {
				ki = randki.nextInt(3);
			}
			break;
		case 2:
			for(int n=0;n<100;n++) {
				ki = randki.nextInt(4);
			}
			break;
		case 3:
			for(int n=0;n<100;n++) {
				ki = randki.nextInt(5);
			}
			break;
		}
		auswertung();
	}
	
	/**
	 * Diese Methode findet anhand der beiden ints der Spieler und der KI heraus wer gewonnen hat.
	 */
	private void auswertung() {
		rundenzahl++;
		labelrunden.setText("Runde: "+rundenzahl);
		if(spieler == ki) {
			labelauswertung.setText("<html>Beide Spieler wählten "+figur(ki)+".<br/>Das Spiel endet unentschieden.</html>");
		}
		if(modus == 1) {
			//0=Stein; 1=Schere; 2=Papier
			if(spieler == 0 && ki == 1) { sieg(1); }
			else if(spieler == 1 && ki == 2) { sieg(1); }
			else if(spieler == 2 && ki == 0) { sieg(1); }
			
			else if(spieler == 1 && ki == 0) { sieg(2); }
			else if(spieler == 2 && ki == 1) { sieg(2); }
			else if(spieler == 0 && ki == 2) { sieg(2); }
		} else if(modus == 2) {
			//0=Stein; 1=Schere; 2=Papier; 3=Brunnen
			if(spieler == 0 && ki == 1) { sieg(1); }
			else if(spieler == 1 && ki == 2) { sieg(1); }
			else if(spieler == 2 && ki == 0) { sieg(1); }
			else if(spieler == 2 && ki == 3) { sieg(1); }
			else if(spieler == 3 && ki == 0) { sieg(1); }
			else if(spieler == 3 && ki == 1) { sieg(1); }
			
			else if(spieler == 1 && ki == 0) { sieg(2); }
			else if(spieler == 2 && ki == 1) { sieg(2); }
			else if(spieler == 0 && ki == 2) { sieg(2); }
			else if(spieler == 3 && ki == 2) { sieg(2); }
			else if(spieler == 0 && ki == 3) { sieg(2); }
			else if(spieler == 1 && ki == 3) { sieg(2); }
		} else if(modus == 3) {
			//0=Stein; 1=Schere; 2=Papier; 3=Echse; 4=Spock
			if(spieler == 0 && ki == 1) { sieg(1); }
			else if(spieler == 0 && ki == 3) { sieg(1); }
			else if(spieler == 1 && ki == 2) { sieg(1); }
			else if(spieler == 1 && ki == 3) { sieg(1); }
			else if(spieler == 2 && ki == 0) { sieg(1); }
			else if(spieler == 2 && ki == 4) { sieg(1); }
			else if(spieler == 3 && ki == 2) { sieg(1); }
			else if(spieler == 3 && ki == 4) { sieg(1); }
			else if(spieler == 4 && ki == 0) { sieg(1); }
			else if(spieler == 4 && ki == 1) { sieg(1); }
			
			else if(spieler == 1 && ki == 0) { sieg(2); }
			else if(spieler == 3 && ki == 0) { sieg(2); }
			else if(spieler == 2 && ki == 1) { sieg(2); }
			else if(spieler == 3 && ki == 1) { sieg(2); }
			else if(spieler == 0 && ki == 2) { sieg(2); }
			else if(spieler == 4 && ki == 2) { sieg(2); }
			else if(spieler == 2 && ki == 3) { sieg(2); }
			else if(spieler == 4 && ki == 3) { sieg(2); }
			else if(spieler == 0 && ki == 4) { sieg(2); }
			else if(spieler == 1 && ki == 4) { sieg(2); }
		}
	}
	
	/**
	 * Diese Methode gibt aus, wer gewonnen hat und gibt ihm einen Punkt.
	 * @param sieger Nimmt den Sieger entgegen.
	 */
	private void sieg(int sieger) {
		if(sieger == 1) {
			pktspieler++;
			labelspieler.setText("Spieler: "+pktspieler);
			labelauswertung.setText("<html>Der Spieler gewinnt.<br/>"+figur(spieler)+" schlägt "+figur(ki)+".</html>");
		} else {
			pktki++;
			labelki.setText("Computer: "+pktki);
			labelauswertung.setText("<html>Der Computer gewinnt.<br/>"+figur(ki)+" schlägt "+figur(spieler)+".</html>");
		}
	}
	
	/**
	 * Diese Methode gibt einen String aus, wie die Figur heisst, die gewaehlt wurde, um dem Spieler eine bessere textuelle Ausgabe zu ermoeglichen.
	 * @param n Nimmt den int der Figur entgegen.
	 * @return Gibt den Namen der Figur zurueck.
	 */
	private String figur(int n) {
		switch(modus) {
		case 1:
			switch(n) {
			case 0:
				return "Stein";
			case 1:
				return "Schere";
			case 2:
				return "Papier";
			}
		case 2:
			switch(n) {
			case 0:
				return "Stein";
			case 1:
				return "Schere";
			case 2:
				return "Papier";
			case 3:
				return "Brunnen";
			}
		case 3:
			switch(n) {
			case 0:
				return "Stein";
			case 1:
				return "Schere";
			case 2:
				return "Papier";
			case 3:
				return "Echse";
			case 4:
				return "Spock";
			}
		default: return "Error";
		}
	}

	public static void main(String[] args) {
		new SteinScherePapier();
	}
}