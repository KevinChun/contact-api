# 설명
이 프로젝트는 restful api와 postgresql을 사용해서 아래의 api end-point들을 제공합니다.
- GET /api/employee 
  - paramter
    - page: 페이지 번호. 0부터 시작
    - pageSize: 한 페이지에 보여줄 데이터의 개수. 기본 20개
- GET /api/users/{name}
  - parameter
    - name: 사용자 이름
- POST /api/employee
  - parameter
    - file: csv or json 파일
    - body: csv or json 데이터
  - description
    - input type을 file로 할 경우 json 파일 또는 csv 파일을 업로드 합니다.
    - 따라서 file 타입일 경우, content-type은 application/json 또는 text/csv 형식만 지원합니다.
    - request body로 json 타입 또는 csv 데이터를 전달해도 됩니다.
    - 하지만, 한 번의 요청에 file과 body를 동시에 전달할 수는 없습니다.
  - Response에 대해서
    - 동일한 생성 요청이 중복으로 발생할 수 있습니다. Http method가 PUT or PATCH 였다면 부분 업데이트를 통해 중복 요청을 그대로 반영할 수 있겠지만, 이번 요구사항에서의 연락처 생성은 POST이므로 중복요청이 발생할 경우를 대비해서, api의 응답을 map으로 전달하도록 구현했습니다.
    - map의 key는 임직원의 이름이고, value는 존재하지 않는 연락처일 경우 "Created". 이미 존재하는 연락처의 경우 "Already exists"로 전달합니다.
    - 아래는 중복 요청의 경우에 대한 예제 응답입니다.
    - ```json
      {
        "김철수": "Created",
        "이영희": "Already exists"
      }
      ```
    

# 실행 방법
docker-compose를 사용해서 application을 실행하며 실행 방법은 아래와 같습니다.
```text
docker-compose build
docker-compose up -d
```

# Swagger
docker-compose로 application을 실행 후, `http://localhost:8080/swagger-ui.html` 로 접속하면 swagger-ui를 통해 api를 테스트할 수 있습니다.
![swagger.png](..%2F..%2FDesktop%2Fswagger.png)

# Postman test
rest api를 테스트하기 위해서 postman을 사용했고, 테스트 했던 스크립트를 프로젝트에 추가했습니다. 이 스크립트를 postman을 통해서 불러와서 다시 테스트를 진행할 수 있습니다.
테스트 스크립트 파일명은 `postman_test.json` 입니다.