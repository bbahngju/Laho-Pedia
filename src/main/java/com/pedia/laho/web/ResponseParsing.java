package com.pedia.laho.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pedia.laho.web.dto.*;

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

    public static String movieDetailsParsing(String jString) {
        List<MovieDetailResponseDto> movieInfoList = new ArrayList<>();

        JsonObject jObject = JsonParser.parseString(jString).getAsJsonObject();
        JsonObject movieInfoResult= jObject.getAsJsonObject("movieInfoResult");
        JsonObject movieInfo = movieInfoResult.getAsJsonObject("movieInfo");

        String title = movieInfo.get("movieNm").getAsString();
        String showTm = movieInfo.get("showTm").getAsString();
        String openDt = movieInfo.get("openDt").getAsString();

        List<NationInfo> nations = new ArrayList<>();
        JsonArray nationsArr = movieInfo.getAsJsonArray("nations");
        for(Object o : nationsArr) {
            JsonObject nationObj = (JsonObject) o;
            String nation = nationObj.get("nationNm").getAsString();

            nations.add(new NationInfo(nation));
        }

        List<GenreInfo> genres = new ArrayList<>();
        JsonArray genresArr = movieInfo.getAsJsonArray("genres");
        for(Object o : genresArr) {
            JsonObject genreObj = (JsonObject) o;
            String genre = genreObj.get("genreNm").getAsString();

            genres.add(new GenreInfo(genre));
        }

        List<DirectorInfo> directors = new ArrayList<>();
        JsonArray directorsArr = movieInfo.getAsJsonArray("directors");
        for(Object o : directorsArr) {
            JsonObject directorObj = (JsonObject) o;
            String director = directorObj.get("peopleNm").getAsString();

            directors.add(new DirectorInfo(director));
        }

        List<ActorInfo> actors = new ArrayList<>();
        JsonArray actorsArr = movieInfo.getAsJsonArray("actors");
        for(Object o : actorsArr) {
            JsonObject actorObj = (JsonObject) o;
            String actor = actorObj.get("peopleNm").getAsString();
            String cast = actorObj.get("cast").getAsString();

            actors.add(new ActorInfo(actor, cast));
        }

        movieInfoList.add(MovieDetailResponseDto.builder()
                .title(title).showTm(showTm).openDt(openDt)
                .nations(nations).genres(genres).directors(directors)
                .actors(actors).build());
        String result = new Gson().toJson(movieInfoList);
        logger.info("movieInfoList : " + result);
        return result;
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
