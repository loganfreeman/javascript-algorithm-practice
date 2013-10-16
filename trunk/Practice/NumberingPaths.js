// http://algorithmguru.com/content/?viewpage=./contentfiles/showccode.php&id=119&type=r

var printf = require("./sprintf.js").printf;
var Max;

var M = [];

function Floyd() {
	var i, j, k, temp;
	for (k = 0; k <= Max; k++) {
		for (i = 0; i <= Max; i++) {
			for (j = 0; j <= Max; j++) {
				if (!M[i])
					M[i] = [];
				if (!M[i][k])
					M[i][k] = {};
				if (!M[k])
					M[k] = [];
				if (!M[k][j])
					M[k][j] = {};
				if (!M[i])
					M[i] = [];
				if (!M[i][j])
					M[i][j] = {};
				if (!M[j])
					M[j] = [];
				if (!M[j][i])
					M[j][i] = {};
				temp = M[i][k].path * M[k][j].path;
				if (temp) {
					if (M[i][k].path < 0 || M[k][j].path < 0)
						M[i][j].path = -1;
					else
						M[i][j].path += temp;

				}
				if (M[i][j].path && M[j][i].path)
					M[i][j].path = M[j][i].path = -1;
			}
		}
	}

}

function Cal() {
	var i, j;
	Floyd();
	for (i = 0; i <= Max; i++) {
		printf("%d", M[i][0].path);
		for (j = 1; j <= Max; j++)
			printf(" %d", M[i][j].path);
		printf("\n");
	}
}

function main(s) {
	var i, u, v, n, j, m = 0;
	// Input 7 0 1 0 2 0 4 2 4 2 3 3 1 4 3
	var arr = s.split(" ");
	n = arr[0];
	Max = -1;
	for (i = 1; i < n; i = i + 2) {
		u = arr[i], v = arr[i + 1];
		if (!M[u])
			M[u] = [];
		if (!M[u][v])
			M[u][v] = {};
		M[u][v].path = 1;
		if (u > Max)
			Max = u;
		if (v > Max)
			Max = v;
	}
	for (i = 0; i <= Max; i++)
		for (j = 0; j <= Max; j++) {
			if (!M[i])
				M[i] = [];
			if (!M[i][j])
				M[i][j] = {};
			if (!M[i][j].path)
				M[i][j].path = 0;
		}

	printf("matrix for city %d\n", m++);
	Cal();
	for (i = 0; i <= Max; i++)
		for (j = 0; j <= Max; j++)
			M[i][j].path = 0;

	return 0;
}

var s = "7 0 1 0 2 0 4 2 4 2 3 3 1 4 3";
main(s);