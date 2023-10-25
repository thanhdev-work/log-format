package vn.com.demologservice.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import java.text.SimpleDateFormat;
import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BaseResponse extends ResponseEntity<BaseResponse.WrapBody> {
    public BaseResponse(@Nullable WrapBody body, HttpStatus status) {
        super(body, status);
    }

    public static BaseResponse OK(Object body) {
        return new BaseResponse(new WrapBody<Object>(200, HttpStatus.OK.getReasonPhrase(), true, body), HttpStatus.OK);
    }

    public static BaseResponse OK() {
        return new BaseResponse(new WrapBody<Object>(200, HttpStatus.OK.getReasonPhrase(), true, null), HttpStatus.OK);
    }

    public static BaseResponse OF(HttpStatus httpStatus, boolean ok, Object body) {
        return new BaseResponse(new WrapBody<Object>(httpStatus.value(), httpStatus.getReasonPhrase(), ok, body), httpStatus);
    }

    public static BaseResponse interErr(String message) {
        return new BaseResponse(new WrapBody<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, false, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static BaseResponse interErr() {
        return new BaseResponse(new WrapBody<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), false, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static BaseResponse badReq(Object body) {
        return new BaseResponse(new WrapBody<Object>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false, body),
                HttpStatus.BAD_REQUEST);
    }

    public static BaseResponse badReq() {
        return new BaseResponse(new WrapBody<Object>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_GATEWAY.getReasonPhrase(), false, null),
                HttpStatus.BAD_REQUEST);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WrapBody<T> {
        private int code;
        private String message;
        private String timeStamp;
        private boolean ok;
        private T data;

        public WrapBody(int code, String message, boolean ok, T data) {
            this.code = code;
            this.message = message;
            this.data = data;
            this.ok = ok;
            this.timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
    }

}
