package webScrapers;

import java.util.concurrent.Callable;

public class GetApiCallable implements Callable<GetApi> {

    private GetApi getApi;

    public GetApiCallable(String apiUrl) {
        getApi = new GetApi(apiUrl);
    }

    @Override
    public GetApi call() throws Exception {
        getApi.getApiData();
        return getApi;
    }
}
