 # Medichat_Upgrade

## Patch
SPRING BOOT VERSION: 2.XX -> 3.XX<br>
DB: ORACLE -> POSTGRESQL<br>
ORM: MYBATIS -> JPA<br>
FRONT: JSP -> HTML(THYMELEAF)<br>

## Patch Note
1. 기존 MyBatis을 사용할 때는 Entity(VO) 와 DTO를 분리할 필요가 없었지만 JPA를 사용하기 위해서는 DB(Table)용 객체와 필요한 값을 담아두거나 처리하기 위한 객체(DTO)가 따로 필요했습니다.<br>
   따라서 Entity(VO) 와 DTO를 분리하여 Entity 에는 Table에 관련된 속성만 넣어 관리하고, 활용을 위한 변수나 메소드는 DTO에 넣어 관리하기로 했습니다.<br>
   추가로 DTO에 toDTO, toEntity 메소드를 넣어 Service 단에서 변환해가며 사용하기로 했습니다.<br>
   정리하자면 DB -> Repository -> Service 관계에서는 Entity 객체만을 사용하고<br>
   Service 단에서 toDTO로 변환하여 Service -> Controller -> Web 으로 DTO의 데이터 이동이 이루어집니다. 역순 또한 마찬가지입니다.
2. 기존 Tiles(레이아웃 프레임워크)는 Spring Boot 2.xx 버전에서만 지원하기 때문에 3.xx 버전으로 업그레이드 하기 위해선 새로운 레이아웃 프레임워크가 필요했습니다. 따라서 Thymeleaf Layout을 사용하기로 했습니다.
3. MyBatis 에서는 간단했던 동적쿼리 사용이 JPA 에서는 쉽지 않았기 때문에 QueryDSL 기술을 채택했습니다.<br>
   하지만 연산 결과를 가져오기 위해서는 Entity(Q 클래스)를 변경해야 한다는 점이 마음에 들지 않았고, 다른 방법으로는 Repository 단에서 DTO를 사용해야 했습니다.<br>
   이 방식들이 마음에 들지 않았고, 다른 방법을 찾기 시작했습니다.<br>
   그러다 문득 모든 데이터를 가져와서 WAS 에서 조건 처리를 한다면 처리 시간이 많이 바뀔까 싶어 테스트 코드를 작성해 시간 측정을 해봤고 그 결과의 차이가 크지 않아 Spring Data JPA 만을 사용해서 WAS(Service) 에서 처리하기로 최종 결정했습니다.<br>
   기존 프로젝트에서 꾸준히 고민했던 대용량 데이터를 WAS 에서 처리할 것인지 DB 에서 처리할 것인지 다시 한번 고민하고 확인해볼 수 있는 기회가 되었습니다.
4. Docker에 애플리케이션을 올리기 위해 application.properties를 3가지로 나누어 분리했습니다.<br>
   dev(개발환경), prod(배포환경), test(테스트환경) 으로 분리해 local 에서는 application-dev.properties, docker 에서는 application-prod.properties, test 에서는 application-test.properties로 각각 매핑되게끔 설정했습니다.<br>
   이렇게 분리한 이유는 각 연결되는 개발 DB, 배포 DB, 테스트 DB 가 다르기 때문에 설정이 달랐기 때문입니다.
5. 기존 init 메소드의 api 호출 횟수가 하드코딩이 되어 있었는데 현재는 더 이상 api로 넘어오는 데이터가 없을 때 까지 진행되도록 수정하여 전부 자동화 가능하게끔 수정했습니다.<br>
   자동화로 수정했기 때문에 Batch를 활용한 데이터 최신화가 가능해졌습니다.<br>
   단, 병원 데이터의 경우 api 호출 URL 쿼리에 특정한 값이 들어가는데 이 값은 자동화가 불가능하여 하드코딩되어 있습니다. 이 부분은 조금 더 고민해보도록 하겠습니다.


#### Commit Message Convention
[DEV] : 개발 관련에 대한 Commit
[MODIFY] : 버그 관련 수정이 아닌 모든 수정에 대한 Commit
[FIX] : 버그 관련 수정에 대한 Commit
[CONFIG] : 개발 환경 설정에 대한 Commit

#### Git Branching Strategy
Main
Dev
Feature(Domain Name)