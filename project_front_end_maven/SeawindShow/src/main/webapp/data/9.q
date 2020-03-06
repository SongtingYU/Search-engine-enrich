<p>What is the most elegant way to check if the directory a file is going to be written to exists, and if not, create the directory using Python? Here is what I tried:</p>

<pre><code>filename = "/my/directory/filename.txt"
dir = os.path.dirname(filename)

try:
    os.stat(dir)
except:
    os.mkdir(dir)       

f = file(filename)
</code></pre>

<p>Somehow, I missed <code>os.path.exists</code> (thanks kanja, Blair, and Douglas). This is what I have now:</p>

<pre><code>def ensure_dir(f):
    d = os.path.dirname(f)
    if not os.path.exists(d):
        os.makedirs(d)
</code></pre>

<p>Is there a flag for "open", that makes this happen automatically?</p>
