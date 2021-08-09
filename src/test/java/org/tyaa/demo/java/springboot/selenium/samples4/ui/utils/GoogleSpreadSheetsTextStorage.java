package org.tyaa.demo.java.springboot.selenium.samples4.ui.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.models.TextItem;
import org.tyaa.demo.java.springboot.selenium.samples4.ui.models.TextItemWrapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GoogleSpreadSheetsTextStorage implements ITextStorage {

    public static final String RESOURCE_INFO_SPREADSHEET_ID_KEY = "spreadsheetId";
    public static final String RESOURCE_INFO_API_KEY = "key";
    public static final String RESOURCE_INFO_ACCESS_TOKEN = "access_token";
    private final String URL_PAGE_KEY = "sheet";
    private final String LAST_CELL_ADDRESS_KEY = "lastCellAddress";
    private Map<String, String> resourceInfo;
    private final ObjectMapper MAPPER;
    private final HttpClient HTTP_CLIENT;
    private final String URL_GET_TEMPLATE =
        "https://sheets.googleapis.com/v4/spreadsheets/{spreadsheetId}/values/{sheet}!A2:Z1000/?key={key}";
    private final String URL_POST_TEMPLATE =
        "https://sheets.googleapis.com/v4/spreadsheets/{spreadsheetId}/values/{sheet}!C2{lastCellAddress}/?valueInputOption=USER_ENTERED";
    private final String URL_RANGE_TEMPLATE = "{sheet}!C2{lastCellAddress}";

    public GoogleSpreadSheetsTextStorage() {
        this.MAPPER = new ObjectMapper();
        this.HTTP_CLIENT = HttpClient.newHttpClient();
    }

    private void checkResourceName() throws Exception {
        if (this.resourceInfo == null) {
            throw new Exception("Resource info is not specified. Call connect(resourceInfo).");
        }
        if (!this.resourceInfo.containsKey(RESOURCE_INFO_SPREADSHEET_ID_KEY)
                || !this.resourceInfo.containsKey(RESOURCE_INFO_API_KEY)
                || !this.resourceInfo.containsKey(RESOURCE_INFO_ACCESS_TOKEN)
        ) {
            throw new Exception("Resource info has wrong structure. Specify 'spreadsheetId', 'key' and 'access_token' parameters.");
        }
        if (this.resourceInfo.get(RESOURCE_INFO_SPREADSHEET_ID_KEY).isBlank()
            || this.resourceInfo.get(RESOURCE_INFO_API_KEY).isBlank()
            || this.resourceInfo.get(RESOURCE_INFO_ACCESS_TOKEN).isBlank()
        ) {
            throw new Exception("Resource info has empty values. Specify 'spreadsheetId', 'key' and 'access_token' parameter values.");
        }
    }

    @Override
    public Boolean connect(Map<String, String> resourceInfo) {
        this.resourceInfo = resourceInfo;
        return true;
    }

    @Override
    public LinkedHashMap<String, TextItem> get(String pageKey) throws Exception {
        checkResourceName();
        TextItem.resetRowNumber();
        String responseBodyString =
            this.HTTP_CLIENT.send(
                HttpRequest.newBuilder()
                    .uri(URI.create(
                        URL_GET_TEMPLATE.replace(
                            String.format("{%s}", RESOURCE_INFO_SPREADSHEET_ID_KEY),
                            resourceInfo.get(RESOURCE_INFO_SPREADSHEET_ID_KEY)
                        ).replace(
                            String.format("{%s}", URL_PAGE_KEY),
                            pageKey
                        ).replace(
                            String.format("{%s}", RESOURCE_INFO_API_KEY),
                            resourceInfo.get(RESOURCE_INFO_API_KEY)
                        )
                    ))
                    .GET()
                    .build(),
                HttpResponse.BodyHandlers.ofString()
            ).body();
        return ((TextItemWrapper) MAPPER.readValue(
            responseBodyString,
            new TypeReference<TextItemWrapper>() {}
        )).getValues()
            .stream()
            .map(
                textItemFields ->
                    new TextItem(
                        (String) textItemFields[0],
                        (String) textItemFields[1],
                        false
                    )
            )
            .collect(
                Collectors.toMap(
                    TextItem::getKey,
                    Function.identity(),
                    (oldValue, newValue) -> oldValue, LinkedHashMap::new
                )
            );
    }

    @Override
    public void set(String pageKey, LinkedHashMap<Integer, Boolean[]> results) throws Exception {
        String lastCellAddress = "";
        int resultsSize = results.size();
        if (resultsSize > 1) {
            lastCellAddress = String.format(":C%d", resultsSize + 1);
        }
        String responseBodyString = this.HTTP_CLIENT.send(
            HttpRequest.newBuilder()
                .uri(URI.create(
                    URL_POST_TEMPLATE.replace(
                        String.format("{%s}", RESOURCE_INFO_SPREADSHEET_ID_KEY),
                        resourceInfo.get(RESOURCE_INFO_SPREADSHEET_ID_KEY)
                    ).replace(
                        String.format("{%s}", URL_PAGE_KEY),
                        pageKey
                    ).replace(
                        String.format("{%s}", LAST_CELL_ADDRESS_KEY),
                        lastCellAddress
                    )
                ))
                .headers("Content-Type", "application/json;charset=UTF-8")
                .headers(
                    "Authorization",
                    String.format("Bearer %s", this.resourceInfo.get(RESOURCE_INFO_ACCESS_TOKEN))
                )
                .PUT(
                    HttpRequest.BodyPublishers.ofString(
                        MAPPER.writeValueAsString(
                            new TextItemWrapper(
                                URL_RANGE_TEMPLATE.replace(
                                    String.format("{%s}", URL_PAGE_KEY),
                                    pageKey
                                ).replace(
                                    String.format("{%s}", LAST_CELL_ADDRESS_KEY),
                                    lastCellAddress
                                ),
                                TextItemWrapper.ROWS_DIMENSION,
                                new ArrayList<>(results.values())
                            )
                        )
                    )
                )
                .build(),
            HttpResponse.BodyHandlers.ofString()
        ).body();
        System.out.printf("'checkContent' report sending result: %s\n", responseBodyString);
    }

    @Override
    public void set(String pageKey, String textKey) throws Exception {
        checkResourceName();
    }

    @Override
    public Boolean close() {
        this.resourceInfo = null;
        return true;
    }
}
