from urllib import urlopen
import json
import os
import time
import datetime

f = open('city_list.txt', 'r')
apiKey = 'f84f759814378e0469d34ed3b8c0bec1'  # API key for request
urlProxy = "http://104.199.93.85:8991"

if os.path.isfile('log_weather.txt'):
    os.remove('log_weather.txt')
log = open('log_weather.txt', 'w')

city_id = []

for line in f:
    data = line.split()
    if len(data) == 5 and data[4] == 'RO':
        city_id.append(data[0])

f.close()

while True:
    try:
        for i in range(len(city_id)):
            # Location is defined by id
            url = 'http://api.openweathermap.org/data/2.5/weather?id=' + city_id[i] + '&appid=' + apiKey
            weatherHandle = urlopen(url)

            weatherJson = json.load(weatherHandle)

            log.write('\nSent at ' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + ' :\n')

            if weatherJson['name'] == '':
                log.write('Name was not sent\n\n')
                continue
            elif weatherJson['sys']['country'] == '':
                log.write('Country was not sent\n\n')
                continue
            elif weatherJson['weather'][0]['description'] == '':
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
                log.write(jsonToProxy + '\n\n')
            except:
                log.write('Eroare!\n\n')
        time.sleep(1200)
    except:
        log.write('Eroare!\n\n' )
        continue
