package swm.group18.healthcare.searcher;

import org.json.simple.JSONArray;

import java.util.List;

public class SearchQuery {
    private String queryString;
    private List<String> symptoms;
    private List<String> diseases; // we may not use this during search
    private List<String> relatedSymptoms;

    public SearchQuery(String queryString) {
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public List<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }

    public List<String> getRelatedSymptoms() {
        return relatedSymptoms;
    }

    public JSONArray getRelatedSymptomsJSONArray() {
        JSONArray relatedSympArray = new JSONArray();
        if (relatedSymptoms != null && relatedSymptoms.size() > 0) {
            for (int i = 0; i < relatedSymptoms.size(); i++) {
                relatedSympArray.add(relatedSymptoms.get(i));
            }
        }
        return relatedSympArray;
    }

    public void setRelatedSymptoms(List<String> relatedSymptoms) {
        this.relatedSymptoms = relatedSymptoms;
    }

    @Override
    public String toString() {
        return "SearchQuery {" +
                "queryString='" + queryString + '\'' +
                ", symptoms=" + symptoms +
                ", diseases=" + diseases +
                ", relatedSymptoms=" + relatedSymptoms +
                '}';
    }
}
