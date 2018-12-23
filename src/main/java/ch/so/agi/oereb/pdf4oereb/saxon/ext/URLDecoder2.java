package ch.so.agi.oereb.pdf4oereb.saxon.ext;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.saxon.s9api.ExtensionFunction;
import net.sf.saxon.s9api.ItemType;
import net.sf.saxon.s9api.OccurrenceIndicator;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.SequenceType;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;

public class URLDecoder2 implements ExtensionFunction {
    Logger log = LoggerFactory.getLogger(URLDecoder2.class);

	@Override
	public QName getName() {
        return new QName("http://oereb.agi.so.ch", "decodeURL");
	}

	@Override
	public SequenceType getResultType() {
        return SequenceType.makeSequenceType(ItemType.ANY_URI, OccurrenceIndicator.ONE);
	}

	@Override
	public SequenceType[] getArgumentTypes() {
        return new SequenceType[] { SequenceType.makeSequenceType(ItemType.ANY_ITEM, OccurrenceIndicator.ONE)};
	}

	@Override
	public XdmValue call(XdmValue[] arguments) throws SaxonApiException {
		XdmNode urlNode = (XdmNode) arguments[0];
		try {
			String decodedUrl = java.net.URLDecoder.decode(urlNode.getStringValue(), "UTF-8");
			URI resultUri = new URI(decodedUrl);
	        return new XdmAtomicValue(resultUri);
		} catch (UnsupportedEncodingException | URISyntaxException e) {
			e.printStackTrace();
			throw new SaxonApiException(e.getMessage());
		}
	}
}
