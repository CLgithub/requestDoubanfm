#coding=utf-8

import requests

def func1():
	# 读取json文件，获取到歌曲信息
	L=read()
	# 下载
	down(L)

def down(L):
	for song in L:
		print(song)
		r=requests.get(song['url'])
		songFile=open('./song/'+song['title']+'-'+song['name_usual']+'.mp3','wb')
		songFile.write(r.content)

def read():
	M={}
	L=[]
	f=open('song192.json','r')
	while True:
		ff=f.readline()
		if '}}]' in ff:
			break
		if '"title"' in ff:
			M['title']=ff.split('"')[-2].replace('/','')
		elif '"name_usual"' in ff:
			M['name_usual']=ff.split('"')[-2].replace('/','')
		elif '"url"' in ff:
			M['url']=ff.split('"')[-2]
			L.append(M)
			M={}
	return L

func1()
