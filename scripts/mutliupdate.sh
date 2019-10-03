kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://example.com/1> <http://schema.org/name> "Some Name 250" <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://example.com/10> <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://schema.org/Thing> <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#first> "First item" <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#rest> <http://www.w3.org/1999/02/22-rdf-syntax-ns#nil> <http://purl.org/linked-delta/replace> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://example.com/1> <http://schema.org/name> "Some Name 26" <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://example.com/10> <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://schema.org/Thing> <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#first> "First item" <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#rest> <http://www.w3.org/1999/02/22-rdf-syntax-ns#nil> <http://purl.org/linked-delta/replace> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://example.com/1> <http://schema.org/name> "Some Name 27" <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://example.com/10> <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://schema.org/Thing> <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#first> "First item" <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#rest> <http://www.w3.org/1999/02/22-rdf-syntax-ns#nil> <http://purl.org/linked-delta/replace> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://example.com/1> <http://schema.org/name> "Some Name 28" <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://example.com/10> <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://schema.org/Thing> <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#first> "First item" <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#rest> <http://www.w3.org/1999/02/22-rdf-syntax-ns#nil> <http://purl.org/linked-delta/replace> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://example.com/1> <http://schema.org/name> "Some Name 29" <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://example.com/10> <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://schema.org/Thing> <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#first> "First item" <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#rest> <http://www.w3.org/1999/02/22-rdf-syntax-ns#nil> <http://purl.org/linked-delta/replace> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://example.com/1> <http://schema.org/name> "Some Name 30" <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://example.com/10> <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://schema.org/Thing> <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#first> "First item" <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#rest> <http://www.w3.org/1999/02/22-rdf-syntax-ns#nil> <http://purl.org/linked-delta/replace> .
EOF

kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://example.com/1> <http://schema.org/name> "Some Name 31" <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/2006/vcard/ns#hasOrganizationName> <http://example.com/10> <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://schema.org/Thing> <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#first> "First item" <http://purl.org/linked-delta/replace> .
_:0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#rest> <http://www.w3.org/1999/02/22-rdf-syntax-ns#nil> <http://purl.org/linked-delta/replace> .
EOF
