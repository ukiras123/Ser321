SHELL = /bin/bash
CC = g++
CFLAGS = -g -ggdb -c -std=c++14
LDFLAGS = -g -ggdb -lstdc++
srcdir = src

PROG = fraction
HDRS = Fraction.hpp
SRCS = main.cpp Fraction.cpp

# Object files (.o) have the same name as corresponding source (.m) files
OBJS = $(SRCS:.cpp=.o)

# Build the fraction executable from the object files
$(PROG) : $(OBJS)
	$(CC) $(OBJS)  -o $(PROG) $(LDFLAGS)

# Compile source to create the object files
# use the default actions to compile
main.o : $(srcdir)/main.cpp $(srcdir)/Fraction.hpp $(srcdir)/Fraction.cpp
	$(CC) $(CFLAGS) $(srcdir)/Fraction.cpp $(srcdir)/main.cpp

Fraction.o : $(srcdir)/Fraction.hpp $(srcdir)/Fraction.cpp
	$(CC) $(CFLAGS) $(srcdir)/Fraction.hpp $(srcdir)/Fraction.cpp

# Remove compilation and debugging files
clean :
	rm -f core $(PROG) $(OBJS) src/Fraction.hpp.gch




