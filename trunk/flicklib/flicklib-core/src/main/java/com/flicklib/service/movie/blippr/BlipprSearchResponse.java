package com.flicklib.service.movie.blippr;

import java.util.List;

class BlipprSearchResponse {
	public BlipprSearch search;
	
	class BlipprSearch {
		public BlipprTitles titles;
	}
	
	class BlipprTitles {
		public List<BlipprTitle> title;
	}
}
