def execute(props) {
	stage('stageCheckoutProject') {
		app_url =props.JAVA_APP_REPO_GIT_URL
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
	stage('stageBuildAutomation') {
		/*sh props.SONAR_SCAN+' '+props.SONAR_HOST*/
		sh props.MAVEN_BUILD
		print 'Build Automation Success'
    }
	
	stage('stageBuildManagement') {
		commonUtility.uploadWarArtifactory();
		sh 'cp props.TOMCAT_DEPLOY props.TOMCAT_LOCATION'
		print 'Build Management Success'
	}
}
return this
