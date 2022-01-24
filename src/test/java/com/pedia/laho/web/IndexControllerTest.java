package com.pedia.laho.web;

import com.pedia.laho.web.dto.SearchResponseDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 메인페이지_로딩() {
        //when
        String body = this.restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("라호 피디아 :>");
    }

    @Test
    public void JSON_데이터_파싱() throws ParseException {
        //given
        IndexController indexController = new IndexController();
        String data = "{\"Query\":\"해리포터\"," + "\"KMAQuery\":\"해리포터\",\"TotalCount\":7," +
                "\"Data\":[{\"CollName\":\"kmdb_new2\",\"TotalCount\":7,\"Count\":7,\"Result\":" +
                "[{\"DOCID\":\"F20511\"," +
                "\"movieId\":\"F\"," +
                "\"movieSeq\":\"20511\"," +
                "\"title\":\" 해리 포터와 불사조 기사단\"," +
                "\"titleEng\":\"Harry Potter and the Order of the Phoenix\"," +
                "\"titleOrg\":\"Harry Potter and the Order of the Phoenix\"," +
                "\"prodYear\":\"2007\"," +
                "\"directors\":{\"director\":[{\"directorNm\":\"데이빗 예이츠\",\"directorEnNm\":\"David Yates\",\"directorId\":\"00066352\"}]}," +
                "\"actors\":{\"actor\":[{\"actorNm\":\"다니엘 래드클리프\",\"actorEnNm\":\"Daniel Radcliffe\",\"actorId\":\"00048945\"}," +
                "{\"actorNm\":\"엠마 왓슨\",\"actorEnNm\":\"Emma Watson\",\"actorId\":\"00069882\"}," +
                "{\"actorNm\":\"루퍼트 그린트\",\"actorEnNm\":\"Rupert Grint\",\"actorId\":\"00114057\"}," +
                "{\"actorNm\":\"제이슨 아이삭스\",\"actorEnNm\":\"Jason Isaacs\",\"actorId\":\"00086862\"}," +
                "{\"actorNm\":\"이반나 린치\",\"actorEnNm\":\"Evanna Lynch\",\"actorId\":\"00070829\"}]}," +
                "\"nation\":\"영국,미국\"," +
                "\"company\":\"Heyday Film ,Warner Bros.\"," +
                "\"plots\":{\"plot\":[{\"plotLang\":\"한국어\",\"plotText\":\"길고도 지루한 여름 날 호그와트 마법학교 다섯 번째 해를 기다리고 있는 해리포터(다니엘 래드클리프). 이모부 더즐리 식구들과 참고 사는 것도 지겨운데다 친구 론(루퍼트 그린트)과 헤르미온느(엠마 왓슨)에게서는 편지 한 통 오지 않는다. 그러던 중 예상치 못했던 편지 한 장이 도착한다. 그것은 해리가 학교 밖인 리틀 위닝에서 얄미운 사촌 두들리, 즉 머글 앞에서 디멘터들의 공격을 막는 마법을 사용했기 때문에 호그와트 마법학교에서 퇴학 당하게 되었다는 소식이었다. 앞이 캄캄한 해리. 갑자기 어둠의 마법사 오러들이 나타나 해리를 불사조 기사단의 비밀 장소로 데리고 간다. 시리우스(게리 올드만)를 위시한 불사조 기사단을 만난 해리는 과거, 부모님들의 활약상을 알게 되어 힘을 얻고, 자신을 퇴학시키기 위해 마법부 장관 코넬리우스 퍼지(로버트 하디)가 법정에 세우지만 덤블도어 교장(마이클 갬볼 경)의 중재 덕분에 무죄 판결까지 받는다.하지만 예언자 일보는 볼드모트(랄프 파인즈)가 돌아왔다는 해리의 말이 새빨간 거짓말이라며 비난하고 학생들 역시 해리를 의심하며 따돌린다. 게다가 자신이 가장 힘들어 할 때 도움을 주던 덤블도어 교장까지도 이유 없이 해리를 멀리하고….한 편, 덤블도어도 못마땅한데 해리의 퇴학마저 무산이 되자 마법부 장관은 ‘어둠의 마법방어술’ 과목에 돌로레스 엄브릿지(이멜다 스털톤)를 교수로 임명한다. 하지만 엄브짓지의 마법방어술 수업은 학생들이 어둠의 힘으로부터 스스로를 지켜내기는커녕 오히려 곤경에 빠지게 한다. 이에 헤르미온느와 론은 해리의 능력을 믿고 자칭 ‘덤블도어의 군대’라고 명명한 비밀단체를 조직한다. 해리는 어둠의 마법에 맞서 스스로를 지켜낼 수 있는 방법을 학생들에게 가르쳐주며 앞으로 닥칠 격전에 대비시킨다. 그러나 밤마다 불길한 사건을 예견하는 악몽에 시달리는 해리. 이제 볼드모트와의 대결이 머지 않았음을 느끼게 된다.시리우스가 공격 당하는 악몽을 꾼 해리는 덤블도어 군대와 함께 마법부 미스터리 부서 예언의 방으로 향한다. 그리고 이어 나타난 죽음을 먹는 자들….\"}]}," +
                "\"runtime\":\"137\"," +
                "\"rating\":\"전체관람가\"," +
                "\"genre\":\"액션,어드벤처,가족,판타지\"," +
                "\"kmdbUrl\":\"https://www.kmdb.or.kr/db/kor/detail/movie/F/20511\"," +
                "\"type\":\"극영화\"," +
                "\"use\":\"극장용\"," +
                "\"ratedYn\":\"Y\"," +
                "\"repRatDate\":\"20070619\"," +
                "\"repRlsDate\":\"20070712\"," +
                "\"posters\":\"http://file.koreafilm.or.kr/thm/02/00/00/95/tn_DPF000126.jpg\"}]}]}";

        String title = "해리 포터와 불사조 기사단";
        String prodYear = "2007";
        String nation = "영국,미국";
        String posters = "http://file.koreafilm.or.kr/thm/02/00/00/95/tn_DPF000126.jpg";

        //when
        List<SearchResponseDto> all = indexController.searchParsing(data);

        //then
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getProdYear()).isEqualTo(prodYear);
        assertThat(all.get(0).getNation()).isEqualTo(nation);
        assertThat(all.get(0).getPosters()).isEqualTo(posters);

    }
}
