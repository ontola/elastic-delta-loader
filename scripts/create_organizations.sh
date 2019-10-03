
kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://id.openraadsinformatie.nl/10> <http://schema.org/name> "Alkmaar" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/10> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://example.com/10> <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/10> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/org#Organization> <http://purl.org/link-lib/supplant> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://id.openraadsinformatie.nl/11> <http://schema.org/name> "Amsterdam" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/11> <http://schema.org/description> "Gemeente Amsterdam" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/11> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://id.openraadsinformatie.nl/11> <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/11> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/org#Organization> <http://purl.org/link-lib/supplant> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://id.openraadsinformatie.nl/12> <http://schema.org/name> "Almere" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/12> <http://schema.org/description> "Gemeente Almere" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/12> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://id.openraadsinformatie.nl/12> <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/12> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/org#Organization> <http://purl.org/link-lib/supplant> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://id.openraadsinformatie.nl/13> <http://schema.org/name> "Amersfoort" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/13> <http://schema.org/description> "Gemeente Amersfoort" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/13> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/org#Organization> <http://purl.org/link-lib/supplant> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://id.openraadsinformatie.nl/14> <http://schema.org/name> "Utrecht" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/14> <http://schema.org/description> "Gemeente Utrecht*" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/14> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/org#Organization> <http://purl.org/link-lib/supplant> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://id.openraadsinformatie.nl/144> <http://schema.org/name> "Enschede" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/144> <http://schema.org/description> "Gemeente Enschede*" <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/144> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/org#Organization> <http://purl.org/link-lib/supplant> .
EOF
