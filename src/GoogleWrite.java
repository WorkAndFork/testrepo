import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GoogleWrite {
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GoogleWrite.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public HashMap<Object, Object> selectDataFromSpreadSheet() throws IOException{
        HashMap<Object, Object> cells = new HashMap<>();
        int i = 0;
        List<Request> requests = new ArrayList<>();
        Sheets service = getSheetsService();
        String spreadsheetId = ""; //spreadsheetId
        String range = "Лист1!A:C";
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        String range1 = "Лист3!A:C";
        ValueRange response1 = service.spreadsheets().values()
                .get(spreadsheetId, range1)
                .execute();
        List<List<Object>> values = response.getValues();
        List<List<Object>> values1 = response1.getValues();
            for (List<Object> row : values) {
               cells.put(row.get(0),row.get(2));
            }
        for (List<Object> row : values1) {
            cells.put(row.get(0),row.get(2));
        }
        return cells;
    }

    public Integer checkLastLine (String listName) throws  java.io.IOException{
        HashMap<Object, Object> tempMap = new HashMap<>();
        int i = 0;
        List<Request> requests = new ArrayList<>();
        Sheets service = getSheetsService();
        String spreadsheetId = "";//spreadsheetId
        String range = listName + "!A:C";
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
       // for (List<Object> row : values) {
     //       tempMap.put(row.get(0),row.get(1));
      //  }
        return values.size();
    }

    public void writeFirst(String stringValue, Integer userId, Integer sheetId, Integer lineName) throws IOException {
        TableDAO tableDAO = new TableDAO();
        Test test = new Test();
        Sheets service = getSheetsService();
        List<Request> requests = new ArrayList<>();

        List<CellData> values = new ArrayList<>();


        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(stringValue)));
        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(sheetId)
                                .setRowIndex(lineName)
                                .setColumnIndex(1))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        service.spreadsheets().batchUpdate("", batchUpdateRequest)//spreadsheetId
                .execute();
    }

    public void writeSecond(String stringValue, Integer userId, Integer sheetId, Integer lineName) throws IOException {
        Sheets service = getSheetsService();
        TableDAO tableDAO = new TableDAO();
        List<Request> requests = new ArrayList<>();

        List<CellData> values = new ArrayList<>();


        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(stringValue)));
        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(sheetId)
                                .setRowIndex(lineName)
                                .setColumnIndex(2))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        service.spreadsheets().batchUpdate("", batchUpdateRequest)//spreadsheetId
                .execute();
    }

    public void writeThird(String stringValue, Integer userId, Integer sheetId, Integer lineName) throws IOException {
        TableDAO tableDAO = new TableDAO();
        Sheets service = getSheetsService();
        List<Request> requests = new ArrayList<>();

        List<CellData> values = new ArrayList<>();


        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(stringValue)));
        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(sheetId)
                                .setRowIndex(lineName)
                                .setColumnIndex(3))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        service.spreadsheets().batchUpdate("", batchUpdateRequest)//spreadsheetId
                .execute();
    }

    public void writeFourth(String stringValue, Integer userId, Integer sheetId, Integer lineName) throws IOException {
        TableDAO tableDAO = new TableDAO();
        Sheets service = getSheetsService();
        List<Request> requests = new ArrayList<>();

        List<CellData> values = new ArrayList<>();


        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(stringValue)));
        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(sheetId)
                                .setRowIndex(lineName)
                                .setColumnIndex(0))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        service.spreadsheets().batchUpdate("", batchUpdateRequest)//spreadsheetId
                .execute();
    }

    private static char[] tempCell(Object cell){
        char[] tempcell = cell.toString().toCharArray();
        char[] tempCell = Arrays.copyOfRange(tempcell,1,tempcell.length-1);
        return tempCell;
    }

}