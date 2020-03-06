<p>I'm trying to understand <code>super()</code>. From the looks of it, both child classes can be created just fine. I'm curious as to what difference there actually is between the following child classes:</p>

<pre><code>class Base(object):
    def __init__(self):
        print "Base created"

class ChildA(Base):
    def __init__(self):
        Base.__init__(self)

class ChildB(Base):
    def __init__(self):
        super(ChildB, self).__init__()

ChildA() 
ChildB()
</code></pre>
