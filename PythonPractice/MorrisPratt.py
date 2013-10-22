def preProcess(x, m, mpNext):
    
    i = 0;
    j = mpNext[0] = -1;
    while (i < m):
        while (j > -1 and x[i] != x[j]):
            j = mpNext[j];
            
        i += 1;
        j += 1;
        mpNext[i] = j;
 

def MP(pattern, text):

    mpNext = [None]*(len(pattern) + 1);
    preProcess(pattern, len(pattern), mpNext);
    i = 0; j = 0;
    m = len(pattern);
    while (j < len(text)):
        while (i > -1 and pattern[i] != text[j]):
            i = mpNext[i];
        i += 1;
        j += 1;
        if (i >= m): 
            print(j - i);
            i = mpNext[i];
        
    



# MP("ABABCD", "ABCABCDABABCDABCDABDE");

txt = "BAABAACAADAABAAABAABAABAADFFSBAABAADSSSSSSSSSSSSSSDDSFFBAABASDDBAABAASFDSFBAABAASSDDBAABA";
pat = "BAABA";

MP(pat, txt);
