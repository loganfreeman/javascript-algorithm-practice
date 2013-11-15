package com.flicklib.service.cache;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Map;

import com.flicklib.service.Source;
import com.flicklib.service.SourceLoader;
import com.flicklib.tools.IOTools;

public class LoggingHttpCache implements SourceLoader {

    private final SourceLoader internal;
    private final File dumpPath;
    private int reqNum;
    private final PrintWriter logFile;
    
    public LoggingHttpCache(SourceLoader internal, File dumpPath) throws IOException {
        this.internal = internal;
        this.dumpPath = dumpPath;
        this.logFile = new PrintWriter(new FileWriter(new File(dumpPath, "request.log"), true));
        while(getOutputFileFor().exists()) {
            reqNum++;
        }
    }

    @Override
    public Source loadSource(String url) throws IOException {
        return log(url, internal.loadSource(url));
    }

    private synchronized Source log(String reqUrl, Source source) {
        logFile.println(reqNum + ". request " + reqUrl + (!reqUrl.equals(source.getUrl()) ? "( forwarded to " + reqUrl + " )" : "") + " stored in request-" + reqNum
                + ".html");
        logFile.flush();
        try {
            IOTools.writeToFile(new StringReader(source.getContent()), getOutputFileFor());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        reqNum ++;
        return source;
    }

    private File getOutputFileFor() {
        return new File(dumpPath, "request-"+reqNum+".html");
    }

    @Override
    public Source loadSource(String url, boolean useCache) throws IOException {
        return log(url, internal.loadSource(url, useCache));
    }

    @Override
    public Source post(String url, Map<String, String> parameters, Map<String, String> headers) throws IOException {
        return log(url, internal.post(url, parameters, headers));
    }
    
    @Override
    public RestBuilder url(String url) {
    	throw new UnsupportedOperationException("not implemented");
    }

}
