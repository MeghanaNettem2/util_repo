def uploadWarArtifactory() {
	script {
		def server = Artifactory.newServer  url: "${props.ARTIFACTORY_ID}",username:'admin',password:'password'
               def uploadSpec = """{
   	
                "files":[
                    {
                   "pattern":"target/*.war",
			"target": "repo/${artifactId}/${version}.${BUILD_NUMBER}/"
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


}
return this
