INSPIRE Alignments LVermGeo RLP
===============================

Verwaltung der hale Alignments für LVermGeo RLP die aus den entsprechenden AdV-Alignments abgeleitet wurden.

Die Ableitung erfolgt teils automatisch (`projects/*-auto.halex`) und wird durch manuelle Anpassungen ergänzt (`projects/*-manual.halex`). Um die manuellen Anpassungen nachvollziehen zu können unterliegen auch die generierten Projekte der Versionskontrolle.

Folgende Alignments werden hier verwaltet:

| Alignment                                             | Status     |
| :---------------------------------------------------- | :--------: |
| Geo5 Hauskoordinaten -> Adressen                      |            |
| Geo5 PostNAS -> Adressen                              |            |
| Geo5 PostNAS -> Verwaltungseinheiten                  |            |
| Geo5 PostNAS -> Flurstücke/Grundstücke                |            |
| Geo5 PostNAS -> Geografische Bezeichnungen            |            |
| Geo5 PostNAS -> Gewässernetz                          |            |
| Geo5 PostNAS -> Verkehrsnetze (Straßenverkehrsnetz)   |            |
| Geo5 PostNAS -> Verkehrsnetze (Schienenverkehrsnetz)  |            |
| Geo5 PostNAS -> Verkehrsnetze (Wasserverkehrsnetz)    |            |
| Geo5 PostNAS -> Verkehrsnetze (Luftverkehrsnetz)      |            |


Verwendung der Alignments
-------------------------

Für die Ausführung der Alignments gelten ähnliche Maßgaben wie für die AdV-Alignments:

- Projekt-Variablen für Modellart und INSPIRE Namespace müssen für die Transformation gesetzt werden
- Es muss ggf. ein Filter nach Modellart für die Transformation angewandt werden

Die hier verwalteten Projekte verwenden nicht direkt ein Datenbankschema um die Verwendung ohne Datenbankzugriff zu erleichtern. Stattdessen ist eine hale Schema-Datei (.hsd) eingebunden die aus dem PostNAS Datenbank-Schema erzeugt wurde.

Für die Verwendung mit einer Datenbank muss das Projekt an die Datenbank angepasst werden, da das Alignment Namespace-Informationen enhtält die den Namen der Datenbank und den Namen des Datenbank-Schemas enthalten.
Das erfolgt über die Tasks `db-migrate-*` (z.B. `db-migrate-cp` für *Cadastral Parcels*).
Dazu muss die Möglichkeit bestehen zu der Datenbank zu verbinden und die nötigen Verbindungsinformationen müssen in der Datei `gradle.properties` hinterlegt werden (siehe auch `gradle.properties.sample`):

```
db.uri=jdbc:postgresql://localhost:5432/testbed
db.user=postgres
db.password=postgres
db.schemas=aaa_ogr
```

Die auf die Datenbank migrierten Projekte werden ebenfalls im Ordner `projects` abgelegt und haben den Suffix `-db` im Dateinamen. Diese Projekte können dann zur Transformation aus dieser Datenbank heraus verwendet werden.


Pflege der Alignments
---------------------

TODO Beschreibung
