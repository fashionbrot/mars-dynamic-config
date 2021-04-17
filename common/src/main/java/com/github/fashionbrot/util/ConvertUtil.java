package com.github.fashionbrot.util;

import com.github.fashionbrot.annotation.ToMapIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.*;


@Slf4j
public class ConvertUtil {


    public static <T> T sourceToTarget(Object source, Class<T> target){
        if(source == null){
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.newInstance();
            BeanUtils.copyProperties(source, targetObject);
        } catch (Exception e) {
            log.error("convert error ", e);
        }

        return targetObject;
    }

    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target){
        if(sourceList == null){
            return null;
        }

        List targetList = new ArrayList<>(sourceList.size());
        try {
            for(Object source : sourceList){
                T targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        }catch (Exception e){
            log.error("convert error ", e);
        }

        return targetList;
    }

    public static Map toMap(Object bean)  {
        Map<String,Object> returnMap = new HashMap();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取该字段的注解
            ToMapIgnore annotation = field.getAnnotation(ToMapIgnore.class);
            //如果没有注解 或者 注解值为false 则获取该值存入返回的map中
            if (annotation == null || !annotation.Value()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(bean);
                    if (value!=null){
                        returnMap.put(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnMap;
    }

}