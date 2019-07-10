import urllib.request
import json
import datetime
import time
import w1thermsensor

myurl = "https://my-raspberry.herokuapp.com/addTemperature"


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

def readSensor():
    sensor = w1thermsensor.W1ThermSensor()
    return sensor.get_temperature()

def getAverage(array):
    sum = 0;
    for n in array:
        sum += n
    return sum / len(array)

while True:
    i = 0;
    result = [0]*10
    while i<10:
        result[i] = readSensor()
        time.sleep(1)
        i+=1
    newJson = prepareJson(getAverage(result))
    req = prepareRequest(newJson)
    print (newJson)
    response = urllib.request.urlopen(req, newJson)