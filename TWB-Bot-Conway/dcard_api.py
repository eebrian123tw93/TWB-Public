from dcard import Dcard
import shuo_api as api
import random
import time
import schedule

import schedule
import time

def get_boards():
    dcard = Dcard()
    forums = dcard.forums.get(no_school=True)

    # 全部的看板
    boards = [forum['alias'] for forum in forums if forum['alias']]
    return boards

class Article:
    def __init__(self, id,pas,title,content,images):
        self.id=id
        self.pas=pas
        self.title=title
        self.content=content
        self.images=images

def post():
    start_time=time.time()
    dcard = Dcard()
    boards = get_boards()

    article_list = []

    f = open('user_list', 'r')
    users = f.read().splitlines()

    for board in boards:
        ariticle_metas = dcard.forums(board).get_metas(num=10, sort='new')
        ids = [meta['id'] for meta in ariticle_metas]
        articles = dcard.posts(ids).get(comments=False)
        time.sleep(1)
        print(board)
        for article in articles.result():

            user = random.choice(users)
            title = article['title']
            content = article['content']
            comments = []
            images = []
            print("comment:" + str(article['comments']))
            if (article['comments'] and len(article['comments']) > 0):
                for comment in article['comments']:
                    if "content" in comment:
                        comments.append(comment['content'])
            print(article['media'])
            if (len(article['media']) > 1):
                for m in article['media']:
                    if 'url' in m:
                        images.append(m['url'])
            article_list.append(Article(user, user, title, content, images))

    random.shuffle(article_list)
    for article in article_list:
        api.post_article(article.id, article.pas, article.title,
                         article.content, article.images)
    end_time=time.time()
    print(end_time-start_time)

if __name__ == '__main__':
    # post()
    schedule.every(10).minutes.do(post)
    while True:
        schedule.run_pending()
        time.sleep(1)










    # none_return_value = dcard.posts(ariticle_metas).get(callback=upload)