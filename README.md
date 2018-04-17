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
