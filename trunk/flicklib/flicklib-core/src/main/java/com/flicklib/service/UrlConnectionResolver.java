package com.flicklib.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AccessControlException;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.tools.IOTools;
import com.google.inject.name.Named;

public class UrlConnectionResolver implements SourceLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlConnectionResolver.class);

	static {
		try {
			CookieManager manager = new CookieManager(null, new CookiePolicy() {
				@Override
				public boolean shouldAccept(URI uri, HttpCookie cookie) {
					if (cookie.getMaxAge() == 0) {
						cookie.setMaxAge(-1);
					}
					if (cookie.getDomain() == null) {
						cookie.setDomain(uri.getHost());
					}
					return true;
				}
			});
			try {
				CookieHandler.setDefault(manager);
			} catch (AccessControlException ace) {
				LOGGER.warn("cookie handler initialization failed!");
			}
		} catch (java.lang.NoClassDefFoundError ncde) {
			LOGGER.warn("CookieManager is not accessible " + ncde.getMessage(), ncde);
		}
	}

	// TODO detect encoding?
	private static final String ENCODING = "UTF-8";

	private final boolean hideAgent = true;
	private Integer timeOut = null;
	private final boolean internalRedirects = true;

	public UrlConnectionResolver(@Named(value = Constants.HTTP_TIMEOUT) final Integer timeout) {
		this.timeOut = timeout;
	}

	@Override
	public Source loadSource(String url) throws IOException {
		URL httpUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
		setupConnection(connection);
		return processRequest(url, connection);
	}

	@Override
	public Source loadSource(String url, boolean useCache) throws IOException {
		return loadSource(url);
	}

	private Source processRequest(String reqUrl, HttpURLConnection connection) throws IOException, UnsupportedEncodingException {
		InputStream input = null;
		Source source = null;
		Reader reader = null;
		try {
			input = connection.getInputStream();
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
				String newLocation = connection.getHeaderField("Location");
				if (newLocation != null) {
					URL redirectUrl = new URL(connection.getURL(), newLocation);
					LOGGER.info("redirect to " + redirectUrl.toString());
					return loadSource(redirectUrl.toExternalForm());
				}
			} else {
				String encoding = connection.getContentEncoding();
				String contentType = connection.getHeaderField("Content-Type");
				if (encoding == null) {
					if (contentType != null && contentType.indexOf("charset") != -1) {
						encoding = contentType.replaceAll(".*charset=([\\w-]*)(;*)", "$1");
					}
				}
				if (encoding == null) {
					// the old default ...
					encoding = "ISO-8859-1";
				}
				reader = new InputStreamReader(input, encoding.toUpperCase());
				String content = IOTools.readerToString(reader);
				source = new Source(connection.getURL().toString(), content, contentType, reqUrl);
				LOGGER.info("request for " + connection.getURL().toString() + " processed, result content type : " + contentType
						+ ", encoding :" + encoding + ", size:" + source.getContent().length());
			}
		} finally {
			IOTools.close(reader);
			IOTools.close(input);
		}

		return source;
	}

	private void setupConnection(final HttpURLConnection connection) {
		if (hideAgent) {
			connection.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
		}
		connection.setInstanceFollowRedirects(internalRedirects);
		if (timeOut != null) {
			connection.setReadTimeout(timeOut);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Source post(String url, Map<String, String> parameters, Map<String, String> headers) throws IOException {
		URL httpUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
		setupConnection(connection);
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		for (Entry<String, String> entry : headers.entrySet()) {
			connection.setRequestProperty(entry.getKey(), entry.getValue());
		}

		StringBuilder buf = new StringBuilder();
		boolean first = true;
		for (Entry<String, String> entry : parameters.entrySet()) {
			if (first) {
				first = false;
			} else {
				buf.append('&');
			}
			buf.append(URLEncoder.encode(entry.getKey(), ENCODING)).append('=').append(URLEncoder.encode(entry.getValue(), ENCODING));
		}
		OutputStream outputStream = null;
		try {
			outputStream = connection.getOutputStream();
			outputStream.write(buf.toString().getBytes(ENCODING));
		} finally {
			IOTools.close(outputStream);
		}

		return processRequest(url, connection);
	}
	
    @Override
    public RestBuilder url(String url) {
    	throw new UnsupportedOperationException("not implemented");
    }

}
