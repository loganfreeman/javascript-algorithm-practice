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

# The total unique paths at grid (r,c) is equal to the sum of total unique paths from grid to the right (r,c+1) and the grid below (r+1,c).
def dp(m, n):
    mat =[];
    row = [0]*(M_MAX+2);
    for i in range(M_MAX+2):
        mat.append(list(row))
    mat[m][n+1] = 1;
    
    for r in range(m, 0, -1):
        for c in range(n, 0, -1):
            mat[r][c] = mat[r+1][c] + mat[r][c+1];
    
    return mat[1][1];


print dp(5, 6);

print [str(x) for x in range(10,0,-1)]