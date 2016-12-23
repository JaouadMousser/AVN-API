AVN-API an API that allows to browse throuth Arabic Verbnet http://ling.uni-konstanz.de/pages/home/mousser/files/Arabic_verbnet.php
and extract the needed information.

The verb classes of AVN are represented as xml files with components as MEMBERS, THEMROLES, FRAMES, SUBCLASSES and sibling classes. The API tries to mimic the structure of the xml classes and  their components. 

The class AvnClass.java represent the main AVN class. Each AvnClass is constituted of other lists of other java objects that correspond to the elements listed obove. Thus:

The class Member.java ist a object that represents one member of the avn classes. 

AVN-API an API that allows to browse through Arabic Verbnet http://ling.uni-konstanz.de/pages/home/mousser/files/Arabic_verbnet.php
and extract the needed information.

The verb classes of AVN are represented as xml files with components as MEMBERS, THEMROLES, FRAMES, SUBCLASSES and sibling classes. The API tries to mimic the structure of the xml classes and  their components.

The class AvnClass.java represent the main AVN class. Each AvnClass is constituted of other lists of other java objects that correspond to the elements listed obove. Thus:

The class Member.java ist a object that represents one member of the avn classes.
The class ThemRole.java thematic role object of a avn class
The class Frame.java is a frame object of a avn class

Similar to the avnclass a AvnClass object my have subclasses and siblingclasses.

================================

AVN-API can be accessed in different way:

1. Calling AvnParser class. 
    AvnParser can be called in three different ways:

        a. Calling AvpParser without arguments:
       AvnParser avp = new AvnParser();
          
           This constructor instantiates an Array-list with all AvnClass object. The Array-list is returned by the method:

           avp.getAvnClasses();
   
         b. Calling AvnParser with the a verb entry as argument:
        AvnParser avp = new AvnParser("جلس");
   
          This constructor instantiates an ArrayList with AvnClass objects where the verb "جلس" is found as member. 
	  The list is returned by the method:

             avp.getAvnClasses();

         c. Calling AvnParser with two arguments, an entry like "جلس" and the enum argument that defines the type (POS) of the entry. The enum is called SEARCH_BY and has the arguments: VERB, ROOT, DEVERBAL, PARTICIPLE

         AvnParser avp = new AvnParser("جلس", SEARCH_BY.ROOT);

         This constructor instan tiates an ArrayList with AvnClass ohjects where the verb "جلس" is found as member of the given type. 		 The list is returned by the method:

         avp.getAvnClasses();

////////

Example:

AvnParser avp = new AvnParser();
//AvnParser avp = new AvnParser("جلس")
//AvnParser avp = new AvnParser("جلس", SEARCH_BY.ROOT);
ArrayList < AvnClass > avnClasses = avp.getAvnClasses();
for (AvnClass avc: avnClasses) {
    if (avc.hasMembers()) {
        ArrayList<Member> members = avc.getMembers();
        for (Member mb: members) {
            String member = "MEMBER(VERB(" + mb.getVerb() + "), " + "ROOT(" + mb.getRoot() + "), ";

            for (String dev: mb.getDeverbals()) {
                member += "DEVERBAL(" + dev + "), ";
            }
            for (String part: mb.getParticiples()) {
                member += "PARTICIPLE(" + part + "), ";
            }
            member = member.substring(0, member.length() - 2);
            System.out.println(member + ")");
        }
    }
    if (avc.hasThemRoles()) {
        ArrayList<ThemRole> themRole = avc.getThemRoles();
        for (ThemRole tr: themRole) {
            System.out.print(tr.getTheme());
            if (tr.hasResrictions()) {
                System.out.println(tr.getRestrictions());
            }
        }
    }
    if (avc.hasFrames()) {
        ArrayList <Frame> frames = avc.getFrames();
        for (Frame fm: frames) {
            System.out.println("DESCRIPTION:    " + fm.getFrameDescription());
            System.out.println("EXAMPLE:    " + fm.getExample());
            System.out.println("SYNTAX: " + fm.getSyntax());
            System.out.println("SEMANTICS:  " + fm.getSemantic());
            System.out.println("====================");
        }
    }
}
  

To print out the same information (plus the subclasses), you can simply call the method getAllInfo(AvnClass)

Example:

ArrayList < AvnClass > avnClasses = avp.getAvnClasses();
for (AvnClass avc: avnClasses) {
 ap.getAllInfo(avc);
}


/////




	     



     

