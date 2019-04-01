import requests

SHUO_BASE_PATH = 'http://34.80.143.220:9999/shuo'


# SHUO_BASE_PATH = 'http://localhost:9999/shuo'


def register_user(user_id, password):
    request_body = {'userId': user_id, 'password': password}
    print(request_body)
    r = requests.post(SHUO_BASE_PATH + '/public/register', json=request_body)
    print(r.status_code)
    print(r.text)


def post_article(user_id, password, title, content):
    request_body = {'title': title, 'content': content}
    print(request_body)
    r = requests.post(SHUO_BASE_PATH + '/postArticle', json=request_body, auth=(user_id, password))
    print(r.status_code)

def post_article(user_id, password, title, content,images):
    request_body = {'title': title, 'content': content,'images':images}
    print(request_body)
    r = requests.post(SHUO_BASE_PATH + '/postArticle', json=request_body, auth=(user_id, password))
    print(r.status_code)


def comment(user_id, password, article_id, content):
    request_body = {'articleId': article_id, 'comment': content}
    print(request_body)
    r = requests.post(SHUO_BASE_PATH + '/comment', json=request_body, auth=(user_id, password))
    print(r.status_code)
