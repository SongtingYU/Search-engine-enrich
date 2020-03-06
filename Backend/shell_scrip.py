#!/Users/snow/anaconda/bin/python3
import os

path_of_python = os.popen('which python3').read().strip()
files = ['shell_scrip.py', 'database_connect.py', 'StackOverflow_api.py', 'YouTube_discripstion.py']
for filename in files:
    with open(filename, 'r') as f:
        content = f.read().split('\n')
        if '#!' in content[0]:
            content[0] = '#!'+path_of_python
        else:
            content.insert(0, '#!'+path_of_python)
    with open(filename, 'w') as ff:
        ff.write('\n'.join(content))
