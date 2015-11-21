from ..items.items import AragogItem
from scrapy.exceptions import DropItem
from scrapy.selector import Selector


class FilterEventBrite(object):
	def isValidPage(self,item):
		valid = False
		htmlTxt =item["body"]
		event = Selector(text=htmlTxt).xpath('//div[@id="track_event_container"]')
		if(len(event)==0):
			return False
		return True

	def process_item(self, item, spider):
		if self.isValidPage(item) :
			#print "[ACCEPTED] Item ACCEPTED!!!"
			return item
		else :
			#print "[DROP]",item["url"]
			raise DropItem("[DROP] Page did not meet Event Brite - Dropping")
