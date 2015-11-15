from ..items.items import AragogItem
from ..spiders.aragog import Properties
from scrapy.exceptions import DropItem
from scrapy.selector import Selector
import os
import json

class PersistItemsPipeline(object):
	docCount = 0
	uniq = []
	def __init__(self, crawler):
        	self.crawler = crawler
	@classmethod
    	def from_crawler(cls, crawler):
        	return cls(crawler=crawler)


	def process_item(self, item, spider):
		prop = Properties()
		if item["url"].split('?')[0] in PersistItemsPipeline.uniq :
			raise DropItem("[DROP] Page already Persisted. Dropping : " + item["url"])
		PersistItemsPipeline.uniq.append(item["url"].split('?')[0])	
		PersistItemsPipeline.docCount = PersistItemsPipeline.docCount+1
		if PersistItemsPipeline.docCount > prop.pagesToCrawl : 
			self.crawler.engine.close_spider(spider, 'Target Reached, Shutting down ARAGOG')
		if not os.path.exists(prop.opDir):
   			os.makedirs(prop.opDir)
		if prop.opEncoding == "JSON":
			jsonObj = {}
			name = []
			location = []
			aggr = []
			desc = []
			sttime = []
			endtime = []
			fileName = prop.opDir+ "/" + str(PersistItemsPipeline.docCount) + '.JSON'
			htmlTxt =item["body"]
			#TITLE
			name = Selector(text=htmlTxt).xpath('//div[@id="event_header"]//span[@itemprop="name"]/text()').extract()
			jsonObj["title"] = name[0].strip()
			#LOCATION
			location = Selector(text=htmlTxt).xpath('//div[@id="event_network"]//*/text()').extract()
			jsonObj["location"] = (" ".join(location)).strip()
			#ADDRESS
			addr = Selector(text=htmlTxt).xpath('//span[@itemprop="location"]//meta[@itemprop="name"]/@content').extract()
			jsonObj["addr"] = addr[0]
			#DESCRIPTION
			desc = Selector(text=htmlTxt).xpath('//div[@class="panel_section"]/span[@itemprop="description"][@class="description"]//p/text()').extract()
			if len(desc)==0:
				desc = Selector(text=htmlTxt).xpath('//div[@class="panel_section"]/span[@itemprop="description"][@class="description"]//p//*/text()').extract()
			jsonObj["desc"] = ((" ".join(desc)).strip()).encode("ascii","replace")
			#START TIME			
			sttime = Selector(text=htmlTxt).xpath('//span[@class="dtstart"]/span[@class="value-title"]/@title').extract()
			if len(sttime)==0:
				jsonObj["starttime"] = ""
			else:	
				jsonObj["starttime"] = sttime[0]
			#END TIME			
			endtime = Selector(text=htmlTxt).xpath('//span[@class="dtend"]/span[@class="value-title"]/@title').extract()
			if len(endtime)==0:
				jsonObj["endtime"] = ""
			else:	
				jsonObj["endtime"] = endtime[0]
			

			#URL
			jsonObj["url"] = item["url"]
			#print(json.dumps(jsonObj,indent=4))
			with open(fileName,'w+') as f:
				f.write(json.dumps(jsonObj,indent=4))
				f.close()
		else:
			fileName = prop.opDir+ "/" + str(PersistItemsPipeline.docCount) + '.HTML'
			with open(fileName,'w+') as f:
				f.write(item["body"])
				f.close()
		print "PERSISTING-- ",item["url"]
		return item

