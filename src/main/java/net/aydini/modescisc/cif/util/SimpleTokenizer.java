package net.aydini.modescisc.cif.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */

public class SimpleTokenizer implements Enumeration<String> {

    private int position;

    private int maxPosition;

    private List<String> tokens;

    private String[] names;

    private static final String EMPTY_STRING = "";


    /**
     *
     * @param source    a string to be parsed.
     * @throws NullPointerException if source is null;
     */
    public SimpleTokenizer(String source) {
        this(source, EMPTY_STRING, null);
    }

    /**
     *
     * @param source    a string to be parsed.
     * @param delimiter the delimiters.
     * @throws NullPointerException if source is null;
     */
    public SimpleTokenizer(String source, String delimiter) {
        this(source, delimiter, null);
    }

    /**
     *
     * @param source    a string to be parsed.
     * @param delimiter the delimiters. uses the default delimiter if delimiter is
     *                  null.
     * @param  filterRegex any element matches this regex will be removing from tokens.
     * @throws NullPointerException if source is null;
     */
    public SimpleTokenizer(String source, String delimiter, String filterRegex) {
        position = 0;
        delimiter = delimiter != null ? delimiter : EMPTY_STRING;
        tokens = new ArrayList<String>(Arrays.asList(source.split(delimiter)));
        filterTokens(filterRegex);
        maxPosition = tokens.size();
    }

    private void filterTokens(String filterRegex) {
        if (filterRegex == null)
            return;
        Pattern pattern = Pattern.compile(filterRegex);
        Iterator<String> iterator = tokens.iterator();
        while (iterator.hasNext()) {
            Matcher matcher = pattern.matcher(iterator.next());
            if (matcher.matches())
                iterator.remove();
        }
    }

    /**
     * Tests if there are more tokens available from this tokenizer's string.
     * If this method returns <tt>true</tt>, then a subsequent call to
     * <tt>nextToken</tt> with no argument will successfully return a token.
     *
     * @return  <code>true</code> if and only if there is at least one token
     *          in the string after the current position; <code>false</code>
     *          otherwise.
     */
    public boolean hasMoreElements() {
        return maxPosition > position;
    }

    /**
     * @return     the next token.
     * @throws  NoSuchElementException  if there are no more tokens.
     */
    public String nextElement() {
        if (hasMoreElements())
            return tokens.get(position++).trim();
        throw new NoSuchElementException("no more element available");
    }

    public int countTokens()
    {
        return maxPosition;
    }

    public void setNames(String ... names)
    {
        if(names == null || names.length == 0)
            throw new IllegalArgumentException();
        if((new HashSet<String>(Arrays.asList(names))).size() != names.length)
            throw new IllegalArgumentException();
        if(names.length != tokens.size())
            throw new IllegalArgumentException();
        this.names=names;
    }

    public Boolean getBooleanToken(String name)
    {
       return Boolean.valueOf(getTokenByName(name));
    }

    public String getStringToken(String name)
    {
      return getTokenByName(name);
    }

    public Long getLongToken(String name)
    {
        return Long.valueOf(getTokenByName(name));
    }

    public Integer getIntegerToken(String name)
    {
        return Integer.valueOf(getTokenByName(name));
    }

    public Float getFloatToken(String name)
    {
        return Float.valueOf(getTokenByName(name));
    }

    public Double getDoubleToken(String name)
    {
        return Double.valueOf(getTokenByName(name));
    }

    public Date getDateToken(String name,String format)
    {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(getTokenByName(name));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }


    private String getTokenByName(String name)
    {
        if(!hasNames())
            throw new UnsupportedOperationException("names not already set");
        int index = getIndexOfName(name);
        if(index <0)
            throw new NoSuchElementException();

        return tokens.get(index).trim();
    }

    public boolean hasNames()
    {
        return names != null && names.length >0;
    }


    private int getIndexOfName(String name)
    {
        int index =  0;
        for(String str : names )
        {
            if(str.equals(name))
                return index;
            index++;
        }
        return -1;
    }
}
