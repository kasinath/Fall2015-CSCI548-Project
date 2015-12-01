__author__ = 'vijay'
import sys
import elasticsearch
import os
import json


class InsertJsonDocuments:



    def __init__(self,folder,index,docType):
        self.inputLocation = folder
        self.database = index
        self.table = docType
        self.count = 0

    def insertFile(self,file):
        with open(file) as data_file:
            data = json.load(data_file)

        es = elasticsearch.Elasticsearch()  # use default of localhost, port 9200
        for jsonRecord in data:
            es.index(index=self.database, doc_type=self.table, id=self.count, body=json.dumps(jsonRecord))
            self.count = self.count+1

    def insertRecords(self):
        #load files
        files = list()
        files = os.listdir(self.inputLocation)
        for file in files:
            if(self.isJsonFile(file)):
                self.insertFile(self.inputLocation+"/"+file)


    def search(self):
        es = elasticsearch.Elasticsearch()  # use default of localhost, port 9200
        res = es.search(index=self.database, q="Landmark" )
        for hits in res['hits']['hits']:
            print(hits["_source"]["place"])
    def isJsonFile(self,file):
        if file.endswith('.json'):
            return True
        else:
            return False

start = InsertJsonDocuments("./es","cities","things")
start.insertRecords()
#start.search()