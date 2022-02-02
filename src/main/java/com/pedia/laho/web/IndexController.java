package com.pedia.laho.web;

import com.google.gson.*;
import com.pedia.laho.web.dto.BoxOfficeResponseDto;
import com.pedia.laho.web.dto.SearchResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@PropertySource("classpath:application-api-key.properties")
@RestController
public class IndexController {
    private static Logger logger = Logger.getLogger(IndexController.class.getName());

    @Value("${kmdb.api.key}")
    private String kmdbKey;

    @Value("${boxOffice.api.key}")
    private String boxOfficeKey;

    @GetMapping("/")
    @ResponseBody
    public String index() throws IOException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");

        calendar.add(Calendar.DATE, -1);
        String day = dayFormat.format(calendar.getTime());

        String urlBuilder = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?" +
                "key=" + boxOfficeKey +
                "&targetDt=" + day;

        String response = apiRequest(urlBuilder);
        return boxOfficeParsing(response);
    }

    @GetMapping("/api/search")
    @ResponseBody
    public String search(HttpServletRequest request) throws IOException {

        String keyWord = request.getParameter("keyWord").replaceAll(" ","").replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z]", "");
        String urlBuilder = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2" +
                "&detail=" + "Y" +
                "&query=" + "\"" + keyWord + "\"" +
                "&ServiceKey=" + kmdbKey;

        String response = apiRequest(urlBuilder);
        return searchParsing(response);
    }

    public String searchParsing(String jString) {
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

    public String boxOfficeParsing(String jString) {
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

            boxOfficeResponseDto = new BoxOfficeResponseDto(rank.trim(), title.trim(), prodYear.trim());
            result.add(boxOfficeResponseDto);
        }

        String boxResult = new Gson().toJson(result);
        logger.info(boxResult);
        return boxResult;
    }

    public String apiRequest(String urlBuilder) throws IOException{
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
            line = line.replaceAll("!HS", "").replaceAll("!HE", "");
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        return sb.toString();
    }
}
