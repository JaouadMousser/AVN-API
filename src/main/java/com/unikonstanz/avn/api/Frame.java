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


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author jaouad mousser 
 * @mail jaouad.mousser at gmail.com
 */
public class Frame {
    
    
    private Element FrameEl = null;
    private String Description = null;
    private String Example  = null;
    private String Syntax = null;
    private String Semantic = null;
    
    public Frame(Element FrameEl){
        this.FrameEl = FrameEl;
        
        
        if(FrameEl!=null){
            NodeList frameChildren = FrameEl.getElementsByTagName("*");
            for(int i =0; i<frameChildren.getLength(); i++){
                Element frameChild = (Element) frameChildren.item(i);
                String nodeName = frameChild.getNodeName();
                
            
                switch (nodeName) {
                    case "DESCRIPTION":
                        String primary = frameChild.getAttribute("primary");
                        String secondary = frameChild.getAttribute("secondary");
                        Description = "primary(" +primary + "), secondary(" + secondary +")";
                        break;
                    case "EXAMPLES":
                        Element exEl  = (Element) frameChild.getElementsByTagName("EXAMPLE").item(0);
                        Example = exEl.getAttribute("sentence");
                        break;
                    case "SYNTAX":
                        if(frameChild.hasChildNodes()){
                         Syntax ="";
                        String selRestrs = getSelRestrs(frameChild);
                        NodeList synChildren = frameChild.getElementsByTagName("*");
                        for(int e = 0; e<synChildren.getLength(); e++ ){
                            Element synEl = (Element) synChildren.item(e);
                            String synName = synEl.getNodeName();
                            switch (synName) {
                                case "PREP":
                                    if(synEl.hasAttribute("value")){
                                        Syntax += "{" + synEl.getAttribute("value") + "} ";
                                        if(selRestrs!=null){
                                            Syntax += selRestrs + " ";
                                        }
                                    }
                                    else {
                                        Syntax += "PREP ";
                                        if(selRestrs!=null){ 
                                            Syntax += selRestrs + " ";
                                        }
                                    }   break;
                                case "VERB":
                                    Syntax += "V ";
                                    break;
                                case "NP":
                                    Syntax += synEl.getAttribute("value")+" ";
                                    if(selRestrs!=null){
                                        Syntax += selRestrs +" ";
                                    }   break;
                                default:
                                    break;
                            }
                        }
                        }
                        break;
                    case "SEMANTICS":
                        if(frameChild.hasChildNodes()){
                        Semantic ="";
                        NodeList semChildren = frameChild.getElementsByTagName("PRED");
                        for(int s =0; s<semChildren.getLength(); s++){
                            Element predEl = (Element) semChildren.item(s);
                            Semantic += getPredArgs(predEl) +", ";
                        }
                        Semantic = Semantic.substring(0, Semantic.length()-2)+ ")";
                        }
                        
                        break;
                    default:
                        break;
                }
            }
            
        }
    }
    
    
    
    
    private String getSelRestrs(Element el){
         Element selRests =  (Element) el.getElementsByTagName("SYNRESTRS").item(0);
            String restrs = null;
            if(selRests.hasChildNodes()){
            NodeList selRestChildren = selRests.getElementsByTagName("SYNRESTR");
            restrs = "<";
            for(int i =0; i<selRestChildren.getLength(); i++){
                Element restEle = (Element) selRestChildren.item(i);
                
                restrs += restEle.getAttribute("Value") + " " + restEle.getAttribute("type") + ", ";
          
            }
            restrs +=">".replace(", >", ">");
    }
            return restrs;
    }
    

    private String getPredArgs(Element PredEl){
        
        String predArg = PredEl.getAttribute("value");
        Element args = (Element) PredEl.getElementsByTagName("ARGS").item(0);
        NodeList argEls = args.getElementsByTagName("ARG");
        if(argEls.getLength()>=1){
            predArg +="(";
        for(int i = 0; i<argEls.getLength();i++){
            Element argEl = (Element) argEls.item(i);
            predArg += argEl.getAttribute("value") + ", " ;
    }
        predArg  = predArg.substring(0, predArg.length()-2);
        }
        if(PredEl.hasAttribute("bool")){
            if("!".equals(PredEl.getAttribute("bool"))){
                predArg = "NOT(" + predArg + ")";
            }
        }
        
        return predArg + ")";
        
    }
    
    public String getFrameDescription(){
        return Description;
    }
    
    public String getExample(){
        return Example;
    }
    
    public String getSyntax(){
        return Syntax;
    }
    
    public String getSemantic(){
        return Semantic;
    }
    
}
