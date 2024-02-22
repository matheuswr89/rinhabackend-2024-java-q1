FROM ghcr.io/graalvm/native-image-community:21 as build
WORKDIR /app
COPY . /app
RUN ENV_DATABASE=r2dbc:postgresql://localhost:5432/rinha ./mvnw -Pnative native:compile

FROM alpine
COPY --from=build /app/target/backend /backend
RUN apk add libc6-compat
EXPOSE 8080
CMD ["./backend"]