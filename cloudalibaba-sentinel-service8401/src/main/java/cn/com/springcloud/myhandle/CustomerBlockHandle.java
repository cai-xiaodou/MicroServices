package cn.com.springcloud.myhandle;

import cn.com.springcloud.entities.CommonResult;
import com.sun.deploy.security.BlockedException;

public class CustomerBlockHandle {

    public static CommonResult handleException(BlockedException exception) {
        return new CommonResult(4444,"按客户自定义，global CustomerBlockHandle---1");
    }

    public static CommonResult handleException2(BlockedException exception) {
        return new CommonResult(4444,"按客户自定义，global CustomerBlockHandle----2");
    }
}
