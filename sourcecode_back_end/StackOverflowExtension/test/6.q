<p>How can I make two decorators in Python that would do the following?</p>

<pre><code>@makebold
@makeitalic
def say():
   return "Hello"
</code></pre>

<p>...which should return:</p>

<pre><code>"&lt;b&gt;&lt;i&gt;Hello&lt;/i&gt;&lt;/b&gt;"
</code></pre>

<p>I'm not trying to make <code>HTML</code> this way in a real application - just trying to understand how decorators and decorator chaining works.</p>
