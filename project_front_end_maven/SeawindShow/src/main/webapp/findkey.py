#!/Users/snow/anaconda/bin/python3
import sys
from apiclient.discovery import build
# Set DEVELOPER_KEY to the API key value from the APIs & auth > Registered apps
# tab of
#   https://cloud.google.com/console
# Please ensure that you have enabled the YouTube Data API for your project.
DEVELOPER_KEY = "AIzaSyDNYfQ-YGxczw4GZpaEh1aoHQVcZ9_DUj8"
YOUTUBE_API_SERVICE_NAME = "youtube"
YOUTUBE_API_VERSION = "v3"

def youtube_search(pattern):
	youtube = build(YOUTUBE_API_SERVICE_NAME, YOUTUBE_API_VERSION,developerKey=DEVELOPER_KEY)

  # Call the search.list method to retrieve results matching the specified
  # query term.
	search_response = youtube.search().list(
	    q=pattern,
	    part="id,snippet",
	    maxResults=25
	  ).execute()

	videos = []

	# Add each result to the appropriate list, and then display the lists of
	# matching videos, channels, and playlists.
	for search_result in search_response.get("items", []):
		if search_result["id"]["kind"] == "youtube#video":
			videos.append(search_result["id"]["videoId"])
			break
			# videos.append("%s (%s)" % (search_result["snippet"]["title"],
			# 				search_result["id"]["videoId"]))
	return videos
if __name__ == '__main__':
    search_tearm = sys.argv[1]
    res = youtube_search(search_tearm)
    if res:
    	print(res[0])
		