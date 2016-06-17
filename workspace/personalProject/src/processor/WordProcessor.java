package processor;

@SuppressWarnings("unused")
public final class WordProcessor {

	public static void main(String[] args) {
		//Americans();
		//Arabs();
		Blacks();
	}
	
	private static void Americans () {
		String[] phrases = {"ai","ami","sfa","yank","yankee","sherman","kano","septic","scab","chopper","hamburger",
							"colonial","imperialist","sceptic","merkin","dubya","amerikkkan","americant"};

		for (String phrase : phrases) {
			Analyzer.getInstance().processWord("Amercian",phrase);
		}
	}
		
	private static void Arabs () {
		String[] phrases = {"rab","atta","bara","moor","terrorist","jihad","abdul","zeb","jihadi","bmo","muhammad","jawa"
		  	 	 		   ,"mocha","double d","ewok","osama","ahab","crunchy","fadi","alphabet","fez","habibi","opec","aravi"
		  	 	 		   ,"tusken raider","aladdin","grenade","bin laden","checkpoint","slurpee","cabbie","turco","firecracker"
		  	 	 		   ,"jacker","shit hand","beur","muzzie","abba dabba","rag head","sand nigger"};
		
		for (String phrase : phrases) {
			Analyzer.getInstance().processWord("Arab",phrase);
		}			
	}
	
	private static void Blacks () {
		String[] phrases = {"inky","african","monkey","nitch","naga","coon","yom","jig","boogie","nog","kala","bap","hnic","nigger",
						  	"cuff","feb","colin","shaft","spook","afro","negro","egot","oreo","scat","naha","napa","nigg","bubba",
						  	"naca","fila","jigga","colored","niche","obg","mlk","spade","webe","tnb","dinge","voodoo","zulu","zoot",
						  	"pookie","nft","ni","bfi","kushi","munt","kafir","wog","huxtable","wookie","jar jar","congo","kizzy",
						  	"juba","montu","redbone","sambo","palta","ybm","bdn","tycoon","nigra","bbk","tar baby","mammy","blackie",
						  	"dwb","uncle tom","fubu"};

		for (String phrase : phrases) {
			Analyzer.getInstance().processWord("Black",phrase);
		}			
		
	}
}


