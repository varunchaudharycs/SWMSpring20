package swm.group18.healthcare.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class IndexRequestHandler extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processIndexRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processIndexRequest(req, resp);
    }

    private void processIndexRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<h2>Started Indexing Data...</h2>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();

//        start data indexing process
//        this can sequentially or parallelly read different JSON data file
//        pass content field through MetaMap and then index along with additional extracted data in Solr core.

        Properties properties = new Properties();
        properties.load(getServletContext().getResourceAsStream("/WEB-INF/crawled_data/test.properties"));
        System.out.println(properties);

        String myfile = getServletContext().getRealPath("./WEB-INF/crawled_data/adhd-posts.json");
        System.out.println(myfile);
        try {
//            This will create problem with large JSON file such as patient info forum posts, for this we will chunk it
//            before indexing the data
            JSONParser parser = new JSONParser();

            JSONArray a = (JSONArray) parser.parse(new FileReader(myfile));
            for (Object object: a) {
                System.out.println(object);
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
