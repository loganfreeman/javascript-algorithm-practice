#!/usr/bin/python
# -*- coding: UTF-8 -*-
"""
When it sees a class definition, Python executes it to collect the attributes (including methods) into a dictionary.
When the class definition is over, Python determines the metaclass of the class. Letâs call it Meta.
Eventually, Python executes Meta(name, bases, dct), where:
1. Meta is the metaclass, so this invocation is instantiating it.
2. name is the name of the newly created class
3. bases is a tuple of the classâs base classes
4. dct maps attribute names to objects, listing all of the classâs attributes

if either a class or one of its bases has a __metaclass__ attribute, itâs taken as the metaclass. Otherwise, type is the metaclass.
To control the creation and initialization of the class in the metaclass, you can implement the metaclass’s __new__ method and/or __init__ constructor.

__new__ should be implemented when you want to control the creation of a new object (class in our case), 
and __init__ should be implemented when you want to control the initialization of the new object after it has been created.
"""
import types

class Tracing:
    # The __init__ method is invoked when a new Tracing instance is created, e.g. the definition of class MyTracedClass later in the example.
    def __init__(self, name, bases, namespace):
        """Create a new class."""
        self.__name__ = name
        self.__bases__ = bases
        self.__namespace__ = namespace
    # The __call__ method is invoked to return an instance of the class Instance
    # __call__ is called when the already-created class is "called" to instantiate a new object. 
    # By overriding __call__() in the metaclass, the creation of instances are intercepted. Instance creation is bypassed if one already exists.
    def __call__(self):
        """Create a new instance."""
        return Instance(self)

    
    
class Instance:
    def __init__(self, klass):
        self.__klass__ = klass
    def __getattr__(self, name):
        try:
            value = self.__klass__.__namespace__[name]
        except KeyError:
            raise AttributeError, name
        if type(value) is not types.FunctionType:
            return value
        return BoundMethod(value, self)

class BoundMethod:
    def __init__(self, function, instance):
        self.function = function
        self.instance = instance
    def __call__(self, *args):
        print "calling", self.function, "for", self.instance, "with", args
        return apply(self.function, (self.instance,) + args)


# Tracing is the metaclass of Trace
Trace = Tracing('Trace', (), {})

class MyTracedClass(Trace):
    def method1(self, a):
        self.a = a
    def method2(self):
        return self.a

aninstance = MyTracedClass()

aninstance.method1(10)

print "the answer is %d" % aninstance.method2()

print Trace.__name__
print Trace.__bases__
print Trace.__namespace__

def howdy(self, you):
    print("Howdy, " + you)

MyList = type('MyList', (list,), dict(x=42, howdy=howdy))

ml = MyList()
ml.append("Camembert")
print(ml)
print(ml.x)
ml.howdy("John")


