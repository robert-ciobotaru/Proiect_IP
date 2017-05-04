from urllib import urlopen
import json

import time

f = open('city_list.txt', 'r')
apiKey = 'f84f759814378e0469d34ed3b8c0bec1'  # API key for request
urlProxy = 'http://localhost:8991'

while True:
    for line in f:
        data = line.split()
        if len(data) == 5 and data[4] == 'RO':
            # Location is defined by id
            url = 'http://api.openweathermap.org/data/2.5/weather?id=' + data[0] + '&appid=' + apiKey
            weatherHandle = urlopen(url)

            weatherJson = json.load(weatherHandle)
            jsonToProxy = json.dumps({'Type': 'Weather',
                                      'Data': {
                                          'Location': {'City': weatherJson['name'],
                                                       'Country': weatherJson['sys']['country']},
                                          'Text': weatherJson['weather'][0]['description']}})

            # send weather json to proxy
            proxyHandle = urlopen(urlProxy, data=jsonToProxy)
            proxyHandle.close()
    time.sleep(1200)
