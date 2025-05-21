package ksy.medichat.view;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@Component
public class DownloadView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		byte[] file = (byte[])model.get("downloadFile");
		String filename = (String)model.get("filename");
		
		response.setContentType("application/download; charset=utf-8");
		response.setContentLength(file.length);
		
		String fileName = new String(
				filename.getBytes("utf-8"),"iso-8859-1");	
		
		response.setHeader("Content-Disposition", 
				"attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		OutputStream out = response.getOutputStream();
		InputStream input = null;
		try {
			input = new ByteArrayInputStream(file);
			IOUtils.copy(input, out);
			out.flush();
		}finally {
			if(out!=null) out.close();
			if(input!=null) input.close();
		}
	}

}





