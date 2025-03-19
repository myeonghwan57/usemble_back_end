# usemble(소셜링 웹 서비스)
## 프로젝트 개요 및 목표
![image](https://github.com/user-attachments/assets/ba75e171-21fa-4dc9-bb4b-ffcac21bff82)
### SPA(Single Page Application)
- 독립적인 컴포넌트로 구성된 웹 애플리케이션
- 초기 로딩시 모든 페이지를 단일 HTML 파일로 받아온 뒤 데이터만 동적으로 업데이트하는 방식
- SPA는 컴포넌트를 재사용해 페이지의 일부만 교체하는 방법을 통해 효율적으로 렌더링

### MSA(MicroService Architecture)
- Rest API로 독립적인 하나의 기능을 만들어 개발, 배포되는 구조
- 기능별로 다른 도메인을 사용하는 서버 배포
- MSA 방식을 사용해 독립적인 기능을 구축하고 각 기능별로 관련된 서비스 제공
>**MSA를 통해 독립적인 서비스를 구축하고 SPA로 뛰어난 반응성과 빠른 페이지 로딩이 가능한 소셜링 서비스 개발을 목표로 함**

### 프로젝트 개요
- 주어진 환경에 맞도록 웹서비스 구현
- 사용자 중심의 직관적 인터페이스 설계
- 관심사 기반의 모임추천 알고리즘 개발
- 모임 생성 및 관리 기능 개발
- 사용자 정보 보호와 신뢰할수 있는 환경 제공
- 사용자 간의 간단한 커뮤니케이션을 통한 소셜링 활성화
- 사용자 관심 기반의 소모임을 쉽게 만들고 참여할 수 있는 플랫폼 개발
- 사용자들이 정보나 콘텐츠를 쉽게 찾고 공유할 수 있는 플랫폼 개발
## 요구사항 정의 및 모델링
### 전체프로세스 정의 (사용자)

![프로세스](https://github.com/user-attachments/assets/be71ce52-5ddf-4a97-b1c9-e476c21ef32f)
### 전체프로세스 정의 (관리자)
![image](https://github.com/user-attachments/assets/a120ce82-4973-43f2-8220-71c350c75e81)

### 프로세스를 구성하는 구현기능 정의
![image](https://github.com/user-attachments/assets/4e2d7936-90bd-4240-94fe-fe1ee9ff475c)
### 시스템 아키텍져(MSA)
![image](https://github.com/user-attachments/assets/1c7a99a8-3450-4984-a80c-b32c14f6f4fc)
### 사용되는 기술 및 툴
![image](https://github.com/user-attachments/assets/582b792b-2d12-4b9b-a1e0-9362671f2c05)
### 프로젝트 일정표
![image](https://github.com/user-attachments/assets/f171a076-9667-45bb-93df-6c3bb0987b71)
### 팀원 소개 및 역할분담

## DB 모델링
### 전체 DB 스키마(ERD)
![image](https://github.com/user-attachments/assets/2c8b5ed1-70e7-46ba-b98b-9fae43591d98)

## Front-end 및 Back-end 설계 및 구현
<details><summary>
**페이지 흐름도**
</summary>

![image](https://github.com/user-attachments/assets/2c2aa6f0-98e1-4ea6-9f55-8c94b2b5fa14)
![image](https://github.com/user-attachments/assets/cfc8b6ac-3ad2-4753-b968-6b302e11114a)
![image](https://github.com/user-attachments/assets/ec173b4f-30d9-4866-91aa-68c8fa4a1ecf)
![image](https://github.com/user-attachments/assets/87cee3e5-1d9b-43b9-8986-1c8fb5ae1275)
![image](https://github.com/user-attachments/assets/89196ae6-ab78-4b9e-a2d4-5ca9fc151923)
![image](https://github.com/user-attachments/assets/65cff688-5c17-460a-9fe7-ecba7ace2916)
![image](https://github.com/user-attachments/assets/2b54411f-e753-4a1b-aeaf-0a6627d4b618)

</details>


<details><summary>
**Back-end API service**
</summary>
![image](https://github.com/user-attachments/assets/11aadc7c-dc96-4bee-9b91-445cb799a92b)
![image](https://github.com/user-attachments/assets/4c8028b5-bf48-42f5-a3b1-4aab8713bb75)
![image](https://github.com/user-attachments/assets/a5d15e44-3c92-45bd-bd33-6b9448f2f40d)
![image](https://github.com/user-attachments/assets/84ba75e3-eff2-4330-b615-c6c653e051e7)

</details>


