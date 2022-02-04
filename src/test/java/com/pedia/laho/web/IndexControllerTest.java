package com.pedia.laho.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pedia.laho.web.controller.IndexController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Map;
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
    public void KMDB_포스터_줄거리_파싱() throws IOException {
        //given
        String posters = "http://file.koreafilm.or.kr/thm/02/00/00/95/tn_DPF000126.jpg";
        String plot = "이것은 줄거리입니다만 :)";

        JsonObject plotTextObj = new JsonObject();
        plotTextObj.addProperty("plotText", plot);

        JsonArray plotArr = new JsonArray();
        plotArr.add(plotTextObj);

        JsonObject plotsObj = new JsonObject();
        plotsObj.add("plot", plotArr);

        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("posters", posters);
        resultObj.add("plots", plotsObj);

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
        Map<String, String> all = ResponseParsing.posterAndplotParsing(jsonObject.toString());

        //then
        assertThat(all.get("posters")).isEqualTo(posters);
        assertThat(all.get("plot")).isEqualTo(plot);
    }
}
