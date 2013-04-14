#!/bin/bash

ISSUE=./test-issue.txt
json="{ \"title\": "\"$1\"", \"description\": "\"$2\"", \"featured\": "\"$3\""}"
echo $json

curl -k -H 'Cookie: CAP-Session=e7b341e0-9fc3-11e2-9e96-0800200c9a66' -X POST -H 'Content-type: application/json' -d "$json" http://localhost:8080/issues

