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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author jaouad mousser 
 * @mail jaouad.mousser at gmail.com
 */
public class ThemRole {
    
    private Element themeEl = null;
    private String theme = null;
    private boolean hasRestrs = false;
    private String restrs = null;
    private int numnberOfRestr =0;
    public ThemRole(Element themeEl){
        this.themeEl = themeEl;
        if(themeEl!=null){
            theme = themeEl.getAttribute("type");
            Element selRests =  (Element) themeEl.getElementsByTagName("SELRESTRS").item(0);
            if(selRests.hasChildNodes()){
                  if(selRests.hasAttribute("logic")){
                 restrs += selRests.getAttribute("logic");
             }
            hasRestrs = true;
            NodeList selRestChildren = selRests.getElementsByTagName("SELRESTR");
            restrs = "[";
            numnberOfRestr = selRestChildren.getLength();
            for(int i =0; i<selRestChildren.getLength(); i++){
                Element restEle = (Element) selRestChildren.item(i);
                
                restrs += restEle.getAttribute("Value")  + restEle.getAttribute("type") + ", ";
          
            }
            restrs = restrs.substring(0, restrs.length()-2)+"]";
            }
            else{
                theme +="\n";
            }
        }
        
        
    }
    
    public String getTheme(){
        return theme;
    }
    
    public String getRestrictions(){
        return restrs;
    }
    
    public boolean hasResrictions(){
        return hasRestrs;
    }
    
    public int numberOfRestrictions(){
        return numnberOfRestr;
    }
    
}
