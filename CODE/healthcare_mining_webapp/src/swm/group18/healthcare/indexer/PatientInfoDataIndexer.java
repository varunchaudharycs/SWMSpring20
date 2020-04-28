package swm.group18.healthcare.indexer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.json.simple.JSONObject;
import swm.group18.healthcare.utils.LoggerUtil;

import java.io.IOException;

public class PatientInfoDataIndexer {
    private static final SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/patient_info_forum_core").build();

    public static void indexPost(JSONObject jsonObject) throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("author", jsonObject.get("author").toString());
        doc.addField("post_title", jsonObject.get("post_title").toString());
        doc.addField("post_content", jsonObject.get("post_content").toString());
        doc.addField("like_count", Integer.parseInt(jsonObject.get("like_count").toString()));

        client.add(doc);

        if (GlobalIndexedDocsCounter.commitThresholdReached()) {
            commit();
        }
    }

    public static void commit() throws IOException, SolrServerException {
        client.commit();
        LoggerUtil.logDebugMsg("Index committed after indexing: " + GlobalIndexedDocsCounter.numberOfIndexedDocuments
                + " new documents");
    }
}
