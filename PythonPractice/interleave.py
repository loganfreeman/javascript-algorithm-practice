def interleave(*args):
    for idx in range(0, max(len(arg) for arg in args)):
        for arg in args:
            try:
                yield arg[idx]
            except IndexError:
                continue


a = [1,3,5]
b = [2,4,6,8]
c = ["x","y","z"]


for x in interleave(a, b, c):
    print x
    
print list(interleave(a, b,c))

