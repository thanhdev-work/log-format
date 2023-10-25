package vn.com.demologservice.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import vn.com.demologservice.util.BaseResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//register rest template log by config RestTemplate bean
//Ex: restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
public class LoggingHttpClient extends LoggingAspect implements ClientHttpRequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggingHttpClient.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        this.logRequest(httpRequest, bytes);
        ClientHttpResponse clientHttpResponse = clientHttpRequestExecution.execute(httpRequest, bytes);
        this.logResponse(clientHttpResponse);

        return clientHttpResponse;
    }

    private void logRequest(HttpRequest httpRequest, byte[] bytes) throws IOException {
        WrapRequest<Object> request = new WrapRequest<Object>();
        request.setHeaders(httpRequest.getHeaders().toSingleValueMap());
        request.setMethod(httpRequest.getMethodValue());
        request.setRequestPath(httpRequest.getURI().getPath());
        request.setRequest(new String(bytes, StandardCharsets.UTF_8));

        log.info(GSON.toJson(request));
    }

    private void logResponse(ClientHttpResponse httpResponse) throws IOException {
        WrapResponse<BaseResponse.WrapBody<Object>> response = new WrapResponse<>();
        BaseResponse.WrapBody<Object> body = new BaseResponse.WrapBody<>();
        response.setHeaders(httpResponse.getHeaders().toSingleValueMap());
        body.setCode(httpResponse.getRawStatusCode());
        body.setMessage(httpResponse.getStatusText());
        body.setData(StreamUtils.copyToString(httpResponse.getBody(), StandardCharsets.UTF_8));

        log.info(GSON.toJson(response));
    }
}
