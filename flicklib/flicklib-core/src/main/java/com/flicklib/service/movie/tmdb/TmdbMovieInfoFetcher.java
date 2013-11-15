package com.flicklib.service.movie.tmdb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;
import com.flicklib.service.SourceLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;

public class TmdbMovieInfoFetcher extends AbstractMovieInfoFetcher {
	
	public static final MovieService TMDB = new MovieService("TMDB", "TheMovieDB", "http://www.themoviedb.org/");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TmdbMovieInfoFetcher.class);
	
	private static final String LANG_EN = "en";
	private static final String TYPE_JSON = "json";
	//private static final String TYPE_YAML = "yaml";
	//private static final String TYPE_XML = "xml";
	
	private static final String BASE = "http://api.themoviedb.org/2.1";
	
	private final String apiKey;
	private final SourceLoader loader;
	private final Gson gson;
	
	@Inject
	public TmdbMovieInfoFetcher(final SourceLoader loader, final String apiKey) {
		this.apiKey = apiKey;
		this.loader = loader;
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	@Override
	public List<? extends MovieSearchResult> search(String title) throws IOException {
		return search(title, (String)null);
	}
	
	@Override
	public List<? extends MovieSearchResult> search(String title, String year) throws IOException {
		TmdbMovie[] tmdbMovies = search(title, Integer.valueOf(year));
		List<MoviePage> moviePages = new ArrayList<MoviePage>();
		for(TmdbMovie tmdbMovie:tmdbMovies){
			MoviePage moviePage = toMoviePage(tmdbMovie);
			moviePages.add(moviePage);
		}
		return moviePages;
	}
	
	@Override
	public MoviePage getMovieInfo(String idForSite) throws IOException {
		// http://api.themoviedb.org/2.1/Movie.getInfo/en/xml/APIKEY/187
		String url = BASE + "/Movie.getInfo/" + LANG_EN + "/" + TYPE_JSON + "/" + apiKey + "/" + idForSite;
		String result = loader.url(url)
				.setHeader("Accept","application/json").get().getString();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(result).getAsJsonArray().get(0);
		TmdbMovie tmdbMovie = gson.fromJson(element, TmdbMovie.class);
		return toMoviePage(tmdbMovie);
	}
	
	@Override
	public MovieService getService() {
		return TMDB;
	}
	
	private TmdbMovie[] search(final String title, final Integer year) throws IOException{
		String search = title;
		if(year != null){
			search = search + " " + year;
		}
		String encodedSearch = urlEncodeUtf8(search);
		// remove question mark ast this doesn't work
		encodedSearch = encodedSearch.replace("%3F", "");
		
		String url = BASE + "/Movie.search/" + LANG_EN + "/" + TYPE_JSON + "/" + apiKey + "/" + encodedSearch;
		String result = loader.url(url)
					.setHeader("Accept","application/json").get().getString();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(result);
		if(element.isJsonArray() && element.getAsJsonArray().get(0).isJsonPrimitive()){
			String message = element.getAsJsonArray().get(0).getAsString();
			LOGGER.warn("%s returns %s", url, message);
			return new TmdbMovie[0];
		}
		//System.out.println(gson.toJson(element));
		return gson.fromJson(element, TmdbMovie[].class);
	}
	

	private MoviePage toMoviePage(TmdbMovie tmdbMovie) {
		MoviePage moviePage = new MoviePage();
		moviePage.setTitle(tmdbMovie.name);
		moviePage.setIdForSite(String.valueOf(tmdbMovie.id));
		moviePage.setScore((int) Math.round(Double.parseDouble(tmdbMovie.rating) * 10));
		return moviePage;
	}

	private String urlEncodeUtf8(final String string) {
		try {
			return URLEncoder.encode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
