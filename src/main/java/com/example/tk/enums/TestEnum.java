package com.example.tk.enums;

import java.util.Arrays;
import java.util.List;

public enum TestEnum {
    COMMON(Type.Common.class),
    CUSTOMIZED(Type.Customized.class);  //枚举常量必须写在最前面，否则会报错

    interface Type{      //使用interface将子枚举类型组织起来
        enum Common implements Type{
            FIRST("1","10,11","first"),
            SECOND("2","20,21","second");
            private String code;
            private String otherCodes;
            private String description;
            Common(String code,String otherCodes,String desciption){
                this.code=code;
                this.description=desciption;
                this.otherCodes=otherCodes;
            }
            public String getCode(){
                return code;
            }
            public String getDescription(){
                return description;
            }
            public List<String> getOtherCodes(){
                return Arrays.asList(otherCodes.split(","));
            }
        }
        enum Customized implements Type{
            FIFTH("5","30,31","fifth"),
            SIXTH("6","40,41","sixth");
            private String code;
            private String otherCodes;
            private String description;
            Customized(String code,String otherCodes,String desciption){
                this.code=code;
                this.description=desciption;
                this.otherCodes=otherCodes;
            }
            public String getCode(){
                return code;
            }
            public String getDescription(){
                return description;
            }
            public List<String> getOtherCodes(){
                return Arrays.asList(otherCodes.split(","));
            }
        }
        String getCode();
        String getDescription();
        List<String> getOtherCodes();
    }

    public static String get(String channel,TestEnum e){
            for(Type t:e.values){
                if(t.getOtherCodes().contains(channel)){
                    return t.getCode();
                }
            }
        return null;
    }

    public Type[] getValues(){
        return values;
    }

    Type[] values;
    TestEnum(Class<? extends Type> kind){
        this.values=kind.getEnumConstants();
    }

    public static void main(String[] args){
        String s1 = TestEnum.get("41", TestEnum.CUSTOMIZED);
        System.out.println(s1);

    }


}
