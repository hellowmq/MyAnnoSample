package com.wenmq.comp;


import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import com.wenmq.anno.NewIntent;

@AutoService(Processor.class)
public class NewIntentProcessor extends AbstractProcessor {

    private static final String METHOD_PREFIX = "getIntent";
    private static final String METHOD_PATH_MAP_ENTRY = "getIntentByPath";
    private static final ClassName classIntent = ClassName.get("android.content", "Intent");
    private static final ClassName classContext = ClassName.get("android.content", "Context");
    private static final ClassName classActivity = ClassName.get("android.app", "Activity");

    private Filer filer;
    private Messager messager;
    private Elements elements;
    private Map<String, String> activitiesWithPackage;
    private HashMap<String, ClassName> pathsWithActivitiesClass;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elements = processingEnvironment.getElementUtils();
        pathsWithActivitiesClass = new HashMap<>();
        activitiesWithPackage = new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            /*
             * 1- Find all annotated element
             */
            for (Element element : roundEnvironment.getElementsAnnotatedWith(NewIntent.class)) {

                if (element.getKind() != ElementKind.CLASS) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Can be applied to class.");
                    return true;
                }

                TypeElement typeElement = (TypeElement) element;
                NewIntent annotation = typeElement.getAnnotation(NewIntent.class);
                activitiesWithPackage.put(
                        typeElement.getSimpleName().toString(),
                        elements.getPackageOf(typeElement).getQualifiedName().toString()
                );
                pathsWithActivitiesClass.put(annotation.path(),
                        ClassName.get(
                                elements.getPackageOf(typeElement).getQualifiedName().toString(),
                                typeElement.getSimpleName().toString()
                        )
                );
            }


            /*
             * 2- Generate a class
             */


            TypeSpec.Builder navigatorClass = TypeSpec
                    .classBuilder("ANavigator")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            buildIndependentMethod(navigatorClass);
            buildByPathMap(navigatorClass);
//            buildByPathMethodRef(navigatorClass);


            /*
             * 3- Write generated class to a file
             */
            JavaFile.builder("com.wenmq.annotationsample", navigatorClass.build()).build().writeTo(filer);


        } catch (IOException e) {
            if (e.getMessage().contains("Attempt to recreate a file")) {
//               override fenerated files, just ignore
            } else {
                e.printStackTrace();
            }
        }

        return true;
    }

    private void buildByPathMap(TypeSpec.Builder navigatorClass) {
        MethodSpec.Builder builder = MethodSpec
                .methodBuilder(METHOD_PATH_MAP_ENTRY);
        builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        builder.returns(classIntent);
        builder.addParameter(classContext, "context");
        builder.addParameter(ClassName.get(String.class), "path");
        builder.addStatement("$T<String, $T<? extends $T>> rMap = new $T()",
                ClassName.get(Map.class),
                ClassName.get(Class.class),
                classActivity,
                ClassName.get(HashMap.class)
        );
        for (Map.Entry<String, ClassName> element : pathsWithActivitiesClass.entrySet()) {
            String path = element.getKey();
            ClassName activityClass = element.getValue();
            builder.addStatement("rMap.put(\"$L\",$L)", path, activityClass + ".class");
        }
        builder.addStatement("return new $T($L, $L)", classIntent, "context", "rMap.get(path)");
        MethodSpec intentMethod = builder
                .build();
        navigatorClass.addMethod(intentMethod);
    }

    private void buildIndependentMethod(TypeSpec.Builder navigatorClass) {
        for (Map.Entry<String, String> element : activitiesWithPackage.entrySet()) {
            String activityName = element.getKey();
            String packageName = element.getValue();
            ClassName activityClass = ClassName.get(packageName, activityName);
            MethodSpec intentMethod = MethodSpec
                    .methodBuilder(METHOD_PREFIX + activityName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(classIntent)
                    .addParameter(classContext, "context")
                    .addStatement("return new $T($L, $L)", classIntent, "context", activityClass + ".class")
                    .build();
            navigatorClass.addMethod(intentMethod);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(NewIntent.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}