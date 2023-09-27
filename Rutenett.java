public class Rutenett {

    protected int antRader;
    protected int antKolonner;

    // Oppretter to-dimensjonelt array som skal inneholde Celle-objekter
    protected Celle[][] rutene;

    // Konstruktør:
    public Rutenett(int r, int k) {
        antRader = r;
        antKolonner = k;
        // Initialiserer arrayet med int rader og int kolonner som sendes med som argument.
        rutene = new Celle[r][k];
    }

    /*
    Metoden lagCelle oppretter først et Celle-objekt
    og bruker så Math.random for å gi cellen en 33% sjanse
    for å være levende. Cellen bli deretter lagt inn i arrayet ut
    ifra argumentene rad, kol som kommer som argumenter i metoden.
     */

    public void lagCelle(int rad, int kol) {
        Celle celle = new Celle();
        if (Math.random() <= 0.3333) {
            celle.settLevende();
        }
        rutene[rad][kol] = celle;
    }

    /*
    Metoden går gjennom alle plassene i rutenettet og fyller hver plass med
    et Celle-objekt ved å kalle metoden lag_celle(
     */
    public void fyllMedTilfeldigeCeller() {
        for (int rad = 0; rad < rutene.length; rad++) {
            for (int kol = 0; kol < rutene[0].length; kol++) {
                lagCelle(rad, kol);
            }
        }
    }

    /*
    Metoden hentCelle får inn rad og kol
    som argumenter og sjekker om plassen det utgjør
    er innenfor rutenettets grenser. Hvis det er true
    returnerer den cellen på den plassen
     */
    public Celle hentCelle(int r, int k) {
        if (r < 0 || k < 0) {
            return null;
        } else if (r >= rutene.length) {
            return null;
        } else if (k >= rutene[r].length) {
            return null;
        } else return rutene[r][k];
    }

    /*
    Metoden tegnRutenett lager først 3 linjer med plass
    i terminalvinduet over rutenettet.

    Metoden går deretter gjennom alle celleobjektene i rutenettet
    og kaller på metoden hentStatusTegn for å skrive
    på terminalen symbolet for om cellen er i live eller død.
     */

    public void tegnRutenett() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
        for (Celle[] rad : rutene) {
            for (Celle celle : rad) {
                System.out.print(celle.hentStatusTegn());
            }
            System.out.println();
        }

    }

    /*
    Lager en metode som returnerer selve rutenettet, altså det to-dimensjonalet array'et
    Dette er for å få tilgang til selve rutenettet i instansen av klassen Rutenett.
    Vil bruke dette i Verden-klassen senere
    */

    public Celle[][] hentRutenett() {
        return rutene;
    }



    /*
    Metoden settNaboer får inn argument for rad og kol,
    bruker metoden hentCelle og tilordener en variabel med det celle-objektet.

    Lager deretter en array som inneholder alle mulige naboer den angitte
    cellen kan ha ved å bruke metoden hentCelle og sende inn alle mulige
    kombinasjoner som argumenter

    Kjører en forløkke som iterer gjennom arrayet
    og vil kalle på metoden leggTilNabo dersom naboen er innenfor rutenettet.
    Sjekker dette ved å ha en if-sjekk
    som sjekker at det ikke er returnert 'null'.
     */
    protected int row;
    protected int col;
    public void settNaboer(int rad, int kol) {
        row = rad;
        col = kol;

        Celle celle = hentCelle(row, col);

        Celle[] naboer = {hentCelle(row - 1, col - 1), hentCelle(row - 1, col),
                hentCelle(row - 1, col + 1), hentCelle(row, col - 1),
                hentCelle(row, col + 1), hentCelle(row + 1, col - 1),
                hentCelle(row + 1, col), hentCelle(row + 1, col + 1)};

        for (Celle celle1 : naboer) {
            if (celle1 != null) {
                celle.leggTilNabo(celle1);
            }
          }
        }

        /*
        Metoden kobleCeller går gjennom alle plassene i rutenettet
        og kaller metoden settNaboer som gir hver celle sine naboer
        */
        public void kobleAlleCeller () {
            for (int i = 0; i < rutene.length; i++) {
                for (int j = 0; j < rutene[i].length; j++) {
                    settNaboer(i, j);
                }
            }
        }

        /*
        Metoden antallLevende går gjennom alle celle-objekter i
        rutenettet og sjekker
        hvis cellen er i live så øker telleren med 1.
        Returnerer til slutt telleren som vil representere antall celler i live
         */

        public int antallLevende() {
        int teller = 0;
             for (Celle[] rad : rutene) {
                 for (Celle cell : rad) {
                     if (cell.erLevende()) {
                         teller++;
                     }
                 }
             }
             return teller;
        }



        // Trodde jeg kanskje ville trenge en hentAlleCeller metode, men løste det annerledes.

        /*Celle[] alleCeller;
        public Celle[] hentAlleCeller() {
            int teller = 0;
            int teller2 = 0;
            teller = antallLevende();
            alleCeller = new Celle[teller];
            for (int i = 0; i < rutene.length; i++) {
                for (int j = 0; j < rutene[i].length; j++) {
                    if (rutene[i][j] != null) {
                        alleCeller[teller2] = rutene[i][j];
                        teller2++;
                    }
                }

            }
            return alleCeller;
        }*/
}










    

