import socket
import json
import os
import thread
import sys
import jsonschema
import simplejson
from time import gmtime, strftime
from jsonschema import validate

output = os.open('log.txt',os.O_RDWR|os.O_CREAT)

schema = {
    "$schema": "http://json-schema.org/draft-04/schema#",
    "properties": {
        "Data": {
            "properties": {
                "Location": {
                    "properties": {
                        "City": {
                            "type": "string"
                        },
                        "Country": {
                            "type": "string"
                        }
                    },
                    "type": "object"
                },
                "Text": {
                    "type": "string"
                }
            },
            "type": "object"
        },
        "Type": {
            "type": "string"
        }
    },
    "type": "object",
    "required": ["City","Country","Text","Type","Location","Data"]
}

# def sendResponse(client,value):
#     response = json.dumps(value)
#     os.write(output,'\nSent answer back : \n\t'+response)
#     client.sendall(response)
#     client.close()

def validate_json(data):
    try:
        jsonschema.validate(simplejson.loads(data), (schema))
        print "JSON is valid"
		urlSend = 'http://fenrir.info.uaic.ro:8765'
        sendSource = urlopen(urlSend, data=data)
        sendSource.close()
    except jsonschema.ValidationError as e:
        print e.message
    except jsonschema.SchemaError as e:
        print e

def treatClient(client):
    print 'Waiting message from client'
    inputs=client.recv(1024)
    inputsArray = inputs.split('\n')
    # print inputsArray[len(inputsArray)-1]
    # print inputsArray
    validate_json(inputsArray[len(inputsArray)-1])

def main():
    HOST, PORT = '', 8991

    listen_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    listen_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    listen_socket.bind((HOST, PORT))
    listen_socket.listen(50)
    print 'Waiting on port %s ...' % PORT
    while True:
        client_connection, client_address = listen_socket.accept()
        thread.start_new_thread(treatClient,(client_connection,))

if __name__ == "__main__":
    main()
