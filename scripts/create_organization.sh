
kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://id.openraadsinformatie.nl/10> <http://schema.org/name> "Alkmaar." <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/10> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://example.com/10> <http://purl.org/link-lib/supplant> .
<http://id.openraadsinformatie.nl/10> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/org#Organization> <http://purl.org/link-lib/supplant> .
EOF
