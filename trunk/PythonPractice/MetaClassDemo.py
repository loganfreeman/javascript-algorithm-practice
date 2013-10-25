import types

class Tracing:
    # The __init__ method is invoked when a new Tracing instance is created, e.g. the definition of class MyTracedClass later in the example.
    def __init__(self, name, bases, namespace):
        """Create a new class."""
        self.__name__ = name
        self.__bases__ = bases
        self.__namespace__ = namespace
    # The __call__ method is invoked to return an instance of the class Instance
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

Trace = Tracing('Trace', (), {})

class MyTracedClass(Trace):
    def method1(self, a):
        self.a = a
    def method2(self):
        return self.a

aninstance = MyTracedClass()

aninstance.method1(10)

print "the answer is %d" % aninstance.method2()