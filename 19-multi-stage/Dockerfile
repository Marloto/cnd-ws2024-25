FROM alpine
RUN apk add --update-cache libstdc++ \
  && rm -rf /var/cache/apk/*
WORKDIR /root
COPY app ./
CMD ["./app"]