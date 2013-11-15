package com.flicklib.service.movie.imdb;

import com.google.gson.annotations.SerializedName;

class ImdbApiMovie{
	
	@SerializedName("ID")
	public String id;
	
	@SerializedName("Title")
	public String title;
	
	@SerializedName("Rating")
	public String rating;
	
	@SerializedName("Votes")
	public Integer votes;
	
	@SerializedName("Runtime")
	public String runtime;
	
	@SerializedName("Poster")
	public String poster;
	
	@SerializedName("Genre")
	public String genre;
	
	@SerializedName("Plot")
	public String plot;
	
	@SerializedName("Year")
	public Integer year;
	
	@SerializedName("Released")
	public String released;
	
	@SerializedName("Director")
	public String director;
	
	public String getUrl(){
		return "http://www.imdb.com/title/" + id;
	}
	
	@Override
	public String toString() {
		return id + " " + title;
	}

}
