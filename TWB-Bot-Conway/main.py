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

    for _ in range(amount):
        user = random.choice(users)
        title_len = random.randint(5, 30)
        content_len = random.randint(200, 2000)
        api.post_article(user, user, word_generator(title_len), word_generator(content_len))


generate_articles(15000)
