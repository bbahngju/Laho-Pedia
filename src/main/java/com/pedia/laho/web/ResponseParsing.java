package com.pedia.laho.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pedia.laho.web.dto.BoxOfficeResponseDto;
import com.pedia.laho.web.dto.SearchResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//TODO: 리팩토링 필요
public class ResponseParsing {
    private static Logger logger = Logger.getLogger(ResponseParsing.class.getName());

    //TODO: void 설정
    public static String boxOfficeParsing(String jString, String key) throws IOException {
        List<BoxOfficeResponseDto> result = new ArrayList<>();

        JsonObject jObject = JsonParser.parseString(jString).getAsJsonObject();
        JsonObject jObjectObject= jObject.getAsJsonObject("boxOfficeResult");
        JsonArray jArray = jObjectObject.getAsJsonArray("dailyBoxOfficeList");

        for(Object o : jArray) {
            BoxOfficeResponseDto boxOfficeResponseDto;
            JsonObject obj = (JsonObject) o;

            String rank = obj.get("rank").getAsString();
            String title = obj.get("movieNm").getAsString();
            String prodYear = obj.get("openDt").getAsString();

            //TODO: 포스터를 얻기 위한 과정 (리팩토링 필요)
            String keyWord = title.replaceAll(" ","").replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z]", "");
            String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2" +
                    "&detail=" + "Y" +
                    "&query=" + "\"" + keyWord + "\"" +
                    "&releaseDts=" + prodYear +
                    "&ServiceKey=" + key;

            logger.info(key);
            String apiResponse = ApiRequest.apiRequest(url);

            logger.info("apiResponse" + apiResponse);

            JsonObject jO = JsonParser.parseString(apiResponse).getAsJsonObject();
            JsonArray jA = jO.getAsJsonArray("Data");
            JsonObject jAO = jA.get(0).getAsJsonObject();
            JsonArray jAOA = jAO.getAsJsonArray("Result");

            Object ob = jAOA.get(0);
            JsonObject jobj= (JsonObject) ob;
            String posters = jobj.get("posters").getAsString();

            boxOfficeResponseDto = new BoxOfficeResponseDto(rank.trim(), title.trim(), prodYear.trim(), posters.trim());
            result.add(boxOfficeResponseDto);
        }

        String boxResult = new Gson().toJson(result);
        logger.info(boxResult);

        //TODO: Domain 불러서 넣기
        return boxResult;
    }

    public static String searchParsing(String jString) {
        List<SearchResponseDto> result = new ArrayList<>();

        JsonObject jObject = JsonParser.parseString(jString).getAsJsonObject();
        JsonArray jArray = jObject.getAsJsonArray("Data");
        JsonObject jArrayObject = jArray.get(0).getAsJsonObject();
        JsonArray jArrayObjectArray = jArrayObject.getAsJsonArray("Result");

        for (Object o : jArrayObjectArray) {
            SearchResponseDto searchResponseDto;
            JsonObject obj= (JsonObject) o;

            String title = obj.get("title").getAsString();
            String prodYear = obj.get("prodYear").getAsString();
            String nation = obj.get("nation").getAsString();
            String posters = obj.get("posters").getAsString();

            searchResponseDto = new SearchResponseDto(title.trim(), prodYear.trim(), nation.trim(), posters.trim());
            result.add(searchResponseDto);
        }

        String searchResult = new Gson().toJson(result);
        logger.info(searchResult);
        return searchResult;
    }
}
