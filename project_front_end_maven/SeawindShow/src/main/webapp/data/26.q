<p>I know that I can do:</p>

<pre><code>try:
    # do something that may fail
except:
    # do this if ANYTHING goes wrong
</code></pre>

<p>I can also do this:</p>

<pre><code>try:
    # do something that may fail
except IDontLikeYourFaceException:
    # put on makeup or smile
except YouAreTooShortException:
    # stand on a ladder
</code></pre>

<p>But if I want to do the same thing inside two different exceptions, the best I can think of right now is to do this:</p>

<pre><code>try:
    # do something that may fail
except IDontLikeYouException:
    # say please
except YouAreBeingMeanException:
    # say please
</code></pre>

<p>Is there any way that I can do something like this (since the action to take in both exceptions is to <code>say please</code>):</p>

<pre><code>try:
    # do something that may fail
except IDontLikeYouException, YouAreBeingMeanException:
    # say please
</code></pre>

<p>Now this really won't work, as it matches the syntax for:</p>

<pre><code>try:
    # do something that may fail
except Exception, e:
    # say please
</code></pre>

<p>So, my effort to catch the two distinct exceptions doesn't exactly come through.</p>

<p>Is there a way to do this?</p>
