package com.pedia.laho.web;

import com.google.gson.*;
import com.pedia.laho.web.dto.SearchResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@PropertySource("classpath:application-api-key.properties")
@RestController
public class IndexController {
    private static Logger logger = Logger.getLogger(IndexController.class.getName());

    @Value("${kmdb.api.key}")
    private String kmdbKey;

    @GetMapping("/")
    public String index() {
        return "Hello, LAHO-PEDIA";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public List<SearchResponseDto> search(@RequestParam("keyWord") String keyWord) throws IOException {
        String urlBuilder = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2" +
                "&detail=" + "Y" +
                "&query=" + "\"" + keyWord + "\"" +
                "&ServiceKey=" + kmdbKey;
        URL url = new URL(urlBuilder);
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
            line = line.replaceFirst("\"\"", "\"");
            line = line.replaceFirst("\"\"", "\"");

            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        logger.info(sb.toString());

        return searchParsing(sb.toString());
    }

    public List<SearchResponseDto> searchParsing(String jString) {
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

        return result;
    }
}
