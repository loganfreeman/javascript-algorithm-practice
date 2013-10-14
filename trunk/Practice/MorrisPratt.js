function preProcess(x, m, mpNext) {
	var i, j;

	i = 0;
	j = mpNext[0] = -1;
	while (i < m) {
		while (j > -1 && x[i] != x[j])
			j = mpNext[j];
		mpNext[++i] = ++j;
	}
}
function MP(pattern, text) {

	var mpNext = [];
	var i, j, m;
	preProcess(pattern, pattern.length, mpNext);
	i = 0, j = 0;
	m = pattern.length;
	while (j < text.length) {
		while (i > -1 && pattern[i] != text[j])
			i = mpNext[i];
		i++;
		j++;
		if (i >= m) {
			console.log(j - i);
			i = mpNext[i];
		}
	}

}

// MP("ABABCD", "ABCABCDABABCDABCDABDE");

var txt = "BAABAACAADAABAAABAABAABAADFFSBAABAADSSSSSSSSSSSSSSDDSFFBAABASDDBAABAASFDSFBAABAASSDDBAABA";
var pat = "BAABA";

MP(pat, txt);
module.exports = MP;