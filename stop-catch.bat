@echo off
echo Arresto di tutti i container Docker attivi...

FOR /F %%i IN ('docker ps -q') DO docker stop %%i

echo Rimozione di tutti i container Docker...
FOR /F %%i IN ('docker ps -a -q') DO docker rm %%i
pause