kafkacat -b 10.0.1.144:9092 -t ori-delta -D~ -P <<EOF
<http://example.com/1> <http://schema.org/name> "Some Name 250 updated" <http://purl.org/linked-delta/replace> .
<http://example.com/1> <http://schema.org/description> "Newly added description" <http://purl.org/linked-delta/replace> .
EOF
