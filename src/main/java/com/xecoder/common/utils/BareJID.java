package com.xecoder.common.utils;

/**
 * Created by  moxz
 * User: 224911261@qq.com
 * 2015/9/29-11:50
 * HabitServer.com.imakehabits.commons
 */
public class BareJID implements Comparable<BareJID> {

    /** Field description */
    public static final String STRINGPREP_PROCESSOR_PROP_KEY = "stringprep-processor";

    /** Field description */
    public static final String[] STRINGPREP_PROCESSORS = { "libidn", "simple", "empty" };


    //~--- fields ---------------------------------------------------------------

    private final String domain;
    private final String localpart;
    private final String to_string;
    private final int hashcode;

    //~--- constructors ---------------------------------------------------------

    private BareJID(String localpart, String domain) {
        this.localpart = localpart;
        this.domain = domain.toLowerCase().intern();
        this.to_string = toString(this.localpart, this.domain);
        this.hashcode = to_string.toLowerCase().hashCode();
    }

    //~--- methods --------------------------------------------------------------

    /**
     * The method creates an instance of the <code>BareJID</code> class. The
     * bare JID is parsed and constructed from the <code>String</code> parameter.
     * <p/>
     * The localpart (nick name) and the domain part of the JID are parsed and
     * processed through the stringprep. If a strngprep parsing error occurs an
     * exception is thrown.<br/>
     * The resource part, if provided in the JID paramater, is thrown away.
     * <p/>
     * Please note, the method does not necessarily has to return a new instance
     * of the class. It may return the same exact object every time you pass
     * parameters which refer to the same bare JID identifier.
     *
     * @param jid is a <code>String</code> parameter used to create the bare JID
     * instance.
     *
     * @return an instance of the <code>BareJID</code> class which corresponds to
     * the JID given as the parameter.
     *
     */

    /**
     * The method creates an instance of the <code>BareJID</code> class. The
     * bare JID is parsed and constructed from the <code>String</code> parameter.
     * <strong>Note, this method does not perform stringprep processing on input
     * parameters.</strong>
     * <p/>
     * The resource part, if provided in the JID paramater, is thrown away.
     * <p/>
     * Please note, the method does not necessarily has to return a new instance
     * of the class. It may return the same exact object every time you pass
     * parameters which refer to the same bare JID identifier.
     *
     * @param jid is a <code>String</code> parameter used to create the bare JID
     * instance.
     *
     * @return an instance of the <code>BareJID</code> class which corresponds to
     * the JID given as the parameter.
     *
     */
    public static BareJID bareJIDInstanceNS(String jid) {
        String[] parsedJid = parseJID(jid);

        return bareJIDInstanceNS(parsedJid[0], parsedJid[1]);
    }

    /**
     * The method creates an instance of the <code>BareJID</code> class. The
     * bare JID is parsed and constructed from two <code>String</code> parameters.
     * <strong>Note, this method does not perform stringprep processing on input
     * parameters.</strong>
     * <p/>
     * Please note, the method does not necessarily has to return a new instance
     * of the class. It may return the same exact object every time you pass
     * parameters which refer to the same bare JID identifier.
     *
     * @param p_localpart is a <code>String</code> parameter assumed to be a
     * JID localpart (nickname) and used to create the bare JID instance. The
     * localpart parameter can be null.
     * @param p_domain is a <code>String</code> parameter assumed to be a JID
     * domain part and used to create the bare JID instance. This parameter must not
     * be null.
     *
     * @return an instance of the <code>BareJID</code> class which corresponds to
     * the JID given as the parameter.
     *
     */
    public static BareJID bareJIDInstanceNS(String p_localpart, String p_domain) {
        return new BareJID(p_localpart, p_domain);
    }

    /**
     * A utility method to strip the resource part from the given JID string.
     * The method doesn't perform any checkings and it doesn't run stringprep
     * processing on the given parameter. This is a pure string manipulation utility
     * method.
     *
     * @param jid is a <code>String</code> representing user full JID.
     *
     * @return a new <code>String</code> instance of the JID wihout resource part.
     */
    public static String jidToBareJID(String jid) {
        String[] parsed = parseJID(jid);

        return toString(parsed[0], parsed[1]);
    }

    /**
     * A utility method to parse and split the given JID string into separate parts.
     * The result is returned as a three elements' <code>String</code> array:
     * <ol>
     * <li>The first element (index 0) of the array is the JID's localpart
     * (nickname). Can be null.</li>
     * <li>The second element (index 1) of the array is the JID's domain part.</li>
     * <li>The third element (index 2) of the array is the JID's resource part.
     * Can be null.</li>
     * </ol>
     * The method doesn't perform any checkings and it doesn't run stringprep
     * processing on the given parameter. This is a pure string manipulation utility
     * method.
     *
     * @param jid is a <code>String</code> representing user full JID.
     *
     * @return a three element <code>String</code> array with parsed JID parts.
     * The array may contain null elements.
     */
    public static String[] parseJID(String jid) {
        String[] result = new String[3];

        // Cut off the resource part first
        int idx = jid.indexOf('/');

        // Resource part:
        result[2] = ((idx == -1) ? null : jid.substring(idx + 1));

        String id = ((idx == -1) ? jid : jid.substring(0, idx));

        // Parse the localpart and the domain name
        idx = id.indexOf('@');
        result[0] = ((idx == -1) ? null : id.substring(0, idx));
        result[1] = ((idx == -1) ? id : id.substring(idx + 1));

        return result;
    }

