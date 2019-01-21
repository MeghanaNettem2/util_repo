def function(props) {
	stage('stageCheckoutProject') {
		app_url =props.JAVA_APP_REPO_GIT_UR
		echo "${app_url}"
		git "${app_url}"
		
			
		pom = readMavenPom file: props.POM_FILE
		artifactId=pom.artifactId
		echo "${artifactId}"
		version=pom.version
		print 'Checkout Project Success'
	}
	
	stage('sonar'){
		commonUtility.sonar();
	}
	stage('Build') {
		/*sh props.SONAR_SCAN+' '+props.SONAR_HOST*/
		sh props.MAVEN_BUILD
		print 'Build Automation Success'
    }
	
	stage('Artifactory') {
		commonUtility.uploadWarArtifactory();
		
		print 'Build Management Success'
	}
	stage('tomcat installation ') {
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
	stage('deploy'){
		sh  props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION
	}
	
	stage('email')
	{
		commonUtility.sendEmail();
	}
}
return this
