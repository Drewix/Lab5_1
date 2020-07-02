import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;


public class Operacje {

    final private static Random r = new Random(System.currentTimeMillis());

    MongoCollection<Document> zapiszDoBazy(MongoCollection<Document> kolekcja) {
        Document doc1;
        Long key1 = (long) Math.abs(r.nextInt());
        int key2 = (int) Math.abs(r.nextInt(899999));
        DodajPolicje dodajPolicje = new DodajPolicje();

        doc1 = dodajPolicje.dodaj(key1, "Posterunkowy", key2 + 100000);
        kolekcja.insertOne(doc1);
        System.out.println("PUT " + key1 + " => " + doc1.toJson());
        doc1.clear();
        key1 = (long) Math.abs(r.nextInt());
        key2 = (int) Math.abs(r.nextInt(899999));
        doc1 = dodajPolicje.dodaj(key1, "Aspirant", key2 + 100000);
        kolekcja.insertOne(doc1);
        System.out.println("PUT " + key1 + " => " + doc1.toJson());
        doc1.clear();
        key1 = (long) Math.abs(r.nextInt());
        key2 = (int) Math.abs(r.nextInt(899999));
        doc1 = dodajPolicje.dodaj(key1, "Podkomisarz", key2 + 100000);
        kolekcja.insertOne(doc1);
        System.out.println("PUT " + key1 + " => " + doc1.toJson());
        doc1.clear();
        key1 = (long) Math.abs(r.nextInt());
        key2 = (int) Math.abs(r.nextInt(899999));
        doc1 = dodajPolicje.dodaj(key1, "Komisarz", key2 + 100000);
        kolekcja.insertOne(doc1);
        System.out.println("PUT " + key1 + " => " + doc1.toJson());
        doc1.clear();
        key1 = (long) Math.abs(r.nextInt());
        key2 = (int) Math.abs(r.nextInt(899999));
        doc1 = dodajPolicje.dodaj(key1, "Inspektor", key2 + 100000);
        kolekcja.insertOne(doc1);
        System.out.println("PUT " + key1 + " => " + doc1.toJson());
        doc1.clear();
        key1 = (long) Math.abs(r.nextInt());
        key2 = (int) Math.abs(r.nextInt(899999));
        doc1 = dodajPolicje.dodaj(key1, "Przodownik", key2 + 100000);
        kolekcja.insertOne(doc1);
        System.out.println("PUT " + key1 + " => " + doc1.toJson());
        doc1.clear();
        return kolekcja;
    }

    MongoCollection<Document> dodawaniePolicjanta(MongoCollection<Document> kolekcja) {
        Document doc1;
        Long key1 = (long) Math.abs(r.nextInt());
        DodajPolicje dodajPolicje = new DodajPolicje();

        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj stopień Policjanta");
        String policjant = "";
        policjant = scan.nextLine();

        System.out.println("Podaj numer odznaki");
        int nr_odznaki;
        nr_odznaki = scan.nextInt();

        doc1 = dodajPolicje.dodaj(key1, policjant, nr_odznaki);
        kolekcja.insertOne(doc1);
        System.out.println("PUT " + key1 + " => " + doc1.toJson());
        doc1.clear();

        return kolekcja;
    }

    void wyswietlInspektorówIKomisarzy(MongoCollection<Document> kolekcja) {

        for (Document dok : kolekcja.find( or ( eq ("policjant", "Inspektor"),
                                                eq ("policjant", "Komisarz"))))
            System.out.println(dok.toJson());
    }

    void wyswietlPolicjantowZNumerem(MongoCollection<Document> kolekcja) {

        for (Document dok : kolekcja.find( and ( gte ("nr_odznaki", 500000),
                                                 lte ("nr_odznaki", 700000))))
            System.out.println(dok.toJson());
    }

    void wyswietlWszystko(MongoCollection<Document> kolekcja) {
        System.out.println("Wszyscy Policjanci: ");

        for (Document doc : kolekcja.find())
            System.out.println(doc.toJson());
    }

    public static void wyswietlKonkretnegoPolicjanta(MongoCollection<Document> kolekcja) {

        Scanner in = new Scanner(System.in);
        System.out.println("Podaj stopien policjanta którego chcesz wyszukać");
        String policjant = "";
        policjant = in.nextLine();

        for (Document doc : kolekcja.find( eq ( Policja.policjant.toString(), policjant))) {
            System.out.println(doc.toJson());
        }
    }


    MongoCollection<Document> przetworz(MongoCollection<Document> collection) {

        Scanner in = new Scanner(System.in);
        System.out.println("Podaj nazwę filmu którego chcesz przedłużyć");
        String nazwa = "";
        nazwa = in.nextLine();

        UpdateResult updateResult = collection.updateMany(eq("NAZWA", nazwa), inc("DLUGOSCSEANSU", 10));

        System.out.println("Liczba filmów o przedłużonym czasie seansu: " + updateResult.getModifiedCount());


        return collection;
    }

    MongoCollection<Document> ZmianaWielkosciLiter(MongoCollection<Document> kolekcja) {
        for (Document doc : kolekcja.find())
        {
            if(doc.get("policjant").toString().equals(doc.get("policjant").toString().toUpperCase())){
                kolekcja.updateOne(eq("id", doc.get("id")),
                                        new Document("$set",
                                                new Document("policjant", doc.get("policjant").toString().toLowerCase()
                                        )));
            } else{
                kolekcja.updateOne(eq("id", doc.get("id")),
                                        new Document("$set",
                                                new Document("policjant", doc.get("policjant").toString().toUpperCase()
                                        )));
            }
        }
        return kolekcja;
    }

    MongoCollection<Document> usunWszystkieDane(MongoCollection<Document> kolekcja) {
        kolekcja.deleteMany(exists("id"));
        return kolekcja;
    }

    MongoCollection<Document> Edycja(MongoCollection<Document> kolekcja) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Zmien odznake policjanta");
        System.out.println("Podaj numer starej odznaki policjanta");
        String stary_nr_odznaki = scan.nextLine();

        System.out.println("Podaj nowy numer odznaki");
        int nr_odznaki = 0;
        nr_odznaki = scan.nextInt();

        kolekcja.updateOne(eq("nr_odznaki", Long.parseLong(stary_nr_odznaki)),
                new Document("$set", new Document("nr_odznaki",  nr_odznaki )));

        return kolekcja;
    }


}
