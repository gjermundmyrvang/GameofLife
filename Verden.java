public class Verden {

    protected int genNr;
    protected Rutenett rutenett;

    // Konstruktør:
    public Verden(int rad, int kol) {
        rutenett = new Rutenett(rad, kol);
        genNr = 0;
        // Bruker metoder fra Rutenett klassen til å fylle rutenettet med celler og koble de sammen
        rutenett.fyllMedTilfeldigeCeller();
        rutenett.kobleAlleCeller();
    }

    /*
    Bruker metoder fra Rutenett klassen til å skrive ut selve rutenettet samt gennr og antall levende celler.
     */
    public void tegn() {
        rutenett.tegnRutenett();
        System.out.print("Generasjonsnr: " + genNr + " - " + "Antall levende celler: " + rutenett.antallLevende());
        System.out.println();

    }

    /*
    I metoden oppdatering bruker jeg metoder fra Rutenett til å telle
    levende naboer for hver celle,
    går gjennom alle celler i rutenettet på nytt, og oppdaterer status
    på hver celle

    Oppdaterer også generasjonsnummeret.
     */

    public void oppdatering() {
        genNr++;
        for (int i = 0; i < rutenett.hentRutenett().length; i++) {
            for (int j = 0; j < rutenett.hentRutenett()[i].length; j++) {
                rutenett.hentRutenett()[i][j].tellLevendeNaboer();
            }

        }
        for (int i = 0; i < rutenett.hentRutenett().length; i++) {
            for (int j = 0; j < rutenett.hentRutenett()[i].length; j++) {
                rutenett.hentRutenett()[i][j].oppdaterStatus();
            }
        }
    }

    public Celle[][] hentRutenett() {
        return rutenett.hentRutenett();
    }

    public Rutenett rutenett() {
        return rutenett;
    }

    public int hentAntall() {
        return rutenett.antallLevende();
    }



}
