package com.genise.zytec.a28_lib_processor;

import com.genise.zytec.a28_lib_annotations.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public class BindingProcessor extends AbstractProcessor {

    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //filer 处理文件的那个工具
        filer = processingEnvironment.getFiler();
    }

    /**
     * 处理注解的
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("annotation processor running!!!");
        //
        ClassName className = ClassName.get("com.genise.zytec.a28_annotation_processing", "Test");
        TypeSpec builtClass = TypeSpec.classBuilder(className).build();

        try {
            JavaFile.builder("com.genise.zytec.a28_annotation_processing", builtClass)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 用来指定 你要支持那些annotation
     * 只处理 BindView 其他的都不管
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }
}
