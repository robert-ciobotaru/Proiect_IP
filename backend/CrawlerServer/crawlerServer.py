import socket
import json
import os
import thread
import time
import datetime
from urllib import urlopen

global jsons_received
global output

def sendResponse(client,value):
    response = json.dumps(value)
    client.sendall(response)
    client.close()
    
def validate_json(message):
    if 'Type' not in message:
        return -1
    if 'Data' not in message:
        return -1
    if message['Type'] == 'Hazzard':
        if 'type' not in message['Data']:
            return -1
        if 'time' not in message['Data']:
            return -1
        if 'url' not in message['Data']:
            return -1
        if 'title' not in message['Data']:
            return -1
        if message['Data']['type'] == 'earthquake':
            if 'magnitude' not in message['Data']:
                return -1
            if 'place' not in message['Data']:
                return -1
        elif message['Data']['type'] == 'floods' or message['Data']['type'] == 'cyclones':
            if 'alert-level' not in message['Data']:
                return -1
            if 'country' not in message['Data']:
                return -1
            if 'description' not in message['Data']:
                return -1

    elif message['Type'] == 'News':
        if 'author' not in message['Data']:
            return -1
        if 'title' not in message['Data']:
            return -1
        if 'description' not in message['Data']:
            return -1
        if 'url' not in message['Data']:
            return -1
        if 'urlToImage' not in message['Data']:
            return -1
        if 'publishedAt' not in message['Data']:
            return -1

    elif message['Type'] == 'Weather':
        if 'location' not in message['Data']:
            return -1
        if 'city' not in message['Data']['location']:
            return -1
        if 'country' not in message['Data']['location']:
            return -1
        if 'text' not in message['Data']:
            return -1

    return 0


def treatClient(client):
    global jsons_received
    try:
        inputs = client.recv(2048)
        # print inputs

        message = inputs.split('\n')
        message = json.loads(message[len(message)-1])
        output.write('\nReceived at ' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + ' :\n')
        output.write(json.dumps(message) + '\n')

        if 'check' in message:
            if len(jsons_received) > 0:
                sendResponse(client, jsons_received.pop(0))
                # print 'check - trimis'
                output.write('check - trimis!\n')
            else:
                sendResponse(client, {'Type':'None'})
                # print "check - i don't have any jsons"
                output.write("check - i don't have any jsons!\n")

        else:
            if validate_json(message) == 0:
                jsons_received.append(message)
                # print 'Valid - ', message['Type']
                output.write('Valid!\n')
            else:
                # print 'Invalid'
                output.write('Invalid!\n')
            sendResponse(client,{'Request':'GET','Data':'Notification','Value':'Received'})
    except:
        # print 'Eroare'
        output.write('Eroare!\n')
        try:
            sendResponse(client,{'Type':'None'})
        except:
            # print 'Client inchis'
            output.write('Client inchis!\n')


def main():
    global jsons_received
    global output
    HOST, PORT = '', 8991
    jsons_received = []
    if os.path.isfile('log.txt'):
        os.remove('log.txt')
    output = open('log.txt','w')

    listen_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    listen_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    listen_socket.bind((HOST, PORT))
    listen_socket.listen(200)
    print 'Waiting on port %s ...' % PORT
    while True:
        statinfo = os.stat('log.txt')
        if statinfo.st_size > 10485760:
            os.remove('log.txt')
            output = open('log.txt','w')
        client_connection, client_address = listen_socket.accept()
        thread.start_new_thread(treatClient,(client_connection,))

if __name__ == "__main__":
    main()
