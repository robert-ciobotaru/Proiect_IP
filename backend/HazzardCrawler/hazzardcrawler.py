from urllib import urlopen
import requests
import time
import json
import datetime

global already_sent_earthquakes
global already_sent_hazzards

def erase_expired_events(past, opt):
    global already_sent_earthquakes
    global already_sent_hazzards

    if opt == 1:
        copy = already_sent_earthquakes.copy()
        for event in copy:
            if copy[event] < past:
                print 'Am sters evenimentul ', event, '\n'
                del already_sent_earthquakes[event]
    else:
        copy = already_sent_hazzards.copy()
        for event in copy:
            if copy[event] < past:
                print 'Am sters evenimentul ', event, '\n'
                del already_sent_hazzards[event]

def earthquakes(past, url, log):
    global already_sent_earthquakes

    dictionar = {'Type':'Hazzard'}
    url_earthquakes = 'https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson'
    url_earthquakes = url_earthquakes + '&starttime=' + past

    erase_expired_events(past, 1)

    log.write('\n\nBetween ' + past + ' and ' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + ' :\n\n')
    response = requests.get(url_earthquakes)
    json_response = response.json()

    for cutremur in json_response['features']:
        if cutremur['id'] not in already_sent_earthquakes:
            if ' of ' in cutremur['properties']['place'] and cutremur['properties']['place'].count(' of ') == 1:
                timestamp = int(cutremur['properties']['time'])
                city_country = cutremur['properties']['place'].split(' of ')[1]
                dictionar['Data'] = { 
                                        'Type' : 'earthquake', 
                                        'Magnitude': cutremur['properties']['mag'],
                                        'Location': { 
                                                        'City' : city_country.split(', ')[0],
                                                        'Country' : city_country.split(', ')[1]
                                                    },
                                        'Time': time.strftime("%Y-%m-%dT%H:%M:%S", time.gmtime(timestamp / 1000.0)),
                                        'URL' : cutremur['properties']['url'],
                                        'Title' : cutremur['properties']['title']
                                    }
                json_to_send = json.dumps(dictionar)
                log.write(json_to_send + '\n\n')
                handle = urlopen(url,data = json_to_send)
                handle.close()
 
                already_sent_earthquakes[cutremur['id']] = dictionar['Data']['Time']

def other_hazzards(past, url, type, log):
    global already_sent_hazzards

    dictionar = {'Type':'Hazzard'}
    url_sigimera = 'https://api.sigimera.org/v1/crises?auth_token=eHRiEPDd3HesnU9diPv4&type='
    url_sigimera = url_sigimera + type

    response = requests.get(url_sigimera)
    json_response = response.json()

    for dezastru in json_response:
        starttime = dezastru['schema_startDate'][:-1]
        if len(dezastru['gn_parentCountry']) == 0:
            continue
        if starttime < past:
            continue
        if dezastru['_id'] in already_sent_hazzards:
            continue
        dictionar['Data'] = {
                                'Type' : type,
                                'Alert-level' : dezastru['crisis_alertLevel'],
                                'Country' : dezastru['gn_parentCountry'][0].title(),
                                'Time' : starttime,
                                'URL' : dezastru['rdfs_seeAlso'],
                                'Title' : dezastru['dc_title'],
                                'Description' : dezastru['dc_description']
                            }
        json_to_send = json.dumps(dictionar)
        log.write(json_to_send + '\n\n')
        handle = urlopen(url,data = json_to_send)
        handle.close()

        already_sent_hazzards[dezastru['_id']] = starttime

def main():
    global already_sent_earthquakes
    global already_sent_hazzards

    already_sent_earthquakes = {}
    already_sent_hazzards = {}
    log = open ('log_hazzard.txt', 'w')
    url='http://fenrir.info.uaic.ro:8991'

    now_minus_5_hours = datetime.datetime.now() - datetime.timedelta(hours=5)
    past = now_minus_5_hours.strftime("%Y-%m-%dT%H:%M:%S")

    earthquakes(past, url, log)
    erase_expired_events(past, 2)
    other_hazzards(past, url, 'floods', log)
    time.sleep(3)
    other_hazzards(past, url, 'cyclones', log)

    while 1:
        print 'Sleeping...'
        time.sleep(3600)

        print 'Checking for new events...'

        now_minus_5_hours = datetime.datetime.now() - datetime.timedelta(hours=5)
        past = now_minus_5_hours.strftime("%Y-%m-%dT%H:%M:%S")

        earthquakes(past, url, log)
        erase_expired_events(past, 2)
        other_hazzards(past, url, 'floods', log)
        time.sleep(3)
        other_hazzards(past, url, 'cyclones', log)

main()
