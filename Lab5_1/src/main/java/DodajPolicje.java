import org.bson.Document;

public class DodajPolicje {

    Document dodaj(Long id, String policjant, int nr_odznaki) {
        return new Document("id", id)
                .append(Policja.policjant.toString(), policjant)
                .append(Policja.nr_odznaki.toString(), nr_odznaki);
    }
}
