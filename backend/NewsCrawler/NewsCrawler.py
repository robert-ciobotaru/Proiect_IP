from urllib import urlopen
import json
import time
import datetime
#import requests
import urllib2
import os

apiKey = 'e013828a660644e7b531f916f38a0f86'
url = 'https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=' + apiKey
urlSend = "http://104.199.93.85:8991"

if os.path.isfile('log_news.txt'):
    os.remove('log_news.txt')
log = open ('log_news.txt', 'w')

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
            log.write('\nSent at ' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + ' :\n')
            log.write(jsonToSend + '\n\n')
        except:
            i = i - 1 
            log.write('Eroare!\n\n')
            time.sleep(1)

    time.sleep(1000)
