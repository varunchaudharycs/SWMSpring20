package swm.group18.healthcare.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SearchRequestHandler extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processSearchRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processSearchRequest(req, resp);
    }

    private void processSearchRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String searchQuery = request.getParameter("query");
//        drug_search - user has typed some disease name looking for related drugs as search results
//        disease_search - user has typed some symptoms and looking for related diseases as search results
        String searchType = request.getParameter("type");
        System.out.println(searchQuery);
        System.out.println(searchType);

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Search Servlet</h1>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }
}