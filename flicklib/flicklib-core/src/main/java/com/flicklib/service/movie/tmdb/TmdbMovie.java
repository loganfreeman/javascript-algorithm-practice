package com.flicklib.service.movie.tmdb;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TmdbMovie{
	
	public long id;
	
	@SerializedName("imdb_id")
	public String imdbId;
	public String name;
	public String url;
	public int popularity;
	public boolean translated;
	public boolean adult;
	public String language;
	@SerializedName("original_name")
	public String originalName;
	@SerializedName("alternative_name")
	public String alternativeName;
	@SerializedName("movie_type")
	public String movieType;
	
	public int votes;
	public String rating;
	public String certification;
	public String overview;
	public String released;

	public int version;
	@SerializedName("last_modified_at")
	public String lastModifiedAt;
	
	public List<TmdbPoster> posters;
	
	public List<TmdbBackDrop> backdrops;
	
	@Override
	public String toString() {
		return id + " " + name;
	}
}
