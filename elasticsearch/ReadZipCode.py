__author__ = 'vijay'
import json
import re
import requests
import openweathermapy.core as owm
import sys
import  googlemaps
class ZipCodeExtractor:

    def weatherreport(self,fwrite,lat,lng):
        try:
            u = "http://api.openweathermap.org/data/2.5/forecast/daily?lat="+str(lat)+"&lon="+str(lng)+"&cnt=10&mode=json&appid=2de143494c0b295cca9337e1e96b00e0"
            r = requests.get(u)
            j = json.loads(r.text)
            for line in j["list"]:
                for weather in line["weather"]:
                   fwrite.write(","+weather["main"])
        except:
            sys.stderr.write("Couldn't load current conditions\n")

    def readJson(self):
        fwrite = open('output/postalcode_la.csv',"w")
        fwrite.write('Name,zipcode,lat,long,Day1,Day2,Day3,Day4,Day5,Day6,Day7,Day8,Day9,Day10'+'\n')
        with open('input/LA.json') as data_file:
            data = json.load(data_file)
        postalcode = list()
        for jsonRecord in data:
            for place in jsonRecord["place"]:
                if 'name' in place :
                    str2= place['name']
                if 'address' in place and 'postalCode' in place['address']:
                    str1 = place['address']['postalCode']
                    str1 = re.sub('[a-zA-Z]', '', str1).strip()
                    if str1 not in postalcode:
                        postalcode.append(str1)
                        gmaps =  googlemaps.Client(key='AIzaSyCofXe7on0svsFXaUZjT1K0Yi9IBLNF0Yw')
                        location = gmaps.geocode(str1.split('-')[0])
                        if len(location) > 0:
                            lat = location[0]["geometry"]["location"]['lat']
                            lng = location[0]["geometry"]["location"]['lng']
                            fwrite.write(str2+","+str1+","+str(location[0]["geometry"]["location"]['lat'])+","+str(location[0]["geometry"]["location"]['lng']))
                            self.weatherreport(fwrite,lat,lng)
                            fwrite.write("\n")
                '''
                if "address" not in place:
                    break
                for address in place["address"]:
                    if "postalCode" not in address:
                        break
                    print(address["postalCode"])
                '''
obj = ZipCodeExtractor()
obj.readJson()




