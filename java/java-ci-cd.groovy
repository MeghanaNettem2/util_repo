def execute() {
	stage('stageCheckoutProject') {
		git url: props.JAVA_APP_REPO_GIT_URL,
        branch: props.BRANCH
		pom = readMavenPom file: props.POM_FILE
		artifactId=pom.artifactId
		version=pom.version
		print 'Checkout Project Success'
	}
	
	stage('stageBuildAutomation') {
		/*sh props.SONAR_SCAN+' '+props.SONAR_HOST*/
		sh props.MAVEN_BUILD
		print 'Build Automation Success'
    }
	
	stage('stageBuildManagement') {
		commonUtility.uploadWarArtifactory();
		sh props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION
		print 'Build Management Success'
	}
}
return this
