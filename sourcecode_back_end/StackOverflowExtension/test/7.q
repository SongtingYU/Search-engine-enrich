<p>I have two Python dictionaries, and I want to write a single expression that returns these two dictionaries, merged.  The <code>update()</code> method would be what I need, if it returned its result instead of modifying a dict in-place.</p>

<pre><code>&gt;&gt;&gt; x = {'a':1, 'b': 2}
&gt;&gt;&gt; y = {'b':10, 'c': 11}
&gt;&gt;&gt; z = x.update(y)
&gt;&gt;&gt; print z
None
&gt;&gt;&gt; x
{'a': 1, 'b': 10, 'c': 11}
</code></pre>

<p>How can I get that final merged dict in z, not x?</p>

<p>(To be extra-clear, the last-one-wins conflict-handling of <code>dict.update()</code> is what I'm looking for as well.)</p>
