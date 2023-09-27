// Denne klassen bruker metodene fra modell-klassen og
// kommuniserer med GUI-klassen

class Kontroll {

    GUI gui;
    Modell modell;


    Kontroll () {
        // Oppretter en instans av klassen modell med 50 rader og kolonner som maks
        // Antall rader og kolonner vil bli bestemt av input saa lenge det ikke overskriver grensen paa 50X50
        modell = new Modell(51, 51);
        gui = new GUI(this);
    }

    void avsluttSpillet () {
        System.exit(0);
    }

    void oppdaterCeller() {
        modell.oppdaterCeller();
        gui.oppdaterGUIRutenett();
    }

    public Celle[][] hentRutenett() {
        return modell.hentRutenett();
    }

    public int hentAntall() {
        return modell.hentAntall();
    }

    public boolean celleErLevende(int r, int k) {
       return  modell.celleErLevende(r, k);
    }

    public void settLevende(int r, int k) {
        modell.settLevende(r, k);
    }

    public void settDoed(int r, int k) {
        modell.settDoed(r, k);
    }


    public void opprettModell(int r, int k) {
        modell = new Modell(r, k);
    }

    public void nullstill() {
        modell.nullstill();
        gui.oppdaterGUIRutenett();
    }

    public void fyllMedRandomCeller() {
        modell.fyllMedRandomCeller();
    }













}
