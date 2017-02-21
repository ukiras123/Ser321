#include "User.hpp"
#include <string>
#include <map>
#include <vector>

using namespace std;

/**
 * Copyright 2015 Tim Lindquist,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Purpose: demonstrate Json serialization and deserialization in C++
 * Ser321 Foundations of Distributed Applications
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version July 2015
 */
class Group {

protected:
   string name;
   std::map<std::string, User> users;

public:
   Group();
   ~Group();

   void initGroupFromJsonFile(string jsonFileName);
   void toJsonFile(string jsonFileName);
   void add(User usr);
   void remove(User usr);
   User get(string usrName);
   std::vector<string> getNames();
   void printGroup();
};
