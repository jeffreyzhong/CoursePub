from io import BytesIO
from lxml.etree import iterparse
import html
import requests
import pymysql
import datetime
import re

QL = "INSERT INTO `sentence` (`video_id`, `video_time`, `words`) VALUES (%s, %s, %s)"

if __name__ == '__main__':
  connection = pymysql.connect(host='localhost', user='root', password='', db='stub', charset='utf8mb4')

  pattern = re.compile('^https://www.youtube.com/embed/([^\?]+)\?*')

  try:
    with connection.cursor() as cursor:
      ql = 'SELECT `id`, `url` FROM `video`'
      cursor.execute(ql)
      videos = cursor.fetchall()

    for video in videos:
      video_id = video[0]
      url = 'https://video.google.com/timedtext?lang=en&v=%s' % pattern.search(video[1]).group(1);
      print('[+] Fetching from %s for video %d...' % (url, video_id))
      list_text = requests.get(url).content

      try:
        for _, element in iterparse(BytesIO(list_text), html=False, tag='text'):

          try:
            time = element.attrib['start']
            words = html.unescape(element.text)

            time = datetime.datetime.fromtimestamp(float(time))

            with connection.cursor() as cursor:
              cursor.execute(QL, (video_id, str(time), words))
          except:
            continue
        print('[+] Success!')
      except:
        print('[-] Failed!')
        continue

    connection.commit()
  finally:
    connection.close()

