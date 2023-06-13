<h1 align="center">동네친구 매칭 플랫폼, 👨‍👩‍👧‍👦 우리 동네로 올래?</h1>

<p align="center"><img src="https://user-images.githubusercontent.com/124491136/234168126-e831e158-8ff0-42e5-a1b5-98dd82da8ed2.png" alt="이미지 설명" width="570" height="480"></p>


📍프로젝트 소개
-------------
- 총 6인의 예비 백엔드 개발자 및 예비 프론트엔드 개발자가 협업하여 제작한 3개월간의 프로젝트입니다.
- 프로젝트 컨셉 설계부터 기획, 서버 배포, 테스트까지 하나의 서비스 개발 플로우를 경험하였습니다.
- Github, Notion, Slack 등 프로젝트 관리툴을 적극 활용하여 실무와 유사한 협업 프로세스를 사용하였습니다.
- 프로젝트에 맞는 컨셉과 요구사항을 반영한 비지니스 로직 구현 Rest api 서버 개발 프로젝트입니다.


🚀프로젝트 개요
-------------
- 프로젝트 "우리 동네로 올래"는 지역 커뮤니티를 만들어 관심사에 따라 모임을 만들 수 있는 플랫폼입니다.
- 사용자는 자신이 관심 있는 분야, 취미, 문화, 스포츠 등을 설정하고, 해당 분야에 관심이 있는 다른 사용자와 모임을 만들거나 모임에 참여할 수 있습니다.

**1.유연하고 확장성 높은 코드 개발**

- 유연하고 확정성 높은 코드로 유지보수성을 높이기 위해 객체지향 SOLID 원칙을 준수합니다. 
- 중복되는 코드는 하나의 메서드로 모듈화 시키거나 AOP등을 활용하여 불필요한 코드 반복을 줄여나갑니다.
- 반복되는 쿼리문으로 생기는 DB부하 및 성능저하를 줄이기 위해 (JPA N+1)  FetchJoin , BatchSize 활용을 고려합니다.

**2.캡슐화 및 보안성 높은 코드**

- 스프링 시큐리티와 JWT 토큰을 활용하여 인증,인가를 활용합니다.
- 민감한 정보인 사용자의 패스워드는 bcryptpasswordencoder 를 통해 암호화 시킵니다.
- 외부에서 쉽게 객체에 대한 정보를 바꿀 수 없도록 팩토리 메서드 패턴 지향 Setter 사용을 지양합니다.

**3.테스트 코드를 활용한 리팩토링**

- 개발자의 의도대로 코드가 흘러가는지 테스트하기 위해 테스트 코드 라이브러리인 Junit5와 Mockito Framework를 활용합니다
- 테스트 코드를 통하여서 실제 서버가 운영되는 환경에서도 안정적인 리팩토링 환경을 구축할 수 있습니다.


🎯프로젝트 기간
------------
2023.03.07 ~ 2023.06.15 (3m)


🙋‍♂️참여 팀원
-------------
- BackEnd : [조찬영](https://github.com/NinjaYoung98), [조동혁](https://github.com/donizz), [박지영](https://github.com/jiyoung10), [이나래](https://github.com/naraeeee)
- FrontEnd : [박규현](https://github.com/henryKyuhyun), [강다예]()

<br></br>
🔧 사용 기술 및 환경
-------------

<img width="620" alt="스크린샷 2023-04-27 123612" src="https://user-images.githubusercontent.com/124491136/234753405-9cf41f54-ef99-400c-a7d9-f3b1be231f8c.png">

🔧 TOOL
-------------

- IntelliJ
- Git hub
- Git Kraken
- Slack

📈 Usecase
-----------
![usecase2](https://github.com/Team-javaJobJob/OllehBoardProject/assets/126131788/4753eaba-a0a1-4c6b-9c21-765c8c2432b7)

프로젝트 아키텍쳐 
-------------
