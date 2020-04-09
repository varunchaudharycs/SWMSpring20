import scrapy


class PIForumLinkItem(scrapy.Item):
    forum_name = scrapy.Field()
    forum_url = scrapy.Field()


class PIForumPostLinkItem(scrapy.Item):
    group_name = scrapy.Field()
    post_title = scrapy.Field()
    post_url = scrapy.Field()


class PIForumPostContentItem(scrapy.Item):
    pass