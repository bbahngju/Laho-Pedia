package com.pedia.laho.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pedia.laho.web.dto.SearchResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
    private static Logger logger = Logger.getLogger(IndexControllerTest.class.getName());

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
    public void JSON_데이터_파싱() {
        //given
        IndexController indexController = new IndexController();

        String title = "해리 포터와 불사조 기사단";
        String prodYear = "2007";
        String nation = "영국,미국";
        String posters = "http://file.koreafilm.or.kr/thm/02/00/00/95/tn_DPF000126.jpg";

        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("title", title);
        resultObj.addProperty("prodYear", prodYear);
        resultObj.addProperty("nation", nation);
        resultObj.addProperty("posters", posters);

        JsonArray resultArr = new JsonArray();
        resultArr.add(resultObj);

        JsonObject dataObj = new JsonObject();
        dataObj.add("Result", resultArr);

        JsonArray dataArr = new JsonArray();
        dataArr.add(dataObj);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Query", "\"해리포터\"");
        jsonObject.add("Data", dataArr);

        logger.info(jsonObject.toString());
        //when
        List<SearchResponseDto> all = indexController.searchParsing(jsonObject.toString());

        //then
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getProdYear()).isEqualTo(prodYear);
        assertThat(all.get(0).getNation()).isEqualTo(nation);
        assertThat(all.get(0).getPosters()).isEqualTo(posters);

    }
}
