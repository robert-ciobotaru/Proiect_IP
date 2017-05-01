import socket
import json
import os
import thread
from time import gmtime, strftime

output = os.open('log.txt',os.O_RDWR|os.O_CREAT)

def sendResponse(client,value):
    response = json.dumps(value)
    os.write(output,'\nSent answer back : \n\t'+response)
    client.sendall(response)
    client.close()
    
def treatClient(client):
    print 'Waiting message from client'
    inputs=client.recv(1024)
    print inputs
    try:
	message=inputs.split('\n')
        message=json.loads(message[len(message)-1])
	os.write(output,'\n' + strftime("%Y-%m-%d %H:%M:%S",gmtime()))
	os.write(output,'\nReceived from '+ message['id']+ '\n\t'+json.dumps(message))
	if message['Request']=='GET': 
	    if message['Data']=='Notification':
		sendResponse(client,{'Request':'POST','Data':'Notification','Value':'Grab my kids from school','Time':'14:45','Repeatable':'True'})
	    else:
		sendResponse(client,{'Request':'POST','Data':'Crawler','Value':'Bad weather, good sports and shaky earthquakes'})
	else:
	    if message['Data']=='Crawler':
		sendResponse(client,{'Request':'GET','Data':'Crawler','Value':'Received'})
	    else:
		sendResponse(client,{'Request':'GET','Data':'Notification','Value':'Received'})
    except:
	print 'Failed to treat client'
	try:
	    sendResponse(client,{'Request':'POST','Value':'Failed to receive message'})
	except:
	    print 'Failed to establish connection to client'
def main():
    HOST, PORT = '', 8765

    listen_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    listen_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    listen_socket.bind((HOST, PORT))
    listen_socket.listen(0xFFFFFFFF)
    print 'Waiting on port %s ...' % PORT
    while True:
	client_connection, client_address = listen_socket.accept()
	thread.start_new_thread(treatClient,(client_connection,))

if __name__ == "__main__":
    main()
