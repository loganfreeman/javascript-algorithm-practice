package com.flicklib.service.movie;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.flicklib.service.HttpClientSourceLoader;
import com.flicklib.service.SourceLoader;
import com.flicklib.service.TestUtil;
import com.flicklib.service.UrlConnectionResolver;

@RunWith(value = Parameterized.class)
public abstract class AlternateLiveTester {
	
	private static final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(30);

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { 
        		{ Boolean.TRUE, Boolean.TRUE }, 
        		//{ Boolean.TRUE, Boolean.FALSE }, 
        		{ Boolean.FALSE, Boolean.FALSE } });
    }

    protected SourceLoader loader;

    public AlternateLiveTester(boolean internalHttpClient, boolean internalRedirects) {
    	SourceLoader internal = null;
    	if(internalHttpClient){
    		//if(internalRedirects){
    		internal = new UrlConnectionResolver(TIMEOUT);
    	}else{
    		internal = new HttpClientSourceLoader(TIMEOUT);
    	}
    	loader = TestUtil.wrapCache(internal);
    }

}
