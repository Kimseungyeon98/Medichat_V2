package ksy.medichat.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.ObjectUtils;

public class CrawlingUtils {



	public static Connection getJsoupConnection(String url) throws Exception {		
		return Jsoup.connect(url);

	}

	public static Document getJsoupElements(Connection connection, String url) throws Exception{    
		Connection conn = !ObjectUtils.isEmpty(connection) ? connection : getJsoupConnection(url);  
		Document result = null;     
		result = conn.get();
		return result;
	}


}
