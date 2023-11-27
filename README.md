## 클래스 다이어그램
<img width="491" alt="class diagram" src="https://github.com/Gyeongsu1997/To-do-app/assets/97381683/d8745a44-2bb9-4857-b5de-83db37c628c2">

## 요구사항 분석
### 기능 목록
- 회원 기능
  - 회원 등록
- 할 일 기능
  - 할 일 등록
  - 할 일 조회
  - 할 일 수정
  - 할 일 삭제
- 기타 요구사항
  - 마감일이 지났거나 마감일 당일에 해당하는 할 일은 빨간색으로 표시한다.
  - Job 엔티티의 description 필드를 데이터베이스 테이블에 VARCHAR(255) 타입으로 설정하고, 사용자가 255바이트 이하의 글자를 입력하도록 제한한다.
    - HTML의 input field에서 입력 글자수를 제한할 수 있는가?
    - 만약 사용자가 255바이트가 넘어가는 입력을 보내면 어떻게 처리되는가?
  <details>
  <summary>할 일의 마감일은 오늘 날짜 이후로 설정 가능하다.</summary>
    
    - input 태그의 min 속성을 이용해 손쉽게 해결하였다.
  </details>
  <details>
  <summary>할 일 목록은 마감일을 기준으로 오름차순으로 정렬하여 표시한다.</summary>
    
    - repository 계층에서 Job을 조회하는 select query에 order by 옵션을 추가함으로써 해결하였다.
  </details>
