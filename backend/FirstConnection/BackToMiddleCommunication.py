from urllib import urlopen
import json
import threading
import thread
#import mysql.connector
from threading import Thread
import time
import BaseHTTPServer
import MySQLdb

HOST_NAME='10.128.0.2'
PORT_NUMBER=8769
dataCrawler=[]

def send(raspuns):
    global dataCrawler
    db=MySQLdb.connect(host="130.211.102.0",user="root",passwd="STUDENT",db="proiectip_a2")  
    #mysql.connector.connect(user='root',password='STUDENT',host='127.0.0.1',database='proiectip_a2')
    #host='130.211.102.0'
    cursor=db.cursor()
    cursor2=db.cursor()
    #print "am ajuns aici"
    print raspuns
    if raspuns['method']=='addNotification':
        try:
            #print "am intrat in addNotification"
            cursor.execute("SELECT MIN(t1.id + 1) FROM notificari t1 LEFT JOIN notificari t2 ON t1.id + 1 = t2.id WHERE t2.ID IS NULL")
            linie=cursor.fetchone()
            if linie[0] is None: 
                interm=1
            else:
                interm=linie[0]
            cursor.execute("INSERT INTO notificari VALUES (%s,%s,%s,%s,%s,%s)",(interm,raspuns['notification']['repeatable'],raspuns['notification']['interval'],raspuns['notification']['time'],raspuns['notification']['text'],raspuns['userId']))
            db.commit()
            date=json.dumps({'notificationId':interm,'error':""})
            #print "am trimis handle"
            cursor.close()
            db.close()
            return date
        except:
            db.rollback()
            date=json.dumps({'notificationId':interm,'error':"Eroare la addNotification"}) 
            return date
    elif raspuns['method']=='getUserNotifications':
        print "aiciii"
        cursor.execute("Select * from notificari where user_id = %s",(raspuns['userId'], ))
        linie=cursor.fetchone()
        if linie is None:
            #print "nothing fetched"
            date=json.dumps({'error':'No notifications fetched'})
            cursor.close()
            db.close()
            return date
        else:
            #print "lets see"
            current=0
            date={}
            date['error']=""
            date['notificationsList']=[]
            date['notificationsList'].append({'id':linie[0],'text':linie[4],'time':str(linie[3]),'repeatable':linie[1],'interval':linie[2]})
            while linie is not None:
                linie=cursor.fetchone()
                if linie is not None:
                    date['notificationsList'].append({'id':linie[0],'text':linie[4],'time':str(linie[3]),'repeatable':linie[1],'interval':linie[2]})
            datee=json.dumps(date)
            return datee
    elif raspuns['method']=='addUser':
        try:
            #print "am ajuns in addUser"
            cursor.execute("SELECT MIN(t1.id + 1) FROM useri t1 LEFT JOIN useri t2 ON t1.id + 1 = t2.id WHERE t2.ID IS NULL")
            linie=cursor.fetchone()
            #print linie
            if linie[0] is None:
                interm=1
            else:
                interm=linie[0]
            cursor.execute("INSERT INTO useri VALUES (%s,%s,%s,%s,%s,%s,%s)",(interm,raspuns['data']['country'],raspuns['data']['city'],raspuns['data']['newsCrawler'],raspuns['data']['hazzardCrawler'],raspuns['data']['weatherCrawler'],raspuns['data']['email']))
            date=json.dumps({'userId':interm,'error':""})
            db.commit()
            #print "am trimis handle in addUser"
            cursor.close()
            db.close()
            return date
        except:
            #print "what happened"
            db.rollback()
            date=json.dumps({'userId':interm,'error':'Eroare la addUser'})
            return date
    elif raspuns['method']=='removeNotification':
        try:
            #print "am intrat in removeNotifications"
            cursor.execute("DELETE FROM notificari WHERE id=%s",(raspuns['notificationId'], ))
            db.commit()
            date=json.dumps({'error':""})
            cursor.close()
            db.close()
            return date
        except:
            #print "ups la remove"
            db.rollback()
            date=json.dumps({'error':"Eroare la removeNotification"})
            return date
    elif raspuns['method']=='removeUser':
        try:
            #print "am intrat in removeUser"
            cursor.execute("DELETE FROM notificari where user_id=%s",(raspuns['userId'], ))
            cursor.execute("DELETE FROM useri where id=%s",(raspuns['userId'], ))
            db.commit()
            date=json.dumps({'error':""})
            return date
        except:
            #print "ups la removeUser"
            db.rollback()
            date=json.dumps({'error':"Eroare la removeUser"})
            return date
    elif raspuns['method']=='getNotifications':
        print "la radu"
        try:
            cursor.execute("SELECT * FROM notificari where user_id=%s and Time<(select now() from dual)",(raspuns['userId'], ))
            linie=cursor.fetchone()
            print "!!!LETS SEEEEE!!!"
            current=0
            date={}
            date['userNotifications']=[]
            date['weatherNotificationsList']=[]
            date['earthquakesList']=[]
            date['floodsList']=[]
            date['cyclonesList']=[]
            date['newsNotificationsList']=[]
            while linie is not None:
                linie=cursor.fetchone()
                if linie is not None:
                    date['userNotifications'].append({'id':linie[0],'text':linie[4],'time':str(linie[3]),'repeatable':linie[1],'interval':linie[2]})
            print len(dataCrawler)                
            for i in range(0,len(dataCrawler)):
                print i
                print dataCrawler[i]
                if dataCrawler[i]['id']==raspuns['userId']:
                    print "Helllo22"
                    print dataCrawler[i]
                    print dataCrawler[i]['type']
                    if dataCrawler[i]['type']=='Weather':
                        date['weatherNotificationsList'].append({'location':{'city':dataCrawler[i]['data']['location']['city'],'country':dataCrawler[i]['data']['location']['country']},'text':dataCrawler[i]['data']['text']})
                        dataCrawler[i]['type']=''
                    elif dataCrawler[i]['type']=='News':
                        print 'HELLO'
                        date['newsNotificationsList'].append({'author':dataCrawler[i]['data']['author'],'title':dataCrawler[i]['data']['title'],'description':dataCrawler[i]['data']['description'],'url':dataCrawler[i]['data']['url'],'urlToImage':dataCrawler[i]['data']['urlToImage'],'publishedAt':dataCrawler[i]['data']['publishedAt']})
                        print 'HELLO'
                        dataCrawler[i]['type']=''
                        print 'HELLO'
                    elif dataCrawler[i]['type']=='floods':
                        date['floodsList'].append({'alertLevel':dataCrawler[i]['data']['alertLevel'],'country':dataCrawler[i]['data']['country'],'time':str(dataCrawler[i]['data']['time']),'title':dataCrawler[i]['data']['title'],'description':dataCrawler[i]['data']['description'],'url':dataCrawler[i]['data']['url']})
                        dataCrawler[i]['type']=''
                    elif dataCrawler[i]['type']=='cyclones':
                        date['cyclonesList'].append({'alertLevel':dataCrawler[i]['data']['alertLevel'],'country':dataCrawler[i]['data']['country'],'time':str(dataCrawler[i]['data']['time']),'title':dataCrawler[i]['data']['title'],'description':dataCrawler[i]['data']['description'],'url':dataCrawler[i]['data']['url']})
                        dataCrawler[i]['type']=''
                    elif dataCrawler[i]['type']=='earthquake':
                        cursor2.execute("SELECT Country,City from useri where hazzardCrawler=1 and id=%s",(raspuns['userId'], ))
                        tara=cursor2.fetchone()
                        if tara[0] in dataCrawler[i]['data']['place'] or tara[1] in dataCrawler[i]['data']['place']: 
                            date['earthquakesList'].append({'magnitude':dataCrawler[i]['data']['magnitude'],'place':dataCrawler[i]['data']['place'],'time':str(dataCrawler[i]['data']['time']),'title':dataCrawler[i]['data']['title'],'url':dataCrawler[i]['data']['url']})
                        dataCrawler[i]['type']=''
            date['error']=""
            print "am iesit din for"
            n=len(dataCrawler)
            i=0
            while i<n:
                if dataCrawler[i]['type']=='':
                    dataCrawler.pop(i)
                    n=n-1
                else:
                    i=i+1
            print "am iesit din al dilea for"
            '''try:
                cursor.execute("UPDATE notificari set Time=timestampadd(second,Interva,Time)  where Repeatable=1 and user_id=%s and Time<(select now() from dual)",(raspuns['userId'], ))
                cursor.execute("DELETE FROM notificari where user_id=%s and Repeatable=0 and Time<(select now() from dual)",(raspuns['userId'], ))
            except:
                date=json.dumps({'error':'SALUT RADU!!'})
                handle=urlopen(url,date)'''
            datee=json.dumps(date)
            cursor2.close()
            cursor.close()
            db.close()
            return datee
        except:
            date=json.dumps({'error':'Helau Radule :>!'})
            return date
