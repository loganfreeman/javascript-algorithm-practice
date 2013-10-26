"""
In Python 2.x, the metaclass hook is a static field in the class called __metaclass__. 
In the ordinary case, this is not assigned so Python just uses type to create the class. 
But if you define __metaclass__ to point to a callable, Python will call __metaclass__() after the initial creation of the class object, passing in the class object, the class name, the list of base classes and the namespace dictionary.

"""
class SimpleMeta1(type):
    def __init__(cls, name, bases, nmspc):
        super(SimpleMeta1, cls).__init__(name, bases, nmspc)
        cls.uses_metaclass = lambda self : "Yes!"

class Simple1(object):
    __metaclass__ = SimpleMeta1
    def foo(self): pass
    @staticmethod
    def bar(): pass

simple = Simple1()
print([m for m in dir(simple) if not m.startswith('__')])
# A new method has been injected by the metaclass:
print simple.uses_metaclass()