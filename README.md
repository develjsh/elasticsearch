# Spring Data Elasticsearch 설정 및 검색 기능 구현
Link: https://tecoble.techcourse.co.kr/post/2021-10-19-elasticsearch/


1. Docker 설치
맥북 m1 pro 사용 중이며 Personal 사용은 무료이기 때문에 Docker desktop으로 설치를 진행했습니다.
Link: https://www.docker.com/pricing/

2. Docker를 활용한 Elasticsearch 설치
터미널에서 docker를 통해 Elasticsearch를 설치할 수 있습니다.

> docker pull docker.elastic.co/elasticsearch/elasticsearch:7.10.0

> docker run -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.10.0
9200 포트는 HTTP 클라이언트와 통신에 사용되며, 9300 포트는 노드들간 통신할 때 사용됩니다.

**해당 예제는 단일 노드로 클러스터를 구성되기 떄문에 만약 멀티 노드로 구성하고 싶으시면 docker-compose를 할용하시면 됩니다.**
**만일 다른 버전의 elasticsearch을 사용하시고 싶으시면 버전 호환 유무를 꼭 확인하시길 바랍니다.**
Link: https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#new-features

3. Spring Boot 생성
3.1 아래 링트를 통해 Spring Boot 생성을 해줍니다.
Link: https://start.spring.io

ElasticsearchApplication에서 Run 또는 Debug가 뜨지 않아 문제 해결을 위해 찾던 중 Java version이 잡히지 않는걸 확인하고
version을 확인해보니 권장 version이 아닌 17이 아니라서 잡히지 않고 있었습니다.
Link: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

결과:
Error: Main method not found in the file, please define the main method as: public static void main(String[] args)

해결:
일단 vscode를 껐다가 다시 켜보니 제대로 작동되었습니다.

4. Elasticsearch 작업을 위해 AbstractElasticsearchConfiguration 클래스 생성합니다.
4.1 RestHighLevelClient: RestHighLevelClient은 Elasticsearch 클러스터와 상호 작용하기 위한 고수준의 Java 클라이언트 라이브러리입니다. Elasticsearch의 REST API를 활용하여 데이터를 색인화하고 검색하는 등의 작업을 수행할 수 있습니다. 이 라이브러리는 Elasticsearch의 주요 기능을 추상화하고, 일반적인 검색 및 인덱싱 작업을 쉽게 수행할 수 있는 메서드와 클래스를 제공합니다.

4.2 ElasticsearchOperations을 Bean으로 등록합니다.

4.3 ElasticsearchRestTemplate으로 구현합니다.

5. ElasticSearchConfig 구현합니다.
Spring Data Elasticsearch는 ElasticsearchClient를 통해 Elasticsearch 노드 혹은 클러스터와 연결됩니다. ElasticsearchClient를 직접 사용할 수 있지만, 대게 더 추상화된 ElasticsearchOperations 혹은 ElasticsearchRepository를 사용합니다.
