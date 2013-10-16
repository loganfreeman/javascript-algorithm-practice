function createArray(length) {
	var arr = new Array(length || 0), i = length;

	if (arguments.length > 1) {
		var args = Array.prototype.slice.call(arguments, 1);
		while (i--)
			arr[length - 1 - i] = createArray.apply(this, args);
	}

	return arr;
}

function Create2DArray(rows) {
	var arr = [];

	for (var i = 0; i < rows; i++) {
		arr[i] = [];
	}

	return arr;
}

function matrix(rows, cols, defaultValue) {

	var arr = [];

	// Creates all lines:
	for (var i = 0; i < rows; i++) {

		// Creates an empty line
		arr.push([]);

		// Adds cols to the empty line:
		arr[i].push(new Array(cols));

		for (var j = 0; j < cols; j++) {
			// Initializes:
			arr[i][j] = defaultValue;
		}
	}

	return arr;
}

var a = createArray(3, 2);
a[2][1] = 3;

var b = matrix(10,10, {});
b[5][5].path = 0;
console.log(b[5][5].path);

module.exports = {
	createArray : createArray,
	Create2DArray : Create2DArray,
	createMatrix : matrix
};