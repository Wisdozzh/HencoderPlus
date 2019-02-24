package com.genise.zytec.a28_lib_reflection;

import android.app.Activity;

import java.lang.reflect.Field;

public class Binding {
    public static void bind(Activity activity) {
//        activity.textView = activity.findViewById(R.id.textView);
        //遍历activity里面的每一个字段
        for (Field field : activity.getClass().getDeclaredFields()) {
            //看到有没有BindView的注解
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                try {
                    //将field设置为public
                    field.setAccessible(true);
                    //将注解的值设置给activity
                    field.set(activity, activity.findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
