# Section 0 블로그 API 만들기 입니다.

> 정리 순서는 강의 순서대로 진행합니다.

## 프로젝트 생성

## 컨트롤러 생성
- 보통 SSR, SPA, 등을 많이 쓴다.
- SSR -> Jsp, thymeleaf, mustache, freemarker -> html rendering
- SPA -> VUE, react -> javascript + <-> API ( JSON )
- test를 생성하는 경우 mac에서 인텔리제이를 사용한다면 command + shift + t
- 위 명령어를 실행하면 테스트를 동일한 경로에 똑같이 생성해 줍니다.

```java
// 아래 세가지는 static method이므로 이렇게 불러와서 쓰거나 MockMvcRequestBuilder class를 선언해서  사용할 수 있습니다.
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/post 요청시 test를 출력한다.")
    void test() throws Exception {
        mockMvc.perform(get("/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("test"))
                .andDo(print());
    }
}
```

- 여기서 조금 꿀팁이라고 생각되는 것은 마지막 라인의 `andDo(print())`입니다.
- 아래 내용을 실행하면 아래내용이 실행 결과로 출력됩니다.

```aidl
MockHttpServletRequest:
      HTTP Method = GET
      Request URI = /v1/posts
       Parameters = {}
          Headers = []
             Body = null
    Session Attrs = {}

Handler:
             Type = com.hodolog.hodollog.controller.PostController
           Method = com.hodolog.hodollog.controller.PostController#get()

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = [Content-Type:"text/plain;charset=UTF-8", Content-Length:"4"]
     Content type = text/plain;charset=UTF-8
             Body = test
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
BUILD SUCCESSFUL in 5s
4 actionable tasks: 1 executed, 3 up-to-date
12:55:45 PM: 실행이 완료되었습니다 ':test --tests "com.hodolog.hodollog.controller.PostControllerTest.test"'.
```


## POST 데이터 콘텐츠 타입


## 데이터 검증-1


## 데이터 검증-2


## 작성글 저장1 - 게시글 저장 구현


## 작성글 저장 2 - 클래스 분리


## 게시글 조회 1 - 단건조회

## 게시글 조회 2 - 응답 클래스 분리

## 게시글 조회 3 - 게시글 여러개 조회


## 게시글 조회 4 - 페이징 처리


## 게시글 조회 5 - 페이징 처리(QeuryDSL)

## 게시글 수정


## 게시글 수정( 오류서정, 보충내용)


## 게시글 삭제

## 예외처리1


## 예외처리2
