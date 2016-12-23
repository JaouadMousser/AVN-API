
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

import java.io.File;
import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.*;




/**
 *
 * @author jaouad mousser 
 * @mail jaouad.mousser at gmail.com
 */
public final class AvnParser {
    private String entry = null;
    private ArrayList<AvnClass> AvnClasses = new ArrayList();
    
   // private AvnClass hostClass = null;
    private SEARCH_BY cat = null;
    private String className = null;
    
    public AvnParser(){
        File avnrepo = new File("src/main/resources/Arabic_verbnet41"); 
        File[] files = avnrepo.listFiles();
        for(File file: files){
            
        if(file.getName().endsWith(".xml")){
            Document avnDoc = parseXML(file);
            System.out.println(file.getName());
            Element avnclass = avnDoc.getDocumentElement();
            AvnClasses.add(new AvnClass(avnclass));
        }
            
        }
    }
    
    
   public  AvnParser(String entry){
        this.entry = removeDiac(entry.trim());
        File avnrepo = new File("src/main/resources/Arabic_verbnet41"); 
        File[] files = avnrepo.listFiles();
        for(File file: files){
            
        if(file.getName().endsWith(".xml")){
       
        Document avnDoc = parseXML(file);
  
                
                Element avnclass = avnDoc.getDocumentElement();
                NodeList members = avnclass.getElementsByTagName("MEMBER");
                for(int i =0; i<members.getLength(); i++){
                    Element membEl = (Element) members.item(i);
                    Member mem = new Member(membEl);
                    String verb = mem.getVerb();
                    if(removeDiac(verb).equals(entry)){
                         // hostClass = mem.getHostClass();
                          AvnClasses.add(new AvnClass(avnclass));
                          break;
                        
                    
                }
                }}
    
   }}


   
   
    public  AvnParser(String entry, SEARCH_BY cat){
        this.entry = removeDiac(entry.trim());
        this.cat = cat;
        File avnrepo = new File("src/main/resources/Arabic_verbnet41"); 
        File[] files = avnrepo.listFiles();
        for(File file: files){
            
        if(file.getName().endsWith(".xml")){
       
        Document avnDoc = parseXML(file);
  
                
                Element avnclass = avnDoc.getDocumentElement();
                NodeList members = avnclass.getElementsByTagName("MEMBER");
                for(int i =0; i<members.getLength(); i++){
                    Element membEl = (Element) members.item(i);
                    Member mem = new Member(membEl);
                     switch (cat.name()) {
                         
                         case "ROOT":
                             
                             if(mem.getRoot().equals(entry)){
                                // hostClass = mem.getHostClass();
                                AvnClasses.add(new AvnClass(avnclass));
                                 break;
                             }
                             break;
                         case "VERB":
                             if(removeDiac(mem.getVerb()).equals(entry)){
                                // hostClass = mem.getHostClass();
                                AvnClasses.add(new AvnClass(avnclass));
                                 break;
                             }
                             break;
                         case "DEVERBAL":
                             if(mem.getDeverbals().contains(entry)){
                                // hostClass = mem.getHostClass();
                                AvnClasses.add(new AvnClass(avnclass));
                                 break;
                             }
                             break;
                         case "PARTICIPLE":
                              if(mem.getParticiples().contains(entry)){
                                // hostClass = mem.getHostClass();
                                AvnClasses.add(new AvnClass(avnclass));
                                 break;
                             }
                             break;
                             default:
                        break;
                             
                             
                     }
                        
                }

    }}
    }
    

 
    public void getAllInfo(AvnClass hostClass){
        //if(hostClass.getClassName().equals("VNCLASS")){
        
            System.out.println("====================");
            System.out.println(hostClass.getClassName()+    ": " + hostClass.getClassID());
            
            System.out.println("====================");
            System.out.println("MEMBERS:          " );
            System.out.println("===================="); 
            if(hostClass.hasMembers()){
            ArrayList<Member> members   = hostClass.getMembers();
            for(Member mb : members){
                String member = "MEMBER(VERB(" + mb.getVerb()+ "), "+ "ROOT("+ mb.getRoot() + "), ";
                
                for(String dev: mb.getDeverbals()){
                    member += "DEVERBAL(" + dev + "), " ;
                }
                
                
                for(String part : mb.getParticiples()){
                    member += "PARTICIPLE(" + part + "), ";
                }
                
                member = member.substring(0, member.length()-2);
                System.out.println(member + ")");

            }   }
            
            if(hostClass.hasThemRoles()){
            ArrayList<ThemRole> themRole = hostClass.getThemRoles();
              System.out.println("====================");
               System.out.println("Thematic Roles:");
               System.out.println("====================");
            for(ThemRole tr : themRole){
          
               System.out.print(tr.getTheme());
               if(tr.hasResrictions()){
                   System.out.println(tr.getRestrictions());
               }
              
            }}
            
            if(hostClass.hasFrames()){
                ArrayList<Frame> frames = hostClass.getFrames();
                System.out.println("====================");
                System.out.println("FRAMES: ");
                System.out.println("====================");
                for(Frame fm : frames){
                
                System.out.println("DESCRIPTION:    " + fm.getFrameDescription());
                System.out.println("EXAMPLE:    "+ fm.getExample());
                
                System.out.println("SYNTAX: " + fm.getSyntax());
                System.out.println("SEMANTICS:  "+ fm.getSemantic());
                System.out.println("====================");
                    
                }
                System.out.println("\n\n");
            }
            if(hostClass.hasSubClasses()){
                ArrayList<AvnClass> subCls = hostClass.getSubClasses();
                
                for(AvnClass asc : subCls){
                    getAllInfo(asc);
                }
            }
           
        
    }
    
    
    private String removeDiac(String str){
        return entry.replaceAll("[aiuoNKFًٌٍَُِْ]", str);
    }
 // TODO: implement a method to get  information from buttom to top
    
    

    public Document parseXML(File xmlFile){
        
         try{
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();   
         return  dBuilder.parse(xmlFile);
         
         }
          catch (Exception e) {
         e.printStackTrace();
         return null;
      }
        
    }
    
    
    
    
    public ArrayList<AvnClass> getAvnClasses(){
        return AvnClasses;
    }
     
   
 
    
    public enum SEARCH_BY{
        VERB, DEVERBAL, PARTICIPLE, ROOT
    }

    
        public static void main(String[] args){
        //AvnParser ap = new AvnParser();
        //System.out.println(ap.removeDiac("عَمِلَ"));

        //ArrayList<AvnClass> avnClasses = ap.getAvnClasses();
        //System.out.println(ap.getMatchingClasses().size());
        
       //for(AvnClass e: ks){
         //   ap.getAllInfo(e);
       //}
       // AvnClass vc = new AvnClass(cl.getDocumentElement());
        
        //ap.getAllInfo(vc);
  
    }
}