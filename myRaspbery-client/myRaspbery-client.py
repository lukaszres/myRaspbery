import urllib.request
import json
import datetime

myurl = "http://localhost:8080/addTemperature"


def prepareJson(temperature):
    body = {'temp': temperature, 'date': str(datetime.datetime.now())} #YYYY-MM-DD HH:MM:SS:MS
    jsonData = json.dumps(body)
    jsonDataAsBytes = jsonData.encode('utf-8')   # needs to be bytes
    return jsonDataAsBytes

def prepareRequest(content):
    req = urllib.request.Request(myurl)
    req.add_header('Content-Type', 'application/json; charset=utf-8')
    req.add_header('Content-Length', len(content))
    return req


newJson = prepareJson(25.4)
req = prepareRequest(newJson)
print (newJson)
response = urllib.request.urlopen(req, newJson)