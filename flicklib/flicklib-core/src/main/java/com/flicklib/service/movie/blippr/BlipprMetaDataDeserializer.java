package com.flicklib.service.movie.blippr;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

class BlipprMetaDataDeserializer implements JsonDeserializer<BlipprMetaData> {
	
	private static final Type STRING_LIST = new TypeToken<List<String>>() {}.getType();
	
	@Override
	public BlipprMetaData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		System.err.println(gson.toJson(json));
		
		if(json.isJsonObject()){
			JsonObject object = json.getAsJsonObject();
			BlipprMetaData metaData = new BlipprMetaData();
			if(object.has("year")){
				Year year = context.deserialize(object.get("year"), Year.class);
				metaData.year = year.year;
			}
			deserialize(context, object, "genres", "genre", metaData.genres);
			deserialize(context, object, "directors", "director", metaData.directors);
			deserialize(context, object, "actors", "actor", metaData.actors);
			
			return metaData;
		}
		
		return null;
	}

	private void deserialize(
			JsonDeserializationContext context, 
			JsonObject object,
			String outer,
			String inner,
			List<String> list) {
		
		if(object.has(outer)){
			JsonElement innerElement = object.getAsJsonObject(outer).get(inner);
			if(innerElement.isJsonPrimitive()){
				list.add(innerElement.getAsString());
			}else{
				List<String> genres = context.deserialize(innerElement, STRING_LIST);
				list.addAll(genres);
			}
		}
	}
	
	private static final class Year{
		private Integer year;
	}
}