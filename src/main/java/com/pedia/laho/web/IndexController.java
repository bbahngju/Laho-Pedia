package com.pedia.laho.web;

import com.pedia.laho.web.dto.SearchResponseDto;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@PropertySource("classpath:application-api-key.properties")
@Controller
public class IndexController {
    private static Logger logger = Logger.getLogger(IndexController.class.getName());

    @Value("${kmdb.api.key}")
    private String kmdbKey;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public List<SearchResponseDto> search(@RequestParam("keyWord") String keyWord) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
        urlBuilder.append("&detail=" + "Y");
        urlBuilder.append("&query=" + "\"" + keyWord + "\"");
        urlBuilder.append("&ServiceKey="+kmdbKey);

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setDoOutput(true);

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        logger.info(sb.toString());
        String jString = sb.toString();

        return searchParsing(jString);
    }

    public List<SearchResponseDto> searchParsing(String jString) throws ParseException {
        List<SearchResponseDto> result = new ArrayList<>();

        JSONParser jParser = new JSONParser();
        JSONObject jObject = (JSONObject) jParser.parse(jString);
        JSONArray jArray = (JSONArray) jObject.get("Data");
        JSONObject jArrayObject = (JSONObject) jArray.get(0);
        JSONArray jArrayObjectArray = (JSONArray) jArrayObject.get("Result");

        for (Object o : jArrayObjectArray) {
            SearchResponseDto searchResponseDto;
            JSONObject obj = (JSONObject) o;
            String title = (String) obj.get("title");
            String prodYear = (String) obj.get("prodYear");
            String nation = (String) obj.get("nation");
            String posters = (String) obj.get("posters");

            searchResponseDto = new SearchResponseDto(title.trim(), prodYear.trim(), nation.trim(), posters.trim());
            result.add(searchResponseDto);
        }

        return result;
    }
}
