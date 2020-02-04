#!/bin/sh

apk --no-cache add curl

PORT=9200
URL="http://elasticsearch:$PORT"

# Check that Elasticsearch is running
curl -s $URL 2>&1 > /dev/null

while [ $? != 0 ]; do
    curl -s $URL 2>&1 > /dev/null
    sleep 2
done

java -jar /user/share/api-jar/api-0.0.1-SNAPSHOT.jar