def sendCrawler():
    #db=mysql.connector.connect(user='root',password='STUDENT',host='127.0.0.1',database='proiectip_a2')
    db=MySQLdb.connect(host="130.211.102.0",user="root",passwd="STUDENT",db="proiectip_a2")
    cursor=db.cursor()
    global dataCrawler
    verificare=json.dumps({'check':'Notificare'})
    print "connected"
    url='http://104.198.38.180:8991'
    #url='http://localhost:8991'
    handle=urlopen(url,verificare)
    raspunss=handle.read()
    #print raspunss
    raspuns=json.loads(raspunss)
    #print raspuns
    if raspuns['Type']=='None':
        #print "none"
        time.sleep(1800)
    elif raspuns['Type']=='Hazzard' and raspuns['Data']['type']=='earthquake':
        print "here"
        cursor.execute("SELECT id from useri where hazardCrawler=1")
    elif raspuns['Type']=="Hazzard" and raspuns['Data']['type']!='earthquake':
        if raspuns['Data']['country']!="":
            cursor.execute("SELECT id from useri where hazardCrawler=1 and Country=%s",(raspuns['Data']['country']))
        else:
            cursor.execute("SELECT id from useri where hazardCrawler=1")
    elif raspuns['Type']=="Weather":
        print raspuns
        if raspuns['Data']['location']['country']!="":
            if raspuns['Data']['location']['city']!="":
                cursor.execute("SELECT id from useri where weatherCrawler=1 and Country=%s and City=%s",(raspuns['Data']['location']['country'],raspuns['Data']['location']['city']))
            else:
                cursor.execute("SELECT id from useri where weatherCrawler=1 and Country=%s",(raspuns['Data']['location']['country'], ))
        elif raspuns['Data']['location']['city']!="":
            cursor.execute("SELECT id from useri where weatherCrawler=1 and City=%s",(raspuns['Data']['location']['city'], ))
        else:
            cursor.execute("SELECT id from useri where weatherCrawler=1")
    elif raspuns['Type']=="News":
        cursor.execute("SELECT id from useri where newsCrawler=1")
    linie=cursor.fetchone()
    #print linie
    if linie is None:
        print "no such user"
    else:
        if raspuns['Type']=="Hazzard":
            #print linie[0]
            dataCrawler.append({'id':linie[0],'data':raspuns['Data'],'type':raspuns['Data']['type']})
        else:
            dataCrawler.append({'id':linie[0],'data':raspuns['Data'],'type':raspuns['Type']})
        while linie is not None:
            linie=cursor.fetchone()
            if linie is not None:
                if raspuns['Type']=="Hazzard":  
                    dataCrawler.append({'id':linie[0],'data':raspuns['Data'],'type':raspuns['Data']['type']})
                else:
                    dataCrawler.append({'id':linie[0],'data':raspuns['Data'],'type':raspuns['Type']}) 
   
    cursor.close()
    db.close()
    sendCrawler()


class MyHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_DELETE(self):
        request_headers = self.headers
        content_length = request_headers.getheaders('content-length')
        length = int(content_length[0])

        print request_headers
        request = self.rfile.read(length)
        try:
            request=json.loads(request)
        except:
            request=json.dumps(request)
        raspuns=json.loads(send(request))
        if raspuns['error']=='':
            self.send_response(200)
        else:
            self.send_response(422)
        self.send_header("Content-type", "application/json")
        self.end_headers()
        self.wfile.write(raspuns)

    def do_GET(self):
        request_headers = self.headers
        content_length = request_headers.getheaders('content-length')
        length = int(content_length[0])

        print request_headers
        request = self.rfile.read(length)
        try:
            request=json.loads(request)
        except:
            request=json.dumps(request)
        raspuns=json.loads(send(request))
        if raspuns['error']=='':
            self.send_response(200)
        else:
            self.send_response(422)
        self.send_header("Content-type", "application/json")
        self.end_headers()
        self.wfile.write(raspuns)

    def do_POST(self):
        request_headers = self.headers
        content_length = request_headers.getheaders('content-length')
        length = int(content_length[0])

        print request_headers
        request = self.rfile.read(length)
        try:
            request=json.loads(request)
        except:
            request=json.dumps(request)
        raspuns=json.loads(send(request))
        if raspuns['error']=='':
            self.send_response(200)
        else: 
            self.send_response(422)
        self.send_header("Content-type", "application/json")
        self.end_headers()
        self.wfile.write(json.dumps(raspuns))


if __name__ == '__main__':
    Thread(target = sendCrawler).start()
    server_class = BaseHTTPServer.HTTPServer
    httpd = server_class((HOST_NAME, PORT_NUMBER), MyHandler)
    print time.asctime(), "Server Starts - %s:%s" % (HOST_NAME, PORT_NUMBER)
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass
    httpd.server_close()
    print time.asctime(), "Server Stops - %s:%s" % (HOST_NAME, PORT_NUMBER)
