import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;



public class GUI {

    Kontroll kontroll;
    JLabel antLevende, visFart;
    JButton start, exit, nullstill, fyllOpp, disco, plus,  minus;
    JFrame ramme;
    JPanel overordnetPanel, menyPanel, gridPanel, bottomPanel;
    JButton[][] ruter;
    String radStreng, kolonnesStreng;
    private int rader, kolonner, antallCeller, r, g, b;

    int antMillisekunder = 1000;
    Color celleFarge = new Color(19, 227, 19);
    Color bakgrunnsFarge = new Color(255, 255, 255);
    Random randomTall = new Random();
    boolean DiscoMode = false;

    Clip clip;


        // Denne action-klassen er den som oppdaterer cellene og deretter oppdaterer
        // antall-levende-celler label og rutenettet med JButtons
        class oppdaterRutenett implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DiscoMode) {
                    r = randomTall.nextInt(255);
                    g = randomTall.nextInt(255);
                    b = randomTall.nextInt(255);
                    bakgrunnsFarge = new Color(r, g, b);
                } else { bakgrunnsFarge = new Color(255, 255, 255); }
                kontroll.oppdaterCeller();
                antallCeller = kontroll.hentAntall();
                antLevende.setText("Antall levende celler:   " + antallCeller);
            }
        }

        // Denne action-klassen kjoerer naar brukeren trykker paa start-knappen
        // Den har en timer slik at naar start er trykket soerger timeren for at
        // oppdaterRutenett action-klassen ovenfor kjoerer hvert sekund

        // Dersom bruker trykker stop vil timeren stoppe og vente til bruker trykker paa start igjen
        class startbehandler implements ActionListener {
            Timer timer;
            @Override
            public void actionPerformed(ActionEvent e) {

                if (start.getText().equalsIgnoreCase("Start")) {
                    start.setText("STOP");
                    start.setBackground(Color.ORANGE);
                    timer = new Timer(antMillisekunder, new oppdaterRutenett());
                    timer.start();

                } else {
                    start.setText("START");
                    start.setBackground(Color.GREEN);
                    timer.stop();
                }
            }
        }


        // Enkel action klasse som avslutter programmet
        class exit implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Ha en fin dag videre!");
                kontroll.avsluttSpillet();
            }
        }

        // Denne action klassen har to parametere
        // den faar inn som argument hvilken knapp som er trykket paa
        // og soerger for at hvis det er trykket paa en knapp der en celle er i live
        // saa settes den doed hvis ikke motsatt

        // Den oppdaterer ogsaa antall levende celler
        class endreCelleStatus implements ActionListener {
            int rad, kol;
            endreCelleStatus (int r, int k) {
                rad = r;  kol = k;
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kontroll.celleErLevende(rad, kol)) {
                    ruter[rad][kol].setBackground(bakgrunnsFarge);
                    kontroll.settDoed(rad, kol);
                    antallCeller--;
                }
                else {
                    ruter[rad][kol].setBackground(celleFarge);
                    kontroll.settLevende(rad, kol);
                    antallCeller++;
                }
                antLevende.setText("Antall levende celler:   " + antallCeller);
            }
        }

        // Nullstiller rutenettet
        class nullstillRutenett implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                bakgrunnsFarge = new Color(255, 255, 255);
                kontroll.nullstill();
                antallCeller = kontroll.hentAntall();
                antLevende.setText("Antall levende celler:   " + antallCeller);
                kontroll.oppdaterCeller();
                DiscoMode = false;
                clip.stop();
            }
        }

        // Fyller opp rutenettet med tilfeldige celler
        class fyllMedRandomCeller implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                kontroll.fyllMedRandomCeller();
                antallCeller = kontroll.hentAntall();
                antLevende.setText("Antall levende celler:   " + antallCeller);
                kontroll.oppdaterCeller();
            }
        }

        // Setter igang discotunes og skrur på discomode
        class discomode implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                DiscoMode = !DiscoMode;
                if (DiscoMode) {
                    try {
                        File soundFile = new File("lydfil.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                        clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    } catch(Exception ex){
                        System.out.println("Fant ikke filen:(");
                    }
                } else { clip.stop(); }


            }
        }

        // Speede opp eller speede ned pausen mellom oppdateringer
        // avhenger av at bruker stopper og starter programmet etter endring
        // Kunne gjort at man kan oeke og senke fart underveis, men syntes det er
        // ryddigere at man maa stoppe og starte programmet igjen
        class OekFart implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (antMillisekunder > 100) {
                    antMillisekunder += 100;
                    visFart.setText("Fart = " + antMillisekunder + "ms" + "     Husk aa starte og stoppe hvis du har endret farten! (100millisekunder)");
                }
            }
        }

        class SenkFart implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (antMillisekunder > 100) {
                    antMillisekunder -= 100;
                    visFart.setText("Fart = " + antMillisekunder + "ms" + "     Husk aa starte og stoppe hvis du har endret farten! (100millisekunder)");
                }
            }
        }



        GUI(Kontroll k) {
            // Standard looknfeel setup
            try {
                UIManager.setLookAndFeel(
                        UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                System.exit(9);
            }

            // Oppretter en intans av klassen kontroll slik at GUI og kontroll-klassen kan samarbeide
            kontroll = k;

            // Oppretter vinduet
            ramme = new JFrame("--------- GAME OF LIFE ----------");
            ramme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Oppretter et overordnet panel og legger det til i vinduet
            overordnetPanel = new JPanel();
            overordnetPanel.setLayout(new BorderLayout());
            ramme.add(overordnetPanel);

            // Input panel
            radStreng = JOptionPane.showInputDialog("Gi inn antall rader (MAX 50):  ");
            // Dersom bruker trykker paa 'close' knappen
            if (radStreng == null) { kontroll.avsluttSpillet(); }
            // Bruker har gitt inn input og lagrer det som en int
            rader = Integer.parseInt(radStreng);

            // Lokke som kjoerer helt til bruker gir inn korrekt input
            while (rader > 50) {
                JOptionPane.showMessageDialog(null, "MAKS 50!");
                radStreng = JOptionPane.showInputDialog("Gi inn antall rader (MAX 50):  ");
                if (radStreng == null) { kontroll.avsluttSpillet(); }
                rader = Integer.parseInt(radStreng);
            }

            kolonnesStreng = JOptionPane.showInputDialog("Gi inn antall kolonner (MAX 50):  ");
            if (kolonnesStreng == null) { kontroll.avsluttSpillet(); }
            kolonner = Integer.parseInt(kolonnesStreng);

            while (kolonner > 50) {
                JOptionPane.showMessageDialog(null, "MAKS 50");
                kolonnesStreng = JOptionPane.showInputDialog("Gi inn antall kolonner (MAX 50):  ");
                if (kolonnesStreng == null) { kontroll.avsluttSpillet(); }
                kolonner = Integer.parseInt(kolonnesStreng);
            }


            // Endrer modellen sine rad og kolonne variabler
            kontroll.opprettModell(rader, kolonner);


            // Oppretter et rutenett av JButtons som er et resultat av brukerinput
            ruter = new JButton[rader][kolonner];


            // Oppretter et nytt panel hvor bruker kan starte, stoppe og avslutte programmet
            menyPanel = new JPanel();
            menyPanel.setLayout(new FlowLayout());
            menyPanel.setBackground(Color.LIGHT_GRAY);
            overordnetPanel.add(menyPanel, BorderLayout.NORTH);



            // Antall levende label
            antLevende = new JLabel();

            // Start-knapp
            start = new JButton("START");
            start.setForeground(Color.BLACK);
            start.setBackground(Color.GREEN);
            start.addActionListener(new startbehandler());

            // Exit-knapp
            exit = new JButton("EXIT");
            exit.setForeground(Color.BLACK);
            exit.setBackground(Color.RED);
            exit.addActionListener(new exit());

            // Nullstill-knapp
            nullstill = new JButton("NULLSTILL");
            nullstill.setForeground(new Color(255, 222, 5));
            nullstill.setBackground(Color.BLUE);
            nullstill.addActionListener(new nullstillRutenett());

            // Fyll Opp-knapp
            fyllOpp = new JButton("FYLL OPP");
            fyllOpp.setForeground(Color.BLACK);
            fyllOpp.setBackground(Color.MAGENTA);
            fyllOpp.addActionListener(new fyllMedRandomCeller());

            // Discomode knapp
            disco = new JButton("DISCOMODE");
            disco.setBackground(new Color(255, 61, 5));
            disco.setForeground(new Color(255, 222, 5));
            disco.addActionListener(new discomode());

            // Legger til komponentene i menypanelet
            menyPanel.add(antLevende);
            menyPanel.add(start);
            menyPanel.add(exit);
            menyPanel.add(nullstill);
            menyPanel.add(fyllOpp);
            menyPanel.add(disco);



            // Oppretter selve griden bestaaende av jbuttons som representerer celler
            gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(rader, kolonner));
            for (int rx = 0; rx < rader; ++rx) {
                for (int kx = 0; kx < kolonner; ++kx) {
                    JButton b = new JButton();
                    ruter[rx][kx] = b;
                    if (kontroll.hentRutenett()[rx][kx].erLevende()) {
                        b.setBackground(celleFarge);
                    } else { b.setBackground(bakgrunnsFarge); }
                    b.addActionListener(new endreCelleStatus(rx, kx));
                    gridPanel.add(b);
                }
            }
            overordnetPanel.add(gridPanel, BorderLayout.CENTER);
            antallCeller = kontroll.hentAntall();
            antLevende.setText("Antall levende celler:   " + antallCeller);

            bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout());
            overordnetPanel.add(bottomPanel, BorderLayout.SOUTH);

            visFart = new JLabel("Fart = " + antMillisekunder + "ms" + "     " +
                                   "Husk aa starte og stoppe hvis du har endret farten! (100millisekunder)");
            visFart.setLayout(new FlowLayout());
            visFart.setHorizontalAlignment(JLabel.CENTER);
            visFart.setVerticalAlignment(JLabel.CENTER);
            bottomPanel.add(visFart);

            // Pluss 100millisekunder knapp
            plus = new JButton(" + ");
            plus.setBackground(new Color(26, 25, 24));
            plus.setForeground(new Color(246, 241, 23));
            plus.addActionListener(new OekFart());

            // Pluss 100millisekunder knapp
            minus = new JButton(" - ");
            minus.setBackground(new Color(246, 241, 23));
            minus.setForeground(new Color(26, 25, 24));
            minus.addActionListener(new SenkFart());

            bottomPanel.add(plus);
            bottomPanel.add(minus);


            ramme.pack();
            ramme.setSize(1000, 1000);
            ramme.setLocationRelativeTo(null);
            ramme.setVisible(true);

        }

        public void oppdaterGUIRutenett() {
            for (int rx = 0; rx < rader; ++rx) {
                for (int kx = 0; kx < kolonner; ++kx) {
                    if (kontroll.hentRutenett()[rx][kx].erLevende()) {
                        ruter[rx][kx].setBackground(celleFarge);
                    } else { ruter[rx][kx].setBackground(bakgrunnsFarge); }
                }
            }
        }







}


