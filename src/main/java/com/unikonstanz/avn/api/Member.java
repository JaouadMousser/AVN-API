 /* Copyright (C) 2016 Jaouad Mousser

 *
 *   AVN-API is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


package com.unikonstanz.avn.api;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author jaouad mousser 
 * @mail jaouad.mousser at gmail.com
 */
public class Member {
    
    private Element member = null;
    private Element hostClass= null;
    private String verb  = null;
    private String root = null;
    private ArrayList<String> deverbals = new ArrayList<>();
    private ArrayList<String> participles = new ArrayList<>();
    
    
    public Member(Element member){
        this.member  =  member;
        hostClass = (Element) member.getParentNode().getParentNode();
        verb = member.getAttribute("name");
            NodeList  children  = member.getElementsByTagName("*");
            for(int i = 0; i<children.getLength(); i++){
                Element child = (Element) children.item(i);
                String nodeName = child.getNodeName();
                switch (nodeName) {
                    case "Root":
                        root = child.getAttribute("name");
                        break;
                    case "Deverbal":
                        deverbals.add(child.getAttribute("name"));
                        break;
                    case "Active_participle":
                        participles.add(child.getAttribute("name"));
                        break;
                    default:
                        break;
                }
            
                    
            
        }
  
    }
    
    public AvnClass getHostClass(){
        AvnClass hc = new AvnClass(hostClass);
        return hc;
    }
    
    public String getVerb(){
        return verb;
    }
    
    public String getRoot(){
        return root;
    }
    
    public ArrayList<String> getDeverbals(){
        return deverbals;
    }
    
   
    public ArrayList<String> getParticiples(){
        return participles;
    }
    
   
    
    
}
