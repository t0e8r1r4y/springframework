# Section 0 블로그 API 만들기 입니다.

> 정리 순서는 강의 순서대로 진행합니다.

## 프로젝트 생성

<br/>

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

<br/>

## POST 데이터 콘텐츠 타입
- Http Method는 뭐가 있을까?
- GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
- 적어도 이 9개는 모두 알고 있어야 한다.
- 지금은 applicaion/json으로 통신을 많이한다.
- 예전에는 application/xml-xxx-encoding 이런식으로 썻다.
- 코드 작성 시 생산성을 높이는 방법 1 - alt + n을 누르면 필요한 코드를 generate 할 수 있는 창이 뜬다.
- json 타입으로 요청을 주고 받는 예시는 아래와 같다. 아래 방법에서는 json을 사용하는 방식을 사용하고 있으며 입력 요청은 dto를 생성해서 사용하는 방법을 쓰고 있음.
```aidl
    @PostMapping("/v1/posts4")
    public  String post4(@RequestBody PostCreate params) {
        log.info("params={}", params.toString());
        return "test4";
    }
```
- 테스트 코드는 아래와 같습니다.
```aidl
    @Test
    @DisplayName("/v1/posts4 요청시 test4을 출력한다.")
    void post4() throws Exception {
        mockMvc.perform(post("/v1/posts4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titme\": \"제목입니다.\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("test4"))
                .andDo(print());
    }
```

- 내 나름의 결론
  - 일단 아래 나올 내용이지만 데이터 검증 측면에서 dto를 쓰는 것이 편할 때가 더 많습니다.
  - 검증과 과련된 로직을 별도의 annotation으로 작성하더라도 dto를 쓰는 것이 코드 가독성 향상 목적에서는 더 좋아보입니다.

<br/>

## 데이터 검증-1
- 데이터를 검증하는 이유
  - client 개발자의 휴먼 에러
  - client 버그
  - 외부의 악성코드 등등
  - DB에 저장할 때 의도치 않은 오류 발생
  - 서버 개발자의 편안함을 위해서
- 방법 1 가장 단순 무식한 방법
  - 장점 : 쉽다.
  - 단점 : 검증 대상이 늘어날 수록 빡세다. 3번이상 단순 반복을 하고 있다면 내가 뭔가를 잘 못할 수 있다. 그리고 개발자의 휴먼에러로 누락이 발생할 수 있다. 여러 조건의 검증을 수행해야한다. 개발자스럽지 않다.
```java
    @PostMapping("/v1/posts4")
    public  String post4(@RequestBody PostCreate params) throws Exception {
        log.info("params={}", params.toString());
        String title = params.getTitle();
        String content = params.getContent();

        if(title == null || title.equals("")){
            throw new Exception("타이틀이 없어요.!");
        }

        if(content == null || content.equals("")){
            throw new Exception("콘텐츠가 없어요.!");
        }

        return "test4";
    }
```

- 방법2 . spring Validation을 사용한다.
  - 특정 버전부터는 spring  validation이 포함되지 않기 때문에 의존성 추가가 필수적임.
  - implementation group: 'org.springframework.boot', name: 'spring-boot-starter-validation', version: '2.7.5'
  - 관련 dependency [링크](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation/2.7.5)
  - 위 validation을 쓰면 Controller에 도달하기 전에 스프링에서 검증을 하고 예외처리를 해버린다. ( 그래서 400으로 날린다. )

- dto class
```java
@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.") // 스프링이 만들어준 title : "must not be blank"
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

}
```

- controller -> 데이터 검증 2에서 이 부분에 대해서 손을 볼 예정입니다.
```java
  @PostMapping("/v1/posts5")
  public  Map<String, String> post5(@RequestBody @Valid PostCreate params, BindingResult result) throws Exception {
      log.info("params={}", params.toString());
      String title = params.getTitle();
      String content = params.getContent();

      if(result.hasErrors()) {
          List<FieldError> fieldErrorList = result.getFieldErrors();
          FieldError firstFieldError = fieldErrorList.get(0);
          String fieldName = firstFieldError.getField();
          String errorMessage = firstFieldError.getDefaultMessage();

          Map<String, String> error = new HashMap<>();
          error.put(fieldName, errorMessage);
          return error;
      }

      return Map.of();
  }
```

- 테스트 코드
```java
  @Test
  @DisplayName("/v1/post5 요청 시 타이틀은 필수입니다. spring validation을 사용한 처리 방법입니다.")
  void post5() throws Exception {
      mockMvc.perform(post("/v1/posts5")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("{\"titme\": \"\", \"content\": \"내용입니다.\"}"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.title").value("타이틀을 입력해주세요."))
              .andDo(print());
  }
```

## 데이터 검증-2
- 위 방식에서 BindingResult result을 사용해서 메서드 마다 에러를 관리하게 되면 아래의 문제가 발생합니다.
  - 에러를 매번 메서드 마다 처리해줘야 하는 문제 발생
  - 개발자의 휴먼에러 발생
  - 프로젝트에서 에러 관리의 일관성 저해
  - 반복작업이 발생한다.
  - 간지가 안난다. 개발자 스럽지 못함
- 이를 개선하기 위해서 @ControllerAdvice를 사용하여 에러가 발생 시 해당 에러를 캐치하여 처리할 수 있음
```java
  @ResponseStatus(HttpStatus.BAD_REQUEST) // 응답에 보낼 것  -> 나중에는 실제 번호를 맞게 넣어야 한다.
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
      // TODO : 이렇게 처리를 하면 에러 내용의 일관성이 부족해짐. 이것에 대해서는 별도의 처리가 필요함. -> 그리고 서비스 기획시 프론트와 백엔드간 협의가 필요한 사항임
      ErrorResponse response =  new ErrorResponse("400", "잘못된 요청입니다.");
      for(FieldError fieldError : e.getFieldErrors()) {
          response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
      }
      return response;
  }
```
- 여기서 발생할 수 있는 에러 종류에 따라서 어노테이션과 메서드를 적절히 추가해서 확인한다.

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
