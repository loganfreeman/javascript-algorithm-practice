#!/usr/bin/python
# -*- coding: UTF-8 -*-

"""
In Python 2.x, the metaclass hook is a static field in the class called __metaclass__. 
In the ordinary case, this is not assigned so Python just uses type to create the class. 
But if you define __metaclass__ to point to a callable, Python will call __metaclass__() after the initial creation of the class object, passing in the class object, the class name, the list of base classes and the namespace dictionary.

"""
class SimpleMeta1(type):   
    """
    By convention, when defining metaclasses cls is used rather than self as the first argument to all methods except __new__() (which uses mcl, for reasons explained later). cls is the class object that is being modified.
    """
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


class Simple2(object):
    class __metaclass__(type):
        def __init__(cls, name, bases, nmspc):
            # This won't work:
            # super(__metaclass__, cls).__init__(name, bases, nmspc)
            # Less-flexible specific call:
            # The compiler won’t accept the super() call because it says __metaclass__ hasn’t been defined, forcing us to use the specific call to type.__init__()
            type.__init__(cls, name, bases, nmspc)
            cls.uses_metaclass = lambda self : "Yes!"


simple = Simple2()
print simple.uses_metaclass()

class final(type):
    def __init__(cls, name, bases, namespace):
        super(final, cls).__init__(name, bases, namespace)
        print name
        for klass in bases:
            if isinstance(klass, final):
                raise TypeError(str(klass.__name__) + " is final")

class A(object):
    pass

class B(A):
    __metaclass__= final

# print B.__bases__
print isinstance(B, final)




"""

__new__ is called for the creation of a new class, while __init__ is called after the class is created, to perform additional initialization before the class is handed to the caller

"""

class Tag1: pass
class Tag2: pass
class Tag3:
    def tag3_method(self): pass

class MetaBase(type):
    def __new__(cls, name, bases, nmspc):
        print('MetaBase.__new__\n')
        return super(MetaBase, cls).__new__(cls, name, bases, nmspc)

    def __init__(cls, name, bases, nmspc):
        print('MetaBase.__init__\n')
        super(MetaBase, cls).__init__(name, bases, nmspc)

class MetaNewVSInit(MetaBase):
    def __new__(cls, name, bases, nmspc):
        # First argument is the metaclass ``MetaNewVSInit``
        print('MetaNewVSInit.__new__')
        for x in (cls, name, bases, nmspc): print(x)
        print('')
        # These all work because the class hasn't been created yet:
        if 'foo' in nmspc: nmspc.pop('foo')
        name += '_x'
        bases += (Tag1,)
        nmspc['baz'] = 42
        return super(MetaNewVSInit, cls).__new__(cls, name, bases, nmspc)

    def __init__(cls, name, bases, nmspc):
        # First argument is the class being initialized
        print('MetaNewVSInit.__init__')
        for x in (cls, name, bases, nmspc): print(x)
        print('')
        if 'bar' in nmspc: nmspc.pop('bar') # No effect
        name += '_y' # No effect
        bases += (Tag2,) # No effect
        nmspc['pi'] = 3.14159 # No effect
        super(MetaNewVSInit, cls).__init__(name, bases, nmspc)
        # These do work because they operate on the class object:
        cls.__name__ += '_z'
        cls.__bases__ += (Tag3,)
        cls.e = 2.718

class Test(object):
    __metaclass__ = MetaNewVSInit
    def __init__(self):
        print('Test.__init__')
    def foo(self): print('foo still here')
    def bar(self): print('bar still here')

t = Test()
print('class name: ' + Test.__name__)
print('base classes: ', [c.__name__ for c in Test.__bases__])
print([m for m in dir(t) if not m.startswith("__")])
t.bar()
print(t.e)