    /**
     * A utility method to construct a <code>String</code> representing user JID from
     * given parameters.
     * The method doesn't perform any checkings and it doesn't run stringprep
     * processing on the given parameter. This is a pure string manipulation utility
     * method.
     *
     * @param p_localpart is a JID's localpart (nickname)
     * @param p_domain is a JID's domain part.
     *
     * @return a new <code>String</code> representing user's JID build from given
     * parameters. If the localpart is null then the method simply returns parameter
     * given as domain part.
     */
    public static String toString(String p_localpart, String p_domain) {
        return (((p_localpart != null) && (p_localpart.length() > 0))
                ? (p_localpart + "@" + p_domain) : p_domain);
    }

    /**
     * A utility method to construct a <code>String</code> representing user JID from
     * given parameters.
     * The method doesn't perform any checkings and it doesn't run stringprep
     * processing on the given parameter. This is a pure string manipulation utility
     * method.
     *
     * @param p_localpart is a JID's localpart (nickname)
     * @param p_domain is a JID's domain part.
     * @param p_resource is a JID's resource part.
     *
     * @return a new <code>String</code> representing user's JID build from given
     * parameters. If the localpart and resource part is null then the method simply
     * returns parameter given as domain part.
     */
    public static String toString(String p_localpart, String p_domain, String p_resource) {
        return toString(p_localpart, p_domain)
                + (((p_resource != null) && (p_resource.length() > 0)) ? "/" + p_resource : "");
    }

    /**
     * A utility method to construct a <code>String</code> representing user JID from
     * given parameters.
     * The method doesn't perform any checkings and it doesn't run stringprep
     * processing on the resource parameter.
     *
     * @param bareJid is a <code>BareJID</code> instance.
     * @param p_resource is a JID's resource part.
     *
     * @return a new <code>String</code> representing user's JID build from given
     * parameters.
     */
    public static String toString(BareJID bareJid, String p_resource) {
        return bareJid.toString()
                + (((p_resource != null) && (p_resource.length() > 0)) ? "/" + p_resource : "");
    }

    /**
     * Changes stringprep processor implementation used for the JID checking. The method
     * can be called at any time to change used processor. All subsequent <code>JID</code>
     * and <code>BareJID</code> instances are created using a new processor.
     *
     * @param stringprepProcessor is a <code>String</code> value with stringprep processor
     * name or class name implementing stringprep processing interface.
     */
    /**
     * Method compares the <code>BareJID</code> instance to the object given
     * as a parameter. The method implements the <code>compareTo</code>
     * specification contract and returns values as we would expect from the
     * call:
     * <pre>
     * bareJID_1.toString().compareTo(bareJID_2.toString())
     * </pre>
     *
     * @param o is an <code>BareJID</code> instance with which the comparision
     * is performed.
     *
     * @return values as we would expect from the call:
     * <pre>
     * bareJID_1.toString().compareTo(bareJID_2.toString())
     * </pre>
     */
    @Override
    public int compareTo(BareJID o) {
        return to_string.compareToIgnoreCase(o.to_string);
    }

    /**
     * Method compares whether this <code>BareJID</code> instance represents the
     * same user bare JID as the one given in parameter. It returns
     * <code>true</code> of both the localpart (nickname) and domain part are the
     * same for both objects.
     *
     *
     * @param b is a <code>BareJID</code> object to which the instance is compared.
     *
     * @return a <code>boolean</code> value of <code>true</code> if both instances
     * represent the same bare JID and <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object b) {
        boolean result = false;

        if (b instanceof BareJID) {

            // Intentionally comparing domains by reference instead of value
            // domain is processed through the String.intern() method
            // NOPMD
            result = ( (this.domain == ((BareJID) b).domain || this.domain.equalsIgnoreCase( ( (BareJID) b ).domain ) ) )
                    && ( ( this.localpart == null )
                    ? this.localpart == ( (BareJID) b ).localpart
                    : this.localpart.equalsIgnoreCase( ( (BareJID) b ).localpart ) );
        }

        return result;
    }

    //~--- get methods ----------------------------------------------------------

    /**
     * Method returns a domain part of the <code>BareJID</code>. Please note the
     * <code>String</code> returned is parsed, checked and processed via stringprep,
     * hence it represents a valid domain name as defined in XMPP RFC.
     *
     * @return a domain part of the <code>BareJID</code> instance.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Method returns a localpart (nickname) of the <code>BareJID</code>.
     * Please note the <code>String</code> returned is parsed, checked and processed
     * via stringprep, hence it represents a valid localpart as defined in XMPP RFC.
     *
     * @return a localpart (nickname) of the <code>BareJID</code> instance.
     */
    public String getLocalpart() {
        return localpart;
    }

    //~--- methods --------------------------------------------------------------

    /**
     * Method returns a hash code calculated for the <code>BareJID</code> instance.
     *
     * @return an object hash code.
     */
    @Override
    public int hashCode() {
        return hashcode;
    }

    /**
     * Method returns a <code>String</code> representation of the
     * <code>BareJID</code> instance.
     *
     * @return a <code>String</code> representation of the
     * <code>BareJID</code> instance.
     */
    @Override
    public String toString() {
        return to_string;
    }
}


