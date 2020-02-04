#!/bin/sh

PORT=9200
URL="http://elasticsearch:$PORT"

# Check that Elasticsearch is running
until $(curl --output /dev/null --silent --head --fail "$URL"); do
    printf '.'
    sleep 1
done

response=$(curl $URL)

until [ "$response" = "200" ]; do
    response=$(curl --write-out %{http_code} --silent --output /dev/null "$URL")
    echo "Elastic Search is unavailable - sleeping"
    sleep 1
done

health="$(curl -fsSL "$URL/_cat/health?h=status")"
health="$(echo "$health" | sed -r 's/^[[:space:]]+|[[:space:]]+$//g')"

until [[ "$health" == "green" || "$health" == "yellow" ]]; do
    health="$(curl -fsSL "$URL/_cat/health?h=status")"
    health="$(echo "$health" | sed -r 's/^[[:space:]]+|[[:space:]]+$//g')"
    echo "Elastic Search is unavailable - sleeping"
    sleep 1
done

echo "Elastic Search is up"

logstash -f /user/share/logstash/data/logstash_traces.config