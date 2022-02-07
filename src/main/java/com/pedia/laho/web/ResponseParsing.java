package com.pedia.laho.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pedia.laho.web.dto.MovieResponseDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ResponseParsing {

    private static final Logger logger = Logger.getLogger(ResponseParsing.class.getName());

    public static String boxOfficeMovieInfoParsing(String jString, String key) throws IOException {
        List<MovieResponseDto> movieInfoList = new ArrayList<>();

        JsonObject jObject = JsonParser.parseString(jString).getAsJsonObject();
        JsonObject boxOfficeResult= jObject.getAsJsonObject("boxOfficeResult");
        JsonArray dailyBoxOfficeList = boxOfficeResult.getAsJsonArray("dailyBoxOfficeList");

        for(Object boxOffice : dailyBoxOfficeList) {
            try {
                MovieResponseDto movieResponseDto;
                JsonObject obj = (JsonObject) boxOffice;

                String movieId = obj.get("movieCd").getAsString();
                String rank = obj.get("rank").getAsString();
                String title = obj.get("movieNm").getAsString();
                String openDt = obj.get("openDt").getAsString();

                String posterAndPlotResponse = posterAndPlotResponse(title, openDt, key);
                Map<String, String> posterAndPlot = posterAndplotParsing(posterAndPlotResponse);

                String posters = posterAndPlot.get("posters");
                String plot = posterAndPlot.get("plot");

                movieResponseDto = MovieResponseDto.builder()
                        .movieId(movieId).rank(rank).title(title)
                        .openDt(openDt).posters(posters).plot(plot)
                        .build();

                movieInfoList.add(movieResponseDto);
            } catch (NullPointerException nullPointerException) {
                logger.info(nullPointerException.getMessage());
            }
        }
        return new Gson().toJson(movieInfoList);
    }

    public static String searchMovieInfoParsing(String jString, String key) throws IOException {
        List<MovieResponseDto> movieInfoList = new ArrayList<>();

        JsonObject jObject = JsonParser.parseString(jString).getAsJsonObject();
        JsonObject movieResult= jObject.getAsJsonObject("movieListResult");
        JsonArray movieList = movieResult.getAsJsonArray("movieList");

        for(Object movie : movieList) {
            try {
                MovieResponseDto movieResponseDto;
                JsonObject obj = (JsonObject) movie;

                String movieId, title, openDt, nation;
                String posterAndPlotResponse;
                Map<String, String> posterAndPlot;

                movieId = obj.get("movieCd").getAsString();
                title = obj.get("movieNm").getAsString();
                openDt = obj.get("openDt").getAsString();
                nation = obj.get("nationAlt").getAsString();

                posterAndPlotResponse = posterAndPlotResponse(title, openDt, key);
                posterAndPlot = posterAndplotParsing(posterAndPlotResponse);

                String posters = posterAndPlot.get("posters");
                String plot = posterAndPlot.get("plot");

                movieResponseDto = MovieResponseDto.builder()
                        .movieId(movieId).title(title).openDt(openDt)
                        .nation(nation).posters(posters).plot(plot)
                        .build();

                movieInfoList.add(movieResponseDto);
            } catch (NullPointerException nullPointerException) {
                logger.info(nullPointerException.getMessage());
            }
        }

        return new Gson().toJson(movieInfoList);
    }

    public static String posterAndPlotResponse(String title, String openDt, String key) throws IOException {
        String keyWord = title.replaceAll(" ","").replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z]", "");
        String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";

        try {
            url += "&ServiceKey=" + key + "&detail=" + "Y" +
                    "&query=" + keyWord +
                    "&releaseDts=" + openDt;
            return ApiRequest.apiRequest(url);
        } catch (NullPointerException nullPointerException) {
            logger.info(nullPointerException.getMessage());
        }

        return "";
    }

    public static Map<String, String> posterAndplotParsing(String apiResponse) {

        Map<String, String> result = new HashMap<>();

        try {
            logger.info(apiResponse);
            JsonObject jObject = JsonParser.parseString(apiResponse).getAsJsonObject();
            JsonArray data = jObject.getAsJsonArray("Data");
            JsonObject dataObject = data.get(0).getAsJsonObject();
            JsonArray resultArray = dataObject.getAsJsonArray("Result");

            JsonObject obj= resultArray.get(0).getAsJsonObject();

            String posters = obj.get("posters").getAsString();
            String plot = obj.getAsJsonObject("plots")
                    .getAsJsonArray("plot").get(0).getAsJsonObject()
                    .get("plotText").getAsString();

            result.put("posters", posters);
            result.put("plot", plot);

            return result;
        } catch (NullPointerException nullPointerException) {
            logger.info(nullPointerException.getMessage());
        }

        return result;
    }
}
