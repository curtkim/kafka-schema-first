## 목적
- schema-registry 등록
- schema 변경

## avro
- generic
- code generation
- reflective(java bean & annotation)

## HOWTO

    docker-compose up -d


    # execute ex1.ProduceMain
    docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:29092 --topic taxi.driver.location --from-beginning
    curl localhost:8081/subjects
    curl localhost:8081/subjects/taxi.driver.location-value/versions/1
    # "{topic}-value"으로 저장된다. 별로
    docker-compose exec schema-registry kafka-avro-console-consumer --bootstrap-server kafka:29092 --topic taxi.driver.location --from-beginning

    docker-compose exec kafka kafka-consumer-groups --bootstrap-server kafka:29092 --list
    docker-compose exec kafka kafka-consumer-groups --bootstrap-server kafka:29092 --group console-consumer-19899 --describe

    docker-compose down






## kafka music의 subjects(localhost:8081/subjects)

    play-events-value
    song-feed-value
    kafka-music-charts-song-play-count-changelog-value
    kafka-music-charts-song-play-count-repartition-key
    kafka-music-charts-song-play-count-repartition-value
    kafka-music-charts-top-five-songs-repartition-value
    kafka-music-charts-top-five-songs-by-genre-repartition-value
    kafka-music-charts-all-songs-changelog-value
    kafka-music-charts-KSTREAM-MAP-0000000004-repartition-value

- subject가 있고 schema가 있음
- subject에는 version이 여러개 있고
- 하나의 version은 schema id를 가지고 있음

    /subjects/play-events-value/versions/1
    {
      subject: "play-events-value",
      version: 1,
      id: 2,
      schema: "{"type":"record","name":"PlayEvent","namespace":"io.confluent.examples.streams.avro","fields":[{"name":"song_id","type":"long"},{"name":"duration","type":"long"}]}"
    }