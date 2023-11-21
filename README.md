## 구글 로그인 셈플 코드
### 설정 사항
1. 사용자 인증 정보 발급
   - 접속 링크 https://console.cloud.google.com/welcome
   - 프로젝트 생성 후 API 및 서비스 => 사용자 인증 정보
   - 사용자 인증 정보 만들기 => OAuth2.0 Client 웹으로 생성 ( access_token 발급 위해 client_id, secret 필요하기 때문에 )

2. strings 파일 설정
   - res => values => string.xml
   - client_id, client_secret 발급 받은 걸로 설정