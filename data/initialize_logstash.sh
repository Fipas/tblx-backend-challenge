#!/bin/sh

PORT=9200
URL="http://elasticsearch:$PORT"

# Check that Elasticsearch is running
curl -s $URL 2>&1 > /dev/null

while [ $? != 0 ]; do
    curl -s $URL 2>&1 > /dev/null
    sleep 2
done

logstash -f /user/share/logstash/data/logstash_traces.config