#!/Users/snow/anaconda/bin/python3
from apiclient.discovery import build
import sys
import requests
import os
import re
DEVELOPER_KEY = 'AIzaSyDNYfQ-YGxczw4GZpaEh1aoHQVcZ9_DUj8'
youtube = build('youtube', 'v3', developerKey=DEVELOPER_KEY)


class SearchFromYouTube:
    def __init__(self, pattern, qid):
        self.pattern = pattern
        self.qid = qid

    def _searchID(self):
        res = []
        search_response = youtube.search().list(
                 q=self.pattern,
                 type="video",
                 part="id,snippet",
                 maxResults=25
               ).execute()
        for dics in search_response['items']:
            if dics['id']['kind'] == 'youtube#video':
                res.append(dics['id']['videoId'])
        return res

    def searchForDescription(self):
        global DEVELOPER_KEY
        # DEVELOPER_KEY = 'AIzaSyDNYfQ-YGxczw4GZpaEh1aoHQVcZ9_DUj8'
        video_ids = self._searchID()
        res = []
        for vid in video_ids:
            # use this url to get full description
            url = 'https://www.googleapis.com/youtube/v3/videos?part=snippet&id=%s&key=%s'%(vid, DEVELOPER_KEY)

            content_dic = requests.get(url).json()
            for item in content_dic['items']:
                if item['kind'] == "youtube#video":
                    description = item['snippet']['description']
                else:
                    description = ""
                res.append([vid, description])
        return res

if __name__ == '__main__':
    # sql = 'select * from '
    # dbconnect = database_connect.dbConnect()
    # dbconnect.select_sql(sql)
    # qid = sys.argv[1]
    # pattern = sys.argv[2]

    for root, directory, files in os.walk('test'):
        for file in files:
            if '.key' in file:
                with open('test/'+file, 'r') as f:
                    keywords = f.read().split('\n')
                    qid = re.sub(r'\..+$', '', file)
                    for pattern in keywords:
                        searchAPI = SearchFromYouTube(pattern, qid)
                        res = searchAPI.searchForDescription()
                        dir = 'test/'+str(qid)
                        if not os.path.exists(dir+'/'):
                            os.mkdir(dir)
                        for i in range(len(res)):
                            with open(dir+'/'+str(res[i][0]), 'w') as ff:
                                ff.write(res[i][1])
