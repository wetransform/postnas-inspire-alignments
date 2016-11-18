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


Einrichtung Gradle und hale-cli
-------------------------------

Gradle wird als Tool verwendet um Aufgaben bzgl. der Verwendung, Migration und Pflege der Alignments auszuführen.
Für das Ausführen von Gradle mit hale müssen folgende Voraussetzungen geschaffen werden:

- Internetverbindung (für Verwendung eines Proxy ist weitere Konfiguration nötig)
- **Java 8** muss auf dem System installiert sein (erreichbar über `PATH` Umgebungsvariable)
- Um eine bestimmte _hale-cli_ Version zu verwenden muss sie auf dem System verfügbar sein, der Pfad zur hale-cli-Executable muss in der Datei `gradle.properties` angegeben werden (siehe `gradle.properties.sample` für ein Beispiel). Wird keine Angabe gemacht werden die hale-cli-Bibliotheken für die Ausführung heruntergeladen.

Gradle selbst wird beim ersten Aufruf von `gradlew.bat` (Windows) bzw. `./gradlew` (Linux / Mac OS X) automatisch heruntergeladen. Folgend wird `gradlew` stellvertretend für den Aufruf im jeweiligen Betriebssystem verwendet.

### Proxy-Konfiguration

Gradle kann in der Datei `gradle.properties` für eine Verbindung über einen HTTP-Proxy konfiguriert werden.
Siehe auch die [entsprechende Gradle-Dokumentation](https://docs.gradle.org/current/userguide/build_environment.html#sec:accessing_the_web_via_a_proxy) oder die Beispiel-Datei `gradle.properties.sample`.

Die Einstellungen für den HTTP-Proxy für hale werden für die Transformation aus der Gradle-Konfiguration übernommen, sofern möglich.


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

Die auf die Datenbank migrierten Projekte werden ebenfalls im Ordner `projects` abgelegt und haben den Suffix `-db` im Dateinamen (z.B. `cp-db.halex` für Cadastral Parcels). Diese Projekte können dann zur Transformation aus dieser Datenbank heraus verwendet werden.


Pflege der Alignments
---------------------

TODO Beschreibung
