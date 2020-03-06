#!/Users/snow/anaconda/envs/butterfly/bin/python2.7
import collections, math, nltk, re
import sys
# import database_connect



def load_file(path):
    with open(path,'r') as f:
        text=f.readlines()[0]
    return text


def extract_candidate_chunks(text, grammar=r'KT: {(<JJ>* <NN.*>+ <IN>)? <JJ>* <NN.*>+}'):
    import itertools, nltk, string
    
    # exclude candidates that are stop words or entirely punctuation
    punct = set(string.punctuation)
    stop_words = set(nltk.corpus.stopwords.words('english'))
    # tokenize, POS-tag, and chunk using regular expressions
    chunker = nltk.chunk.regexp.RegexpParser(grammar)
    tagged_sents = nltk.pos_tag_sents(nltk.word_tokenize(sent) for sent in nltk.sent_tokenize(text))
    all_chunks = list(itertools.chain.from_iterable(nltk.chunk.tree2conlltags(chunker.parse(tagged_sent))
                                                    for tagged_sent in tagged_sents))
    # join constituent chunk words into a single chunked phrase
    # print (all_chunks)
    candidates = [' '.join(word for word, pos, chunk in group).lower() for key, group in itertools.groupby(all_chunks, lambda (word,pos,chunk): chunk != 'O') if key]

    return [cand for cand in candidates
            if cand not in stop_words and not all(char in punct for char in cand)]


def extract_candidate_features(candidates, doc_text, doc_title, dic):
    import collections, math, nltk, re
    
    candidate_scores = collections.OrderedDict()
    
    # get word counts for document
    doc_word_counts = collections.Counter(word.lower()
                                          for sent in nltk.sent_tokenize(doc_text)
                                          for word in nltk.word_tokenize(sent))
    
    for candidate in candidates:
        
        pattern = re.compile(r'\b'+re.escape(candidate)+r'(\b|[,;.!?]|\s)', re.IGNORECASE)
        
        # frequency-based
        # number of times candidate appears in document
        cand_doc_count = len(pattern.findall(doc_text))
        # count could be 0 for multiple reasons; shit happens in a simplified example
        if not cand_doc_count:
            # print '**WARNING:', candidate, 'not found!'
            continue
    
        # statistical
        candidate_words = candidate.split()
        max_word_length = max(len(w) for w in candidate_words)
        term_length = len(candidate_words)
        # get frequencies for term and constituent words
        sum_doc_word_counts = float(sum(doc_word_counts[w] for w in candidate_words))
        try:
            # lexical cohesion doesn't make sense for 1-word terms
            if term_length == 1:
                lexical_cohesion = 0.0
            else:
                lexical_cohesion = term_length * (1 + math.log(cand_doc_count, 10)) * cand_doc_count / sum_doc_word_counts
        except (ValueError, ZeroDivisionError) as e:
            lexical_cohesion = 0.0
        
        # positional
        # found in title, key excerpt
        in_title = 1 if pattern.search(doc_title) else 0
        # in_excerpt = 1 if pattern.search(doc_excerpt) else 0
        # first/last position, difference between them (spread)
        doc_text_length = float(len(doc_text))
        first_match = pattern.search(doc_text)
        abs_first_occurrence = first_match.start() / doc_text_length
        if cand_doc_count == 1:
            spread = 0.0
            abs_last_occurrence = abs_first_occurrence
        else:
            for last_match in pattern.finditer(doc_text):
                pass
            abs_last_occurrence = last_match.start() / doc_text_length
            spread = abs_last_occurrence - abs_first_occurrence

        if candidate in dic:
        	in_dic=1
        else:
        	in_dic=0
        candidate_scores[candidate] = {'term_count': cand_doc_count,
                                       'term_length': term_length, 'max_word_length': max_word_length,
                                       'spread': spread, 'lexical_cohesion': lexical_cohesion,
                                       'in_title': in_title,
                                       'in_dic':in_dic,
                                       'abs_first_occurrence': abs_first_occurrence,
                                       'abs_last_occurrence': abs_last_occurrence}
    return candidate_scores


# def reduce_redundancy():
# def store_keyphrase():
def load_dic(path):
	dic=[]
	with open(path,'r') as f:
		for line in f:
			dic.append(line.strip().lower())	
	return dic



