Notizen zu manuellen Anpassungen
--------------------------------

- Custom function *AX_Lagebezeichnung zu GeographicalName* angepasst um auf PostNAS-Schema operieren zu können
- Verwendung *AX_Lagebezeichnung zu GeographicalName* angepasst (für Hafenbecken und Stehendes Gewässer)
- Entfernen der Geometrie-Abbildung auf `LandWaterBoundary` (nicht ersetzt da diese Abbildung derzeit ohnehin deaktiviert ist)
- Mapping für `uom` der Höhe von `Falls` entfernt (da keine solche Information in der Quelle vorhanden). Mapping für Standardwert ist bereits aus dem Original-Alignment vorhanden
- Anpassung von Groovy Typ-Relationen mit korrigierten Typ- und Eigenschafts-Namen
