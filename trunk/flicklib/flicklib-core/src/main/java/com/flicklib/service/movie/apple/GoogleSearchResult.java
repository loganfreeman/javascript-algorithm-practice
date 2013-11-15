package com.flicklib.service.movie.apple;

import com.google.gson.annotations.SerializedName;

public class GoogleSearchResult {
	public String content;
	@SerializedName("GsearchResultClass")
	public String resultClass;
	public String titleNoFormatting;
	public String title;
	public String cacheUrl;
	public String unescapedUrl;
	public String url;
	public String visibleUrl;
}