def select_keyphrase(candidate_scores):
	can_score=[]
	keyphrase=[]
	for candidate in candidate_scores:
		if candidate_scores[candidate]['in_dic']==1:
			keyphrase.append(candidate)
			continue
		if candidate_scores[candidate]['max_word_length']>19:
			continue
	# print 'keyphrase:'
	# print a 
	# print 'term_count',candidate_scores[a]['term_count']
	# print 'term_length',candidate_scores[a]['term_length']
	# print 'max_word_length',candidate_scores[a]['max_word_length']
	# print 'spread',candidate_scores[a]['spread']
	# print 'lexical_cohesion',candidate_scores[a]['lexical_cohesion']
	# print 'in_title',candidate_scores[a]['in_title']
	# print 'abs_first_occurrence',candidate_scores[a]['abs_first_occurrence']
	# print 'abs_last_occurrence',candidate_scores[a]['abs_last_occurrence']
	# print '--------------------'
		word_score=candidate_scores[candidate]['term_count']+candidate_scores[candidate]['term_length']+candidate_scores[candidate]['lexical_cohesion']+candidate_scores[candidate]['in_title']+1-candidate_scores[candidate]['abs_first_occurrence']+1-candidate_scores[candidate]['abs_last_occurrence']
		can_score.append([word_score,candidate])
		can_score.sort()
		can_score.reverse()
	for cand in can_score:
		keyphrase.append(cand[1])
	return keyphrase,can_score

# print load_dic('dic.txt')

# text=load_file(sys.argv[1])
# title=load_file(sys.argv[2])
def store_keyphrase(folder):
    # for file in folder:
    #     title_filename=filename.split('.')+".qsn"
    #     text=file.readlines()[0]
    #     title=title_filename
    import os
    for file in os.listdir(folder):
        if file.endswith(".a"):
            index=file.split('.')[0]
            qsn=folder+'/'+index+".q"
            ans=folder+'/'+file
            key=folder+'/'+index+".key"
            with open (ans,'r') as f1:
                answer=f1.readlines()[0]
            with open (qsn,'r') as f2:
                question=f2.readlines()[0]

            candidates=extract_candidate_chunks(answer)
            dic=load_dic('dic.txt')
            candidate_scores=extract_candidate_features(candidates,answer,question,dic)
            keyphrase,can_score=select_keyphrase(candidate_scores)
            f3=open(key,'w')
            for phrase in keyphrase:
                f3.write(phrase+"\n")
            f3.close()


def test(folder):
    import os
    print os.listdir(folder)
    for file in os.listdir(folder):
        if file.endswith(".a"):
            print file
            index=file.split('.')[0]
            qsn=folder+'/'+index+".q"
            ans=folder+'/'+file
            with open (ans,'r') as f1:
                answer=f1.readlines()[0]
            with open (qsn,'r') as f2:
                question=f2.readlines()[0]
            candidates=extract_candidate_chunks(answer)
            dic=load_dic('dic.txt')
            candidate_scores=extract_candidate_features(candidates,answer,question,dic)
            keyphrase,can_score=select_keyphrase(candidate_scores)
            print can_score
test('test')
# store_keyphrase('new_data')
# text=sys.argv[1]
# title=sys.argv[2]
# print text

# candidates=extract_candidate_chunks(text)
# dic=load_dic('dic.txt')
# candidate_scores=extract_candidate_features(candidates,text,title,dic)
# # print candidate_scores

# keyphrase,can_score=select_keyphrase(candidate_scores)
# print '|'.join(keyphrase)
# print "no such file"
# print '|'.join()
# can_score=[]
# for a in candidate_scores:
# 	print 'keyphrase:'
# 	print a 
# 	print 'term_count',candidate_scores[a]['term_count']
# 	print 'term_length',candidate_scores[a]['term_length']
# 	print 'max_word_length',candidate_scores[a]['max_word_length']
# 	print 'spread',candidate_scores[a]['spread']
# 	print 'lexical_cohesion',candidate_scores[a]['lexical_cohesion']
# 	print 'in_title',candidate_scores[a]['in_title']
# 	print 'abs_first_occurrence',candidate_scores[a]['abs_first_occurrence']
# 	print 'abs_last_occurrence',candidate_scores[a]['abs_last_occurrence']
# 	print '--------------------'
# 	word_score=candidate_scores[a]['term_count']+candidate_scores[a]['term_length']+candidate_scores[a]['lexical_cohesion']+candidate_scores[a]['in_title']+1-candidate_scores[a]['abs_first_occurrence']+1-candidate_scores[a]['abs_last_occurrence']
# 	can_score.append([word_score,a])		

# can_score.sort()
# can_score.reverse()
# print can_score






