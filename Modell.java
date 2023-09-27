
// Denne klassen oppretter en instans av verden-klassen og bruker metodene fra den klassen

class Modell {

    Verden verden;

    Modell(int r, int k) {
        verden = new Verden(r, k);
    }

    public void oppdaterCeller() {
        verden.oppdatering();
    }

    public Celle[][] hentRutenett() {
        return verden.hentRutenett();
    }

    public int hentAntall() {
        return verden.hentAntall();
    }

    public boolean celleErLevende(int r, int k) {
        Celle c = verden.rutenett.hentCelle(r, k);
        return c.erLevende();
    }

    public void settLevende(int r, int k) {
        Celle c = verden.rutenett.hentCelle(r, k);
        c.settLevende();
    }

    public void settDoed(int r, int k) {
        Celle c = verden.rutenett.hentCelle(r, k);
        c.settDoed();
    }

    public void nullstill() {
        for (int i = 0; i < verden.rutenett().hentRutenett().length; i++) {
            for (int j = 0; j < verden.rutenett().hentRutenett()[i].length; j++) {
                verden.rutenett().hentRutenett()[i][j].settDoed();
            }
        }
    }

    public void fyllMedRandomCeller() {
        verden.rutenett().fyllMedTilfeldigeCeller();
        verden.rutenett().kobleAlleCeller();
    }



















}
