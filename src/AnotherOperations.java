import java.util.HashMap;
import java.util.logging.Handler;

/**
 * Created by User on 02.06.2017.
 */
public class AnotherOperations {




    public HashMap<Object, Object> getMapToUploadToDB() {
        TableDAO tableDAO = new TableDAO();
        HashMap<Object, Object> map = new HashMap<>();
        GoogleWrite googleWrite = new GoogleWrite();
        HashMap<Object, Object> mapToUpload = new HashMap<>();
        try {
            for (Object key : tableDAO.getDataFromDBToCompare().keySet()) {
                if (!googleWrite.selectDataFromSpreadSheet().get(key).equals(tableDAO.getDataFromDBToCompare().get(key))) {
                    mapToUpload.put(key, googleWrite.selectDataFromSpreadSheet().get(key));
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return mapToUpload;
    }
}
