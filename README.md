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
페이지 흐름도
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
Back-end API service
</summary>

![image](https://github.com/user-attachments/assets/11aadc7c-dc96-4bee-9b91-445cb799a92b)
![image](https://github.com/user-attachments/assets/4c8028b5-bf48-42f5-a3b1-4aab8713bb75)
![image](https://github.com/user-attachments/assets/a5d15e44-3c92-45bd-bd33-6b9448f2f40d)
![image](https://github.com/user-attachments/assets/84ba75e3-eff2-4330-b615-c6c653e051e7)
</details>

## 기능 설계 및 구현  
<details><summary>
메인페이지
</summary>

  컴포넌트 구성
![image](https://github.com/user-attachments/assets/3fff898e-da6f-46c6-9c80-d28c87cee092)
  사용흐름
![image](https://github.com/user-attachments/assets/ab734938-3322-418b-8e69-db8b51c47375)
  Axios
![image](https://github.com/user-attachments/assets/5d1da313-e10f-4ed9-a3be-5f3cc8bce2a2)
  Back-end
 ![image](https://github.com/user-attachments/assets/2e49f3ff-6d28-4d1a-8741-44cac8f665f7)
</details>

<details><summary>
인증기능
</summary>

컴포넌트 구성 및 사용흐름
![image](https://github.com/user-attachments/assets/5bc64a5c-e025-4eb9-9948-9948e84b8cff)
Axios
![image](https://github.com/user-attachments/assets/ab734938-3322-418b-8e69-db8b51c47375)
![image](https://github.com/user-attachments/assets/616589a4-7d61-4f55-94ff-c47254f0c33b)
Back-end
![image](https://github.com/user-attachments/assets/e0755c27-7b87-4ce0-a2ec-2e87fd8e7975)
![image](https://github.com/user-attachments/assets/2d6be4d9-bed4-4aa3-93c9-9bf4a4bfe26b)
![image](https://github.com/user-attachments/assets/4dafba6f-50ae-4df5-8e57-d79cd45af1ca)
![image](https://github.com/user-attachments/assets/ec360132-c53e-4f6d-bf8e-4ba68123d286)
</details>

<details><summary>
소셜링 기능(리스트)
</summary>

컴포넌트 구성 및 사용흐름
![image](https://github.com/user-attachments/assets/7885a186-15bc-46a0-8486-27e340f37693)
Axios
![image](https://github.com/user-attachments/assets/bd047a71-dffb-47d9-b49b-abde013f16fb)
Back-end
![image](https://github.com/user-attachments/assets/f220081c-9bf4-400c-af12-fd56e8cff0f5)
</details>

<details><summary>
소셜링 기능(읽기)
</summary>

컴포넌트 구성 및 사용흐름
![image](https://github.com/user-attachments/assets/3cd127ee-a9e6-462f-8533-e4c289991b64)
Axios
![image](https://github.com/user-attachments/assets/8c997326-88cd-4ca8-b2bf-7ecd024f35d7)
Back-end
![image](https://github.com/user-attachments/assets/01794d7f-4cdc-4d8d-a913-48f889a91219)
![image](https://github.com/user-attachments/assets/545565d0-667e-48ed-b299-095f8c8b55dc)
</details>

<details><summary>
소셜링 기능(작성)
</summary>

컴포넌트 구성 및 Axios
![image](https://github.com/user-attachments/assets/73c85223-1052-456a-9ae3-161d122285f5)
Back-end
![image](https://github.com/user-attachments/assets/a6181e01-3074-411b-b37a-53615fa5ca4c)
</details>

<details><summary>
소셜링 기능(사용자 페이지)
</summary>

컴포넌트 구성 
![image](https://github.com/user-attachments/assets/31a5af26-d508-4c04-a9f9-28cf5cf6c9e1)
Axios
![image](https://github.com/user-attachments/assets/86c13d3a-2230-4161-8912-5ca46d82a4f3)
![image](https://github.com/user-attachments/assets/e4096449-70ec-4605-bad7-0fedaf200842)
Back-end
![image](https://github.com/user-attachments/assets/58ebbb9c-d8f8-47ae-98fd-69dffd8695ab)
![image](https://github.com/user-attachments/assets/e3bd9e5e-3825-4405-bd56-c9a7cf36af48)
![image](https://github.com/user-attachments/assets/2d2faf8e-c01b-46dc-aef6-bdb6755438ae)
</details>

<details><summary>
소셜링 기능(검색 페이지)
</summary>

컴포넌트 구성 Axios
![image](https://github.com/user-attachments/assets/1a1c9bf8-0f51-4097-ad35-5a8a6ec707e8)
Back-end
![image](https://github.com/user-attachments/assets/cb37d461-0cda-4ae6-aad8-44bd601d4f33)
</details>

<details><summary>
마이페이지
</summary>

컴포넌트 구성
![image](https://github.com/user-attachments/assets/d4ad1b94-da8d-4142-9d2f-e4f5457ba0fb)
![image](https://github.com/user-attachments/assets/ee752b28-8e28-4e85-9a25-a9e88072fc5c)
Axios
![image](https://github.com/user-attachments/assets/99bc1f13-0023-4769-a0f2-27cb5a6dfa2c)
Back-end
![image](https://github.com/user-attachments/assets/59822c6d-de49-47f9-8890-7eedb9175bdd)
</details>

<details><summary>
마이페이지 소셜링 내역
</summary>

컴포넌트 구성
![image](https://github.com/user-attachments/assets/9c69676a-6657-4911-9389-d798dc2a8f52)
Axios
![image](https://github.com/user-attachments/assets/83ef4324-ab95-4f7f-aeee-60ea1bb4376a)
Back-end
![image](https://github.com/user-attachments/assets/c31e74a5-d86e-4a0a-98f5-467413afedea)
</details>

<details><summary>
마이페이지 리뷰
</summary>

컴포넌트 구성
![image](https://github.com/user-attachments/assets/d48e7865-6e30-4d0b-8c18-1d1ff2232535)
Axios
![image](https://github.com/user-attachments/assets/02dbca30-c29e-4e32-ab7a-42f0bca6ba5e)
Back-end
![image](https://github.com/user-attachments/assets/2c18b17b-4860-4a3c-b69a-a3be5c6b27dd)
</details>

<details><summary>
마이페이지 개인정보
</summary>

컴포넌트 구성
![image](https://github.com/user-attachments/assets/d05e612f-b603-465a-ae69-24c8d6dcb62f)
Axios
![image](https://github.com/user-attachments/assets/ba40cca5-a132-47cb-957d-ce672414c33d)
Back-end
![image](https://github.com/user-attachments/assets/7b3e6e47-6b55-44d6-b037-e3f18b9d7fa2)
</details>

<details><summary>
관리자 페이지 대시보드
</summary>

컴포넌트 구성 Axios
![image](https://github.com/user-attachments/assets/5865e924-19f0-4f24-9326-054e4d780b0c)
Back-end
![image](https://github.com/user-attachments/assets/5184fcfe-a97d-45e0-b99e-855b2166039b)
</details>

<details><summary>
관리자 페이지 소셜링 관리
</summary>

컴포넌트 구성 Axios
![image](https://github.com/user-attachments/assets/27ec66f9-aa96-4455-87c2-70663596125e)
Back-end
![image](https://github.com/user-attachments/assets/5cb0a591-1b05-4b5d-880a-804e90c5f8f7)
</details>
<details><summary> 
관리자 페이지 회원 관리
</summary>

컴포넌트 구성 Axios
![image](https://github.com/user-attachments/assets/833444f2-e553-42c2-a420-9fef278db6bf)
Back-end
![image](https://github.com/user-attachments/assets/f295e216-897d-45c8-a61e-f0676bfa50b9)
</details>

<details><summary>
관리자 페이지 회원 상세
</summary>

컴포넌트 구성 Axios
![image](https://github.com/user-attachments/assets/1f801c8f-fa5b-4362-9803-27d6a4f5c433)
Back-end
![image](https://github.com/user-attachments/assets/7924b051-e38e-47a6-959a-cd779169cbbe)
</details>

<details><summary>
관리자 페이지 공지사항 관리
</summary>

컴포넌트 구성 Axios
![image](https://github.com/user-attachments/assets/000da9ea-d22d-48a0-9b49-429f854c6cd1)
![image](https://github.com/user-attachments/assets/d7b69ee4-5414-428a-8e80-e3c47c738db9)
Back-end
![image](https://github.com/user-attachments/assets/6ed1dc8c-73d5-4a90-82f0-f18c86127488)
</details>




