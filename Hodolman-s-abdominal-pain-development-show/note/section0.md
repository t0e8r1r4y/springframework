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
- service와 repository를 생성하고 실제 요청 받은 결과를 저장하는 로직입니다.
- 호돌맨님 과정과는 다르게 Builder pattern을 사용하여 객체를 저장하였습니다.
- 테스트 과정에서 아래 내용이 중요합니다.
  - 각각의 테스트가 서로 영향을 받지 않도록 하는 장치가 필요합니다. @BeforeAll을 사용하면 각 메서드를 실행하기 전에 초기화를 할 수 있습니다.
  - 그리고 @BeforeAll을 사용하기 위해서는 전체 테스트에 @TestInstance(TestInstance.Lifecycle.PER_CLASS)을 작성합니다.
  - 그리고 현재 Controller 단에서 service까지 호춣하여 테스트하고 있습니다. 보통 이에대해서는 권장하지는 않는 방식입니다만 굳이 하려고 하면 @SpringBootTest 어노테이션을 붙여주어야 합니다.
```java
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PostRepository postRepository;

  @BeforeAll
  void setUpDB() {
    postRepository.deleteAll();
  }
  
  ...

  @Test
  @DisplayName("/post7 요청시 DB에 값이 저장된다.")
  void post7() throws Exception {
    // when
    mockMvc.perform(post("/v1/posts7")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"제목임다.\", \"content\": \"내용입니다.\"}"))
            .andExpect(status().isOk())
            .andDo(print());
    // then
    Assertions.assertEquals(1L, postRepository.count());

    Post post = postRepository.findAll().get(0);
    Assertions.assertEquals("제목임다.", post.getTitle());
    Assertions.assertEquals("내용입니다.", post.getContent());
  }
}
```

## 작성글 저장 2 - 클래스 분리
- 빌더의 장점
  - 가독성에 좋다.
  - 값 생성에 대한 유여함
  - 필요한 값만 받을 수 있다. -> 오버로딩 되는 조건 찾아본다..
  - 객체의 불변성
- 객체의 불변성 관련해서 아래와 같이 쓸 수 있다. 뭐가 좋은지는 판단이 좀 필요하다.
```java

public class Example {
    private final String path;
    
    @Builder
    public Example(Stirng path){
        this.path = path;
    }
    
    public Example changeValue(String path) {
        return new Example().builder()
                .path(path)
                .build();
    }
}
- ```
- 서비스에서 리턴값을 던지는 방법
  - case1 : 저장한 객체 그대로를 던져준다.
  - case2 : 저장한 객체의 PK만 던져준다.
  - case3 : 그냥 안던진다.
  - 대게 2번 정도. 그리고 정상적으로 POST 요청이 처리가 되면 200, 201을 응답하는 것이 일반적이다.
- 서버에서는 조금 유연하게 대응을 해주는 것이 좋음 ( 가령 case1과 case2를 같이 쓰도록 Api를 만들거나 하면 됨)

## 게시글 조회 1 - 단건조회
- 단건 조회 구현 및 테스트 입니다.
- 서비스에서 임시 예외처리는 아래와 같이 합니다.

```java
  public Post get(Long id) {
      Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
      return post;
  }
```

## 게시글 조회 2 - 응답 클래스 분리
- 응답 클래스도 dto로 분리가 필요하다.
- 새로운 기능이나 기술 스택이 반영이 될 때 객체를 바로 return하는 것은 수정사항이 생길때마다 정책의 변화가 생길 수 있다.
- 서비스에서 응답 DTO를 나눠야 하는가?
  - 대게 Service와 WebService로 구분하여 Controller와 통신하는 서비스, 그리고 실제 서비스 로직을 구분해서 처리하는데
  - 규모가 작으면 관리 범주가 너무 쪼개지므로 하나에서 관리하기도 한다.
- 이 강의에서 개꿀팁은 Gradle에서 Tasks > verification > test 버튼을 누르면 전체 테스트가 한번에 돌아간다.

## 게시글 조회 3 - 게시글 여러개 조회
- findAll을 사용해서 조회하는 방법

## 게시글 조회 4 - 페이징 처리
- 페이징이 필요한 경우
  - 글이 너무 많은경우 => 비용이 너무 많이 든다.
  - 글이 백만건 이백만건이인 경우 모두 조회하는 경우 ->DB가 뻗을 수 있다.
  - DB -> 애플리케이션 서버로 전달하는 시간, 트래픽 비용이 많이 들어갈 수 있다.
  - sql에서 select, limit, offset 같은 것들은 무조건 다 알고 있어야 한다.
  - 페이징을 적용하는 방법은 간단하다. 아래 코드를 보라

```java
  @GetMapping("/pageposts")
  public List<PostResponse> getList( Pageable pageable) { // Pageable 앞에 PageableDefault 라고 쓸 수 있다. 혹은 @PageableDefault(size = 10) 이런식으로 가능
      return postService.getListByPage2(pageable);
  }
```

