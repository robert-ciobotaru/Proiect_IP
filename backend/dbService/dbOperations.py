from urllib import urlopen
import json
import threading
import thread
import mysql.connector
import time

dataCrawler=[]

def send():
	global dataCrawler
	db=mysql.connector.connect(user='root',password='STUDENT',host='127.0.0.1',database='proiectip_a2')
	cursor=db.cursor()
	verificare=json.dumps({'check':'Notificare'})
	url='http://students.info.uaic.ro:8769'
	handle=urlopen(url,verificare)
	raspuns=json.loads(handle.read())
	#print "am ajuns aici"
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
			handle=urlopen(url,date)
			#print "am trimis handle"
			cursor.close()
			db.close()
		except:
			db.rollback()
			date=json.dumps({'notificationId':interm,'error':"Eroare la addNotification"}) 
			handle=urlopen(url,date)
	elif raspuns['method']=='getUserNotifications':
		cursor.execute("Select * from notificari where user_id = %s",(raspuns['userId'], ))
		linie=cursor.fetchone()
		if linie is None:
			#print "nothing fetched"
			date=json.dumps({'error':'No notifications fetched'})
			handle=urlopen(url,date)
			cursor.close()
			db.close()
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
			handle=urlopen(url,datee)
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
			handle=urlopen(url,date)
			db.commit()
			#print "am trimis handle in addUser"
			cursor.close()
			db.close()
		except:
			#print "what happened"
			db.rollback()
			date=json.dumps({'userId':interm,'error':'Eroare la addUser'})
			handle=urlopen(url,date)
	elif raspuns['method']=='removeNotification':
		try:
			#print "am intrat in removeNotifications"
			cursor.execute("DELETE FROM notificari WHERE id=%s",(raspuns['notificationId'], ))
			db.commit()
			date=json.dumps({'error':""})
			handle=urlopen(url,date)
			cursor.close()
			db.close()
		except:
			#print "ups la remove"
			db.rollback()
			date=json.dumps({'error':"Eroare la removeNotification"})
			handle=urlopen(url,date)
	elif raspuns['method']=='removeUser':
		try:
			#print "am intrat in removeUser"
			cursor.execute("DELETE FROM notificari where user_id=%s",(raspuns['userId'], ))
			cursor.execute("DELETE FROM useri where id=%s",(raspuns['userId'], ))
			db.commit()
			date=json.dumps({'error':""})
			handle=urlopen(url,date)
		except:
			#print "ups la removeUser"
			db.rollback()
			date=json.dumps({'error':"Eroare la removeUser"})
			handle=urlopne(url,date)
	elif raspuns['method']=='getNotifications':
		try:
			cursor.execute("SELECT text FROM notificari where user_id=%s and Repeatable>0 and Time<(select now() from dual)",(raspuns['userId'], ))
			linie=cursor.fetchone()
			#print "lets see"
			current=0
			date={}
			date['userNotifications']=[]
			date['weatherNotificationsList']=[]
			date['earthquakesList']=[]
			date['floodsList']=[]
			date['cyclonesList']=[]
			date['newsNotificationsList']=[]
			date['userNotifications'].append({'id':linie[0],'text':linie[4],'time':linie[3],'repeatable':linie[1],'interval':linie[2]})
			while linie is not None:
				linie=cursor.fetchone()
				if linie is not None:
					date['userNotifications'].append({'id':linie[0],'text':linie[4],'time':linie[3],'repeatable':linie[1],'interval':linie[2]})
			for i in range(0,len(dataCrawler)):
				if dataCrawler[i]['id']==raspuns['id']:
					if dataCrawler[i]['type']=='Weather':
						date['weatherNotificationsList'].append({'location':{'city':dataCrawler[i]['data']['city'],'country':dataCrawler[i]['data']['country']},'text':dataCrawler[i]['data']['text']})
						dateCrawler.pop(i)
					elif dataCrawler[i]['type']=='News':
						data['newsNotificationsList'].append({'author':dataCrawler[i]['data']['author'],'title':dataCrawler[i]['data']['title'],'description':dataCrawler[i]['data']['description'],'url':dataCrawler[i]['data']['url'],'urlToImage':dataCrawler[i]['data']['urlToImage'],'publishedAt':dataCrawler[i]['data']['publishedAt']})
						dateCrawler.pop(i)
					elif dataCrawler[i]['type']=='floods':
						data['floodsList'].append({'alertLevel':dataCrawler[i]['data']['alertLevel'],'location':{'country':dataCrawler[i]['data']['location']['country']},'time':str(dataCrawler[i]['data']['time']),'title':dataCrawler[i]['data']['title'],'description':dataCrawler[i]['data']['description'],'url':dataCrawler[i]['data']['url']})
						dateCrawler.pop(i)
					elif dataCrawler[i]['type']=='cyclones':
						data['cyclonesList'].append({'alertLevel':dataCrawler[i]['data']['alertLevel'],'location':{'country':dataCrawler[i]['data']['location']['country']},'time':str(dataCrawler[i]['data']['time']),'title':dataCrawler[i]['data']['title'],'description':dataCrawler[i]['data']['description'],'url':dataCrawler[i]['data']['url']})
						dateCrawler.pop(i)
					elif dataCrawler[i]['type']=='earthquake':
						data['earthquakesList'].append({'magnitude':dataCrawler[i]['data']['magnitude'],'location':{'country':dataCrawler[i]['data']['location']['country'],'city':dataCrawler[i]['data']['location']['city']},'time':str(dataCrawler[i]['data']['time']),'title':dataCrawler[i]['data']['title'],'url':dataCrawler[i]['data']['url']})
						dateCrawler.pop(i)
			date['error']=""
			if linie[1]==1:
				cursor.execute("UPDATE notificari set Time = Time + Interval where repeatable=1 and user_id=%s and Time<(select now() from dual)",(raspuns['userId'], ))
			elif linie[1]==0:
				cursor.execute("DELETE FROM notificari where user_id=%s and repeatable=0 and Time<(select now() from dual)",(raspuns['userId'], ))
			datee=json.dumps(date)
			handle=urlopen(ulr,datee)
			cursor.close()
			db.close()
		except:
			date=json.dumps({'error':'Eroare la fetching notificare expirata'})
			handle=urlopen(url,date)
		
	send()

