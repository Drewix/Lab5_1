import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class Main {
	public static void main(String[] args) {
		String user = "student01";
		String password = "student01";
		String host = "localhost";
		int port = 27017;
		String database = "database01";
		String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
		MongoClientURI uri = new MongoClientURI(clientURI);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase(database);
		db.getCollection("people").drop();
		
		MongoCollection<Document> kolekcja = db.getCollection("people");
		Operacje operacje = new Operacje();

		Scanner in = new Scanner(System.in);
		String wybor = "";

		while (wybor != "0") {
			boolean wyjscie = false;

			System.out.println("Proszę wybrac numer operacji:");
			System.out.println("0. Wyjście");
			System.out.println("1. Dodaj policjantów testowe (Dane testowe)");
			System.out.println("2. Dodaj konkretnego policjanta");
			System.out.println("3. Wypisz wszytskich policjantów");
			System.out.println("4. Zmiana wielkosći liter w nazwie policjantów");
			System.out.println("5. Wyświetl tylko Inspektorów i Komisarzy");
			System.out.println("6. Wyświetl tylko policjantów z numer odznaki z zakresu 500000-700000");
			System.out.println("7. Wyświetla konkretnego policjanta");
			System.out.println("8. Usunięcie wszytskich danych ");
			System.out.println("9. Edycja numeru odznaki");


			wybor = in.nextLine();

			switch (wybor) {
				case "1":
					kolekcja = operacje.zapiszDoBazy(kolekcja);
					break;
				case "2":
					kolekcja = operacje.dodawaniePolicjanta(kolekcja);
					break;
				case "3":
					operacje.wyswietlWszystko(kolekcja);
					break;
				case "4":
					kolekcja = operacje.ZmianaWielkosciLiter(kolekcja);
					break;
				case "5":
					operacje.wyswietlInspektorówIKomisarzy(kolekcja);
					break;
				case "6":
					operacje.wyswietlPolicjantowZNumerem(kolekcja);
					break;
				case "7":
					operacje.wyswietlKonkretnegoPolicjanta(kolekcja);
					break;
				case "8":
					operacje.usunWszystkieDane(kolekcja);
					break;
				case "9":
					operacje.Edycja(kolekcja);
					break;
				case "0":
					wyjscie = true;
					break;
				default:
					System.out.println("Zły znak");
			}
			if (wyjscie)
				break;
		}
		mongoClient.close();
	}
}