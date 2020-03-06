<p>Anyone tinkering with Python long enough has been bitten (or torn to pieces) by the following issue:</p>

<pre><code>def foo(a=[]):
    a.append(5)
    return a
</code></pre>

<p>Python novices would expect this function to always return a list with only one element: <code>[5]</code>. The result is instead very different, and very astonishing (for a novice):</p>

<pre><code>&gt;&gt;&gt; foo()
[5]
&gt;&gt;&gt; foo()
[5, 5]
&gt;&gt;&gt; foo()
[5, 5, 5]
&gt;&gt;&gt; foo()
[5, 5, 5, 5]
&gt;&gt;&gt; foo()
</code></pre>

<p>A manager of mine once had his first encounter with this feature, and called it "a dramatic design flaw" of the language. I replied that the behavior had an underlying explanation, and it is indeed very puzzling and unexpected if you don't understand the internals. However, I was not able to answer (to myself) the following question: what is the reason for binding the default argument at function definition, and not at function execution? I doubt the experienced behavior has a practical use (who really used static variables in C, without breeding bugs?)</p>

<p><strong>Edit</strong>: </p>

<p>Baczek made an interesting example. Together with most of your comments and Utaal's in particular, I elaborated further:</p>

<pre><code>&gt;&gt;&gt; def a():
...     print "a executed"
...     return []
... 
&gt;&gt;&gt;            
&gt;&gt;&gt; def b(x=a()):
...     x.append(5)
...     print x
... 
a executed
&gt;&gt;&gt; b()
[5]
&gt;&gt;&gt; b()
[5, 5]
</code></pre>

<p>To me, it seems that the design decision was relative to where to put the scope of parameters: inside the function or "together" with it?</p>

<p>Doing the binding inside the function would mean that <code>x</code> is effectively bound to the specified default when the function is called, not defined, something that would present a deep flaw: the <code>def</code> line would be "hybrid" in the sense that part of the binding (of the function object) would happen at definition, and part (assignment of default parameters) at function invocation time.</p>

<p>The actual behavior is more consistent: everything of that line gets evaluated when that line is executed, meaning at function definition.</p>
