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

public class WebMDDrugReviewDataSearcher {
    private static final SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/webmd_dr_core").build();

    public static JSONObject search(SearchQuery searchQuery) {
        System.out.println(searchQuery.getQueryString());
        JSONObject responseObj = new JSONObject();
        final Map<String, String> queryParamMap = new HashMap<>();
//        queryParamMap.put("q", "health_condition_name:" + searchQuery.getQueryString() + "*");
        queryParamMap.put("q", "health_condition_name:" + searchQuery.getQueryString());
        queryParamMap.put("fl", "drug_name, drug_review_page, drug_detail_page");
        queryParamMap.put("start", "0");
        queryParamMap.put("rows", "100");
//        queryParamMap.put("sort", "id asc");
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        final QueryResponse response;
        try {
            response = client.query(queryParams);
            final SolrDocumentList documents = response.getResults();
            LoggerUtil.logDebugMsg("Found " + documents.getNumFound() + " documents");
            Set<String> drugsAlreadyInResults = new HashSet<>();
            JSONArray resultsArray = new JSONArray();
            for(SolrDocument document : documents) {
                JSONObject result = new JSONObject();
                final String drugName = (String) document.getFirstValue("drug_name");
                if (!drugsAlreadyInResults.contains(drugName)) {
                    final String detailPageURL = (String) document.getFirstValue("drug_detail_page");
                    final String reviewPageURL = (String) document.getFirstValue("drug_review_page");

                    result.put("drug_name", drugName);
                    result.put("drug_detail_page", detailPageURL);
                    result.put("drug_review_page", reviewPageURL);
                    resultsArray.add(result);

                    drugsAlreadyInResults.add(drugName);
                }
            }

            responseObj.put("results", resultsArray);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseObj;
    }
}
