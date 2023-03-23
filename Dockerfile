FROM alvrme/alpine-android:android-33-jdk17-v2022.07.02
RUN git clone https://github.com/andob/libmagic-android-bindings
WORKDIR ./libmagic-android-bindings

RUN apk update
RUN apk add --no-cache autoconf automake libtool cmake make gcc g++

RUN ./gradlew :bindings:clean :sample:clean :bindings:assembleDebug :sample:assembleDebug
