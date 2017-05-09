from urllib import urlopen
import json
import time
#import requests
import urllib2

apiKey = 'e013828a660644e7b531f916f38a0f86'
url = 'https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=' + apiKey
urlSend = 'http://fenrir.info.uaic.ro:8911'
while True:
	urlRequest = urlopen(url)
	requestedJson = json.load(urlRequest)
	#response = requests.get(url)
    	#requestedJson= response.json()
	for i in range(len(requestedJson['articles'])):
		jsonToSend =json.dumps({'Type': 'News',
					'Data':  requestedJson['articles'][i],
					'error': 'Segmentation fold'})

		#print jsonToSend
		#sendSource = urlopen(urlSend, data=jsonToSend)
		#sendSource.close()
            	try:
			sendSource = urlopen(urlSend, data=jsonToSend)
			sendSource.close()
		except:
			i = i - 1 
			time.sleep(1)

	time.sleep(1000)
