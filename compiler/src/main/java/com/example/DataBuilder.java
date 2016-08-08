package com.example;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import static javax.tools.Diagnostic.Kind.ERROR;

/**
 * Created by linxj on 16/8/6.
 */

public class DataBuilder {

    private RoundEnvironment environment;
    private ProcessingEnvironment processingEnvironment;
    public DataBuilder(RoundEnvironment environment,ProcessingEnvironment processingEnvironment){
        this.environment = environment;
        this.processingEnvironment = processingEnvironment;
    }

    public boolean build(String className,String packageName){
        String fullName = packageName+"."+className;
        try {
        TypeSpec spec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(SchemeDataBase.class)
                .addMethod(generateGetSchemeHandlerClassesMethod(findSchemeUrl()))
                .addJavadoc("Please don't modify this code")
                .build();

            JavaFile javaFile = JavaFile.builder(packageName,spec).build();
            JavaFileObject object = processingEnvironment.getFiler().createSourceFile(fullName);
            Writer writer = object.openWriter();
            writer.write(javaFile.toString());
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
            return false;
    }


    public Map<String,String> findSchemeUrl(){
        Set<? extends Element> elementsAnnotatedWithScheme = environment.getElementsAnnotatedWith(UrlScheme.class);
        Map<String, String> handlersMap = new HashMap<>();
        for (Element element : elementsAnnotatedWithScheme) {

            String clazz = ((TypeElement) element).getQualifiedName().toString();
            String key = element.getAnnotation(UrlScheme.class).value();
            if (handlersMap.containsKey(key)) {
                processingEnvironment.getMessager().printMessage(ERROR, "cannot add same scheme",element);
            }
            handlersMap.put(key, clazz + ".class");
        }
        return handlersMap;
    }


    private MethodSpec generateGetSchemeHandlerClassesMethod(Map<String, String> handlersMap) {
        ClassName map = ClassName.get("java.util", "Map");
        ClassName hashMap = ClassName.get("java.util", "HashMap");
        ClassName aClass = ClassName.get("java.lang", "Class");
        ClassName aString = ClassName.get("java.lang", "String");

        ParameterizedTypeName mapOfClass = ParameterizedTypeName.get(map, aString, aClass);
        ParameterizedTypeName hashMapOfClass = ParameterizedTypeName.get(hashMap, aString, aClass);
        MethodSpec.Builder builder = MethodSpec.methodBuilder("getSchemeActivityClasses")
                .addModifiers(Modifier.PUBLIC)
                .returns(mapOfClass)
                .addAnnotation(Override.class)
                .addStatement("$T classes = new $T()", mapOfClass, hashMapOfClass);

        for (Map.Entry<String, String> entry : handlersMap.entrySet()) {
            builder.addStatement(String.format("classes.put(\"%s\", %s)", entry.getKey(), entry.getValue()));
        }
        builder.addStatement("return classes");
        return builder.build();
    }


}
