#!/Users/snow/anaconda/bin/python3
import requests
import re
import database_connect
import os

def getTitleQuestionAndAnswer(url):
    content_dic = requests.get(url).json()
    result = []
    for each_dic in content_dic['items']:
        max_score = 0
        for score in each_dic['answers']:
            if score['score'] > max_score:
                max_score = score['score']

                try:
                    title = each_dic['title']
                    answer = score['body']
                    question = each_dic['body']
                    original_answer = score['body_markdown']

                except:
                    print("This question don't have any answers")
        result.append([title, question, answer, original_answer])
    return result


def nlp_answer(answer):
    in_answer = re.sub(r"<code>.+?<\code>", "", answer)
    in_answer = re.sub(r"<.+?>", "", in_answer)
    in_answer = re.sub(r"[\t\r]", '', in_answer)
    in_answer = re.sub(r"\s+", " ", in_answer)
    in_answer = re.sub(r"(\\s)|(\\t)", ' ', in_answer)
    in_answer = re.sub(r"(&nbsp)", "\n", in_answer)
    in_answer = re.sub(r"(\\r)|(\\n)", '', in_answer)
    in_answer = re.sub(r"\\", '', in_answer)
    in_answer = re.sub(r"\"", "'", in_answer)
    in_answer = re.sub(r"&quot", "'", in_answer)
    in_answer = re.sub(r"&gt", '>', in_answer)
    in_answer = re.sub(r"&lt", "<", in_answer)
    return in_answer


def deal_quot(s):
    return re.sub(r"\"", "&quot", s)


if __name__ == '__main__':

    api_json = 'https://api.stackexchange.com/2.2/search?order=desc&sort=votes&tagged=python&site=stackoverflow&filter=!-MH(IsVcH.eojLRF.TgT7urtW.FNbqodT'
    qid = 0
    for element in getTitleQuestionAndAnswer(api_json):
        if not element:
            continue
        title, question, answer, nlpAnswer = element
        # title = deal_quot(title)
        # question = deal_quot(question)
        # answer = deal_quot(answer)
        # nlpAnswer = nlp_answer(answer)

        path = 'test'

        if not os.path.exists('test'):
            try:
                os.mkdir(path)
            except:
                print('mkdir error')
        qfilename = str(qid)+'.q'
        afilename = str(qid)+'.a'
        ofilename = str(qid)+'.o'
        tfilename = str(qid)+'.t'

        with open(path+'/'+qfilename, 'w') as f:
            f.write(question)
        with open(path+'/'+afilename, 'w') as f:
            f.write(nlpAnswer)
        with open(path+'/'+ofilename, 'w') as f:
            f.write(answer)
        with open(path+'/'+tfilename, 'w') as f:
            f.write(title)
        # urlpath = str(qid)
        # os.mkdir(urlpath)


        # print(title, question, answer)
        insertSql = 'insert into questions_table(id, title, question, origin_answer, answer, url) '+ 'value(%d, %s, %s, %s, %s, %s)'%(qid, "'"+tfilename+"'", "'"+qfilename+"'", "'"+ofilename+"'", "'"+afilename+"'", '"Null"')

        qid += 1
        dbconnect = database_connect.dbConnect()
        dbstate = dbconnect.update_sql(insertSql)
        # # # # # # # # # # # # # #
        # if dbstate == '1':
        #   success
        # else:
        #   faild
        # # # # # # # # # # # # # #