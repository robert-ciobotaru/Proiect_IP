@@ -0,0 +1,98 @@
Fisa Cerintelor, Proiect_IP - Back_End

Membrii Echipei:
	-Leon Daniel
	-Andonesei Samuel
	-Roman Nicusor Alexandru
	-Nastasa Doru Alexandru
	-Olarasu Loredana Mihaela
	-Matei Bogdan
	
Descrierea task-ului:
	Un RESTFul service care stocheaza datele utilizatorilor,
notificarile pe care acestia doresc sa le primeasca,
proceseaza datele primite trimitand utilizatorului
instiintarile dorite.

Actori:
    NewsCrawler    \
    HazzardCrawler  <-----------> CrawlerProxy   \
    WeatherCrawler /                    |         \
                                        |          \
                            Database <->            <-> Central <---> (Middle_End)												
                                        |          /
                                        |         /
                                 DataBaseManager /
	
Central
	Serviciul cu care partea de
Middle_End va comunica, el se ocupa
cu trimiterea datelor despre utilizatori
spre Database, prin intermediul Manager-ului.
Primirea notificarilor pe care DataBaseManager observa
ca au expirat(trebuiesc trimise), sau pe care CrawlerProxy
le genereaza, si trimiterea lor spre Middle_End.
	Input:	
		Modificari asupra bazei de date(primite de la Middle_End)
		Notificari in format .json primite de la CrawlerProxy
		Notificari in format [ID][String] de la DatabaseManager
	Output:
		Notificarile specificate mai sus, trimise mai departe spre Middle_End
		Modificarile primite de la Middle_End spre DataBaseManager.
		
	
DataBaseManager
	Inserts/updates in baza de date,si un background service care:
		-face verificari constante in baza de date, cauta inregistrarile
	pentru care informatia din coloanele in care sunt stocate ora si frecventa unei notificari se 
	potrivesc cu momentul actual, selecteaza id-urile utilizatorilor
	carora le corespund inregistrarile, si descrierea specifica fiecarei notificari si le trimite spre Central.
	Asta intamplandu-se o data la minut ,astfel nu se poate omite nicio inregistrare,acestea fiind 
	memorate in format ora:minut

	Input:
		Comenzi sql asupra bazei de date  [si datele care sa fie introduse]	
	
	Output:
		Confirmari de update/insert/delete sau posibile descrieri de erori ce au intervenit in urma 
	unor astfel de cereri;
		Bulk-ri de id-uri de utilizatori si descrieri de notificari
	
Database
	O baza de date MySql cu 3 tabele,
	Users - contine datele utilizatorilor
	Notifications - notificarile care utilizatorii doresc sa le primeasca
	User_Notification - un tabel care face legatura intre useri si notificarile	
		in ideea in care o notificare poate fi dorita de mai multi utilizatori,
		si utilizatorii pot avea mai multe notificari.Relatie Many-to-many solved.


NetCrawlers
	Diverse servicii custom despre care utilizatorul
poate sa aleaga sa fie instiintat.Fiecare din ele
avand o lista de alte restFull services disponibile
online.
	WeatherCrawler - multiple servicii gasite la adresa https://openweathermap.org/api, plus altele
	NewsCrawler - https://newsapi.org/reddit-r-all-api, plus altele(wall street journal/cnn/fox news)
	HazzardCrawler - https://earthquake.usgs.gov/fdsnws/event/1/, si alte hazarde posibile (incendii/etc.)
	
	Fiecare dintre servicii va cauta periodic(o data pe ora) printre clientii alesi
	date relevante la campul lor de informare, si va trimite spre Proxy, in format .json
	informatiile pe care le-a procurat.
	
	Input:
		Raspunsul primite de la Proxy, care v-a parsa .json-ul trimis, si va confirma corectitudinea sa.
	Output:
		Un .json care sa contina datele parsate.
		

CrawlerProxy
	Serviciul care se ocupa cu parsarea si validarea fisierelor primite de la NetCrawlers,
parcurgerea bazei de date, obtinand lista de utilizatori ce au specificat ca doresc sa fie
instiintati de Crawler-ul respectiv, si trimiterea fisierelor spre Central.

	Input:
		.json files
	Output:
		Boolean .json validation
		.json files care sa contina lista de utilizatori care trebuie sa fie instiintati
\ No newline at end of file
