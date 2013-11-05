def insertSort(arr, size):
    i, key, j = 0, 0, 0;
    for i in range(1, size):
        key = arr[i];
        j = i - 1;
        while j >= 0 and arr[j] > key:
            arr[j+1] = arr[j];
            j = j - 1;
        arr[j+1] = key;


arr = [2, 4, 3, 6, 7,12, 11, 15, 14];
insertSort(arr, len(arr));
print arr;