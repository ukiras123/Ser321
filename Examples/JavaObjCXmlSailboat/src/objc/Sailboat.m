#import "Sailboat.h"
#import <math.h>

// to see debug messages, change NO below to YES
#define DEBUGON NO

/**
 * An Objective-C class defining information about a sailboat.
 * Sailboat objects are serializable to/from Xml in a format compatible with
 * Java's beans xml decoder/encoder. This class implements methods supporting
 * sailboat properties, the xml parser delegate (to construct a Sailboat object
 * from an xml file), and a methods to write the sailboat object to xml.
 *
 * Cst502 Emerging Languages and Programming Technologies
 * see http://pooh.poly.asu.edu/Cst502
 * @author Tim Lindquist (Tim@asu.edu), ASU Polytechnic Engineering
 * @version December 2011
 */
@implementation Sailboat

- (id) initWithModel: (NSString*) aModel
               name: (NSString*) aName
               beam: (double) aBeam
       displacement: (double) aDisplacement
             lengthWL: (double) aLengthWL {
   if ((self = [super init])){
      [model autorelease];
      model = [aModel retain];
      [name autorelease];
      name = [aName retain];
      beam = aBeam;
      displacement = aDisplacement;
      lengthWL = aLengthWL;
   }
   return self;
}

- (id) initFromXMLFile: (NSString*) aFile {
   if ( (self = [super init])){
      [self startWithFileName: aFile];
   }
   return self;
}

- (NSString*) model {
   return model;
}
- (void) setModel: (NSString*) aModel {
   [model autorelease];
   model = [aModel retain];
}
- (NSString*) name{
   return name;
}
- (void) setName: (NSString*) aName {
   [name autorelease];
   name = [aName retain];
}
- (double) beam {
   return beam;
}
- (void) setBeam: (double) aBeam {
   beam = aBeam;
}
- (double) displacement {
   return displacement;
}
- (void) setDisplacement: (double) aDisplacement {
   displacement = aDisplacement;
}
- (double) lengthWL {
   return lengthWL;
}
- (void) setLengthWL: (double) aLengthWL {
   lengthWL = aLengthWL;
}

- (double) dOverL {
   return (displacement / 2240.0) / (pow(0.01*lengthWL,3.0));
}

- (void) print {
   printf("Sailboat %s is an %s with d/l %6.2f\n",
          [name UTF8String], [model UTF8String], [self dOverL]);
}

- (void)dealloc {
   [model release];
   [name release];
   [currentElement release];
   [currentProperty release];
   [xmlParser release];
   [super dealloc];
}

- (void)startWithFileName:(NSString*)fileName {
   //The simplest to read the file is with the following construction, but this
   //does not lend itself to getting the xml string from a socket connection.
   //So, for reading we get an xmlData object.
   //NSString * xmlStr = [NSString stringWithContentsOfFile: fileName
   //                                encoding: NSUTF8StringEncoding error: nil];
   NSFileHandle* fh = [NSFileHandle fileHandleForReadingAtPath: fileName];
   NSData* xmlData = [fh availableData];
   NSString * xmlStr = [[[NSString alloc] initWithData:xmlData
                                    encoding:NSUTF8StringEncoding] autorelease];
   [self debug:[NSString stringWithFormat: @"Xml file contains:\n%s\n",
                                           [xmlStr UTF8String]]];
   xmlParser = [[NSXMLParser alloc] initWithData:
                               [xmlStr dataUsingEncoding:NSUTF8StringEncoding]];
   [xmlParser setDelegate:self];
   [xmlParser setShouldProcessNamespaces:NO];
   [xmlParser setShouldReportNamespacePrefixes:NO];
   [xmlParser setShouldResolveExternalEntities:NO];
   [xmlParser parse];
}

#pragma mark NSXMLParserDelegate methods

- (void)parserDidStartDocument:(NSXMLParser *)parser {
//    NSLog(@"Document started", nil);
    currentElement = nil;
}

- (void)parser:(NSXMLParser *)parser parseErrorOccurred:(NSError *)parseError {
    NSLog(@"Error: %@", [parseError localizedDescription]);
}

