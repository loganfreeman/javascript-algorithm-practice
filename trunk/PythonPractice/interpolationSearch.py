def interpolation_search(sortedArray, target):
    low, high, mid = 0, len(sortedArray) - 1, 0;
    while sortedArray[low] <= target and sortedArray[high] >= target:
        mid = low + (target - sortedArray[low]) * (high - low) / (sortedArray[high] - sortedArray[low]);
        if sortedArray[mid] < target:
            low = mid + 1;
        elif sortedArray[mid] > target:
            high = mid - 1;
        else:
            return mid;
    if(sortedArray[low] == target):
        return mid;
    else:
        return -1;

test = [3, 9, 12, 34, 66, 89];
print interpolation_search(test, 66)
        