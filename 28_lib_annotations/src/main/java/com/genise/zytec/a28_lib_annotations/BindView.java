package com.genise.zytec.a28_lib_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//什么地方保留它 保留到最后 可以被反射看见
//Source只是在编译之前看到就可以了
@Retention(RetentionPolicy.SOURCE)
//用在字段
@Target(ElementType.FIELD)
//Annotation 可以看成是一种特殊的interface
public @interface BindView {
    //特殊的方法 value= 可以省略
    int value();
}
