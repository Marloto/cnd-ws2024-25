FROM alpine
ENV PLACE=World
WORKDIR /stuff
RUN echo "Hello $PLACE" > ./bar
COPY foo ./
ADD https://en.wikipedia.org/wiki/File:Example.jpg ./
EXPOSE 80
CMD ["ls", "-la"]