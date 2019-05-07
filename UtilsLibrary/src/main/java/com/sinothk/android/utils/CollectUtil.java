package com.sinothk.android.utils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  @author 梁玉涛 (https://github.com/sinothk)
 *  @Create 2018/2/9 15:52
 *  @Project: OUtilsLib
 *  @Description:
 *  @Update:
 * <pre>
 */
@Deprecated
public class CollectUtil<E> {

    public static boolean isNotEmpty(List<?> list) {
        return !(list == null || list.isEmpty());
    }

    public static boolean isEmpty4Map(Map<?, ?> map) {
        return !(map == null || map.isEmpty());
    }

    public static boolean isEmpty4Arrays(Object[] array) {
        return !(array == null || array.length == 0);
    }

    public static final String DESC = "desc";
    public static final String ASC = "asc";

//    /**
//     * 实体列表中，按中文关键字排序:默认升序
//     *
//     * @param list
//     * @param method 属性的get方法名
//     */
//    public void sortByChineseKeyword(List<E> list, final String method) {
//        sortByChineseKeyword(list, method, "asc");
//    }
//
//    /**
//     * 实体列表中，按中文关键字排序:根据指定的排序方式排序
//     *
//     * @param list
//     * @param method 属性的get方法名
//     * @param sort
//     */
//    public void sortByChineseKeyword(List<E> list, final String method, final String sort) {
//        Collections.sort(list, new Comparator<Object>() {
//            Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
//
//            public int compare(Object a, Object b) {
//                int ret = 0;
//                try {
//                    Method m1 = ((E) a).getClass().getMethod(method, null);
//                    Method m2 = ((E) b).getClass().getMethod(method, null);
//
//                    String v1 = m1.invoke(((E) a), null).toString();
//                    String v2 = m2.invoke(((E) b), null).toString();
//
//                    if (sort != null && "desc".equals(sort)) {
//                        // 倒序
//                        ret = cmp.compare(v2, v1);
//                    } else {
//                        // 正序 {}
//                        ret = cmp.compare(v1, v2);
//                    }
//                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ne) {
//                    System.out.println(ne);
//                }
//                return ret;
//            }
//        });
//        ArrayList<Bank> dList = new ArrayList<Bank>();
//        dList.add(new Bank("11", "啊商银行"));
//        dList.add(new Bank("12", "上海农商银行"));
//        dList.add(new Bank("13", "波业银行"));
//        dList.add(new Bank("14", "中国工商银行"));
//        new OCollectUtil<Bank>().sort4ChineseKeyword(dList, "getBankName", OCollectUtil.ASC);
//        for (Bank b : dList) {
//            System.out.println("code = " + b.getBankCode() + "，name = " + b.getBankName());
//        }
//    }
    /**
     * 纯文字列表的排序
     *
     * @param list
     */
    public static void sort4ChineseStringList(ArrayList<String> list) {
        Collections.sort(list, Collator.getInstance(java.util.Locale.CHINA));
//        // ================列表中，按中文关键字排序=================
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("一鸣惊人");
//        list.add("人山人海");
//        list.add("海阔天空");
//        list.add("空前绝后");
//        list.add("后来居上");
//        OCollectUtil.sort4ChineseStringList(list);
//        for (int i = 0; i < list.size(); i++) {
//            Log.e("MainActivity", i + " = " + list.get(i));
//        }
    }

//    /**
//     * 获得列表中，实体内某个中文属性的首文字字母：使用于通信录中，获得联系人字母导航。
//     *
//     * @param dList  实体列表
//     * @param method 实体中获得属性值的get方法名
//     * @return
//     */
//    public String[] getChineseUppercase(ArrayList<E> dList, String method) {
//
//        String[] pinyinArray = new String[dList.size()];
//
//        for (int i = 0; i < dList.size(); i++) {
//            try {
//                Method m1 = ((E) dList.get(i)).getClass().getMethod(method, null);
//                String v1 = m1.invoke(((E) dList.get(i)), null).toString();
//
//                char word = v1.charAt(0);
//                HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//                format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
//
//                String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(word, format);
//
//                String firstPinYin = pinyin[0];
//                pinyinArray[i] = firstPinYin.charAt(0) + "";
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return pinyinArray;
////        // ================实体列表中，按中文关键字排序=================
////        ArrayList<Bank> dList = new ArrayList<Bank>();
////        dList.add(new Bank("11", "啊商银行"));
////        dList.add(new Bank("08", "上海农商银行"));
////        dList.add(new Bank("13", "波业银行"));
////        dList.add(new Bank("04", "中国工商银行"));
////
////        // 排序
////        new OCollectUtil<Bank>().sortByChineseKeyword(dList, "getBankName");
////
////        // 获得字母字符串数组
////        String[] pinyins =  new OCollectUtil<Bank>().getChineseUppercase(dList, "getBankName");
////
////        for (int i = 0; i < pinyins.length; i++) {
////            Log.e("MainActivity", "uppercase = " + pinyins[i]);
////        }
//    }
}
