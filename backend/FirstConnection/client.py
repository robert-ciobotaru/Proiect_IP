from urllib import urlopen
import json

myData=json.dumps({'id':'dleon','Request': 'POST', 'Data':'Notification'})
url='http://localhost:8765'
myProxies = {'http': 'http://localhost:8765'}
handle=urlopen(url,data=myData,proxies=myProxies)
print 'Received answer : \n\t' +  json.dumps(handle.read())
