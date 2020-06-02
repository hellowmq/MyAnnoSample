# MyAnnoSample

This is a practice of Annotation Processor. 

JUST FOR LEARNING. 

### About the Reference

The feference article is [Here](https://medium.com/@iammert/annotation-processing-dont-repeat-yourself-generate-your-code-8425e60c6657)

### FAQ

Some problems below may occur when you try coding as the artical say.

1. Navigator.class is not created?

	1. create `main/MERA-INF/service/javax.annotation.processing.Processor` file;
	2. input the CanonicalName of your Processor class;
	3. add `annotationProcessor project(':comp')` in `app/build.hradle`;


2. Don't want to create '/MERA-INF/service/javax.annotation.processing.Processor' manaully?

	AutoService will generate the file META-INF/services/javax.annotation.processing.Processor in the output classes folder.

	1. add Google AutoService into dependencies 

		```groovy

		    implementation 'com.google.auto.service:auto-service:1.0-rc4'
		//    In the Gradle 5.0 project， annotationProcessor will be ignored
		//    Add annotationProcessor '***' manually
		    annotationProcessor 'com.google.auto.service:auto-service:1.0-rc4'

		```

	2. add `@AutoService(Processor.class)` before your Processor. 

	3. rebuild the project, AutoService will generate the META-INF metadata file.




