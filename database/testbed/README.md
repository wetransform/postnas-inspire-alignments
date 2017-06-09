Testumgebung für SQL Schema Import und Export
=============================================

*Docker Compose* setup zum Ausführen einer Test-Datenbank mit dem PostNAS schema.
Es kann verwendet werden um sich mit hale mit der Datenbank zu verbinden und die Hale Schema Definition Datei zu erstellen die für die Generierung des Datenbank-Matchings verwendet wird.

Voraussetzung für die Verwendung ist die Installation von *Docker* und *Docker Compose*.

Starten der Datenbank:

```
docker-compose up
```

Die Datenbank sollte dann mit den folgenden Verbindungsinformationen erreichbar sein:

* Port: 15432 (erreichbar auf dem Docker Host)
* Datenbank: testbed
* Benutzer: postgres
* Passwort: postgres


Datenbank-Schema
----------------

Das Datenbank-Schema ist im Ordner `database/sql/` abgelegt. Alle `.sql` oder `.sql.gz` Dateien werden beim ersten Start der Datenbank importiert.

Falls Änderungen vorgenommen werden (Dateien hinzugefügt/entfernt/geändert werden) müssen folgende Kommandos ausgeführt werden um die Änderungen zu übernehmen. Danach kann die Datenbank wie oben angegeben wieder gestartet werden.

```
docker-compose rm -v
docker-compose build
```


Hale Schema Definition
----------------------

Um unnötige Änderungen in der Hale Schema Definition zu vermeiden sollte für den Export sichergestellt werden, dass Datenbank-Name (`testbed`) und der Name des Schemas (`aaa_ogr`) beibehalten werden, wenn das Schema aktualisiert wird oder die Erzeugung der Hale Schema Definition auf einer anderen Datenbank erfolgt as der hier angegebenen.

Um die Hale Schema Definition zu erzeugen müssen folgende Schritte getan werden:

1. hale studio mit neuem Projekt starten
2. Datenbank-Schema als Quell-Schema importieren (nur `aaa_ogr` Schema)
3. Quell-Schema als Hale Schema Definition (`.hsd`) exportieren und `schema.hsd` ersetzen
