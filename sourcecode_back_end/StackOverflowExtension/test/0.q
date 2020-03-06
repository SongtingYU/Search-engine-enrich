<p>What is the use of the <code>yield</code> keyword in Python? What does it do?</p>

<p>For example, I'm trying to understand this code<sup><strong>1</strong></sup>:</p>

<pre><code>def _get_child_candidates(self, distance, min_dist, max_dist):
    if self._leftchild and distance - max_dist &lt; self._median:
        yield self._leftchild
    if self._rightchild and distance + max_dist &gt;= self._median:
        yield self._rightchild  
</code></pre>

<p>And this is the caller:</p>

<pre><code>result, candidates = list(), [self]
while candidates:
    node = candidates.pop()
    distance = node._get_dist(obj)
    if distance &lt;= max_dist and distance &gt;= min_dist:
        result.extend(node._values)
    candidates.extend(node._get_child_candidates(distance, min_dist, max_dist))
return result
</code></pre>

<p>What happens when the method <code>_get_child_candidates</code> is called?
A list is returned? A single element is returned? Is it called again? When will subsequent calls stop?</p>

<hr>

<p><sub>
1. The code comes from Jochen Schulz (jrschulz), who made a great Python library for metric spaces. This is the link to the complete source: <a href="http://well-adjusted.de/~jrschulz/mspace/">Module mspace</a>.</sub></p>
