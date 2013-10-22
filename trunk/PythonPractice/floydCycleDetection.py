def floyd(f, x0):
    # The main phase of the algorithm, finding a repetition x_mu = x_2mu
    # The hare moves twice as quickly as the tortoise
    # Eventually they will both be inside the cycle 
    # and the distance between them will increase by 1 until
    # it is divisible by the length of the cycle.
    tortoise = f(x0) # f(x0) is the element/node next to x0.
    hare = f(f(x0))
    while tortoise != hare:
        tortoise = f(tortoise)
        hare = f(f(hare))
 
    # at this point the position of tortoise which is the distance between 
    # hare and tortoise is divisible by the length of the cycle. 
    # so hare moving in circle and tortoise (set to x0) moving towards 
    # the circle will intersect at the beginning of the circle.
 
    # Find the position of the first repetition of length mu
    # The hare and tortoise move at the same speeds
    mu = 0
    tortoise = x0
    while tortoise != hare:
        tortoise = f(tortoise)
        hare = f(hare)
        mu += 1
 
    # Find the length of the shortest cycle starting from x_mu
    # The hare moves while the tortoise stays still
    lam = 1
    hare = f(tortoise)
    while tortoise != hare:
        hare = f(hare)
        lam += 1
 
    return lam, mu


def brent(f, x0):
    # main phase: search successive powers of two
    power = lam = 1
    tortoise = x0
    hare = f(x0)  # f(x0) is the element/node next to x0.
    while tortoise != hare:
        if power == lam:  # time to start a new power of two?
            tortoise = hare
            power *= 2
            lam = 0
        hare = f(hare)
        lam += 1
 
    # Find the position of the first repetition of length lambda
    mu = 0
    tortoise = hare = x0
    for i in range(lam):
    # range(lam) produces a list with the values 0, 1, ... , lam-1
        hare = f(hare)
    while tortoise != hare:
        tortoise = f(tortoise)
        hare = f(hare)
        mu += 1
 
    return lam, mu