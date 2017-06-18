#coding=utf-8
import random
import threading
from time import ctime,sleep
import json
import urllib2
import time
import os

def random_mac():
    macList = []
    for i in range(1, 7):
        randStr = "".join(random.sample("0123456789abcdef",2))
        macList.append(randStr)
    randMac = ":".join(macList)
    return randMac


def random_rssi():
    return str(random.randrange(-100, 0))


def random_range():
    return str(round(random.uniform(0, 100), 1))



def random_id():
    return str(random.randrange(1, 1000))


probeList = []


# def randomProbe(times):
#
#     for i in range(1000):
#         probe = {"id": random_id(), "mmac": random_mac(), "rate": 3, "wssid": "test", "wmac": random_mac(), "time": }
#         probes = json.dumps(probe)
#         probeList.append(probes)


def random_json(item):

    headers = {'Content-Type': 'application/json'}
    probe = {"id": ''+random_id(), "mmac": random_mac(), "rate": "3", "wssid": "test", "wmac": random_mac(), "time": time.strftime('%a %b %e %H:%M:%S %Y', time.localtime(time.time()))}
    mac_data ={"mac": random_mac(), "rssi": random_rssi(), "range": random_range()}
    mac_DataMul = []
    #data_json = json.dumps(mac_data)
    for i in range(random.randrange(1, 5)):
        mac_DataMul.append(mac_data)
    probe['data'] = mac_DataMul

    probe = json.dumps(probe)
    
    fileName = '/Users/mac/Workspace/Java/WIFIProbeAnalysis_Backstage/wifiProbe/source/A00'+str(item)
    file = open(fileName, 'w')
    file.write(probe)
    file.close()
    os.system('hdfs dfs -put '+fileName+' /source')
    sleep(1)

if __name__ == '__main__':
    threads = []
    probeList = []
    index=0
    for i in range(10):
        random_json(i)

    print "all over %s" %ctime()
