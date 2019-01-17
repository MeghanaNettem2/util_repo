def execute() {
	stage('Checkout') {
		sh 'git props.JAVA_APP_REPO_GIT_URL'
       
		
	}
	
	stage('Build') {
		/*sh props.SONAR_SCAN+' '+props.SONAR_HOST*/
		sh props.MAVEN_BUILD
		
    }
	
	stage('stageBuildManagement') {
		commonUtility.uploadWarArtifactory();
		sh props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION
		print 'Build Management Success'
	}
}
return this
