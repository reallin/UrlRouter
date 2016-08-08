package com.example;

import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;


/**
 * Created by linxj on 16/8/6.
 */
@AutoService(Processor.class)
public class SchemeProcess extends AbstractProcessor {
    boolean processed;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportTypes = new LinkedHashSet<String>();
        supportTypes.add(UrlScheme.class.getCanonicalName());

        return supportTypes;
    }
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(processed){
            return true;
        }
        processed = new DataBuilder(roundEnv, processingEnv)
                .build("SchemeDispatcher$$SchemeDatabase", "com.example.linxj.internal");
        return processed;

    }
}
