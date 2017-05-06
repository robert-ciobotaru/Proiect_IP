from urllib import urlopen
import json
import time

apiKey = 'e013828a660644e7b531f916f38a0f86'
url = 'https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=' + apiKey
while True:
	urlRequest = urlopen(url)
	requestedJson = json.load(urlRequest)
	for i in range(len(requestedJson['articles'])):
		jsonToSend =json.dumps({'Type': 'News',
					'Data':  requestedJson['articles'][i],
					'error': 'Segmentation fold'})
		urlSend = 'http://fenrir.info.uaic.ro:8991'
		sendSource = urlopen(urlSend, data=jsonToSend)
		sendSource.close()
	time.sleep(1000)