- (void)parser:(NSXMLParser *)parser
                       didStartElement:(NSString *)elementName 
                       namespaceURI:(NSString *)namespaceURI 
                      qualifiedName:(NSString *)qName 
                         attributes:(NSDictionary *)attributeDict {
    [currentElement release];
    currentElement = [elementName copy];
    [self debug:[NSString stringWithFormat:
                              @"In didStartElement for element: %s\n",
                              [currentElement UTF8String]]];
    NSEnumerator * myEnum = [attributeDict keyEnumerator];
    NSString *thisObject;
    [self debug:[NSString stringWithFormat:@"Attributes are:\n"]];
    while ((thisObject = [myEnum nextObject])) {
       if([thisObject isEqualToString:@"property"]){
          [currentProperty release];
          currentProperty = [[attributeDict valueForKey:thisObject] copy];
          [self debug:[NSString stringWithFormat:
                                     @"found attribute for property: %s\n",
                                     [currentProperty UTF8String]]];
       }
       [self debug:[NSString stringWithFormat:@"name: %s value: %s\n",
                        [thisObject UTF8String],
                        [[attributeDict valueForKey:thisObject] UTF8String]]];
    }
}

- (void)parser:(NSXMLParser *)parser
                             didEndElement:(NSString *)elementName 
                              namespaceURI:(NSString *)namespaceURI 
                             qualifiedName:(NSString *)qName {
    [self debug:[NSString stringWithFormat:
                                         @"In didEndElement for element: %s\n",
                                         [elementName UTF8String]]];
    if([elementName isEqualToString:@"double"] || 
       [elementName isEqualToString:@"string"]) {
       [currentProperty release];
       currentProperty = nil;
    }
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string {
    [self debug:[NSString stringWithFormat:@"In foundCharacters, found: %s\n",
                                           [string UTF8String]]];
    if (string == nil){
       printf("characters found is empty\n");
    }else if([currentProperty isEqualToString:@"beam"]){
       beam = [string doubleValue];
    }else if([currentProperty isEqualToString:@"displacement"]){
       displacement = [string doubleValue];
    }else if([currentProperty isEqualToString:@"lengthWL"]){
       lengthWL = [string doubleValue];
    }else if([currentProperty isEqualToString:@"model"]){
       [model release];
       model = [string copy];
    }else if([currentProperty isEqualToString:@"name"]){
       [name release];
       name = [string copy];
    }
}

- (void)parserDidEndDocument:(NSXMLParser *)parser {
   [self debug:[NSString stringWithFormat:@"Document parse completed.\n"]];
}

- (void)writeSailboatToXmlFile:(NSString*)fileName {
   NSDictionary* aDict = [NSDictionary dictionaryWithObjectsAndKeys:
            [NSArray arrayWithObjects:@"double",
                     [NSString stringWithFormat:@"%f",beam],nil],
            @"beam",
            [NSArray arrayWithObjects:[@"double" copy],
                     [NSString stringWithFormat:@"%f",displacement],nil],
            @"displacement",
            [NSArray arrayWithObjects:@"double",
                     [NSString stringWithFormat:@"%f",lengthWL],nil],
            @"lengthWL",
            [NSArray arrayWithObjects:@"string",model,nil],
            @"model",
            [NSArray arrayWithObjects:@"string",name,nil],
            @"name",nil];
   NSData *xmlData = [self createXMLDocumentFromDict:aDict];
   [xmlData writeToFile:fileName atomically:YES];
}

/* method to create xmlDocument corresponding to this sailboat instance.
 * note the document is created in a format that's readable by Java's xml
 * bean decoder. Two versions of this method; one for Mac OS X and the other
 * for Windows GNUstep ver 0.25. GNUStep has problems with xml documents
 * The resulting xml document should look something like:
 * <?xml version="1.0" encoding="UTF-8"?> 
 * <java version="1.6.0_22" class="java.beans.XMLDecoder"> 
 *  <object class="cst502.objcXml.Sailboat"> 
 *   <void property="beam"> 
 *    <double>12.0</double> 
 *   </void> 
 *   <void property="displacement"> 
 *    <double>17500.0</double> 
 *   </void> 
 *   <void property="lengthWL"> 
 *    <double>30.0</double> 
 *   </void> 
 *   <void property="model"> 
 *    <string>IP35</string> 
 *   </void> 
 *   <void property="name"> 
 *    <string>FirstStar</string> 
 *   </void> 
 *  </object> 
 * </java> 
 */
#if !defined(WINGS)
- (NSData*)createXMLDocumentFromDict:(NSDictionary*)propDict {
   NSData *xmlData;
   // java xml decoder XML format has "java" as root element.
   NSXMLElement *root = (NSXMLElement *)[NSXMLNode elementWithName:@"java"];
   //set up generic XML doc data (<?xml version="1.0" encoding="UTF-8"?>)
   NSXMLDocument *xmlDoc = [[NSXMLDocument alloc] initWithRootElement:root];
   [xmlDoc setVersion:@"1.0"];
   [xmlDoc setCharacterEncoding:@"UTF-8"];
   //set Java version number to 1.6.0_22    
   [root addAttribute:[NSXMLNode attributeWithName:@"version"
                                       stringValue:@"1.6.0_22"]];
   //add the class attribute
   [root addAttribute:[NSXMLNode attributeWithName:@"class"
                                       stringValue:@"java.beans.XMLDecoder"]];
   //create the object element for a sailboat object
   NSXMLElement *objNode = [NSXMLNode elementWithName:@"object"];
   //add the class attribute to the sailboat object
   [objNode addAttribute:[NSXMLNode attributeWithName:@"class"
                                       stringValue:@"cst502.objcXml.Sailboat"]];
   //add the object node to the document root
   [root addChild:objNode];
   /* use the dictionary parameter to add an element for each object property:
    * <void property="beam">
    *   <double>12.0</double>
    * </void>
    * dictionary keys are property names, value is an array with two strings
    * type and value. Dictionary entry for above is:
    *    key:"beam"; value:{"double","12.0"}
    */
    NSEnumerator * myEnum = [propDict keyEnumerator];
    NSString *thisObject;
    while ((thisObject = [myEnum nextObject])) {
       NSArray* typeNValue = [propDict valueForKey:thisObject];
       NSXMLElement *voidNode = [NSXMLNode elementWithName:@"void"];
       //add the property attribute to the node
       [voidNode addAttribute:[NSXMLNode attributeWithName:@"property"
                                               stringValue:thisObject]];
       [voidNode addChild:[NSXMLNode
                                 elementWithName:[typeNValue objectAtIndex:0]
                                     stringValue:[typeNValue objectAtIndex:1]]];
       [objNode addChild:voidNode];
    }
   xmlData = [xmlDoc XMLDataWithOptions:NSXMLNodePrettyPrint];
    return xmlData;
}
#else
- (NSData*)createXMLDocumentFromDict:(NSDictionary*)propDict {
   NSData *xmlData;
   // xmldocument not completely implemented in GNUStep. Use brute force.
   NSMutableString* doc = [NSMutableString stringWithFormat:@"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<java version=\"1.6.0_22\" class=\"java.beans.XMLDecoder\">\n<object class=\"cst502.objcXml.Sailboat\">\n"];
   NSEnumerator * myEnum = [propDict keyEnumerator];
   NSString *thisObject;
   while ((thisObject = [myEnum nextObject])) {
      NSArray* typeNValue = [propDict valueForKey:thisObject];
      [doc appendString:[[self emitBeginElement:@"void"
                                           attr:@"property"
                                          value:thisObject] copy]];
      [doc appendString:@"\n"];
      [doc appendString:[self emitElement:[typeNValue objectAtIndex:0]
                                   string:[typeNValue objectAtIndex:1]]];
      [doc appendString:@"\n"];
      [doc appendString:[self emitEndElement:@"void"]];
      [doc appendString:@"\n"];
    }
    [doc appendString:[self emitEndElement:@"object"]];
    [doc appendString:@"\n"];
    [doc appendString:[self emitEndElement:@"java"]];
    [doc appendString:@"\n"];
    xmlData = [doc dataUsingEncoding:NSUTF8StringEncoding];
    return xmlData;
}
#endif

- (NSString*) emitBeginElement:(NSString*) elementName
                          attr:(NSString*) anAttributeName
                          value:(NSString*) anAttributeValue {
   NSString* ret = [NSString stringWithFormat:@"<%s %s=\"%s\">",
                       [elementName UTF8String], [anAttributeName UTF8String],
                       [anAttributeValue UTF8String]];
   return ret;
}

- (NSString*) emitElement: (NSString*) elementName
                   string: (NSString*) inLineValue {
   NSString* ret = [NSString stringWithFormat:@"<%s>%s</%s>",
                        [elementName UTF8String], [inLineValue UTF8String],
                        [elementName UTF8String]];
   return ret;
}

- (NSString*) emitEndElement: (NSString*) elementName {
   NSString* ret = [NSString stringWithFormat:@"</%s>",
                                                      [elementName UTF8String]];
   return ret;
}

- (void) debug: (NSString*) aMessage{
   if(DEBUGON){
      printf("debug: %s\n", [aMessage UTF8String]);
   }
}
@end
