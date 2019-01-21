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

def sonar(){
	def mvncmd=props.SONAR_SCAN
	def sonarurl=props.SONAR_HOST
	def url=mvncmd+sonarurl
	sh "${url}"
}
def sendEmail() {
	emailext( 
			subject: '${DEFAULT_SUBJECT}', 
			body: '${DEFAULT_CONTENT}',
		        to: props.RECEPIENT_MAIL_ID
		);
	print 'mail sent'
}

def failureEmail(err) {
	emailext( 
			subject: 'BUILD Failure', 
		         body: "${err}",
		        to: props.RECEPIENT_MAIL_ID
		);
	print 'mail sent'
}
return this
