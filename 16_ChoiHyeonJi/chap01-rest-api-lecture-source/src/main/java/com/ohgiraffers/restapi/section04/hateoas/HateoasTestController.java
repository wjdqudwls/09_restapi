package com.ohgiraffers.restapi.section04.hateoas;

import com.ohgiraffers.restapi.section02.responseentity.ResponseMessage;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*  HATEOAS : Hypermedia as the Engine of Application State
* - API 응답에 관련된 리소스의 링크를 포함시켜,
*   클라이언트가 다음에 수행할 수 있는 행위를 안내
* - 클라이언트가 하드 코딩된 URL 없이도 API를 탐색할 수 있음
* (API 진화에 유연하게 대응할 수 있다)
*
* - REST API 성숙도 모델 (0~3레벨) 중 3레벨을 달성하기 위한 기술
* LV 0 : REST 원칙을 거의 지키지 않음
* LV 1 : 자원 개념만 도입 ex) 앞에 user로 시작, book으로 시작됨 이런거만
* LV 2 : HTTP Method 활용 (GET, POST, PUT, DELETE .. ) + 상태 코드
* LV 3 : 응답 본문에 다음 수행할 수 있는 작업 링크를 포함하는 것 (응답 데이터 안에 url도 같이 넘어감 선택해서 사용 가능)
* */

@RestController
@RequestMapping("/hateoas")
public class HateoasTestController {

	private List<UserDTO> users;

	public HateoasTestController() {
		users = new ArrayList<>();

		users.add(
			new UserDTO(1, "user01", "pass01", "홍길동", new java.util.Date())
		);
		users.add(
			new UserDTO(2, "user02", "pass02", "유관순", new java.util.Date())
		);
		users.add(
			new UserDTO(3, "user03", "pass03", "이순신", new java.util.Date())
		);

	}

	@GetMapping("/users")
	public ResponseEntity<ResponseMessage> findAllUsers() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

    /* EntityModel : 데이터와 링크를 함께 담는 래퍼 클래스
    * -> Spring HATEOAS 핵심 클래스 */
		List<EntityModel<UserDTO>> userWithRel = // 이 유저가 함께 스면 좋을 관계 여기에 링크 추가함.
				users.stream().map( // 여기서의 map = 새 스트림 생성  users.stream() 기존 스트림의 요소를 하나씩 꺼내와서 RETURN 값 이용해 새로 생성
					user -> EntityModel.of(
						user,
						linkTo(
							methodOn(HateoasTestController.class)
							.findUserByNo(user.getNo())
						) // http://localhost:8080/hateoas/users/{userNo}
						.withSelfRel(), // self 링크 추가 (현재 요청 주소 findAllUsers 나의 링크를 다시 한번 더 담아서 보냄)
						linkTo( // 한번 더 담김
							methodOn(HateoasTestController.class)
							.findAllUsers()
						) //  http://localhost:8080/hateoas/users 이런식으로 링크 생성
						.withRel("users") //users 관계 링크 // 클라이언트가 다시 전체 유저를 조회할 수 있는 경로 제공 (링크의 이름을 users라고 함 )
					)
				).collect(Collectors.toList());

		Map<String, Object> responseMap = new HashMap<>(); // 요청용
		responseMap.put("users", userWithRel); // 전체 조회된 User + 링크 리스트 반환  // 지금 만든 리스트 통으로 담음 user가 조회됨.

		ResponseMessage responseMessage
			= new ResponseMessage(200, "조회 성공", responseMap); // 조회 성공으로 반환

		return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

	}


	@GetMapping("/users/{userNo}")
	public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(
			new MediaType("application", "json", Charset.forName("UTF-8"))
		);

		UserDTO foundUser
			= users.stream().filter(user -> user.getNo() == userNo)
				.collect(Collectors.toList()).get(0);
		System.out.println(foundUser);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("user", foundUser);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "조회 성공", responseMap));
	}
}