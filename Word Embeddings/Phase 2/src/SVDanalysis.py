import numpy as np
from numpy import dot
from numpy.linalg import norm
import os

# Sets the working directory
os.chdir('C:\\data')

# Imports Document x Term sparse array
docxterm = np.loadtxt("SparseArray.txt")

# Transposes the sparse array included in SparseArray.txt so 
# as columns represent documents and rows represent terms
termxdoc = docxterm.transpose()

# Deletes array from memory
del docxterm

# Performs Singular Value Decomposition to Term x Document array
U, S, V = np.linalg.svd(termxdoc,full_matrices=False)

# Transforms S to diagonal
S = np.diag(S)

# Reads the Query x Term array
q = np.loadtxt("QueryArray.txt")


############## RANK 50 ##############
# Rank 50 approximation of termxdoc #
#####################################

# Rank 50 approximation of termxdoc
k=50
Uk = U[:, :k]
Sk = S[:k, :k]
Vk = V[:k, :]
Vk = Vk.transpose()

# Opens files for writing
top20_rank50 = open("top20_rank50.txt", "a")
top30_rank50 = open("top30_rank50.txt", "a")
top50_rank50 = open("top50_rank50.txt", "a")

# Calculates query in the new dimensions and the cosine similarity between queries and Vk array
# Produces results for the top 20, 30 and 50 documents for each query for rank 50 approximation of termxdoc
query_counter = 1
for rowQ in q:   
    doc_counter = 1   
    total = []
    for rowV in Vk:
        spec = []
		
		# Calculates queries in the new dimensions
        qk = rowQ.dot(Uk).dot(Sk)
		
		# Cosine similarity calculation
        cos_sim = dot(qk, rowV)/(norm(qk)*norm(rowV))
        spec = [cos_sim,doc_counter]
        total.append(spec)
		
		# Document counter
        doc_counter = doc_counter + 1
		
	# Top 20 docs
    top20_sorted = sorted(total, key=lambda x: x[0],reverse=True)[:20]
    number = 1;
    for line in top20_sorted:
        top20_rank50.write(str(query_counter) + " Q0 " + str(line[1]) + " " + str(number) + " " + str(round(line[0],7)) + " STANDARD" + '\n')
        number = number + 1;
        
	# Top 30 docs
    top30_sorted = sorted(total, key=lambda x: x[0],reverse=True)[:30]
    number = 1;
    for line in top30_sorted:
        top30_rank50.write(str(query_counter) + " Q0 " + str(line[1]) + " " + str(number) + " " + str(round(line[0],7)) + " STANDARD" + '\n')
        number = number + 1;

	# Top 50 docs
    top50_sorted = sorted(total, key=lambda x: x[0],reverse=True)[:50]
    number = 1;
    for line in top50_sorted:
        top50_rank50.write(str(query_counter) + " Q0 " + str(line[1]) + " " + str(number) + " " + str(round(line[0],7)) + " STANDARD" + '\n')		
        number = number + 1;

    # Query counter
    query_counter = query_counter + 1

# Closes file writer
top20_rank50.close();
top30_rank50.close();
top50_rank50.close();


############## RANK 150 ##############
# Rank 150 approximation of termxdoc #
######################################

# Rank 150 approximation of termxdoc
k=150
Uk = U[:, :k]
Sk = S[:k, :k]
Vk = V[:k, :]
Vk = Vk.transpose()

# Opens files for writing
top20_rank150 = open("top20_rank150.txt", "a")
top30_rank150 = open("top30_rank150.txt", "a")
top50_rank150 = open("top50_rank150.txt", "a")

