# 블로그 검색 서비스

## 프로젝트 설명
***

### 기술 스택
* JDK 11
* Springboot 2.7.3
* Redis (Embedded)
* Gradle
* Junit5

### Pakages
***
* Presentation : 인바운드 컨트롤러
* Application : 어플리케이션 서비스
* Domain : 비지니스 로직
* Infrastructure : 아웃바운트 어댑터
* Configuration : 설정

<pre>
<code>
- com.my.blogsearch
  - application
    - blog
  - configuration
  - infrastructure
    - kakao
    - naver
  - domain
    - blog
  - presentation
    - blog
</code>
</pre>

### 실행 방법
***
* 선행조건
  * Java, Git  설치
* 프로젝트 URL 접속 또는 Git Clone
  * https://github.com/ukjae88/blogSearch
* 프로젝트 루트 경로에 위치한 blogSearch.jar 다운로드 및 실행
  * Executable jar 다운로드 경로 : https://github.com/ukjae88/blogSearch/raw/master/blogSearch.jar
<pre>
<code>
java -jar blogSearch.jar
</code>
</pre>

## 핵심 문제해결 전략
***

### 데이터 관리 및 처리
***

* 블로그 검색은 검색 소스 API 요청하여 받은 데이터를 보여줍니다.
* 동시 트랜잭션 처리가 필요한 키워드 검색 카운트 관리는 Redis DB를 사용하였습니다.
* Redis 를 사용한 이유는 
  * in-memory DB로 연산 처리속도가 빠르고,
  * 연산이 단일 스레드에서 처리되어, 데이터 정합성이 보장되고 동시성 문제를 해결할 수 있습니다.
* Redis 데이터 처리는 Spring Data Redis 를 통해 구현하였습니다.
* 별도 작업없이 어플리케이션 서버를 실행했을 때, 바로 테스트가 가능하도록 Redis 를 Embedded 형태로 구성했습니다.

### Redis 자료구조 및 연산처리
***

* 멤버십 포인트 관리는 Redis Hash 자료 구조를 사용하여,   
Key 값은 **검색키워드** 로, 검색 카운트 개수를 Field-Value 형태로 저장합니다.
* 예시)   
  * Hash명 blog, 검색키워드 카카오 의 검색 카운트가 50 인 경우   
  blog:카카오 -> count-50
* 검색 카운트 증가를 위한 연산 처리는 Redis Hash 명령인 hset, hincrby 명령을 사용합니다.
* 예시)   
  * Hash명 blog, 검색키워드 카카오 의 검색 카운트 증가   
  hset blog:카카오 count 0   
  * 검색 카운트 증가   
  hincrby blog:카카오 count 1   
  
## 기능 요구사항
***
* Base Domain
  * http://localhost:8080
* Response Code (Error 발생 시)
   
| Code |Message|
|------|-------|
| 1001 |키워드 파라미터 필요|
| 1002 |유효하지 않은 파라미터 오류|


### 1. 블로그 검색 API
***

* Method & URI
  * GET
  * /blogs


* Request Parameter 

|Name|Type| Description                                                | Required |
|----|----|------------------------------------------------------------|----------|
|query|String| 검색 키워드                                                     | O        |
|sort|String| 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy | X |
|page|Integer| 결과 페이지 번호, 1~50 사이의 값, 기본 값 1                              | X |
|size|Integer| 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10                       | X |

* Response Parameter

| Name     | Type   | Description |
|----------|--------|-------------|
| title    | String | 블로그 제목   |
| contents | String | 블로그 본문 중 일부 |
| url      | String | 블로그 URL   |
| datetime | Datetime | 블로그 작성시간 |

* Request
<pre>
<code>
  /blogs?query=kakao&sort=accuracy&page=1&size=10
</code>
</pre>

* Response
<pre>
{
    "result": [
        {
          "title": "블로그 제목1",
          "contents": "블로그 본문 중 일부"
          "url": "블로그 URL"
          "datetime": "2022-06-03T10:20:00"
        },
        {
          "title": "블로그 제목2",
          "contents": "블로그 본문2 중 일부"
          "url": "블로그 URL2"
          "datetime": "2022-06-04T10:20:00"
        },
    ]
}
</pre>

### 2. 인기 검색어 목록 조회 API
***

* Method & URI
  * GET
  * /keywords


* Request Parameter

|Name|Type| Description |
|----|----|-------------|


* Response Parameter

| Name    | Type   | Description |
|---------|--------|-------------|
| keyword | String | 검색 키워드   |
| count   | Number | 검색 카운트   |

* Request
<pre>
<code>
  /keywords
</code>
</pre>

* Response
<pre>
<code>
{
    "result": [
        {
          "keyword": "카카오",
          "count": 50
        },
        {
          "keyword": "카카오뱅크",
          "count": 20
        },
    ]
}
</code>
</pre>
