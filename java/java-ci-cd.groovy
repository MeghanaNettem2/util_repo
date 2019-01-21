def function(props) {
	stage('CheckoutProject') {
		app_url =props.JAVA_APP_REPO_GIT_URL
		echo "${app_url}"
		git "${app_url}"
		
			
		pom = readMavenPom file: props.POM_FILE
		artifactId=pom.artifactId
		echo "${artifactId}"
		version=pom.version
		print 'Checkout Project Success'
	}
	
	stage('SonarAnalysis'){
		commonUtility.sonar();
	}
	stage('BuildProject') {
		/*sh props.SONAR_SCAN+' '+props.SONAR_HOST*/
		sh props.MAVEN_BUILD
		print 'Build Automation Success'
    }
	
	stage('UploadArtifactory') {
		commonUtility.uploadWarArtifactory();
		
		print 'Build Management Success'
	}
	stage('Tomcat Installation ') {
	def Install = false;
	try {
		input message: 'Install?', ok: 'Install'
		Install = true
		} catch (err) {
	Install = false
	
	}
	
       if (Install){   
	 
	  sh "docker run -d --name tom -p 9001:8080 tomcat"
        }
    }	
	stage('Deploying to Tomcat'){
		sh  props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION
	}
	
	stage('Email Notification')
	{
		commonUtility.sendEmail();
	}
}
return this
