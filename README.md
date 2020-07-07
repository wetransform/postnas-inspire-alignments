PostNAS INSPIRE Alignments
==========================

Aus den AdV-Alignments abgeleitete Alignments (PostNAS nach INSPIRE)
Herkunft: Komplementärbeauftragung Rheinland-Pfalz

Die Ableitung erfolgt teils automatisch (`projects/*-auto.halex`) und wird durch manuelle Anpassungen ergänzt (`projects/*-manual.halex`). Um die manuellen Anpassungen nachvollziehen zu können unterliegen auch die generierten Projekte der Versionskontrolle.

Folgende Alignments werden hier verwaltet:

| Alignment                                             | Status         | Kürzel     |
| :---------------------------------------------------- | :------------: | :--------- |
| PostNAS -> Adressen                                   | Migriert       | ad         |
| PostNAS -> Verwaltungseinheiten                       | Migriert       | au         |
| PostNAS -> Flurstücke/Grundstücke                     | Migriert       | cp         |
| PostNAS -> Geografische Bezeichnungen                 | Migriert       | gn         |
| PostNAS -> Gewässernetz                               | Migriert       | hy-n, hy-p |
| PostNAS -> Verkehrsnetze (Straßenverkehrsnetz)        | Migriert       | tn-ro      |
| PostNAS -> Verkehrsnetze (Schienenverkehrsnetz)       | Migriert       | tn-ra      |
| PostNAS -> Verkehrsnetze (Wasserverkehrsnetz)         | Migriert       | tn-w       |
| PostNAS -> Verkehrsnetze (Luftverkehrsnetz)           | Update nötig   | tn-a       |
| PostNAS -> Verkehrsnetze (Kabel)                      | Update nötig   | tn-c       |


Einrichtung Gradle und hale-cli
-------------------------------

Gradle wird als Tool verwendet um Aufgaben bzgl. der Verwendung, Migration und Pflege der Alignments auszuführen.
Für das Ausführen von Gradle mit hale müssen folgende Voraussetzungen geschaffen werden:

- Internetverbindung (für Verwendung eines Proxy ist weitere Konfiguration nötig)
- **Java 8** muss auf dem System installiert sein (erreichbar über `PATH` Umgebungsvariable)
- *(Optional)* Um eine bestimmte _hale-cli_ Version zu verwenden muss sie auf dem System verfügbar sein, der Pfad zur hale-cli-Executable muss in der Datei `gradle.properties` angegeben werden (siehe `gradle.properties.sample` für ein Beispiel). Wird keine Angabe gemacht werden die hale-cli-Bibliotheken für die Ausführung heruntergeladen.

Gradle selbst wird beim ersten Aufruf von `gradlew.bat` (Windows) bzw. `./gradlew` (Linux / Mac OS X) automatisch heruntergeladen. Folgend wird `gradlew` stellvertretend für den Aufruf im jeweiligen Betriebssystem verwendet.

### Proxy-Konfiguration

