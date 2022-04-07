FROM openjdk:8-jre-alpine as builder
ENV SBT_VERSION=1.6.2
RUN \
  apk --update add bash wget tar && \
  wget https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.tgz -O sbt.tgz -o /dev/null && \
  mkdir -p "/usr/local/sbt" && \
  tar -xC /usr/local/sbt --strip-components=1 -f sbt.tgz && \
  export PATH="/usr/local/sbt/bin:$PATH" && \
  rm sbt.tgz && \
  apk del wget tar && \
  rm -rf /var/cache/apk/*

COPY ./ /system/

RUN ln -s /usr/local/sbt/bin/sbt /usr/local/bin

WORKDIR /system
RUN \
    sbt ";project server; dist"

# STAGE 2: switch to small runtime
FROM openjdk:8-jre-alpine

COPY --from=builder /system/server/target/universal/server.zip /server/

WORKDIR /server

RUN \
    apk add --update bash && rm -rf /var/cache/apk/* && \
    unzip -q server.zip && \
    rm -f server.zip

CMD bash ./server/bin/server $HOST_PORT