def sendCrawler():
	db=mysql.connector.connect(user='root',password='STUDENT',host='127.0.0.1',database='proiectip_a2')
	cursor=db.cursor()
	verificare=json.dumps({'check':'Notificare'})
	url='http://students.info.uaic.ro:8769'
	handle=urlopen(url,verificare)
	raspuns=json.loads(handle.read())
	if raspuns['Type']=="":
		time.wait(1800)
	elif raspuns['Type']=="Hazzard":
		if raspuns['Data']['location']['country']!="":
			if raspuns['Data']['location']['country']!="":
				cursor.execute("SELECT id from useri where hazzardCrawler=1 and Country=%s and City=%s",(raspuns['Data']['location']['country'],raspuns['Data']['location']['city']))
			else:
				cursor.execute("SELECT id from useri where hazzardCrawler=1 and Country=%s",(raspuns['Data']['location']['country'], ))
		elif raspuns['Data']['location']['city']!="":
			cursor.execute("SELECT id from useri where hazzardCrawler=1 and City=%s",(raspuns['Data']['location']['city'], ))
		else:
			cursor.execute("SELECT id from useri where hazzardCrawler=1")
	elif raspuns['Type']=="Weather":
		if raspuns['Data']['location']['country']!="":
			if raspuns['Data']['location']['country']!="":
				cursor.execute("SELECT id from useri where weatherCrawler=1 and Country=%s and City=%s",(raspuns['Data']['location']['country'],raspuns['Data']['location']['city']))
			else:
				cursor.execute("SELECT id from useri where weatherCrawler=1 and Country=%s",(raspuns['Data']['location']['country'], ))
		elif raspuns['Data']['location']['city']!="":
			cursor.execute("SELECT id from useri where weatherCrawler=1 and City=%s",(raspuns['Data']['location']['city'], ))
		else:
			cursor.execute("SELECT id from useri where weatherCrawler=1")
	elif raspuns['Type']=="News":
		if raspuns['Data']['location']['country']!="":
			if raspuns['Data']['location']['country']!="":
				cursor.execute("SELECT id from useri where newsCrawler=1 and Country=%s and City=%s",(raspuns['Data']['location']['country'],raspuns['Data']['location']['city']))
			else:
				cursor.execute("SELECT id from useri where newsCrawler=1 and Country=%s",(raspuns['Data']['location']['country'], ))
		elif raspuns['Data']['location']['city']!="":
			cursor.execute("SELECT id from useri where newsCrawler=1 and City=%s",(raspuns['Data']['location']['city'], ))
		else:
			cursor.execute("SELECT id from useri where newsCrawler=1")
	linie=cursor.fetchone()
	dataCrawler.append({'id':linie[0],'data':raspuns['Data'],'type':raspuns['Type']})
	while linie is not None:
		linie=cursor.fetchone()
		if linie is not None:
			if raspuns['Type']=="Hazzard":  
				dataCrawler.append({'id':linie[0],'data':raspuns['Data'],'type':raspuns['Type']['type']})
			else:
				dataCrawler.append({'id':linie[0],'data':raspuns['Data'],'type':raspuns['Type']}) 
		cursor.close()
		db.close()
	
		

def main():
	sendCrawler()
	send()

if __name__=="__main__":
	main()
