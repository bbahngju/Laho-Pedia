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

    @Value("${kobis.api.key}")
    private String kobisKey;

    @GetMapping("/")
    @ResponseBody
    public String mainPage() throws IOException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");

        calendar.add(Calendar.DATE, -1);
        String day = dayFormat.format(calendar.getTime());

        String apiUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?" +
                "key=" + kobisKey +
                "&targetDt=" + day;

        String response = ApiRequest.apiRequest(apiUrl);
        return ResponseParsing.boxOfficeMovieInfoParsing(response, kmdbKey);
    }

    @GetMapping("/api/search")
    @ResponseBody
    public String search(HttpServletRequest request) throws IOException {

        String keyWord = request.getParameter("keyWord").replaceAll(" ","").replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z]", "");
        String urlBuilder = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?" +
                "key=" + kobisKey +
                "&movieNm=" + keyWord;

        String response = ApiRequest.apiRequest(urlBuilder);
        return ResponseParsing.searchMovieInfoParsing(response, kmdbKey);
    }

}
