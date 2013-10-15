/**
 * 
 * Given a target number, and a series of candidate numbers, print out all combinations, so that the sum of candidate numbers equals to the target.

Here order is not important, so don’t print the duplicated combination.
To search for all combination, we use a backtracking algorithm. Here, we use the above example of candidate={2,3,6,7} and target=7.

 */
function printSum(candidates, index, n) {
	var s = "";
  for (var i = 1; i <= n; i++){
	    s += (""+ candidates[index[i]] + ((i == n) ? "" : "+"));
  }
  console.log(s);
}

function _solve(target, sum, candidates, sz, index, n) {
  if (sum > target)
    return;
  if (sum === target)
    printSum(candidates, index, n);

  for (var i = index[n] + 1; i < sz; i++) {
    index[n+1] = i;
    
    _solve(target, sum + candidates[i], candidates, sz , index, n+1);
  }
}

function solve(target, candidates, sz) {
  var index = [];
  index[0] = 0;
  _solve(target, 0, candidates, sz, index, 0);
}

solve(8, [10, 1, 2, 7, 6, 1, 5], 7);