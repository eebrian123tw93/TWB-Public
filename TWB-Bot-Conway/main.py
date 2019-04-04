import random
import string

import shuo_api as api


def word_generator(length):
    return ''.join(random.choices(string.ascii_lowercase + string.digits, k=length))


def register_multiple_users(amount):
    new_users = []
    for _ in range(amount):
        N = random.randint(4, 20)
        user_id = word_generator(N)
        # password = user_id
        new_users.append(user_id)
        # api.register_user(user_id, password)
    f = open('user_list', 'a')
    f.write('\n'.join(new_users))
    f.close()
    for user in new_users:
        api.register_user(user, user)


def generate_articles(amount):
    f = open('user_list', 'r')
    users = f.read().splitlines()
    # print(users)

    article_list = []

    for _ in range(amount):
        user = random.choice(users)
        # title_len = random.randint(5, 30)
        content_len = random.randint(200, 2000)
        id = api.post_article(user, user, '測試文章 ' + word_generator(5), word_generator(content_len), None)
        article_list.append(id)

    f = open('article_list', 'a')
    f.write('\n'.join(article_list))
    f.close()


def generate_comments(amount):
    f = open('user_list', 'r')
    users = f.read().splitlines()
    f.close()
    f = open('article_list', 'r')
    articles = f.read().splitlines()
    for _ in range(amount):
        user = random.choice(users)
        article_id = random.choice(articles)
        # title_len = random.randint(5, 30)
        content_len = random.randint(5, 50)
        api.comment(user, user, article_id, word_generator(content_len))


# register_multiple_users(1)
generate_articles(100)
# generate_comments(1660)
