import socket
import json
import os
import sys
import thread
from time import gmtime, strftime

output = os.open('log.txt', os.O_RDWR | os.O_CREAT)
server = 'http://localhost:8769'

received = {}

methodToSend = {}
methodLen = 0

reponseToSend = {}
reponseLen = 0

methodClient = {}
methodClientLen = 0

crawlerClient = 0


def sendResponse(client, value, toMiddle = False): 
    print 'sendResponse()'

    print '0'
    response =  json.dumps(value)

    if toMiddle:
        print 'toMIddle = 2'
	message = json.dumps(value)
	print 'toMiddle = True'
        contentSize = len(message)
        contentLength = "Content-Length: " + str(contentSize) + "\n";
        response = "HTTP/1.1 200 OK\n" + "Content-Type: application/json\n" + contentLength + "\n" + message

    os.write(output, '\nSent answer back : \n\t' + response)
    client.sendall(response)
    client.close()


def treatClient(client):
    global methodToSend
    global methodLen
    global responseToSend
    global responeLen
    global methodClient
    global methodClientLen
    
    print 'Waiting message from client'
    inputs = client.recv(5000)
    #sendResponse(client,{'method':'removeUser','userId':0})
    #sendResponse(client,{'method':'addUser','data':{'country':'Romania','city':'iasi','newsCrawler':1,'hazzardCrawler':1,'weatherCrawler':0,'email':'sugigcucu@hah.ro'}})
    #sendResponse(client,{'method':'getUserNotifications','userId':23})
    #sendResponse(client,{'method':'addNotification','userId':0,'data':{'repeatable':1,'interval':12,'time':'2015-08-05 18:12:12','text':'Wake me up before you go go'}})
    #sendResponse(client,{'method':'removeNotification','notificationId':2})
    #sendResponse(client,{'method':'getNotifications','userId':23})
    #inputs = client.recv(5000)
    print inputs
    
    try:

        message = inputs.split('\n')
        message = json.loads(message[len(message) - 1])
        os.write(output, '\n' + strftime("%Y-%m-%d %H:%M:%S", gmtime()))
        os.write(output, '\nReceived ' + json.dumps(message))
        if 'check' in message:
	    print message['check']
            if message['check'] == 'Method':
                while methodLen == 0:
                    continue

                sendResponse(crawlerClient, methodToSend[methodLen - 1])
                methodLen -= 1
                methodToSend[methodLen] = client.recv(1024)

                sendResponse(methodClient[methodClientLen], methodToSend[methodLen])

        if 'method' in message:
            print '[INFO] middle end clent detected'
            methodClient[methodClientLen] = client
            methodClientLen += 1

            methodToSend[methodLen] = message
            methodLen += 1
            sendResponse(client, message, True)

    except:
	e = sys.exc_info()[0]
	print str(e)
        print 'Failed to treat client'
    try:
        sendResponse(client, {'Request': 'POST', 'Value': 'Failed to receive message'})
    except:
        print 'Failed to establish connection to client'


def main():
    HOST, PORT = '', 8769

    listen_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    listen_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    listen_socket.bind((HOST, PORT))
    listen_socket.listen(0xFFFFFF)
    print 'Waiting on port %s ...' % PORT
    while True:
        client_connection, client_address = listen_socket.accept()
        thread.start_new_thread(treatClient, (client_connection,))


if __name__ == "__main__":
    main()

