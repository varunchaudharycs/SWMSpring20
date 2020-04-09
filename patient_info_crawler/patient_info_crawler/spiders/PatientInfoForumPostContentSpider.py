import scrapy
import json
import os

from patient_info_crawler.items import PIForumPostContentItem

URL_PREFIX = "https://patient.info"
CURRENT_DIR = os.path.dirname(__file__)


# scrapy crawl pi_forum_posts_content_crawler  -t json -o patient_info_forum_posts_content.json
# Uses posts links json file crawled by PatientInfoForumPostsLinkSpider
class PatientInfoForumPostContentSpider(scrapy.Spider):
    name = 'pi_forum_posts_content_crawler'
    allowed_domains = ['patient.info']
    # start_urls = [
    #     'https://patient.info/forums/discuss/browse/abdominal-disorders-3321',
    # ]
    # group_url_to_group_name_map = {
    #     'https://patient.info/forums/discuss/browse/abdominal-disorders-3321' : 'Abdominal Disorders'
    # }

    start_urls = []
    group_url_to_group_name_map = {}
    with open(CURRENT_DIR + '/../../patient_info_forums.json') as f:
        forums = json.load(f)
        for forum in forums:
            start_urls.append(forum['forum_url'])
            group_url_to_group_name_map[forum['forum_url']] = forum['forum_name']

    # TODO: don't forget trim some fields - .strip()
    def parse(self, response):
        print("Processing: " + response.url)
