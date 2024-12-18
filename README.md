# Project - Golden Time (SpringBoot)
골든타임(빠른 응급실 찾기) - 백엔드(Spring) <br/>
공공데이터 API를 활용하여 사용자 위치를 기준으로 의료기관 정보를 빠르고 정확하게 찾는 웹사이트입니다.

## ⏰ 개발 기간
2024.11.04 ~ 2024.12.09

## 😀 멤버 구성
- 강문선
  - 건강검진센터 조회
  - 마이페이지
- 김엄지
  - 응급실 조회
  - 의약품정보 조회
  - 메인페이지(응급실 조회)
- 양지은
  - 병원 조회
  - 로그인, 회원가입, ID/PW찾기
  - 메인페이지(퍼블리싱)
- 한상혁
  - 약국조회
  - 응급처치안내

## ⚙️ 개발환경
**운영체제** <br/>
<img src="https://img.shields.io/badge/Window10-0a85d9?style=for-the-badge&logo=Window10&logoColor=white"> <br/>
**Front-end** <br/>
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <br/>
**Back-end** <br/>
<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white">
<img src="https://img.shields.io/badge/JSP&Servlet-e06c00?style=for-the-badge&logo=JSP&Servlet&logoColor=white"> <br/>
**FrameWork / Library** <br/>
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black">
<img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white">
<img src="https://img.shields.io/badge/MyBatis-d30101?style=for-the-badge&logo=MyBatis&logoColor=white"> 
<img src="https://img.shields.io/badge/Lombok-000000?style=for-the-badge&logo=Lombok&logoColor=white"> 
<img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white"> <br/>
**Database** <br/>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <br/>
**WAS** <br/>
<img src="https://img.shields.io/badge/tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black"> <br/>

### API
- TMAP API
- HTML5 Geolocation API
- 공공데이터 API
  - 응급의료기관 정보 조회
  - 병원 정보 조회
  - 약국 정보 조회
  - 의약품 정보 조회
  - 검진기관 정보 조회

## ⭐ 주요기능
### 1. 메인 페이지
![image](https://github.com/user-attachments/assets/1cac7a39-3571-473a-8b58-3e4381b7cd08)
- 사용자 위치 기반 실시간 응급실 정보 조회

### 2. 응급실 조회
![image](https://github.com/user-attachments/assets/200f65c4-47d0-4339-9ff1-7c0626c1d1d4)
- 사용자 위치 기반 실시간 응급실 정보 조회(공공데이터 API 사용)
- 응급실 위치 지도에 마커표시
- 응급실 리스트 클릭 시 응급실 상세정보 OPEN
- 지역 검색, 병원명 검색
- 길찾기 기능(자동차 경로)

### 3. 병원 & 약국 조회
![image](https://github.com/user-attachments/assets/5eaf9112-57d9-4e0c-b00d-7720b35a9a09)
- 사용자 위치 기반 병원(약국) 정보 조회(공공데이터 API 사용)
- 병원(약국) 위치 지도에 마커표시
- 병원(약국) 리스트 클릭 시 병원 상세정보 OPEN
- 지역검색, 병원(약국)명 검색, 병원 종류 검색
- 길찾기 기능(자동차 경로)
- 병원(약국) 즐겨찾기
- 리뷰 및 평점 기능

### 4. 건강검진기관 조회
![image](https://github.com/user-attachments/assets/41d0be91-34d6-4463-b3a8-039478db9e8a)
- 사용자 위치 기반 검진기관 조회(공공데이터 API 사용)
- 상세보기 버튼 클릭 시 검진기관 상세정보 OPEN
- 지역 검색, 기관명 검색, 검진 유형 검색
- 검진기관 즐겨찾기







