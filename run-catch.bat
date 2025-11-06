@echo off
echo Arresto dei container esistenti...
docker-compose down

echo Avvio dei container con build...
docker-compose up --build

echo Frontend e backend di Catch in esecuzione..

pause