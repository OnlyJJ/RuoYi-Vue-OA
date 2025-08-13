package com.ruoyi.tools.utils.compare;

import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author wocurr.com
 */
@Slf4j
public class CompareUtils {
    private static final String DOU = ",";

    private static final String TRANSLATE_USER = "user";

    private static final String TRANSLATE_DEPT = "dept";

    private static final String TRANSLATE_CODE = "code";

    private static final String TRANSLATE_DATE = "date";

    private static final String TRANSLATE_STRING = "string";

    public static List<CompareResult> compare(Object source, Object target) {
        return compare(source, target, null);
    }

    public static List<CompareResult> compare(Object source, Object target, List<String> ignoreCompareFields) {
        if (source == null || target == null)
            return null;
        Map<String, CompareNode> sourceMap = getFiledValueMap(source);
        Map<String, CompareNode> targetMap = getFiledValueMap(target);
        if (sourceMap.isEmpty() || targetMap.isEmpty())
            return null;
        return doCompare(sourceMap, targetMap, ignoreCompareFields);
    }

    private static List<CompareResult> doCompare(Map<String, CompareNode> sourceMap, Map<String, CompareNode> targetMap, List<String> ignoreCompareFields) {
        List<CompareResult> result = new ArrayList<>();
        Set<String> keys = sourceMap.keySet();
        boolean isIgnore = CollectionUtils.isEmpty(ignoreCompareFields);
        for (String key : keys) {
            String sv, tv, sd, td, sc, tc;
            List<String> su, tu, dsu, dtu;
            CompareNode sn = sourceMap.get(key);
            CompareNode tn = targetMap.get(key);
            if (!isIgnore && ignoreCompareFields.contains(sn.getFieldKey()))
                continue;
            switch (sn.getFieldTranslate()) {
                case "string":
                    sv = (sn.getFieldValue() == null) ? "" : sn.getFieldValue().toString();
                    tv = (tn.getFieldValue() == null) ? "" : tn.getFieldValue().toString();
                    if (!sv.equals(tv)) {
                        CompareResult r = new CompareResult();
                        r.setFieldKey(sn.getFieldKey());
                        r.setFieldName(sn.getFieldName());
                        r.setFieldValue(sv);
                        r.setOrigFieldValue(tv);
                        result.add(r);
                    }
                case "date":
                    sd = (sn.getFieldValue() == null) ? "" : DateUtils.parseDateToStr(sn.getFormat(), (Date) sn.getFieldValue());
                    td = (tn.getFieldValue() == null) ? "" : tn.getFieldValue().toString();
                    if (!sd.equals(td)) {
                        CompareResult r = new CompareResult();
                        r.setFieldKey(sn.getFieldKey());
                        r.setFieldName(sn.getFieldName());
                        r.setFieldValue(sd);
                        r.setOrigFieldValue(td);
                        result.add(r);
                    }
                case "code":
                    sc = (sn.getFieldValue() == null) ? "" : sn.getFieldValue().toString();
                    tc = (tn.getFieldValue() == null) ? "" : tn.getFieldValue().toString();
                    if (!sc.equals(tc)) {
                        CompareResult r = new CompareResult();
                        r.setFieldKey(sn.getFieldKey());
                        r.setFieldName(sn.getFieldName());
                        r.setFieldValue(sc);
                        r.setOrigFieldValue(tc);
                        r.setFieldTranslate("code_" + sn.getFormat());
                        result.add(r);
                    }
                case "user":
                    su = (sn.getFieldValue() == null) ? new ArrayList<>() : Arrays.<String>asList(sn.getFieldValue().toString().split(","));
                    tu = (tn.getFieldValue() == null) ? new ArrayList<>() : Arrays.<String>asList(tn.getFieldValue().toString().split(","));
                    if (!CollectionUtils.isEqualCollection(su, tu)) {
                        CompareResult r = new CompareResult();
                        r.setFieldKey(sn.getFieldKey());
                        r.setFieldName(sn.getFieldName());
                        r.setFieldValue(StringUtils.join(su, ","));
                        r.setOrigFieldValue(StringUtils.join(tu, ","));
                        r.setFieldTranslate("user");
                        result.add(r);
                    }
                case "dept":
                    dsu = (sn.getFieldValue() == null) ? new ArrayList<>() : Arrays.<String>asList(sn.getFieldValue().toString().split(","));
                    dtu = (tn.getFieldValue() == null) ? new ArrayList<>() : Arrays.<String>asList(tn.getFieldValue().toString().split(","));
                    if (!CollectionUtils.isEqualCollection(dsu, dtu)) {
                        CompareResult r = new CompareResult();
                        r.setFieldKey(sn.getFieldKey());
                        r.setFieldName(sn.getFieldName());
                        r.setFieldValue(StringUtils.join(dsu, ","));
                        r.setOrigFieldValue(StringUtils.join(dtu, ","));
                        r.setFieldTranslate("dept");
                        result.add(r);
                    }
            }
        }
        return result;
    }

    private static Map<String, CompareNode> getFiledValueMap(Object t) {
        if (t == null)
            return Collections.emptyMap();
        Field[] fields = t.getClass().getDeclaredFields();
        if (fields == null || fields.length == 0)
            return Collections.emptyMap();
        Map<String, CompareNode> map = new LinkedHashMap<>();
        for (Field field : fields) {
            Compare compareAnnotation = field.<Compare>getAnnotation(Compare.class);
            if (compareAnnotation != null) {
                field.setAccessible(true);
                try {
                    String fieldKey = field.getName();
                    CompareNode node = new CompareNode();
                    node.setFieldKey(fieldKey);
                    node.setFieldValue(field.get(t));
                    node.setFieldName(compareAnnotation.value());
                    node.setFormat(compareAnnotation.format());
                    node.setFieldTranslate(compareAnnotation.fieldTranslate());
                    map.put(field.getName(), node);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return map;
    }

}
