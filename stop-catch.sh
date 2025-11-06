#!/bin/bash

echo "🔻 Arresto di tutti i container Docker attivi..."
docker ps -q | xargs -r docker stop

echo "🧹 Rimozione di tutti i container Docker..."
docker ps -a -q | xargs -r docker rm