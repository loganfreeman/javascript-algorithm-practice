"""
Example of setting up a state machine using the EasyStatePattern
module.
"""
import StateMachine as esp
import math


class Parent(object):
    """ the context for the state machine class """
    moodTable = esp.StateTable('mood')

    def __init__(self, pocketbookCash, piggybankCash):
      """Instance initializer must invoke the initialize method of the StateTable """
      Parent.moodTable.initialize( self)
      self.pocketBook = pocketbookCash
      self.piggyBank = piggybankCash

    """The Transiton decorator defines a method which causes transitions to other
    states"""
    @esp.Transition(moodTable)
    def getPromotion(self): pass

    @esp.Transition(moodTable)
    def severalDaysPass(self): pass


    """The Event decorator defines a method whose exact method will depend on the
    current state. These normally do not cause a state transition.
    For this example, this method will return an amount of money that the asker is to receive,
    which will depend on the parent's mood, the amount asked for, and the availability of
    money."""
    @esp.Event(moodTable)
    def askForMoney(self, amount): pass

    """The TransitionEvent decorator acts like a combination of both the Transition
    and Event decorators.  Calling this causes a transition(if defined in the state
    table) and the called function is state dependant."""
    @esp.TransitionEvent(moodTable)
    def cashPaycheck(self, amount): pass

    @esp.TransitionEvent(moodTable)
    def getUnexpectedBill(self, billAmount ): pass

    """onEnter is called when entering a new state. This can be overriden in
    the individual state classes.  onLeave (not defined here) is called upon
    leaving a state"""
    def onEnter(self):
      print 'Mood is now', self.mood.name()

# 
# Below are defined the states. Each state is a class, States may be derived from
# other states. Topmost states must have a __metaclass__ = stateclass( state_machine_class )
# declaration.
#
class Normal( Parent ):
    __metaclass__ = esp.stateclass( Parent )
    """Normal becomes the name of the state."""

    def getPromotion(self): 
        """This shouldn't get called, since get was defined as a transition in
        the Parent context"""
        pass

    def severalDaysPass(self): 
       """neither should this be called."""
       pass

    def askForMoney(self, amount): 
        amountToReturn = min(amount, self.pocketBook )
        self.pocketBook -= amountToReturn
        if  40.0 < self.pocketBook:
            print 'Here you go, sport!'
        elif 0.0 < self.pocketBook < 40.00:
            print "Money doesn't grow on trees, you know."
        elif 0.0 < amountToReturn < amount:
            print "Sorry, honey, this is all I've got."
        elif amountToReturn == 0.0:
            print "I'm broke today ask your aunt."
            self.mood.nextState = Broke
        return amountToReturn

    def cashPaycheck(self, amount): 
        self.pocketBook += .7 * amount
        self.piggyBank += .3*amount

    def getUnexpectedBill(self, billAmount ): 
        amtFromPktBook = min(billAmount, self.pocketBook)
        rmngAmt = billAmount - amtFromPktBook
        self.piggyBank -= rmngAmt
        self.pocketBook -= amtFromPktBook
        

class Happy( Parent ):
    __metaclass__ = esp.stateclass( Parent )
    """Grouchy becomes the name of the state."""

    def askForMoney(self, amount): 
        availableMoney = self.pocketBook + self.piggyBank
        amountToReturn = max(min(amount, availableMoney), 0.0)
        amountFromPktbook =  min(amountToReturn, self.pocketBook)
        self.pocketBook -= amountFromPktbook
        self.piggyBank -= (amountToReturn - amountFromPktbook)

        if 0.0 < amountToReturn < amount:
            print "Sorry, honey, this is all I've got."
        elif amountToReturn == 0.0:
            print "I'm broke today ask your aunt."
            self.mood.nextState = Broke
        else:
            print 'Here you go, sport!'
        return amountToReturn

    def cashPaycheck(self, amount): 
        self.pocketBook += .75 * amount
        self.piggyBank += .25*amount

    def getUnexpectedBill(self, billAmount ): 
        print "why do these things always happen?"
        amtFromPktBook = min(billAmount, self.pocketBook)
        rmngAmt = billAmount - amtFromPktBook
        self.piggyBank -= rmngAmt
        self.pocketBook -= amtFromPktBook
        
    def onEnter(self):
      print 'Yippee! Woo Hoo!', self.mood.name()*3

