package es.thefactory.javaee8app.util.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
public class URLBuilder {
    
    /**
     * 
     * @param baseURL
     * @param path
     * @return URL
     * @throws MalformedURLException
     */
    public static URL build(URL baseURL, String path) throws MalformedURLException {
        return (URLBuilder.build(baseURL, path, null));
    }
    
    /**
     * 
     * @param baseURL
     * @param path
     * @param params
     * @return URL
     * @throws MalformedURLException
     */
    public static URL build(URL baseURL, String path, Map<String, String> params) throws MalformedURLException {
        String origin = baseURL.toString();
        
        if (origin.endsWith("/")) {
            origin = origin.substring(0, origin.length() - 1);
        }
        
        StringBuilder urlComponents = new StringBuilder(origin);
        urlComponents.append(path);
        
        if ((params != null) && (!(params.isEmpty()))) {
            StringBuilder queryString = new StringBuilder();
            queryString.append("?");
            
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (queryString.length() > 1) {
                    queryString.append("&");
                }
                
                queryString.append(param.getKey());
                queryString.append("=");
                queryString.append(param.getValue());
            }
            
            urlComponents.append(queryString);
        }
        
        URL url = new URL(urlComponents.toString());
        
        return url;
    }
}
