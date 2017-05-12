from urllib import urlopen
import json

import time

f = open('city_list.txt', 'r')
apiKey = 'f84f759814378e0469d34ed3b8c0bec1'  # API key for request
urlProxy = 'http://fenrir.info.uaic.ro:8991'

city_id = []

for line in f:
    data = line.split()
    if len(data) == 5 and data[4] == 'RO':
        city_id.append(data[0])

f.close()

while True:

    for i in range(len(city_id)):
        # Location is defined by id
        url = 'http://api.openweathermap.org/data/2.5/weather?id=' + city_id[i] + '&appid=' + apiKey
        weatherHandle = urlopen(url)

        weatherJson = json.load(weatherHandle)
        jsonToProxy = json.dumps({'Type': 'Weather',
                                  'Data': {
                                      'location': {'city': weatherJson['name'],
                                                   'country': weatherJson['sys']['country']},
                                      'text': weatherJson['weather'][0]['description']}})

        # send weather json to proxy
        proxyHandle = urlopen(urlProxy, data=jsonToProxy)
        proxyHandle.close()
    time.sleep(1200)
