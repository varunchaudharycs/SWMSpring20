package swm.group18.healthcare.indexer;

public class GlobalIndexedDocsCounter {
    private static final int COMMIT_EVERY_NTH_DOC = 100;
    public static long numberOfIndexedDocuments = 0;

    public static void incrementDocCounter() {
        numberOfIndexedDocuments++;
    }

    // commit after indexing 50 posts
    // need to send commit from client side otherwise the last few posts will be lost.
    public static boolean commitThresholdReached() {
        if (numberOfIndexedDocuments % COMMIT_EVERY_NTH_DOC == 0) {
            return true;
        }
        return false;
    }
}
