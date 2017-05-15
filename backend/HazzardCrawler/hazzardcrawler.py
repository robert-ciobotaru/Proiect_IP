from urllib import urlopen
import requests
import time
import json
import datetime
import os

global already_sent_earthquakes
global already_sent_hazzards

def erase_expired_events(past, opt, log):
    global already_sent_earthquakes
    global already_sent_hazzards

    try:
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
    except:
        log.write('Eroare la erase_expired_events!\n\n')
        return

def earthquakes(past, url, log):
    global already_sent_earthquakes

    dictionar = {'Type':'Hazzard'}
    url_earthquakes = 'https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson'
    url_earthquakes = url_earthquakes + '&starttime=' + past

    erase_expired_events(past, 1, log)

    # log.write('\n\nBetween ' + past + ' and ' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + ' :\n\n')
    try:
        response = requests.get(url_earthquakes)
        json_response = response.json()
    except:
        log.write('Eroare la primire json!\n\n')
        return

    if 'features' not in json_response:
        log.write('Jsonul nu are "features"!\n\n')
        return

    for cutremur in json_response['features']:
        if 'id' not in cutremur:
            log.write('Jsonul nu are "id"!\n\n')
            continue
        if cutremur['id'] not in already_sent_earthquakes:
            # print cutremur
            try:
                timestamp = int(cutremur['properties']['time'])
                dictionar['Data'] = { 
                                        'type' : 'earthquake', 
                                        'magnitude': format(cutremur['properties']['mag'], '.2f'),
                                        'place': cutremur['properties']['place'],
                                        'time': time.strftime("%Y-%m-%dT%H:%M:%S", time.gmtime(timestamp / 1000.0)),
                                        'url' : cutremur['properties']['url'],
                                        'title' : cutremur['properties']['title']
                                    }
                json_to_send = json.dumps(dictionar)
            except:
                log.write('Json-ul primit este incorect!\n\n')
                continue

            try:
                handle = urlopen(url,data = json_to_send)
                handle.close()
            except:
                log.write('Eroare la trimitere json!\n\n')
                continue

            log.write('\nSent at ' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + ' :\n')
            log.write(json_to_send + '\n\n')

            already_sent_earthquakes[cutremur['id']] = dictionar['Data']['time']

def other_hazzards(past, url, type, log):
    global already_sent_hazzards

    dictionar = {'Type':'Hazzard'}
    url_sigimera = 'https://api.sigimera.org/v1/crises?auth_token=eHRiEPDd3HesnU9diPv4&type='
    url_sigimera = url_sigimera + type

    try:
        response = requests.get(url_sigimera)
        json_response = response.json()
    except:
        log.write('Eroare la primire json!\n\n')
        return

    for dezastru in json_response:
        try:
            starttime = dezastru['schema_startDate'][:-1]
            if len(dezastru['gn_parentCountry']) == 0:
                continue
            if starttime < past:
                continue
            if dezastru['_id'] in already_sent_hazzards:
                continue
            dictionar['Data'] = {
                                    'type' : type,
                                    'alert-level' : dezastru['crisis_alertLevel'],
                                    'country' : dezastru['gn_parentCountry'][0].title(),
                                    'time' : starttime,
                                    'url' : dezastru['rdfs_seeAlso'],
                                    'title' : dezastru['dc_title'],
                                    'description' : dezastru['dc_description']
                                }
            json_to_send = json.dumps(dictionar)
        except:
            log.write('Json-ul primit este incorect!\n\n')
            continue

        try:
            handle = urlopen(url,data = json_to_send)
            handle.close()
        except:
            log.write('Eroare la trimitere json!\n\n')
            continue

        log.write('\nSent at ' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + ' :\n')
        log.write(json_to_send + '\n\n')
    
        already_sent_hazzards[dezastru['_id']] = starttime
   

def main():
    global already_sent_earthquakes
    global already_sent_hazzards

    already_sent_earthquakes = {}
    already_sent_hazzards = {}

    if os.path.isfile('log_hazzard.txt'):
        os.remove('log_hazzard.txt')
    log = open ('log_hazzard.txt', 'w')
    # url='http://fenrir.info.uaic.ro:8991'
    url = "http://104.199.93.85:8991"

    now_minus_5_hours = datetime.datetime.now() - datetime.timedelta(hours=5)
    past = now_minus_5_hours.strftime("%Y-%m-%dT%H:%M:%S")

    earthquakes(past, url, log)
    erase_expired_events(past, 2, log)
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
        erase_expired_events(past, 2, log)
        other_hazzards(past, url, 'floods', log)
        time.sleep(3)
        other_hazzards(past, url, 'cyclones', log)

        statinfo = os.stat('log_hazzard.txt')
        if statinfo.st_size > 10485760:
            os.remove('log_hazzard.txt')
            log = open('log_hazzard.txt','w')

main()