# Calculates query in the new dimensions and the cosine similarity between queries and Vk array
# Produces results for the top 20, 30 and 50 documents for each query for rank 150 approximation of termxdoc
query_counter = 1
for rowQ in q:   
    doc_counter = 1   
    total = []
    for rowV in Vk:
        spec = []
		
		# Calculates queries in the new dimensions
        qk = rowQ.dot(Uk).dot(Sk)
		
		# Cosine similarity calculation
        cos_sim = dot(qk, rowV)/(norm(qk)*norm(rowV))
        spec = [cos_sim,doc_counter]
        total.append(spec)
		
		# Document counter
        doc_counter = doc_counter + 1
		
	# Top 20 docs
    top20_sorted = sorted(total, key=lambda x: x[0],reverse=True)[:20]
    number = 1;     
    for line in top20_sorted:   
        top20_rank150.write(str(query_counter) + " Q0 " + str(line[1]) + " " + str(number) + " " + str(round(line[0],7)) + " STANDARD" + '\n')
        number = number + 1;        
		
	# Top 30 docs
    top30_sorted = sorted(total, key=lambda x: x[0],reverse=True)[:30]
    number = 1;    
    for line in top30_sorted:
        top30_rank150.write(str(query_counter) + " Q0 " + str(line[1]) + " " + str(number) + " " + str(round(line[0],7)) + " STANDARD" + '\n')
        number = number + 1;
		
	# Top 50 docs
    top50_sorted = sorted(total, key=lambda x: x[0],reverse=True)[:50]
    number = 1;    
    for line in top50_sorted:
        top50_rank150.write(str(query_counter) + " Q0 " + str(line[1]) + " " + str(number) + " " + str(round(line[0],7)) + " STANDARD" + '\n')		
        number = number + 1;
        
    # Query counter
    query_counter = query_counter + 1

# Closes file writer
top20_rank150.close();
top30_rank150.close();
top50_rank150.close();


############## RANK 300 ##############
# Rank 300 approximation of termxdoc #
######################################

# Rank 300 approximation of termxdoc
k=300
Uk = U[:, :k]
Sk = S[:k, :k]
Vk = V[:k, :]
Vk = Vk.transpose()

# Opens files for writing
top20_rank300 = open("top20_rank300.txt", "a")
top30_rank300 = open("top30_rank300.txt", "a")
top50_rank300 = open("top50_rank300.txt", "a")

# Calculates query in the new dimensions and the cosine similarity between queries and Vk array
# Produces results for the top 20, 30 and 50 documents for each query for rank 300 approximation of termxdoc
query_counter = 1
for rowQ in q:   
    doc_counter = 1   
    total = []
    for rowV in Vk:
        spec = []
		
		# Calculates queries in the new dimensions
        qk = rowQ.dot(Uk).dot(Sk)
		
		# Cosine similarity calculation
        cos_sim = dot(qk, rowV)/(norm(qk)*norm(rowV))
        spec = [cos_sim,doc_counter]
        total.append(spec)
		
		# Document counter
        doc_counter = doc_counter + 1
		
	# Top 20 docs
    top20_sorted = sorted(total, key=lambda x: x[0],reverse=True)[:20]
    number = 1;    
    for line in top20_sorted:
        top20_rank300.write(str(query_counter) + " Q0 " + str(line[1]) + " " + str(number) + " " + str(round(line[0],7)) + " STANDARD" + '\n')
        number = number + 1;
		
	# Top 30 docs
    top30_sorted = sorted(total, key=lambda x: x[0],reverse=True)[:30]
    number = 1;    
    for line in top30_sorted:
        top30_rank300.write(str(query_counter) + " Q0 " + str(line[1]) + " " + str(number) + " " + str(round(line[0],7)) + " STANDARD" + '\n')
        number = number + 1;
		
	# Top 50 docs
    top50_sorted = sorted(total, key=lambda x: x[0],reverse=True)[:50]
    number = 1;    
    for line in top50_sorted:
        top50_rank300.write(str(query_counter) + " Q0 " + str(line[1]) + " " + str(number) + " " + str(round(line[0],7)) + " STANDARD" + '\n')		
        number = number + 1;
        
    # Query counter
    query_counter = query_counter + 1

# Closes file writer
top20_rank300.close();
top30_rank300.close();
top50_rank300.close();
