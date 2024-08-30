Notizen zu manuellen Anpassungen
--------------------------------

- Custom function *AX_Lagebezeichnung zu GeographicalName* angepasst um auf PostNAS-Schema operieren zu können
- Verwendung *AX_Lagebezeichnung zu GeographicalName* angepasst
- Bedingungen für Mappings angepasst, die sich auf Quell-Objekte mit AX_Lagebezeichnung als primärem Namen beziehen
- Skript für Index-Erstellung Lagebezeichnung angepasst (Attribut-Name geändert)
- Doppelt-Belegungen von Quellen für Eigenschaftsabbildungen entfernt (Im Kontext von Joins mit `ap_pto`, `ax_kommunalesgebiet` und `ax_gebiet_*`)
- Zugriff auf Geometrie in Groovy-Skripten angepasst
- Zuweisung von `datumderletztenueberpruefung` erweitert, so dass Quellwert als Datum geparst wird
- Zugriff auf Eigenschaft `WKT` in Groovy-Skript der Abbildung von `extent_bundesland` angepasst