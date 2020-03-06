#!/Users/snow/anaconda/bin/python
import pymysql


class dbConnect():
    def __init__(self):
        self.db = pymysql.connect("localhost", "root", "", "soeservice")
        # 使用cursor()方法获取操作游标
        self.cursor = self.db.cursor()

    def select_sql(self, sql):

        try:
            # 执行SQL语句
            self.cursor.execute(sql)
            # 获取所有记录列表
            results = self.cursor.fetchall()
            # 关闭数据库连接
            self.db.close()
            return results
        except:
            # 关闭数据库连接
            self.db.close()
            return "-1"



    def update_sql(self, columns):
        try:
            # 执行SQL语句
            # print(columns)
            # print(values)
            self.cursor.execute(columns)
            self.db.commit()
            print('update questions_table successfully!')
            self.db.close()
            # 获取所有记录列表
            return "1"
        except :
            self.db.close()
            print('error occurs')
            print(columns)
            return "-1"

        # db=dbConnect()
        # sql="select * from questions_table where id = 35"
        # print(db.select_sql(sql))