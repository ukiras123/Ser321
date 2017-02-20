
//#import <Foundation/Foundation.h>
#import <Foundation/NSFileHandle.h>
#import <Foundation/NSAutoreleasePool.h>
#import <Foundation/NSString.h>
#import <Foundation/NSData.h>
#import <Foundation/NSArray.h>
#import <Foundation/NSArchiver.h>
#import <stdio.h>
#import "Sailboat.h"

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/**
 * Purpose: demonstrate reading and writing XML in Objective-C .
 * Cst502 Emerging Languages and Programming Technologies
 * see http://pooh.poly.asu.edu/Cst502
 * @author Tim Lindquist (Tim@asu.edu), ASU Polytechnic Engineering
 * @version December 2011
 */
NSString* readALine() {     
      char buf[256];
      char *str;
      int len;
      //printf("Input string: ");
      fgets(buf, sizeof(buf), stdin);
      len = strlen(buf);
      /* remove new line character */
      if (buf[len - 1] == '\n')
            buf[len - 1] = 0;
      str = (char *)calloc(len, sizeof(char));
      strcpy(str, buf);
      //printf("Your string: %s\n", str);
      NSString* aNSString = [NSString stringWithUTF8String: str];
      free(str);
      return aNSString;
}

int main( int argc, const char *argv[] ) {
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    // create a new instance
    Sailboat *sb;
    printf("About to create the dictionary.\n");
    NSMutableDictionary *env=[[[NSMutableDictionary alloc] init] autorelease];
    printf("The dictionary for storing boat information is created.\n" );
    printf("Enter one of the following:\nnew model name beam disp lengthWL\nread name\nwrite name\nprint name\nlist\nend\n>");
    fflush (stdout);
    NSString* aModel, * aName;
    double aBeam, aDisp, aLength;
    NSString* inputString = readALine();
    //printf("finished reading the line");
    NSString* newText = @"new";
    NSString* readText = @"read";
    NSString* writeText = @"write";
    NSString* printText = @"print";
    NSString* listText = @"list";
    NSString* endText = @"end";
    NSArray *chunks = [inputString componentsSeparatedByString: @" "];
    while ( ![[chunks objectAtIndex:0] isEqualToString: endText] ) {
       NSString* nextWord = [chunks objectAtIndex:0];
       if([nextWord isEqualToString: newText]){
          aModel = [chunks objectAtIndex: 1];
          aName = [chunks objectAtIndex: 2];
          aBeam = [[chunks objectAtIndex: 3] doubleValue];
          aDisp = [[chunks objectAtIndex: 4] doubleValue];
          aLength = [[chunks objectAtIndex: 5] doubleValue];
          sb = [[[Sailboat alloc] initWithModel: aModel name: aName beam: aBeam
                               displacement: aDisp
                                   lengthWL: aLength] autorelease];
          [env setObject:sb forKey: aName];
          [sb print];
       }else if([nextWord isEqualToString: readText]){
          NSString * fileName = [NSString stringWithFormat:@"%s.xml",
                                     [[chunks objectAtIndex: 1] UTF8String]];
          sb = [[Sailboat alloc] initFromXMLFile:fileName];
          [env setObject:sb forKey: [sb name]];
          [sb print];
       }else if([nextWord isEqualToString: writeText]){
          NSString * fileName = [NSString stringWithFormat:@"%s.xml",
                                     [[chunks objectAtIndex: 1] UTF8String]];
          sb = [env valueForKey:[chunks objectAtIndex:1]];
          [sb writeSailboatToXmlFile:fileName];
       }else if([nextWord isEqualToString: printText]){
          sb = [env valueForKey:[chunks objectAtIndex:1]];
          [sb print];
       }else if([nextWord isEqualToString: listText]){
          NSEnumerator * myEnum = [env keyEnumerator];
          NSString *thisObject;
          printf("Sailboats I know about are:");
          while ((thisObject = [myEnum nextObject])) {
             printf(" %s ", [thisObject UTF8String]);
          }
          printf("\n");
       }
       printf( "> " );
       fflush (stdout);
       inputString = readALine();
       chunks = [inputString componentsSeparatedByString: @" "];
    }
    printf("exiting\n");
    //free memory
    [pool release];
    return 0;
}


