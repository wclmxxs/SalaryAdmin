package cwall.club.common.Enum;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    //100----普遍错误
    NEED_PARAMETER(100000,"需要的参数为空","the parameter is null"),
    REQUEST_AFTER(100001,"请求频繁","request is too quickly"),
    GENERATE_ERROR(100002,"生成ID失败","the id is error"),
    ROCKETMQ_ERROR(100003,"队列错误","mq is error"),

    //101----权限错误
    EXIST_USER(101000,"用户已存在","User has exist"),
    NOT_USER(101001,"用户不存在","User not exist"),

    //102----匹配错误
    LENGTH_NOT_MATCH(102000,"字符串长度不匹配","String length is unAdaptable"),
    NOT_MATCH(102001,"参数不可更改,或参数类型不匹配","parameter is not correctly"),

    //103----登录注册错误
    CODE_INVALID(103000,"验证码已经过期","the code has invalid"),
    CODE_ERROR(103001,"验证码错误","the code is error"),
    PWD_NOT_EQUAL(103002,"密码不相等","the pwd is not equal"),
    PWD_NOT_VALID(103003,"用户ID或密码错误","the info is error"),
    NOT_LOGIN(103004,"用户未登录","user is not login"),

    //104----公司错误
    COMPANY_EXIST(104000,"用户已经创建过公司","user has create company"),
    //105----redis错误
    NOT_LOCK(105000,"加锁失败","redis is error"),
    ;
    int code;
    String name;
    String description;
    ExceptionCode(int code,String message,String name){
        this.code = code;
        this.name = name;
        this.description = message;
    };
}