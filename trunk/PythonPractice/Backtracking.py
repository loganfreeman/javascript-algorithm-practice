def backtrack(r, c, m, n):
    if (r == m and c == n):
        return 1;
    if (r > m or c > n):
        return 0;
    
    return backtrack(r+1, c, m, n) + backtrack(r, c+1, m, n);


print backtrack(1,1, 5, 6);

M_MAX = 100;
N_MAX = 100;
 
def backtrack1(r, c, m, n, mat):
  if (r == m and c == n):
    return 1;
  if (r > m or c > n):
    return 0;
 
  if (mat[r+1][c] == -1):
    mat[r+1][c] = backtrack1(r+1, c, m, n, mat);
  if (mat[r][c+1] == -1):
    mat[r][c+1] = backtrack1(r, c+1, m, n, mat);
 
  return mat[r+1][c] + mat[r][c+1];

 
def bt(m, n):
    mat =[];
    row = [-1]*(M_MAX+2);
    for i in range(M_MAX+2):
        mat.append(list(row))
      
    
    return backtrack1(1, 1, m, n, mat);   


print bt(5, 6);