class Grouchy( Parent ):
    __metaclass__ = esp.stateclass( Parent )
    """Grouchy becomes the name of the state."""

    def askForMoney(self, amount): 
       print """You're always spending too much. """
       return 0.0

    def cashPaycheck(self, amount): 
        self.pocketBook += .70 * amount
        self.piggyBank += .30*amount

    def getUnexpectedBill(self, billAmount ): 
        print 'These things always happen at the worst possible time!'

        amtFromPktBook = min(billAmount, self.pocketBook)
        rmngAmt = billAmount - amtFromPktBook
        self.piggyBank -= rmngAmt
        self.pocketBook -= amtFromPktBook
        

class Broke( Normal ):
    #   __metaclass__ = esp.stateclass( Parent )
    """ No metaclass declaration as its as subclass of Grouchy. """

    def cashPaycheck(self, amount): 
        piggyBankAmt = min ( amount, max(-self.piggyBank, 0.0))
        rmngAmount = amount - piggyBankAmt
        self.pocketBook += .40 * rmngAmount
        self.piggyBank += (.60 * rmngAmount + piggyBankAmt)

    def askForMoney(self, amount): 
        amountToReturn = min(amount, self.pocketBook )
        self.pocketBook -= amountToReturn
        if  40.0 < self.pocketBook:
            print 'Here you go, sport!'
        elif 0.0 < self.pocketBook < 40.00:
            print "Spend it wisely."
        elif 0.0 < amountToReturn < amount:
            print "This is all I've got."
        elif amountToReturn == 0.0:
            print "Sorry, honey, we're broke."
            self.mood.nextState = Broke
        return amountToReturn


# After defining the states, The following lines set up the transitions.
# We've set up four transitioning methods, 
# getPromotion, severalDaysPass, cashPaycheck, getUnexpectedBill 
# Therefore there are four states in each list, the ordering of the states in the
# list corresponds toorder that the transitioning methods were defined.

Parent.moodTable.nextStates( Normal, ( Happy, Normal, Normal, Grouchy ))
Parent.moodTable.nextStates( Happy, ( Happy, Happy, Happy, Grouchy ))
Parent.moodTable.nextStates( Grouchy, ( Happy, Normal, Grouchy, Grouchy ))
Parent.moodTable.nextStates( Broke, ( Normal, Broke, Grouchy, Broke ))

# This specifies the initial state. Instances of the Parent class are placed
# in this state when they are initialized.
Parent.moodTable.initialstate = Normal


def Test():
    dad = Parent(50.0, 60.0)
    amount = 20.0
    print amount, dad.askForMoney(amount)
    print amount, dad.askForMoney(amount)
    dad.cashPaycheck( 40.0)
    print amount, dad.askForMoney(amount)
    dad.cashPaycheck( 40.0)
    dad.severalDaysPass()
    print amount, dad.askForMoney(amount)
    dad.getUnexpectedBill(100.0 )
    print amount, dad.askForMoney(amount)
    dad.severalDaysPass()
    print amount, dad.askForMoney(amount)
    dad.severalDaysPass()
    dad.cashPaycheck( 100.0)
    print amount, dad.askForMoney(amount)
    dad.cashPaycheck( 50.0)
    print amount, dad.askForMoney(amount)
    dad.getPromotion()
    dad.cashPaycheck( 200.0)
    amount = 250
    print amount, dad.askForMoney(amount)

Test()
