package net.sf.JRecord.zExamples.recordEditorXml.readWrite;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.zTest.Common.TstConstants;

/**
 * Reading / writing files using a RecordEditor-XML copybook
 * 
 * @author Bruce Martin
 *
 */
public final class XmplLineIO7 {


	    /**
	     * Example of
	     * 1) Loading an XML copybook (RecordEditor-Xml)
	     * 2) LineReader / LineWrite classes
	     */
	    private XmplLineIO7() {
	        super();

		    double GST_CONVERSION = 1.1;
	
		    String installDir     = TstConstants.SAMPLE_DIRECTORY;
		    String salesFile      = installDir + "DTAR020.bin";
		    String salesFileOut   = installDir + "DTAR020out.bin";
		    String copybookName   = TstConstants.RE_XML_DIRECTORY
	    					+ "DTAR020.Xml";
	        int lineNum = 0;
	        double gstExclusive;
	        AbstractLine saleRecord;

	        try {
	            CopybookLoader loader = new RecordEditorXmlLoader();
	            LayoutDetail layout = loader.loadCopyBook(copybookName, 0, 0, "", 0, 0, null).asLayoutDetail();
		            
	            AbstractLineReader reader  = LineIOProvider.getInstance().getLineReader(layout);
	            AbstractLineWriter writer  = LineIOProvider.getInstance().getLineWriter(layout);
	            
	            reader.open(salesFile, layout);
	            writer.open(salesFileOut);

	            while ((saleRecord = reader.read()) != null) {
	                lineNum += 1;

	                System.out.print(saleRecord.getFieldValue("KEYCODE-NO").asString()
	                        + " " + saleRecord.getFieldValue("QTY-SOLD").asString()
	                        + " " + saleRecord.getFieldValue("SALE-PRICE").asString());

	                gstExclusive = saleRecord.getFieldValue("SALE-PRICE").asDouble() / GST_CONVERSION;
	                saleRecord.getFieldValue("SALE-PRICE").set(gstExclusive);
	                writer.write(saleRecord);

	                System.out.println(" " + saleRecord.getFieldValue("SALE-PRICE").asString());
	            }

	            reader.close();
	            writer.close();
	        } catch (Exception e) {
	            System.out.println("~~> " + lineNum + " " + e.getMessage());
	            System.out.println();

	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	    	new XmplLineIO7();
	    }
}
