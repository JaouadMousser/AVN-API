 /* Copyright (C) 2016 Jaouad Mousser

 *
 *   AVN-API is a free software: you can redistribute it and/or modify
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

import java.io.File;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jaouad mousser 
 * @mail jaouad.mousser at gmail.com
 */
public final class AvnClass {
    
    private  Element avnClass = null;
    private String classID  = null;
    private String className = null;
    private final ArrayList<Member> members  = new ArrayList<>();
    private final ArrayList<ThemRole> themRoles  = new ArrayList<>();
    private final ArrayList<Frame> frames  = new ArrayList<>();
    private boolean hasMembers = false;
    private boolean hasThemRoles = false;
    private boolean hasFrames = false;
    private boolean hasSubClasses = false;
    private boolean hasSiblingClasses = false;
    private ArrayList<AvnClass> subClasses = new ArrayList<>();
    private ArrayList<AvnClass> siblingClasses = new ArrayList<>();
    private ArrayList<String> siblingClassesIDs = new ArrayList<>();
    private File AVNXML = null;
    
    public AvnClass(Element avnClass){
        this.avnClass = avnClass;
        initializeClass();
       }
    
    
    public AvnClass(File AVNXML){
        this.AVNXML = AVNXML;
        AvnParser avp = new AvnParser();
        Document doc = avp.parseXML(AVNXML);
        Element vnclassEl = doc.getDocumentElement();
        this.avnClass = vnclassEl;
        initializeClass();
        
    }
    
       
    
    
    
    private void initializeClass(){
         className = avnClass.getNodeName();
        classID = avnClass.getAttribute("ID");
        Element membersEl =  getChildElements(avnClass, "MEMBERS").get(0);
        if(membersEl.hasChildNodes()){
            hasMembers = true;
            NodeList memberChildren = membersEl.getElementsByTagName("MEMBER");
            for (int i =0; i<memberChildren.getLength(); i++){
                Element memEl = (Element) memberChildren.item(i);
                Member member  = new Member(memEl);
                members.add(member);
        }}
        
        ////////
        
        // GET THEMROLES
        
        Element themRolesEl = getChildElements(avnClass, "THEMROLES").get(0);
       
        
        if(themRolesEl.hasChildNodes()){
            hasThemRoles = true;
            NodeList themRoleChildren = themRolesEl.getElementsByTagName("THEMROLE");
            
            for(int e =0; e<themRoleChildren.getLength(); e++){
                Element themRolEl = (Element) themRoleChildren.item(e);
                ThemRole themRol = new ThemRole(themRolEl);
                themRoles.add(themRol);
                }
            
        }
        
        //// get fames 
        
        
        Element framesEl = getChildElements(avnClass, "FRAMES").get(0);
        if(framesEl.hasChildNodes()){
            hasFrames = true;
            NodeList frameChildren = framesEl.getElementsByTagName("FRAME");
            
            for(int e =0; e<frameChildren.getLength(); e++){
                Element frameEl = (Element) frameChildren.item(e);
                Frame frame = new Frame(frameEl);
                frames.add(frame);
                }    
    }
        
///
        
        Element subclassesEl = getChildElements(avnClass, "SUBCLASSES").get(0);
       
        if(subclassesEl.hasChildNodes()){
            hasSubClasses = true;
            NodeList subEls =subclassesEl.getElementsByTagName("VNSUBCLASS");
            for(int i =0; i<subEls.getLength(); i++){
                Element subClass = (Element) subEls.item(i);
                AvnClass avnSubClass = new AvnClass(subClass);
                subClasses.add(avnSubClass);
                
            }
        }
        
        
        ArrayList<Element> siblingEls = getChildElements(avnClass, "SIBLINGCLASSES");
        if(!siblingEls.isEmpty()){
         Element siblingEl = siblingEls.get(0);
        if(siblingEl.hasChildNodes()){
            hasSiblingClasses = true;
            NodeList sibEls = siblingEl.getElementsByTagName("SIBLINGCLASS");
            for(int i =0; i<sibEls.getLength(); i++){
                Element siblingClass = (Element) sibEls.item(i);
                siblingClassesIDs.add(siblingClass.getNodeName());   
            }
        }
        
        
        
    }
    }
    public ArrayList<Element> getChildElements(Element parentElement, String elementName){
        ArrayList<Element> childElements = new ArrayList<>();
        NodeList children = parentElement.getChildNodes();
        for(int i =0; i<children.getLength(); i++){
            Node child = children.item(i);
            if(child.getNodeType()==Node.ELEMENT_NODE && child.getNodeName().equals(elementName)){
                
                childElements.add((Element) child);
            }
        }
        
        return childElements;
        
    }
    
    
    
    
    
    
       public boolean hasMembers(){
            return hasMembers;
        }
        
       public boolean hasThemRoles(){
           return hasThemRoles;
       }
       
       public boolean hasFrames(){
           return hasFrames;
       }
       
       public boolean hasSubClasses(){
           return hasSubClasses;
       }
       public boolean hasSiblingClasses(){
           return hasSiblingClasses;
       }
       
       public ArrayList<Member> getMembers(){
           return members;
       }
       
       
       public ArrayList<ThemRole> getThemRoles(){
           return themRoles;
       }
       
       public ArrayList<Frame> getFrames(){
           return frames;
       }
    
       public String getClassID(){
           return classID;
       }
       
       public String getClassName(){
           return className;
       }
       
       public ArrayList<AvnClass> getSubClasses(){
           return subClasses;
       }
       
       public ArrayList<String> getSiblingClassIDs(){
           return siblingClassesIDs;
       }
       
   
}
