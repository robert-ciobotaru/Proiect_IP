from urllib import urlopen
import json
import os
import time
import datetime

f = open('city_list.txt', 'r')
apiKey = '8adc3c7f8f9235744ffd288bc0a9a2b4'  # API key for request
urlProxy = "http://104.199.93.85:8991"

if os.path.isfile('log_weather.txt'):
    os.remove('log_weather.txt')
log = open('log_weather.txt', 'w')

curReqCount = 0
city_id = []

for line in f:
    data = line.split()
    if len(data) == 5 and data[4] == 'RO':
        city_id.append(data[0])

f.close()

while True:
    for i in range(len(city_id)):

        if curReqCount == 60: #60 is the request limit for free use of Weather API
            curReqCount = 0
            time.sleep(60)

        curReqCount = curReqCount + 1
        # Location is defined by id
        url = 'http://api.openweathermap.org/data/2.5/weather?id=' + city_id[i] + '&appid=' + apiKey
        try:
            weatherHandle = urlopen(url)
            weatherJson = json.load(weatherHandle)
        except:
            log.write('Eroare la primire json!\n\n')
            continue

        if 'name' not in weatherJson:
            log.write('Name was not sent\n\n')
            continue
        if 'sys' not in weatherJson:
            log.write('Sys was not sent\n\n')
            continue
        if 'country' not in weatherJson['sys']:
            log.write('Country was not sent\n\n')
            continue
        if 'weather' not in weatherJson:
            log.write('Weather was not sent\n\n')
            continue
        if len(weatherJson['weather']) < 1:
            log.write('Weather list was not sent\n\n')
            continue
        if 'description' not in weatherJson['weather'][0]:
            log.write('Text was not sent\n\n')
            continue

        jsonToProxy = json.dumps({'Type': 'Weather',
                                  'Data': {
                                      'location': {'city': weatherJson['name'],
                                                   'country': weatherJson['sys']['country']},
                                      'text': weatherJson['weather'][0]['description']}})

        # send weather json to proxy
        try:
            proxyHandle = urlopen(urlProxy, data=jsonToProxy)
            proxyHandle.close()
            log.write('\nSent at ' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + ' :\n')
            log.write(jsonToProxy + '\n\n')
        except:
            log.write('Eroare la trimitere json!\n\n')
    time.sleep(1554)
