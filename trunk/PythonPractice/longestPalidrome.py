def expandAroundCenter(s,c1, c2):
    l = c1; r = c2;
    n = len(s);
    while(l >= 0 and r <= (n -1) and s[l] == s[r]):
        l -= 1;
        r += 1;
    return s[l+1:r];

s = expandAroundCenter("abcdedcbc", 4, 4);
print s;

def longestPalindromeSimple(s):
    n = len(s);
    if(n == 0):
        return "";
    longest = s[0:1];
    for i in range(n):
        p1 = expandAroundCenter(s, i, i);
        if(len(p1) > len(longest)):
            longest = p1;
        p2 = expandAroundCenter(s, i, i+1);
        if(len(p2) > len(longest)):
            longest = p2;
    
    return longest;
        
s = longestPalindromeSimple("abacdfgdcaba");
print s;
print longestPalindromeSimple("abacdfggfdcabadcaba");
print longestPalindromeSimple("abacdfggfdcabadcaba");
print longestPalindromeSimple("kkddllddddadfadfererearearearegjkkjkkk");
print longestPalindromeSimple("abacdfggfdcabadcaba");
print longestPalindromeSimple("abacdfggfdcabadcaba");