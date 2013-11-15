package com.flicklib.service.movie.blippr;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.MovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;
import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BlipprInfoFetcher implements MovieInfoFetcher {

    private final static Logger LOG = LoggerFactory.getLogger(BlipprInfoFetcher.class);
    private final static MovieService BLIPPR = new MovieService("BLIPPR", "Blippr.com", "http://www.blippr.com");

    private static final String ROOT = "http://api.blippr.com/v2/";

    private final SourceLoader sourceLoader;
    private final Gson gson;

    @Inject
    public BlipprInfoFetcher(final SourceLoader sourceLoader) {
        this.sourceLoader = sourceLoader;
        this.gson = new GsonBuilder().registerTypeAdapter(BlipprMetaData.class, new BlipprMetaDataDeserializer()).create();
    }

    @Override
    public MoviePage fetch(String title) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MoviePage getMovieInfo(String idForSite) throws IOException {
        URL url = new URL(getUrlForID(idForSite));
        Source source = sourceLoader.loadSource(url.toExternalForm());
        LOG.info(url.toString());
        LOG.info(source.getContentType());
        LOG.info(source.getContent());
        MoviePage page = new MoviePage();
        try {
        	BlipprResponse response = gson.fromJson(source.getContent(), BlipprResponse.class);
            resultToMovie(page, response.title);
        } catch (JsonParseException e) {
            throw new IOException(e.getMessage(), e);
        }
        return page;
    }

    private String getUrlForID(String idForSite) {
        return ROOT + "titles/" + idForSite + ".json";
    }

    @Override
    public List<MovieSearchResult> search(String title) throws IOException {
        URL url = new URL(ROOT + "movies/search.json?query=" + URLEncoder.encode(title, "UTF-8"));
        LOG.info("Searching with :" + url.toString());
        Source source = sourceLoader.loadSource(url.toExternalForm());
        return parseSearchResult(source);
    }

    private List<MovieSearchResult> parseSearchResult(Source source) throws IOException {
        LOG.info("response content type is : " + source.getContentType());
        LOG.info("response is : " + source.getContent());

        List<MovieSearchResult> results = new ArrayList<MovieSearchResult>();
        try {
        	BlipprSearchResponse blipprSearch = gson.fromJson(source.getContent(), BlipprSearchResponse.class);
            List<BlipprTitle> titles = blipprSearch.search.titles.title;
            for (BlipprTitle title:titles) {
                MoviePage movieSearchResult = new MoviePage();
                resultToMovie(movieSearchResult, title);
                results.add(movieSearchResult);
            }
        } catch (JsonParseException e) {
            throw new IOException(e.getMessage(), e);
        }
        return results;
    }

    private void resultToMovie(MoviePage movie, BlipprTitle title) {
        movie.setIdForSite(String.valueOf(title.id));
        movie.setUrl(getUrlForID(movie.getIdForSite()));
        movie.setService(BLIPPR);
        movie.setTitle(title.name);
        movie.setScore((int) Double.parseDouble(title.score));
        if(title.images != null){
        	if(title.images.medium != null){
        		 movie.setImgUrl(title.images.medium);
        	}else if(title.images.small != null){
        		movie.setImgUrl(title.images.small);
        	}else if(title.images.thumb != null){
        		movie.setImgUrl(title.images.thumb);
        	}
        }
       	movie.setVotes(title.reviewCount);
        movie.setDescription(title.summary);

        if (title.metadata != null) {
            movie.setYear(title.metadata.year);
            for(String genre:title.metadata.genres){
            	movie.getGenres().add(genre);
            }
            for(String actor:title.metadata.actors){
            	movie.getActors().add(actor);
            }
            for(String director:title.metadata.directors){
            	movie.getDirectors().add(director);
            }
        }
    }

    @Override
    public List<? extends MovieSearchResult> search(String title, String year) throws IOException {
        return search(title);
    }
    
    @Override
    public MovieService getService() {
        return BLIPPR;
    }

}
