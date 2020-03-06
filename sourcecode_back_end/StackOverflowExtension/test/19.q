<p>The Python documentation seems unclear about whether parameters are passed by reference or value, and the following code produces the unchanged value 'Original'</p>

<pre><code>class PassByReference:
    def __init__(self):
        self.variable = 'Original'
        self.Change(self.variable)
        print self.variable

    def Change(self, var):
        var = 'Changed'
</code></pre>

<p>Is there something I can do to pass the variable by actual reference?</p>
