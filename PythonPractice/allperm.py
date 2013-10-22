def allperm(inputstr):
    for i in range(len(inputstr)):
        yield(inputstr[i])        
        for s in allperm(inputstr[:i] + inputstr[i+1:]):
            yield(inputstr[i] + s)


def base10toN(num, base):
    """Change ``num'' to given base
    Upto base 36 is supported."""

    converted_string, modstring = "", ""
    currentnum = num
    if not 1 < base < 37:
        raise ValueError("base must be between 2 and 36")
    if not num:
        return '0'
    while currentnum:
        mod = currentnum % base
        currentnum = currentnum // base
        converted_string = chr(48 + mod + 7*(mod > 10)) + converted_string
    return converted_string


