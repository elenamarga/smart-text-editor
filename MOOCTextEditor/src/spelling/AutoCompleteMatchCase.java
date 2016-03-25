package spelling;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteMatchCase implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteMatchCase()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		char[] originalCharacters = word.toCharArray();
		boolean flag = addWord(originalCharacters);
		
		boolean capitalized = false;
		char[] allCaps;
		char[] allNoCaps;
		char[] firstCap;
		String wordMinusFirst = word.substring(1);
		String firstLetter = word.substring(0, 1);
		if (firstLetter.toUpperCase().equals(firstLetter) && 
				wordMinusFirst.toLowerCase().equals(wordMinusFirst)) {
			allCaps = word.toUpperCase().toCharArray();
			if (!flag) {
				flag = addWord(allCaps);
			}
		}
		
		if (!capitalized) {
			if (word.toLowerCase().equals(word)) { // no capital letters
				allCaps = word.toUpperCase().toCharArray();
				if (!flag) {
					flag = addWord(allCaps);
				}
				
				firstCap = word.toCharArray();
				firstCap[0] = firstLetter.toUpperCase().charAt(0);
				if (!flag) {
					flag = addWord(firstCap);
				}
			} else if (word.toUpperCase().equals(word)) { // all capital letters
				allNoCaps = word.toLowerCase().toCharArray();
				if (!flag) {
					flag = addWord(allNoCaps);
				}
				
				firstCap = word.toLowerCase().toCharArray();
				firstCap[0] = firstLetter.charAt(0);
				if (!flag) {
					flag = addWord(firstCap);
				}
			} else {
				allCaps = word.toUpperCase().toCharArray();
				if (!flag) {
					flag = addWord(allCaps);
				}
				
				allNoCaps = word.toLowerCase().toCharArray();
				if (!flag) {
					flag = addWord(allNoCaps);
				}
			}
		}
		return flag;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return this.size;
	}
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		char[] characters = s.toCharArray();
		
		TrieNode curr = root;
		for (char c : characters) {
			if (curr.getChild(c) == null) {
				return false;
			} 
			curr = curr.getChild(c);
		}
		if (curr.endsWord()) {
			return true;
		}
		return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	
    	 List<String> suggestedWords = new ArrayList<String>();
    	 String prefixLowerCase = prefix.toLowerCase();
    	 char[] characters = prefixLowerCase.toCharArray();
 		
    	 TrieNode curr = root;
 		 for (char c : characters) {
 			if (curr.getChild(c) == null) {
 				return suggestedWords;
 			} 
 			curr = curr.getChild(c);
 		 }
 		 
 		 List<TrieNode> q = new LinkedList<TrieNode>();
 		 q.add(curr);
 		 while(!q.isEmpty() && suggestedWords.size() < numCompletions) {
 			 TrieNode maybeWord = q.remove(0);
 			 if (maybeWord.endsWord()) {
 				 suggestedWords.add(maybeWord.getText());
 			 }
 			 for (char c : maybeWord.getValidNextCharacters()) {
 				 q.add(maybeWord.getChild(c));
 			 }
 		 }
 		 
         return suggestedWords;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	private boolean addWord(char[] word) {
 		TrieNode curr = root;
		for (char c : word) {
			
			if (curr.getChild(c) == null) {
				curr.insert(c);
			} 
			curr = curr.getChild(c);
		}
		if (curr.endsWord()) {
			return false;
		}	
		curr.setEndsWord(true);
		
		this.size++;
		return true;
 	}
}