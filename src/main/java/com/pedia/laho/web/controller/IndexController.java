package com.pedia.laho.web.controller;

import com.pedia.laho.web.ApiRequest;
import com.pedia.laho.web.ResponseParsing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@PropertySource("classpath:application-api-key.properties")
@RestController
public class IndexController {
    private static Logger logger = Logger.getLogger(IndexController.class.getName());

    @Value("${kmdb.api.key}")
    private String kmdbKey;

    //TODO: 삭제
    @Value("${boxOffice.api.key}")
    private String boxOfficeKey;

    @GetMapping("/")
    @ResponseBody
    public String index() throws IOException {
        //TODO: DB 접근 후 데이터 가져오기
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");

        calendar.add(Calendar.DATE, -1);
        String day = dayFormat.format(calendar.getTime());

        String urlBuilder = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?" +
                "key=" + boxOfficeKey +
                "&targetDt=" + day;

        String response = ApiRequest.apiRequest(urlBuilder);
        return ResponseParsing.boxOfficeParsing(response, kmdbKey);
    }

    @GetMapping("/api/search")
    @ResponseBody
    public String search(HttpServletRequest request) throws IOException {

        //Query가 있을 때에만 추가되도록
        String keyWord = request.getParameter("keyWord").replaceAll(" ","").replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z]", "");
        String urlBuilder = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2" +
                "&detail=" + "Y" +
                "&query=" + "\"" + keyWord + "\"" +
                "&ServiceKey=" + kmdbKey;

        String response = ApiRequest.apiRequest(urlBuilder);
        return ResponseParsing.searchParsing(response);
    }

}
