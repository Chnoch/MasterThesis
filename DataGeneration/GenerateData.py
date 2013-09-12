__author__ = 'Chnoch'

import json


def generateData():
    f = open("1.json", "w")

    f.write(json.dumps({'record': {'stationId': '008507076', 'stationName':'Belp', 'date':'02.09.2013', 'time':'07:35'}}))
    # f.write(json.dumps({'record': {'stationId': '008507000', 'stationName':'Bern', 'date':'02.09.2013', 'time':'07:50'}}))
    # f.write(json.dumps({'record': {'stationId': '008507000', 'stationName':'Bern', 'date':'02.09.2013', 'time':'17:12'}}))
    # f.write(json.dumps({'record': {'stationId': '008507076', 'stationName':'Belp', 'date':'02.09.2013', 'time':'17:27'}}))

    f.close()

def readData():
    f=open("1.json", "r")
    print f.readline()
    js = json.load(f)
    print js
    f.close()

read = True

if read:
    readData()
else:
    generateData()

