from urllib import urlopen
import json
import time
import datetime
#import requests
import urllib2
import os

apiKey = 'e013828a660644e7b531f916f38a0f86'
url = 'https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=' + apiKey
urlSend = "http://104.198.38.180:8991"

if os.path.isfile('log_news.txt'):
    os.remove('log_news.txt')
log = open ('log_news.txt', 'w')

while True:
    try:
        urlRequest = urlopen(url)
        requestedJson = json.load(urlRequest)
    except:
        log.write('Eroare la primire json!\n\n')
        continue
    #response = requests.get(url)
        #requestedJson= response.json()
    for i in range(len(requestedJson['articles'])):
        try:
            jsonToSend =json.dumps({'Type': 'News',
                        'Data':  requestedJson['articles'][i],
                        'error': 'Segmentation fold'})
        except:
            log.write('Json invalid!\n\n')
            continue
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
            log.write('Eroare la trimitere json:\n')
            log.write(jsonToSend + '\n\n')
            time.sleep(1)

    time.sleep(1000)
    statinfo = os.stat('log_news.txt')
    if statinfo.st_size > 10485760:
        os.remove('log_news.txt')
        log = open('log_news.txt','w')
