package swm.group18.healthcare.searcher;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import swm.group18.healthcare.utils.LoggerUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MayoClinicDataSearcher {
    private static final SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/mayo_clinic_core").build();

    public static JSONArray search(SearchQuery searchQuery) {
        System.out.println(searchQuery.getQueryString());
//        JSONObject responseObj = new JSONObject();
        JSONArray resultsArray = new JSONArray();
        final Map<String, String> queryParamMap = new HashMap<>();
//        queryParamMap.put("q", "health_condition_name:" + searchQuery.getQueryString() + "*");
        queryParamMap.put("q", "symptoms:" + searchQuery.getQueryString() +
                " OR health_condition:" + searchQuery.getQueryString() +
                " OR symptoms_text:" + searchQuery.getQueryString()
        );
        queryParamMap.put("fl", "health_condition, page_url, symptoms_text");
        queryParamMap.put("start", "0");
        queryParamMap.put("rows", "20");
//        queryParamMap.put("sort", "id asc");
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        final QueryResponse response;
        try {
            response = client.query(queryParams);
            final SolrDocumentList documents = response.getResults();
            LoggerUtil.logDebugMsg("Found " + documents.getNumFound() + " documents");

            Set<String> drugsAlreadyInResults = new HashSet<>();
//            JSONArray resultsArray = new JSONArray();
            for(SolrDocument document : documents) {
                JSONObject result = new JSONObject();
                final String postTitle = (String) document.getFirstValue("health_condition");
                if (!drugsAlreadyInResults.contains(postTitle)) {
                    final String postUrl = (String) document.getFirstValue("page_url");
                    final String summary = (String) document.getFirstValue("symptoms_text");

                    result.put("title", postTitle);
                    result.put("url", postUrl);
                    result.put("summary", summary);
                    resultsArray.add(result);

                    drugsAlreadyInResults.add(postTitle);
                }
            }

//            responseObj.put("web_md_mb", resultsArray);
            return resultsArray;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultsArray;
    }
}
