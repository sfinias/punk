#!/bin/bash

cd ./data
for file in *.json; do
  curl -X PUT localhost:8080/api/v1/beer -H "Content-Type: application/json" --data "@./$file"
done