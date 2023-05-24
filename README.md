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

5.1 ElasticsearchClient 구현체는 RestHighLevelClient가 일반적입니다.

6. application.properties에서 logging 설정
logging:
  level:
    logging.level.org.springframework.data.elasticsearch.client.WIRE=TRACE

7. BasicProfile을 @Embeddable로 생성해줍니다.

8. User.java 생성해줍니다.
@Document: 해당 클래스가 elasticsearch DB에 매핑될 클래스라는 것을 표기해줍니다. @Documented와는 다릅니다.
@Id: JPA 엔티티 객체의 식별자로 사용할 필드에 적용하며, 유니크한 DB의 컬럼과 맵핑합니다.
@GeneratedValue(strategy = GenerationType.IDENTITY): 기본키를 자동으로 생성 방식을 설정해줍니다.
  - GenerationType.IDENTITY: 기본키 생성을 데이터베이스에게 위임하는 방식으로 id값을 따로 할당하지 않아도 데이터베이스가 자동으로
    AUTO_INCREMENT를 하여 기본키를 생성해줍니다.
  - GenerationType.SEQUNCE: @SequenceGenerator 어노테이션이 필요합니다. 그리고 데이터 베이스의 Sequence Object를 
    사용하여 데이터베이스가 자동으로 기본키를 생성해줍니다.
    SEQUENCE는 값을 계속 DB에서 가져와서 사용해야 하기 때문에 성능에 저하를 일으킬 수 있기 때문에 allocationSize 의 크기를 적당히 설정하여 성능 저하를 개선시킬 수 있습니다.
  - GenerationType.TABLE: @TableGenerator 어노테이션이 필요하며, 키를 생성하는 테이블을 사용하는 방법으로 Sequence와 
    유사합니다.
  - GenerationType.AUTO: 본 설정 값으로 각 데이터베이스에 따라 기본키를 자동으로 생성합니다.
    제약조건:
    null이면 안됩니다.
    유일하게 식별할 수 있어야합니다.
    변하지 않는 값이어야 합니다.
@Embedded: 복합 값 타입으로 불리며 새로운 값 타입을 직접 정의해서 사용하는 JPA의 방법을 의미합니다.
@PersistenceCreator: 데이터베이스에서 가져온 객체를 초기화할 때 사용할 생성자를 마킹해줍니다.
매개변수가 존재하는 생성자가 여러개 존재할 때 @PersistenceConstructor를 사용하지 않으면 org.springframework.data.mapping.model.MappingInstantiationException이 발생합니다.

9. ElasticsearchRepository 인터페이스를 생성합니다.
PagingAndSortingRepository를 extends 해줍니다.

10. UserRepository 인터페이스를 생성합니다.

11. ElasticSearchConfig.java에 @EnableElasticsearchRepositories있는걸 확인합니다.


