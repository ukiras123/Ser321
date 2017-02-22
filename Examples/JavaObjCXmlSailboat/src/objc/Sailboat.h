#import <Foundation/Foundation.h>
#import <Foundation/NSXMLElement.h>

/**
 * An Objective-C class defining information about a sailboat.
 * Sailboat objects are serializable to/from Xml in a format compatible with
 * Java's beans xml decoder/encoder. This class implements methods supporting
 * sailboat properties, the xml parser delegate (to construct a Sailboat object
 * from an xml file), and a methods to write the sailboat object to xml.
 *
 * Here are some sample boats:
 * Contessa32 Ceres 9.5 9500 24.0
 * Ericson26 GrandCru 9.25 5250 21.9
 * Ericson32 BlueLagoon 10.83 9800 25.83
 * IP35 FirstStar 12.0 17500.0 30.0   (model is IslandPacket)
 * IP29 Wanderer 10.83 11900 25.58
 *
 * Cst502 Emerging Languages and Programming Technologies
 * see http://pooh.poly.asu.edu/Cst502
 * @author Tim Lindquist (Tim@asu.edu), ASU Polytechnic Engineering
 * @version December 2011
 */
// GNUstep on windows leaves the NSXMLParserDelegate as an informal protocol.
#if !defined(WINGS)
// for Mac OS X
@interface Sailboat : NSObject <NSXMLParserDelegate> {
#else
// for windows GNUstep
@interface Sailboat : NSObject {
#endif
@private
   //Sailboat properties
   NSString * model, * name;
   double beam, displacement, lengthWL;
   // instance variables that support parsing xml
   NSXMLParser *xmlParser;
   NSString *currentElement;
   NSString *currentProperty;
}
- (id) initWithModel: (NSString*) aModel
               name: (NSString*) aName
               beam: (double) aBeam
       displacement: (double) aDisplacement
             lengthWL: (double) aLengthWL;
- (id) initFromXMLFile: (NSString*) aFile;
- (NSString*) model;
- (void) setModel: (NSString*) aModel;
- (NSString*) name;
- (void) setName: (NSString*) aName;
- (double) beam;
- (void) setBeam: (double) aBeam;
- (double) displacement;
- (void) setDisplacement: (double) aDisplacement;
- (double) lengthWL;
- (void) setLengthWL: (double) aLengthWL;
- (double) dOverL;
- (void) print;
// These methods support reading from and writing to xml.
// Some should really be defined as private to the implemention.
- (NSData*)createXMLDocumentFromDict:(NSDictionary*)propDict; 
- (void) writeSailboatToXmlFile:(NSString*) fileName;
- (void)startWithFileName:(NSString*)fileName;
// The three emit methods are used by the brute force xml writer for
// GNUstep. The resulting strings are appended onto a mutable string
// that contains the sailboat's xml to be written to file.
- (NSString*) emitBeginElement:(NSString*) elementName
                          attr:(NSString*) anAttributeName
                         value:(NSString*) anAttributeValue;
- (NSString*) emitElement: (NSString*) elementName
                   string: (NSString*) inLineValue;
- (NSString*) emitEndElement: (NSString*) elementName;

- (void) debug: (NSString*) aMessage;
@end
