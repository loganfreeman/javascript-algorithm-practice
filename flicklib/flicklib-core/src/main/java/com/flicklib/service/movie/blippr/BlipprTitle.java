package com.flicklib.service.movie.blippr;

import com.google.gson.annotations.SerializedName;

public class BlipprTitle {
	public long id;
	public String name;
	public String summary;
	public String link;
	public String score;
	@SerializedName("media_type")
	public String mediaType;
	@SerializedName("reviews_count")
	public Integer reviewCount;
	
	public BlipprImages images;
	public BlipprMetaData metadata;
}


//{
//	  "title": {
//	    "name": "Pulp Fiction",
//	    "media_type": "movies",
//	    "reviews_count": "242",
//	    "metadata": {
//	      "actors": {
//	        "actor": [
//	          "Rosanna Arquette",
//	          "Samuel L. Jackson",
//	          "John Travolta",
//	          "Bruce Willis",
//	          "Christopher Walken",
//	          "Uma Thurman",
//	          "Tim Roth",
//	          "Ving Rhames",
//	          "Maria de Medeiros",
//	          "Amanda Plummer",
//	          "Quentin Tarantino",
//	          "Harvey Keitel",
//	          "Steve Buscem"
//	        ]
//	      },
//	      "producers": {
//	        "producer": "Lawrence Bender"
//	      },
//	      "tags": {
//	        "tag": [
//	          "action",
//	          "adventure",
//	          "crime",
//	          "Action",
//	          "Crime",
//	          "Exploitation",
//	          "drugs",
//	          "mycomputer",
//	          "five",
//	          "P",
//	          "Pulp Fiction"
//	        ]
//	      },
//	      "year": {
//	        "year": "1994"
//	      },
//	      "directors": {
//	        "director": "Quentin Tarantino"
//	      },
//	      "genres": {
//	        "genre": "Crime"
//	      }
//	    },
//	    "id": "1167",
//	    "images": {
//	      "square_thumb": "http://cdn.blippr.com/images/titles/v2/000/001/167/pulp-fiction-music_small.jpg?1271000450",
//	      "medium": "http://cdn.blippr.com/images/titles/v2/000/001/167/pulp-fiction-medium.jpg?1271000450",
//	      "small": "http://cdn.blippr.com/images/titles/v2/000/001/167/pulp-fiction-small.jpg?1271000450",
//	      "thumb": "http://cdn.blippr.com/images/titles/v2/000/001/167/pulp-fiction-thumb.jpg?1271000450",
//	      "square": "http://cdn.blippr.com/images/titles/v2/000/001/167/pulp-fiction-square.jpg?1271000450"
//	    },
//	    "latest_reviews": {
//	      "review": [
//	        {
//	          "created_at": "2011-04-29 10:03:13 -0700",
//	          "bias": "2",
//	          "url": "http://www.blippr.com/movies/1167-Pulp-Fiction/blips/123713",
//	          "id": "123713",
//	          "user": {
//	            "name": "Luke W",
//	            "id": "170049",
//	            "avatar": "http://cdn.blippr.com/images/avatars/seg/000/170/049/170049_size100.jpg?1304085101",
//	            "link": "http://www.blippr.com/profiles/170049"
//	          },
//	          "reviewtext": "love it"
//	        },
//	        {
//	          "created_at": "2011-02-19 20:48:59 -0800",
//	          "bias": "1",
//	          "url": "http://www.blippr.com/movies/1167-Pulp-Fiction/blips/122300",
//	          "id": "122300",
//	          "user": {
//	            "name": "menggang",
//	            "id": "164707",
//	            "avatar": "http://cdn.blippr.com/images/avatars/seg/000/164/707/164707_size100.jpg?1298088049",
//	            "link": "http://www.blippr.com/profiles/164707"
//	          },
//	          "reviewtext": "65/100"
//	        },
//	        {
//	          "created_at": "2011-02-16 09:56:32 -0800",
//	          "bias": "2",
//	          "url": "http://www.blippr.com/movies/1167-Pulp-Fiction/blips/122154",
//	          "id": "122154",
//	          "user": {
//	            "name": "renatohsc",
//	            "id": "171161",
//	            "avatar": "http://cdn.blippr.com/images/avatars/seg/000/171/161/171161_size100.jpg?1305761747",
//	            "link": "http://www.blippr.com/profiles/171161"
//	          },
//	          "reviewtext": "foda"
//	        }
//	      ]
//	    },
//	    "link": "http://www.blippr.com/movies/1167-Pulp-Fiction",
//	    "score": "89.0"
//	  }
//	}
