Notizen zu manuellen Anpassungen
--------------------------------

- Custom function *AX_Lagebezeichnung zu GeographicalName* angepasst um auf PostNAS-Schema operieren zu können
- Verwendung *AX_Lagebezeichnung zu GeographicalName* angepasst (für `AX_Platz`, `AX_Strassenverkehr`, `AX_Weg`)
- Bedingungen mit `internationaleBedeutung` angepasst (zu `internationalebedeutung`; auch in Join-Konfiguration)
- Anpassung von Groovy Typ-Relationen mit korrigierten Typ- und Eigenschafts-Namen
- Explizite Behandlung von Codes als Strings in Groovy Typ-Relationen (um Logik kompatibel zu halten)
- Entfernen von mehrfach vorkommenden identischen Relationen
- `FormattedString` mit Quelle `gml_id` auf geänderten Namen der Quelle angepasst
