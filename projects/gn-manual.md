Notizen zu manuellen Anpassungen
--------------------------------

- Custom function *AX_Lagebezeichnung zu GeographicalName* angepasst um auf PostNAS-Schema operieren zu können
- Verwendung *AX_Lagebezeichnung zu GeographicalName* angepasst
- Bedingungen für Mappings angepasst, die sich auf Quell-Objekte mit AX_Lagebezeichnung als primärem Namen beziehen (zu `unverschluesselt is not null or lage is not null`)
- Skript für Index-Erstellung Lagebezeichnung angepasst (Attribut-Name geändert)
- Doppelt-Belegungen von Quellen für Eigenschaftsabbildungen entfernt (Im Kontext von Joins mit `ap_pto`, `ax_kommunalesgebiet` und `ax_gebiet_*`)
- Fehlerhaft vorhandene Bedingung zu `ax_kommunalesgebiet` entfernt (in der Folge auch im Original-Mapping entfernt)
- Zugriff auf Geometrie in Groovy Joins angepasst
