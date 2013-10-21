def expandAroundCenter(s,c1, c2):
    l = c1; r = c2;
    n = len(s);
    while(l >= 0 and r <= (n -1) and s[l] == s[r]):
        l -= 1;
        r += 1;
    return s[l+1:r];

s = expandAroundCenter("abcdedcbc", 4, 4);
print s;