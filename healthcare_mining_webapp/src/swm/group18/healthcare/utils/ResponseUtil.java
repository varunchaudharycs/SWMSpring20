package swm.group18.healthcare.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.PrintWriter;

public class ResponseUtil {
    public static void setDefaultResponseHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }


    public static void sendDevJSONResponseForDiseaseSearch(ServletContext servletContext, HttpServletResponse response) {
        String respFilePath = servletContext.getRealPath("./WEB-INF/crawled_data/disease_search_development_resp.json");
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new FileReader(respFilePath));
            PrintWriter out = response.getWriter();
            out.write(obj.toJSONString());
            out.flush();
            out.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void sendDevJSONResponseForDrugSearch(ServletContext servletContext, HttpServletResponse response) {
        String respFilePath = servletContext.getRealPath("./WEB-INF/crawled_data/drug_search_development_resp.json");
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new FileReader(respFilePath));
            PrintWriter out = response.getWriter();
            out.write(obj.toJSONString());
            out.flush();
            out.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
