public class Celle {
    protected boolean status;
    protected Celle[] naboer;
    protected int antLevendeNaboer;
    protected int antNaboer;

    /* Oppretter konstruktør for klassen Celle med status død som
    utgangspunkt, som en boolsk verdi setter jeg derfor til false.
    Instansvariablene naboer settes lik en array med åtte
    elementer, antNaboer og antLevendeNaboer initieres til 0. */

    public Celle() {
        status = false;
        naboer = new Celle[8];
        antLevendeNaboer = 0;
        antNaboer = 0;
    }

    public void settDoed() {
        status = false;
    }
    public void settLevende() {
        status = true;
    }
    public boolean erLevende() {
        if (status) {
            return true;
        }
        return false;
    }
    public String hentStatusTegn() {
        if (status) {
            return " O ";
        }
        // Bruker '-' istedenfor punktum (personlig preferanse)
        return " ";
    }

    // Looper gjennom array med naboer, plass som er 'null' vil få Celle-objekt 'nabo' som sendes som argument.
    // Øker int variabelen antNaboer.
    public void leggTilNabo(Celle nabo) {
        for (int i = 0; i < naboer.length; i++) {
            if (naboer[i] == null) {
                naboer[i] = nabo;
                antNaboer++;
                break;
            }

        }
    }

    /* Starter med å sette 'antLevendeNaboer' til 0
    fordi vil at når metoden blir kalt på skal den
    telle fra 0 uavhengig hvilken verdi den evt kan ha fra før
    */
    public void tellLevendeNaboer() {
        antLevendeNaboer = 0;
        for (int j = 0; j < naboer.length; j++) {
            if (naboer[j] != null) {
                if (naboer[j].erLevende()) {
                    antLevendeNaboer++;
                }
            }
        }
    }

    /*
    Implementerer reglene for game of life på cellene.
    Hvis cellen er i live og har mindre enn 2 eller fler enn 3 naboer så skal
    cellen settes doed ved å kalle på settDoed metoden

    Dersom cellen er doed, men har akkurat tre naboer settes cellen
    i live igjen ved å kalle på metoden settLevende
     */
    public void oppdaterStatus() {
        if (erLevende()) {
            if (antLevendeNaboer < 2 || antLevendeNaboer > 3) {
                settDoed();
            }
        }
        else {
            if (antLevendeNaboer == 3) {
                settLevende();
            }
        }
    }

}
