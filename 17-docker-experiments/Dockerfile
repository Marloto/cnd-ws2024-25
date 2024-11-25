# Base-Image
FROM alpine:3.18.3

# Env-Variable / Umgebungsvariable
ENV PLACE=World

# Bewegt die aktuelle Konsole in den Ordner, wird erstellt falls nicht da, alle folgende befehle
# werden in diesem ordner ausgeführt
WORKDIR /stuff2

# Normaler Shell-Befehl
RUN echo "Hello $PLACE" > ./bar

# vgl. docker cp, i.R. zu verwenden wenn es ums kopieren geht
COPY foo ./
COPY something.sh ./

RUN chmod +x something.sh && ./something.sh

# Erlaubt verwendung von URLs, fügt am Ende aber auch nur Daten in das Dateisystem
ADD https://en.wikipedia.org/wiki/File:Example.jpg ./

CMD ls -la
# vgl. CMD ["cat","bar"]