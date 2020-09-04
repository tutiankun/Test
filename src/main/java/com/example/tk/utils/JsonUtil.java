package com.example.tk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;

import java.lang.reflect.Type;

public class JsonUtil {
    /**
     * 封装 fastJson SerializerFeature
     */
    public enum SFeature {

        /**
         * 无 SerializerFeature
         */
        EMPTY,
        /**
         * 只有 SerializerFeature.BrowserCompatible 避免乱码
         */
        BROWSER_COMPATIBLE_ONLY(SerializerFeature.BrowserCompatible),
        /**
         * 如果字段为空，用默认值代替
         */
        WRITE_NULL_AS_DEFAULT_VALUE_AND_BROWSER_COMPATIBLE(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.BrowserCompatible
        );


        private SerializerFeature[] features = new SerializerFeature[0];

        SFeature(SerializerFeature... features) {
            this.features = features;
        }

        private SerializerFeature[] getFeatures() {
            return features;
        }
    }

    /**
     * 封装 TypeReference，通过子类来获取泛型类型
     * <p>
     * <pre>
     * new JsonReference &lt; Map &lt; String, Integer &gt; &gt;() {};
     * </pre>
     */
    public abstract static class AbstractJsonReference<T> extends TypeReference<T> {
    }

    /**
     * Java 对象转换成 Json 字符串
     *
     * @param object 对象
     * @return json 字符串
     */
    public static String toJson(Object object) {
        return toJson(object, SFeature.EMPTY);
    }


    /**
     * Java 对象转换成 Json 字符串
     *
     * @param object  对象
     * @param feature 序列化方式
     * @return json 字符串
     */
    public static String toJson(Object object, SFeature feature) {
        if (null == feature) {
            feature = SFeature.EMPTY;
        }
        return JSON.toJSONString(object, feature.getFeatures());
    }


    /**
     * json 转换成 Java 对象
     *
     * @param json  json字符串
     * @param clazz Java 对象
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return toObject(json, (Type) clazz);
    }

    /**
     * json 转换成 Java 对象（可嵌套）
     *
     * @param json          json字符串
     * @param jsonReference new JsonReference &lt; Map &lt; String, Integer &gt; &gt;() {}; 等方式；new 一个 JsonReference 的匿名子类来记录泛型类型
     * @param <T>           泛型类型
     * @return Java 对象
     */
    public static <T> T toObject(String json, AbstractJsonReference<T> jsonReference) {
        return toObject(json, jsonReference.getType());
    }

    public static <T> T toObject(String json, Type type) {
        try {
            return JSON.parseObject(json, type);
        } catch (JSONException ex) {
            throw new JSONException(json + " >> error parse to >> " + type.getTypeName(), ex);
        }
    }

    /**
     * 一个Java对象转换成另一个Java对象
     *
     * @param obj   Java 对象
     * @param clazz 转换后的 Java 对象类型
     * @return 转换后的 Java 对象
     */
    public static <T> T caseObject(Object obj, Class<T> clazz) {
        return TypeUtils.cast(obj, clazz, ParserConfig.getGlobalInstance());
    }
}
