<p>What does the <code>if __name__ == "__main__":</code> do?</p>

<pre><code># Threading example
import time, thread

def myfunction(string, sleeptime, lock, *args):
    while 1:
        lock.acquire()
        time.sleep(sleeptime)
        lock.release()
        time.sleep(sleeptime)
if __name__ == "__main__":
    lock = thread.allocate_lock()
    thread.start_new_thread(myfunction, ("Thread #: 1", 2, lock))
    thread.start_new_thread(myfunction, ("Thread #: 2", 2, lock))
</code></pre>
