def uploadWarArtifactory() {
	script {
		server = Artifactory.newServer  url: "${props.ARTIFACTORY_ID}", 
		uploadSpec = """{
			"files":[{
			"pattern": "target/*.war",
			"target": "repo/${artifactId}/${version}.${buildNo}/"
			}]
		}"""
		server.upload(uploadSpec) 	
	}
}

def sendEmail() {
	emailext( 
			subject: '${DEFAULT_SUBJECT}', 
			body: '${DEFAULT_CONTENT}',
			to: props.BUILD_EMAIL_RECIPIENT
		);
	print 'mail sent'
}

def cleanWorkspace() {
	script {
		sh 'rm -rf ../'+jobName+'/*'
	}
	print 'cleaned workspace'
}
return this
