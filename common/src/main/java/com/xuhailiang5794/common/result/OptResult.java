package com.xuhailiang5794.common.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <pre>
 * 操作结果集
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/7 10:27
 */
@Data
public class OptResult<T> {

    /**
     * 数据结果
     */
    @ApiModelProperty("数据结果,一般是一个json对象也可能是null")
    private T data;

    /**
     * 是否查询成功
     */
    @ApiModelProperty("是否查询成功,true表示操作成功，false表示操作出错")
    private boolean success;

    /**
     * 回馈信息
     */
    @ApiModelProperty("回馈信息,操作结果描述")
    private String message;

    /**
     *错误代码
     */
    @ApiModelProperty("错误代码")
    private String errorCode;

    public static OptResult success(String message) {
        OptResult result = new OptResult();
        result.setSuccess(true);
        result.setData(message);
        return result;
    }

    public static OptResult success() {
        return success(null);
    }
    public static OptResult fail(String errorCode, String message) {
        OptResult result = new OptResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setMessage(message);
        return result;
    }
    public static OptResult fail() {
        return fail(null, null);
    }
}