Gradle kann in der Datei `gradle.properties` für eine Verbindung über einen HTTP-Proxy konfiguriert werden.
Siehe auch die [entsprechende Gradle-Dokumentation](https://docs.gradle.org/current/userguide/build_environment.html#sec:accessing_the_web_via_a_proxy) oder die Beispiel-Datei `gradle.properties.sample`.

Die Einstellungen für den HTTP-Proxy für hale werden für die Transformation aus der Gradle-Konfiguration übernommen, sofern möglich.

### AdV Alignments über Git Submodul einbinden

In diesem Git-Repository ist ein Git-Submodul für das Einbinden der AdV Alignments für den Ordner `adv-alignments/` konfiguriert. Allerdings wird dazu Zugriff auf das entsprechende Git-Repository der AdV benötigt. Falls dieser nicht vorhanden ist kann auch ein Download des Repositorys verwendet werden.

Um das Submodul erstmalig zu initialisieren kann folgender Befehl verwendet werden:

```
git submodule update --init
```

Damit wird der Ordner `adv-alignments` erstellt und mit der zuletzt im Zusammenhang mit den PostNAS Alignments verwendeten Version der AdV Alignments initialisiert.

Wenn der aktuelle Stand der PostNAS Alignments (z.B. mit `git pull`) vom Server geholt wird, sollte auch das Submodul mit `git submodule update` aktualisiert werden.

Wenn auf den aktuellen Stand der AdV Alignments aktualisiert werden soll, muss in den Ordner des Submodules (`adv-alignments/`) gewechselt werden. Dort kann das Submodul mit den folgenden Ḱommandos auf den aktuellen Stand gebracht werden:

```
git checkout master
git pull
```

Um ein aktualisiertes Submodul im PostNAS Repository zu speichern, muss die Änderung auf Ebene des PostNAS Repositories (also nicht mehr im Unterordner) einem Commit hinzugefügt werden. Also beispielsweise mit den folgenden Kommandos:

```
cd .. # um ggf. in den Root-Ordner zu wechseln
git add adv-alignments
git commit
```


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

Die Pflege der Alignments und Aktualisierung auf Basis der AdV-Alignments hat einen automatischen und einen manuellen Teil. Der automatische Prozess basiert auf einem Matching zwischen AdV XML Schemas und dem PostNAS Datenbank-Schema.

Basierend auf diesem Matching werden die AdV-Alignments soweit möglich automatisch auf das Datenbank-Schema migriert. Anschließend können manuelle Anpassungen erfolgen. Manuelle Anpassungen die zuvor bereits erfolgt waren werden versucht zu übertragen.

Als Grundlage wird ein Verweis auf die AdV Alignments benötigt. Standardmäßig wird davon ausgegangen das sie im Ordner `adv-alignments` zu finden sind.

In diesem Git-Repository ist dazu ein Git-Submodul konfiguriert das auf das Repository mit den AdV Alignments verweist. Allerdings wird dazu Zugriff auf das entsprechende Git-Repository der AdV benötigt. Falls dieser nicht vorhanden ist kann auch ein Download des Repositorys verwendet werden.

Es kann jedoch auch ein anderer Ort über die Gradle-Property `advAlignments` angegeben werden (siehe `gradle.properties.sample`). Der Pfad sollte auf das Basis-Verzeichnis des AdV Repositories zeigen, dort werden nicht nur Alignments, sondern auch das AAA-Schema nachgeschlagen.

Außerdem wird für das Management der Änderungen zwischen automatisch migriertem Mapping und manuell gepflegtem Projekt das Programm `git` verwendet. Dieses wird im System-Pfad erwartet. Es wird verwendet um sog. Diffs zu erzeugen und anzuwenden.

Es wird empfohlen nach abgeschlossenen Arbeitsschritten immer einen Git Commit zu erzeugen. Damit wird sichergestellt das nichts verloren geht, Änderungen dokumentiert sind und die nachfolgenden Änderungen durch automatische Arbeitsschritte klar ersichtlich sind.


### AAA-PostNAS Matching

Das Matching zwischen AdV XML Schemas und PostNAS Datenbank Schema erfolgt auf Basis einer hinterlegten hale Schema-Datei (`database/schema.hsd`). Diese muss aktualisiert werden falls sich das Datenbank-Schema ändert. Hintergrund dass eine solche Datei verwendet wird ist, das so einerseits keine entsprechende Datenbank verfügbar sein muss und andererseits genau dokumentiert ist auf welches Schema sich der aktuelle Stand der Migration bezieht.

Die Schema-Datei kann erzeugt werden, indem das Datenbank-Schema in hale importiert wird, und von dort in eine Datei exportiert (siehe auch [database/testbed/README.md](database/testbed/README.md)).

Der Prozess um das Matching zu erzeugen enthält spezifische Annahmen über beide Schemata, ist also nicht auf ohne weiteres auf andere Schemata zu übertragen. Z.B. werden die Informationen, die im PostNAS-Schema in Kommentaren hinterlegt sind, ausgewertet.

Das Matching kann mit dem Gradle-Task `dbMatching` angestoßen werden, z.B.:

```
./gradlew dbMatching
```

Dies erzeugt bzw. aktualisiert das Projekt `database/matching.halex`. Diese Ressource ist dazu gedacht, wie auch das Schema selbst, mit der Versionskontrolle zu unterliegen und entsprechend nur aktualisiert zu werden wenn sich eines der Schemata oder der Matching-Prozess selbst geändert hat.


### Automatische Migration

In der Datei `migrations.json` sind alle zu migrierenden Projekte konfiguriert. Die Konfiguration eines Projekts erfolgt unter einem bestimmten Kürzel, z.B. `cp` für das Cadastral Parcels Projekt.

Die automatische Migration eines Projekts wird über den Task `migrate-<Kürzel>` ausgeführt, z.B. `migrate-cp`.

Dort wird zuerst eine Diff-Datei erzeugt, zwischen einer eventuell schon existierenden manuellen Anpassung und der zuvor automatisch generierten Version.

Dann erfolgt die automatische Migration anhand des Matchings. Das Ergebnis ist das Projekt `projects/<Kürzel>-auto.halex`.

Das automatisch erzeugte Projekt wird kopiert und ersetzt die bisherige manuelle Anpassung.
Danach wird versucht das Diff erneut anzuwenden. Mit etwas Glück ist es zumindest teilweise erfolgreich, andernfalls muss dieser Schritt manuell geschehen. Bei einem Fehlschlag bitte genau prüfen welche Änderungen ggf. nicht übernommen wurden und wie das ggf. zu beheben ist.

Bei fehlgeschlagener Anwendung des Diff auf das Projekt mit den manuellen Anpassungen erhält man eine Ausgabe ähnlich der folgenden:

```
Hunk #1 applied cleanly.
Rejected hunk #2.
Hunk #3 applied cleanly.
ERROR applying diff to update manually curated project.
Please check if you can apply the changes manually.
Please check any .rej files for rejected hunks.
Be aware that the manually created project has been replaced by the generated version.
```

Hier bietet es sich an zu prüfen (z.B. mit `git diff <datei-name>`) welche Änderungen es in der automatisch generierten Alignment-Datei gab und welche Änderungen sich in der manuell angepassten Alignment-Datei ergeben haben.
Zusätzlich sollte auf jeden Fall die `.rej`-Datei geprüft werden, denn diese enthält die manuellen Anpassungen die nicht automatisch übernommen werden konnten.

Auch bei erfolgreicher Anwendung des Diffs sollte geprüft werden, ob eventuell neu hinzugekommene oder angepasste Mappings im Kontext des angepassten Quell-Schemas noch gültig sind.


### Manuelle Anpassung

Durch den automatischen Migrationsprozess wird ein Projekt `projects/<Kürzel>-manual.halex` erzeugt.
Dort sollten manuelle Anpassungen erfolgen und ebenfalls die ggf. manuell nötige Aktualisierung anhand des Diff bei Aktualisierung durch die automatische Migration.

Anpassungen sind normalerweise:

- Anpassung von Groovy Skripts an geänderte Attributnamen und Strukturen
- Anpassung von Bedingungen an geänderte Attributnamen und Strukturen
- Anpassung von Custom Functions an geänderte Strukturen
- Entfernen von nicht anwendbaren Mappings, ggf. ersetzen
- Erweiterte Anpassungen durch Strukturänderungen erforderliche Anpassung von Typ-Relationen
- Anpassung der Behandlung von Codes in Groovy Skripts, für die sich der Datentyp geändert hat (z.B. Zahlentyp statt String)
- Entfernen von mehrfach vorkommenden identischen Relationen (normalerweise im Kontext von Joins)

Manuell erfolgte Anpassungen sollten zur möglicherweise später notwendigen Reproduktion dokumentiert werden. Dies erfolgt in den Dateien `projects/<Kürzel>-manual.md`.

Nach erfolgter manueller Anpassung sollten die Änderungen in einem Commit abgelegt werden. Zur Kontrolle empfiehlt es sich dann die automatische Migration noch einmal anzustoßen. Diese sollte erfolgreich sein und keine Änderungen an der manuell angepassten vornehmen. Außerdem wird hier ein aktuelles Diff erzeugt das die manuellen Änderungen repräsentiert. Diese kann dem zuvor erzeugten Commit hinzugefügt werden.


Transformation
--------------

Die Transformation von Daten basierend auf den hier verwalteten Projekten kann ebenfalls über Gradle ausgeführt werden.

Die Datei `transformations.yaml` enthält die Basis-Konfiguration für die Transformations-Tasks.

Die Konfiguration enthält alle Projekte, sowie generelle Einstellungen zur Validierung.
Anpassungen an der Konfiguration (z.B. Deaktivierung von Projekten oder individuelle Projekt-Variablen) können in separaten Konfigurations-Dateien vorgenommen werden (siehe nächster Abschnitt).

Muster für die Konfiguration von Transformations-Tasks:

```yaml
tasks:
  ad: # Task-ID
    project: ad # Referenz auf Projekt über Kürzel
    model: DLKM # Modellart
    additionalModels: # optionale weitere Modellarten deren Objekte akzeptiert werden
      - DKKM1000
    enabled: true # Gradle-Tasks zu dieser Task-ID anbieten?
    variables: # Projekt-Variablen
      INSPIRE_NAMESPACE: https://registry.gdi-de.org/id/REPLACEME
    # Konfiguration des GML-Writers (Transformation und Split)
    writer:
      # Der standardmäßig verwendete hale-Writer kann überschrieben werden (nicht empfohlen)
      provider: eu.esdihumboldt.hale.io.wfs.fc.write-2.0
      # Einstellungen des Writers können überschrieben oder zusätzlich angegeben werden
      settings:
        xml.pretty: false # z.B. kein Pretty printing für XML
    # Konfiguration für Validierung
    validation:
      xmlSchema: true # Soll XML Schema Validierung ausgeführt werden?
      haleInternal: true # Soll die hale-interne Validierung ausgeführt werden?
    # Konfigurationen für Prozessierung
    split: # Konfiguration für Aufteilung in mehrere Dateien
      enabled: false # Aufteilen aktiviert?
      mode: related # Aufteilungs-Modus
      instances: 1000 # Wie viele Objekte sollen in eine Datei (Umsetzung je nach Modus)
    # Konfiguration für Upload
    upload:
      enabled: true # Upload aktiviert?
      url: http://localhost:8080/services/wfs # Adresse des WFS-T

  # ... weitere Tasks

# Standard-Einstellungen für alle Transformationen
defaults:
  # z.B. um Projekte standardmäßig zu deaktivieren
  enabled: false
```

Das verwendete Transformations-Projekt ist das Projekt das aus den `db-migrate-*`-Tasks entsteht.
Für die Quell-Daten wird die dafür konfigurierte Datenbankanbindung herangezogen.


### Benutzerdefinierte Konfiguration

Die allgemeine Konfiguration in `transformations.yaml` kann durch benutzerdefinierte Konfigurations-Dateien überschrieben werden.

Eine Möglichkeit ist die Verwendung eines sogenannten Profils, in dem die Konfiguration angepasst wird und das mit in das Repository eingecheckt werden kann.

Zur Verwendung eines Profils muss ein entsprechender Unterordner im Ordner `profiles` erstellt werden. Der Name des Profils ist der Name des Unterordners.
Eine im Profil-Ordner abgelegte Datei `transformations.yaml` überschreibt Konfigurations-Optionen aus der allgemeinen Konfiguration.

Um ein Profil zu verwenden, muss das Profil über die Gradle Property `profile` angegeben werden. Diese kann z.B. in der Datei `gradle.properties` hinterlegt werden (siehe auch `gradle.properties.sample`):

```
profile=hamburg
```

Wenn nur wenige Konfigurations-Optionen für die lokale Ausführung überschrieben werden sollen, kann die Datei `transformations-override.yaml` verwendet werden. Diese wird nicht mit in das Repository eingecheckt und überschreibt Konfiguration aus allgemeiner Konfiguration und dem ggf. angegebenen Profil.


### Stufen der Transformations-Pipeline

Der Transformations-Ablauf ist in verschiedene Stufen unterteilt:

1. `transform` - die eigentlich Transformation der Daten
2. `validate` - die Auswertung der Transformationsergebnisse und Validierung der Daten
3. `process` - Nachprozessierung des Transformationsergebnisses (optional)
4. `upload` - Upload der Daten über eine WFS-T Schnittstelle (optional)

Die Stufen lassen sich einzeln ausführen, oder als komplette "Pipeline".

Der komplette Ablauf lässt sich über die `run-<Task-ID>`-Tasks starten. Dabei wird der aktuelle Stand zurück gesetzt und beginnend mit der Transformation neu gestartet.

Beim Ausführen der einzelnen Stufen (*stages*) wird die letzte erfolgreich abgeschlossene Stufe als Zustand in einer Datei gespeichert. Diese Information kann verwendet werden um den Ablauf an der Stelle, an der ein Problem aufgetreten ist, wieder aufzugreifen.
Dazu dienen die `continue-<Task-ID>`-Tasks. Schlägt beispielweise der Upload eines Transformationsergebnisses in der **upload**-Stufe fehl, wird `continue-*` beim nächsten Aufruf wieder den Upload versuchen, ohne z.B. die Transformation oder Validierung neu laufen zu lassen.

Der Status wird in der Datei `state` je Transformation im Verzeichnis `results/<Task-ID>` abgelegt.
Es ist eine einfache Text-Datei die als einzigen Inhalt den Zustand enthält:

- `start` - keine Transformation wurd ausgeführt oder der Ablauf wurde zurückgesetzt
- `transformed` - die Transformation wurde abgeschlossen
- `validated` - die Validierung des Transformations-Ergebnisses war erfolgreich
- `processed` - die Prozessierung des Transformations-Ergebnisses war erfolgreich
- `uploaded` - der Upload der (prozessierten) Daten war erfolgreich

Um die verschiedenen Stufen individuell auszuführen können die folgenden Tasks verwendet werden:

- `transform-<Task-ID>` - setzt die Pipeline zurück und führt die Transformation aus (Voraussetzung: keine; Erfolgs-Status: `transformed`)
- `validate-<Task-ID>` - wertet das Transformationsergebnis aus und validiert die Daten (Voraussetzung: `transformed`; Erfolgs-Status: `validated`)
- `process-<Task-ID>` - prozessiert das Transformationergebnis, aktuell nur Aufteilen in mehrere Dateien unterstützt (Voraussetzung: `validated`; Erfolgsstatus: `processed`)
- `upload-<Task-ID>` - lädt (prozessiertes) Transformationergebnisin über eine WFS-T Schnitstelle hoch (Voraussetzung: `validated`; Erfolgsstatus: `uploaded`)

### Alle Transformationen ausführen

Um alle Transformationen auszuführen kann der Task `runAll` verwendet werden.
Dieser versucht den Transformations-Ablauf für alle Projekte neu auszuführen.
Schlägt der Ablauf für ein Projekt fehl, werden trotzdem die anderen danach auch noch ausgeführt.

Mit dem Task `continueAll` wird versucht alle Transformations-Abläufe die nicht bis zum Ende ausgeführt wurden fortzusetzen.
Hier wird aber beim ersten Fehler unterbrochen.

Wenn man `continueAll` ohne Fehlerunterbrechung für alle Transformationen starten will muss man den Parameter `--continue` and Gradle übergeben, also z.B.:

```
gradlew --continue continueAll
```

### Fehler-Analyse

Im Verzeichnis `logs/` werden zu jeder Ausführung eines Build (Unterverzeichnis mit Start-Zeit) eine Datei `summary.yaml` erzeugt die Auskunft über die ausgeführten Tasks und deren Erfolg oder Fehlschlag gibt.

Damit lässt sich identifizieren, welche Tasks fehlgeschlagen sind aber keine genau Fehlerursache feststellen. Zu diesem Zweck sind in den Ordnern der verschiedenen Stufen des Prozesses üblicherweise Log- und/oder Fehler-Dateien vorhanden.
Diese Ordner sind gruppiert nach der Task ID im Ordner `results/` zu finden.
Dort werden ebenfalls die Ergebnisse aus den verschiedenen Stufen abgelegt.