```java
  public List<PostResponse> getListByPage2(Pageable pageable)  {

      return postRepository.findAll(pageable).stream().map(PostResponse::new).collect(Collectors.toList());
  }
```

```yaml
  data:
    web:
      pageable:
        one-indexed-parameters: true
        default-page-size: 10
```


## 게시글 조회 5 - 페이징 처리(QeuryDSL)
- 방법이 조금 어렵지만 호돌맨님의 방식을 따라해보았습니다.
- 1단계. 그래들에 의존성 추가 [참고자료](http://honeymon.io/tech/2020/07/09/gradle-annotation-processor-with-querydsl.html)
```
  implementation 'com.querydsl:querydsl-core'
  implementation 'com.querydsl:querydsl-jpa'
  annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jpa" // querydsl JPAAnnotationProcessor 사용 지정
  annotationProcessor 'jakarta.persistence:jakarta.persistence-api' // java.lang.NoClassDefFoundError(javax.annotation.Entity) 발생 대응
  annotationProcessor 'jakarta.annotation:jakarta.annotation-api'
```
- 2단계. IDE에서 설정. -> annotation 설정이 필요하다.
- 3단계. config 생성
```java
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {
    @PersistenceContext
    public EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(em);
    }
}
```
- 4단계. 페이징 쿼리 요청을 받는 것도 dto를 만들어서
  - 페이지와 사이즈 오프셋 등에 대한 전처리가 dto에서 가능하다.
```java
@Getter
@Setter
public class PostSearch {

    private static final int MAX_SIZE = 2000;
    private int page;
    private int size;

    @Builder
    public PostSearch(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public long getOffset() {
        return (long) ( max(1,page) - 1) * Math.min(size,MAX_SIZE);
    }
}
```
- 5단계. Custom Repository 생성
```java
public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
```
- 6단계. Custom Repository 구현 클래스 생성
```java
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
```
- 7단계 PostRepository에서 extends
```java
public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {
}
```

## 게시글 수정
- 호돌맨님의 노하우가 깃들어 있음
- 1단계. 컨트롤러에서는 Patch를 사용하여 하나 만든다.
```java
  @PatchMapping("/posts/{postId}")
  public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit reuest) {
      return postService.edit(postId, reuest);
  }
```
- 2단계. service
  - postEditor라는 클래스를 선언하고 Post 클래스에서 PostEditor의 빌더를 리턴한다.
  - 빌더 패턴으로 필요한 값을 추가한뒤 builder로 끝맺고 객체를 생성한다.
```java
  @Transactional
  public PostResponse edit(Long id, PostEdit postEdit){
      Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));

      PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

      PostEditor postEditor = postEditorBuilder.title(postEdit.getTitle()).content(postEdit.getContent()).build();

      post.edit(postEditor);

      return new PostResponse(post);

  }
```
- 수정에 대해서 클라이언트의 요청이 있으면 return을 만들되 대게는 그렇게 하지 않는다.


## 게시글 수정( 오류서정, 보충내용)
- builder 패턴이 이해가 잘 되서 그냥 패스

## 게시글 삭제
- 게시글 삭제는 간단하여 패스

## 예외처리1
- 예외처리를 위해서 class를 만드는 방법에 대한 코드 작성 및 테스트 적용
- 이런식으로 적용하면 메시지를 일관되도록 가져갈 수 있고 PostNotFound라는 이름만으로 유추가 가능하다.
```java
public class PostNotFound extends RuntimeException{

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    public PostNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }
}
```


## 예외처리2
- 예외 처리의 종류가 많아지게 되면 관리가 어렵게 됩니다
- 그것을 해소하기 위해서 예외를 class 단위로 구분하는데 대장 역할을 할 수 있는 abstract class를 선언하여 처리합니다.
- 추상 클래스는 아래와 같습니다.
```java
@Getter
public abstract class BlogException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();
    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int statusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
```
- 추상클래스를 상속하는 예외는 두가지가 있습니다.
```java
@Getter
public class InvalidRequest extends BlogException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    private String fieldName;
    private String message;

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    public InvalidRequest(String message, Throwable cause) {
        super(MESSAGE, cause);
    }

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
```

```java
/**
 * 정책상 -> 404
 */
public class PostNotFound extends BlogException{

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    public PostNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
```

- 위 내용으로 예외를 던지면 아래 컨트롤러에서 처리합니다.
```java
  @ResponseBody
  @ExceptionHandler(BlogException.class)
  public ResponseEntity<ErrorResponse> ErrorResponse (BlogException e){

      int statusCode = e.statusCode();

      ErrorResponse body = ErrorResponse.builder()
              .code(String.valueOf(statusCode))
              .message(e.getMessage())
              .validation(e.getValidation())
              .build();


      return ResponseEntity.status(statusCode)
              .body(body);
  }
```
- 예외를 잘 정의해야된다.