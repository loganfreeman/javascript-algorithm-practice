package com.flicklib.service.movie.imdb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.api.AbstractMovieInfoFetcher;
import com.flicklib.domain.MoviePage;
import com.flicklib.domain.MovieSearchResult;
import com.flicklib.domain.MovieService;
import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class ImdbApiFetcher extends AbstractMovieInfoFetcher {
	
	final static Logger LOG = LoggerFactory.getLogger(ImdbApiFetcher.class);
	
	final static MovieService IMDB_API = new MovieService("IMDB_API", "IMDB Api", "http://www.imdbapi.com");

	private final SourceLoader loader;
	private final Gson gson;

	public ImdbApiFetcher(SourceLoader loader) {
		this.loader = loader;
		this.gson = new Gson();
	}

	@Override
	public List<? extends MovieSearchResult> search(String title) throws IOException {
		Source source = loader.loadSource(generateSearchUrl(title));
		// it seems that it's returns the first result, only ...
		return Collections.singletonList(parseJson(source));
	}

	@Override
	public MoviePage getMovieInfo(String idForSite) throws IOException {
		Source source = loader.loadSource(generateUrl(idForSite));
		return parseJson(source);

	}

	private MoviePage parseJson(Source source) throws IOException {
		LOG.info("response from " + source.getUrl() + " type is :" + source.getContentType());

		try {
			ImdbApiMovie imdbApiMovie = gson.fromJson(source.getContent(), ImdbApiMovie.class);
			return parseInfo(imdbApiMovie);
		} catch (JsonParseException e) {
			throw new IOException(e);
		}
	}

	private MoviePage parseInfo(ImdbApiMovie imdbApiMovie) {
		MoviePage mp = new MoviePage(IMDB_API);
		mp.setIdForSite(imdbApiMovie.id);
		mp.setTitle(imdbApiMovie.title);
		mp.setYear(imdbApiMovie.year);
		mp.setPlot(imdbApiMovie.plot);
		mp.setVotes(imdbApiMovie.votes);
		mp.setImgUrl(imdbApiMovie.poster);
		mp.setScore((int) Math.round(Double.parseDouble(imdbApiMovie.rating) * 10));
//		for (String genre : obj.optString("Genre", "").split(",")) {
//			mp.getGenres().add(genre);
//		}
//		for (String director : obj.optString("Director", "").split(",")) {
//			mp.getDirectors().add(director);
//		}
//		for (String actor : obj.optString("Actors", "").split(",")) {
//			mp.getActors().add(actor);
//		}
		return mp;
	}

	private String generateUrl(String id) {
		return "http://www.imdbapi.com/?i=" + id + "&plot=full&r=JSON&tomatoes=true";
	}

	private String generateSearchUrl(String id) {
		try {
			return "http://www.imdbapi.com/?t=" + URLEncoder.encode(id,"UTF8") + "&plot=full&r=JSON&tomatoes=true";
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public MovieService getService() {
	    return IMDB_API;
	}
}
