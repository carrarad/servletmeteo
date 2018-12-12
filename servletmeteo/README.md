

**Exercise-Servlet-Weather-API**

Esempi di richiesta:

 - localhost:8080/?format=xml&city=bergamo&forecast=4  
 - localhost:8080/?format=json&city=brescia&forecast=2
 
Note:
 
 - Il capo city dev'essere specificato
 - Il campo forecast deve avere un valore numerico compreso tra 0 e 5
 - Nel caso il valore di forecast sia 0 verra fornita solo la situazione meteo del giorno corrente
 
 TODO:
 
 - Gestire le situazioni di errore, aggiungere una risposta nel caso qualcosa vada storto, aggiungere uno status code alla risposta (es: 200 OK)

