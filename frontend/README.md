# Fisa Cerintelor - Proiect_IP - Front_End

## Echipa, alfabetic:
    Calinescu Monica Georgiana
    Lupu Teodor
    Munteanu Silviu
    Pojar Raluca
    Popa Codrin
    Schifirnet Cosmin Ionut
    Ventaniuc Vladimir
		
## Descrierea task-ului

Crearea unei aplicatii pentru Android care va interpreta informatiile din back-end.

Aceasta aplicatie constituie front-end-ul proiectului.
Ea va face request-uri catre middle-end folosind protocoalele specificate de acesta in
vederea obtinerii de informatii ce vor fi interpretate si afisate.

Interfata va fi nu doar practica ci si estetica.

## Schita

O pagina de log-in / register.
Un meniu lateral pentru setari.
View-uri pentru alegerea tipurilor de notificari dorite.
View-uri pentru alegerea notificarilor personalizate.


                                                   | ------ Asigurarea comunicarii cu middle-end-ul
                                                   |
BACK-END ------- MIDDLE-END ------------ FRONT-END | ------ Interpretarea mesajelor primite de la middle-end
                              protocol             |
                                                   | ------ Afisarea informatiilor in mod corespunzator
                                                   |
                                                   | ------ Programarea notificarilor


## Actori
### User-ul
Utilizatorul care se va putea inregistra/loga in aplicatie, va putea alege tipurile de notificari
standard pe care si le doreste, si va putea seta notificari personalizate.

### Middle-end
Entitatea cu care se va interactiona pentru acces la serviciile oferite de back-end.
Se va folosi un protocol de comunicare stabilit de comun acord cu middle-end-ul.

### Aplicatia Android 
Va afisa notificarile corespunzatoare serviciului la care utilizatorii 
sunt abonati si le va oferi posibiltatea de a adauga notificari si memo-uri custom. 

### Baza de date
Va stoca informatiile pentru fiecare utilizator in parte, precum si
informatii utile pentru logare si inregistrare.


## Componente
### View-uri-le Android
Vor fi folosite de utilizator pentru alegerea tipurilor de notificari dorite din categoriile alese.
Vor exista view-uri pentru setari specifice aplicatiei, inregistrare si logare.

### Middle-End Message Sender
Se va ocupa de organizarea unui mesaj conform protocolului necesar si trimiterea mesajului 
catre modulul middle-end-ului ce se va ocupa de trimiterea acestuia catre Back-end.

### Middle-End Message Receiver
Se va ocupa de preluarea mesajului trimis de Middle-End si il va analiza in vederea afisarii notificarilor.
