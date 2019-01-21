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
		MAIL_ID: PROPS.RECEPIENT_MAIL_ID
		to: "${MAIL_ID}"
		);
	print 'mail sent'
}

def failureEmail(err) {
	emailext( 
			subject: 'BUILD Failure', 
		         body: "${err}",
			MAIL_ID: PROPS.RECEPIENT_MAIL_ID
		        to: "${MAIL_ID}"
		);
	print 'mail sent'
}
return this
