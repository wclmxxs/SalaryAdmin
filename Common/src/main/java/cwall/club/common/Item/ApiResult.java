package cwall.club.common.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult {
    private int code;
    private Object result;
    public static ApiResult rendFail(int code, String message) {
        return new ApiResult(code, message);
    }

    public static ApiResult renderSuccess(Object obj){
        return new ApiResult(200, obj);
    }
}
